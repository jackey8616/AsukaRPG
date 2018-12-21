package info.clo5de.asuka.rpg;

import com.codingforcookies.armorequip.ArmorListener;
import info.clo5de.asuka.rpg.event.ExEnchantListener;
import info.clo5de.asuka.rpg.event.PlayerListener;
import info.clo5de.asuka.rpg.event.WorkbenchCraftingListener;
import info.clo5de.asuka.rpg.item.ItemHandler;
import info.clo5de.asuka.rpg.recipe.Handler;
import info.clo5de.asuka.rpg.utils.Metrics;
import info.clo5de.asuka.rpg.version.v1_12_R1.item.ItemNMSHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class AsukaRPG extends JavaPlugin {

    public static final Logger logger = Logger.getLogger("Minecraft.Asuka.RPG");
    public static Random random = new Random();
    public static AsukaRPG INSTANCE;
    public static String version;

    private ArmorListener armorListener;

    private ConfigManager configManager;
    private ItemHandler itemHandler;
    private Handler recipeHandler;
    private info.clo5de.asuka.rpg.player.Handler playerHandler;

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

        this.fitVersion();
        this.configManager = new ConfigManager(this);;
        this.recipeHandler = new Handler(this);
        this.playerHandler = new info.clo5de.asuka.rpg.player.Handler(this);

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

    private void fitVersion () {
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

            if (version.equals("v1_13_R1")) {
                this.itemHandler = new ItemHandler(this,
                        new info.clo5de.asuka.rpg.version.v1_13_R1.item.ItemNMSHandler());
            } else if (version.equals("v1_12_R1")) {
                this.itemHandler = new ItemHandler(this,
                        new ItemNMSHandler());
            } else if (version.equals("v1_11_R1")) {
                this.itemHandler = new ItemHandler(this,
                        new info.clo5de.asuka.rpg.version.v1_11_R1.item.ItemNMSHandler());
            } else {
                this.itemHandler = new ItemHandler(this,
                        new info.clo5de.asuka.rpg.version.v1_13_R1.item.ItemNMSHandler());
            }
        } catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
            //Bukkit.getPluginManager().disablePlugin(this);
            //Bukkit.getServer().shutdown();
            this.itemHandler = new ItemHandler(this,
                    new ItemNMSHandler());
        }
    }

    public void setMetrics () {
        new Metrics(this);
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(this.armorListener, this);
        pm.registerEvents(this.playerListener, this);
        pm.registerEvents(this.workbenchCraftingListener, this);
        pm.registerEvents(this.exEnchantListener, this);
    }

    public Logger logger () {
        return logger;
    }

    public ConfigManager getConfigManager () {
        return this.configManager;
    }

    public info.clo5de.asuka.rpg.player.Handler getPlayerHandler () {
        return this.playerHandler;
    }

    public ItemHandler getItemHandler () {
        return this.itemHandler;
    }

    public Handler getRecipeHandler () {
        return this.recipeHandler;
    }

}
