package ew.sr.x1c.quilt.meow.bungee.CommandBlock;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBungeeChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        String target = player.getServer().getInfo().getName();
        if (player.hasPermission("bungee.command.bypass.global") || player.hasPermission("bungee.command.bypass." + target)) {
            return;
        }

        String message = event.getMessage();
        if (message.length() > 1) {
            String[] arg = message.split(" ");
            message = arg[0];
        }

        boolean limit = shouldBlock(message, target);
        if (limit) {
            player.sendMessage(new TextComponent(Main.getConfig().getString("message").replaceAll("&", "§")));
            event.setCancelled(true);
        }
    }

    public static boolean shouldBlock(String message, String target) {
        Configuration config = Main.getConfig();
        for (String command : config.getStringList("server." + target)) {
            command = "/" + command;
            if (command.equalsIgnoreCase(message)) {
                return true;
            }
        }
        for (String command : config.getStringList("server.global")) {
            command = "/" + command;
            if (command.equalsIgnoreCase(message)) {
                return true;
            }
        }
        return false;
    }
}
