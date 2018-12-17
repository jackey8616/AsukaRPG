package info.clo5de.asukarpg;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private AsukaRPG plugin;
    private FileConfiguration config;

    public ConfigManager (AsukaRPG plugin) {
        this.plugin = plugin;
    }

    public void load () {
        this.config = this.plugin.getConfig();
        this.config.options().copyDefaults(true);
        this.plugin.saveConfig();
    }

}
