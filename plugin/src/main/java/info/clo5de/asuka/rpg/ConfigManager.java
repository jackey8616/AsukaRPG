package info.clo5de.asuka.rpg;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private AsukaRPG plugin;
    private FileConfiguration config;

    private String logLevel;
    private String lang;

    public ConfigManager (AsukaRPG plugin) {
        this.plugin = plugin;
    }

    public void load () {
        this.config = this.plugin.getConfig();
        this.config.options().copyDefaults(true);
        this.plugin.saveConfig();

        this.logLevel = this.config.getString("LogLevel", "INFO");
        //this.plugin.logger().setLevel(Level.toLevel(this.logLevel));
        this.lang = this.config.getString("Lang", "en");
    }

}
