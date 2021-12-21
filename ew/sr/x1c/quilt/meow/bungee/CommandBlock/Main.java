package ew.sr.x1c.quilt.meow.bungee.CommandBlock;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin {

    private static Configuration config;
    private static File pluginFolder;

    public static Configuration getConfig() {
        return config;
    }

    public static File getPluginFolder() {
        return pluginFolder;
    }

    @Override
    public void onEnable() {
        Main.pluginFolder = getDataFolder();
        if (!pluginFolder.exists()) {
            pluginFolder.mkdir();
        }

        File file = new File(pluginFolder, "config.yml");
        if (!file.exists()) {
            try (InputStream is = getResourceAsStream("config.yml")) {
                Files.copy(is, file.toPath(), new CopyOption[0]);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        initConfig();

        PluginManager pm = getProxy().getPluginManager();
        pm.registerListener(this, new ChatListener());
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static Configuration initConfig() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(pluginFolder, "config.yml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return config;
    }
}
