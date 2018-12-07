package info.clo5de.asukarpg;

import info.clo5de.asukarpg.event.ItemListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class AsukaRPG extends JavaPlugin {

    public static final Logger logger = Logger.getLogger("Minecraft.AsukaRPG");
    public static AsukaRPG INSTANCE;

    public ConfigManager configManager;
    public info.clo5de.asukarpg.item.Handler itemHandler;
    public info.clo5de.asukarpg.recipes.Handler recipeHandler;

    public ItemListener itemListener;

    @Override
    public void onEnable () {
        this.INSTANCE = this;
        this.configManager = new ConfigManager(this);
        this.itemHandler = new info.clo5de.asukarpg.item.Handler(this);
        this.recipeHandler = new info.clo5de.asukarpg.recipes.Handler(this);

        this.configManager.load();
        this.itemHandler.load();
        this.recipeHandler.loadItemRecipes();

        this.itemListener = new ItemListener(this);
    }

    @Override
    public void onDisable () {  }

}
