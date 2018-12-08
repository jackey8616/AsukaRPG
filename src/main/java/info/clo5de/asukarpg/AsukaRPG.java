package info.clo5de.asukarpg;

import info.clo5de.asukarpg.event.ItemListener;
import info.clo5de.asukarpg.utils.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class AsukaRPG extends JavaPlugin {

    public static final Logger logger = Logger.getLogger("Minecraft.AsukaRPG");
    public static AsukaRPG INSTANCE;

    private Metrics metrics;
    private ConfigManager configManager;
    private info.clo5de.asukarpg.item.Handler itemHandler;
    private info.clo5de.asukarpg.recipes.Handler recipeHandler;

    private ItemListener itemListener;

    @Override
    public void onEnable () {
        this.INSTANCE = this;
        this.metrics = new Metrics(this);
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

    public ConfigManager getConfigManager () {
        return this.configManager;
    }
    public info.clo5de.asukarpg.item.Handler getItemHandler () {
        return this.itemHandler;
    }
    public info.clo5de.asukarpg.recipes.Handler getRecipeHandler () {
        return this.recipeHandler;
    }
    public ItemListener getItemListener () {
        return this.itemListener;
    }

}
