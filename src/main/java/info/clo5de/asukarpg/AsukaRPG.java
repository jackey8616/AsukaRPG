package info.clo5de.asukarpg;

import info.clo5de.asukarpg.event.WorkbenchCraftingListener;
import info.clo5de.asukarpg.utils.Metrics;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.util.logging.Logger;

public class AsukaRPG extends JavaPlugin {

    public static final Logger logger = Logger.getLogger("Minecraft.Asuka.RPG");
    public static AsukaRPG INSTANCE;

    private ConfigManager configManager = new ConfigManager(this);
    //private info.clo5de.asukarpg.feature.event.player.Handler playerHandler = new info.clo5de.asukarpg.feature.event.player.Handler(this);
    private info.clo5de.asukarpg.item.Handler itemHandler = new info.clo5de.asukarpg.item.Handler(this);
    private info.clo5de.asukarpg.recipe.Handler recipeHandler = new info.clo5de.asukarpg.recipe.Handler(this);

    //private PlayerListener playerListener = new PlayerListener(this, playerHandler);
    private WorkbenchCraftingListener workbenchCraftingListener = new WorkbenchCraftingListener(this);
    //private WorkbenchCraftListener workbenchCraftListener = new WorkbenchCraftListener(this, playerHandler);

    public AsukaRPG () {
        super();
    }

    public AsukaRPG(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable () {
        this.INSTANCE = this;
        this.setMetrics();
        this.registerEvents();

        this.configManager.load();
        this.itemHandler.load();
        this.recipeHandler.loadItemRecipes();
    }

    @Override
    public void onDisable () {  }

    public void setMetrics () {
        Metrics metrics = new Metrics(this);
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        //pm.registerEvents(this.playerListener, this);
        pm.registerEvents(this.workbenchCraftingListener, this);
        //pm.registerEvents(this.workbenchCraftListener, this);
    }

    public ConfigManager getConfigManager () {
        return this.configManager;
    }

    public info.clo5de.asukarpg.item.Handler getItemHandler () {
        return this.itemHandler;
    }

    public info.clo5de.asukarpg.recipe.Handler getRecipeHandler () {
        return this.recipeHandler;
    }

}
