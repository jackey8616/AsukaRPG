package info.clo5de.asukarpg;

import com.codingforcookies.armorequip.ArmorListener;
import info.clo5de.asukarpg.event.ExEnchantListener;
import info.clo5de.asukarpg.event.PlayerListener;
import info.clo5de.asukarpg.event.WorkbenchCraftingListener;
import info.clo5de.asukarpg.utils.Metrics;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

public class AsukaRPG extends JavaPlugin {

    public static final Logger logger = Logger.getLogger("Minecraft.Asuka.RPG");
    public static AsukaRPG INSTANCE;

    private ArmorListener armorListener;

    private ConfigManager configManager;
    private info.clo5de.asukarpg.item.Handler itemHandler;
    private info.clo5de.asukarpg.recipe.Handler recipeHandler;
    private info.clo5de.asukarpg.player.Handler playerHandler;

    private PlayerListener playerListener;
    private WorkbenchCraftingListener workbenchCraftingListener;
    private ExEnchantListener exEnchantListener;

    public AsukaRPG () {
        super();
    }

    public AsukaRPG(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable () {
        this.INSTANCE = this;
        this.armorListener = new ArmorListener(new ArrayList<>());

        this.configManager = new ConfigManager(this);;
        this.itemHandler = new info.clo5de.asukarpg.item.Handler(this);
        this.recipeHandler = new info.clo5de.asukarpg.recipe.Handler(this);
        this.playerHandler = new info.clo5de.asukarpg.player.Handler(this);

        this.playerListener = new PlayerListener(this);
        this.workbenchCraftingListener = new WorkbenchCraftingListener(this);
        this.exEnchantListener = new ExEnchantListener(this);

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

        pm.registerEvents(this.armorListener, this);
        pm.registerEvents(this.playerListener, this);
        pm.registerEvents(this.workbenchCraftingListener, this);
        pm.registerEvents(this.exEnchantListener, this);
    }

    public ConfigManager getConfigManager () {
        return this.configManager;
    }

    public info.clo5de.asukarpg.player.Handler getPlayerHandler () {
        return this.playerHandler;
    }

    public info.clo5de.asukarpg.item.Handler getItemHandler () {
        return this.itemHandler;
    }

    public info.clo5de.asukarpg.recipe.Handler getRecipeHandler () {
        return this.recipeHandler;
    }

}
