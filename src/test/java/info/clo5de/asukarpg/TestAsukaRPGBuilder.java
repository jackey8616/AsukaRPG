package info.clo5de.asukarpg;

import info.clo5de.asukarpg.event.ItemListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemFactory;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Logger;

import static org.powermock.api.mockito.PowerMockito.*;

public class TestAsukaRPGBuilder {


    private static boolean inited = false;
    public static final File pluginDirectory = new File("bin/test/server/plugins/AsukaRPG-Test");
    public final File serverDirectory = new File("bin/test/server");

    private Server mockServer;
    private ItemFactory mockItemFactory;
    private ItemMeta mockMeta;
    private PluginDescriptionFile pdf;
    private JavaPluginLoader mockJPL;
    private AsukaRPG asukaRPG;
    private ConfigManager configManager = mock(ConfigManager.class);
    private info.clo5de.asukarpg.item.Handler itemHandler = mock(info.clo5de.asukarpg.item.Handler.class);
    private info.clo5de.asukarpg.recipe.Handler recipeHandler = mock(info.clo5de.asukarpg.recipe.Handler.class);
    private ItemListener itemListener = mock(ItemListener.class);

    public void setup () throws Exception {
        if (inited)
            return;
        // Server mock
        mockServer = mock(Server.class);
        mockItemFactory = mock(CraftItemFactory.class);
        mockMeta = mock(ItemMeta.class);
        when(mockItemFactory.getItemMeta(Mockito.any(Material.class))).thenReturn(mockMeta);
        when(mockServer.getName()).thenReturn("TestBukkit");
        when(mockServer.getLogger()).thenReturn(Logger.getLogger("Minecraft.Asuka.RPG"));
        // PluginDescriptionFile mock
        pdf = spy(new PluginDescriptionFile("AsukaRPG", "0.0.1",
                "info.clo5de.asukarpg.AsukaRPG"));
        when(pdf.getAuthors()).thenReturn(new ArrayList<>());
        // JavaPluginLoader  mock
        mockJPL = mock(JavaPluginLoader.class);
        Whitebox.setInternalState(mockJPL, "server", mockServer);

        // AsukaRPG init
        asukaRPG = spy(new AsukaRPG(mockJPL, pdf, pluginDirectory, new File(pluginDirectory, "testPluginFile")));
        doAnswer(answer -> null).when(asukaRPG).setMetrics();
        // Patch everything have to mock
        declaredFields();

        PluginManager mockPluginManager = PowerMockito.mock(PluginManager.class);
        // Set others for server.
        when(mockServer.getPluginManager()).thenReturn(mockPluginManager);
        when(mockServer.getItemFactory()).thenReturn(mockItemFactory);

        asukaRPG.onEnable();
        inited = true;
    }

    public void teardown () {
        if (!inited)
            return;
        asukaRPG.onDisable();
        inited = false;
    }

    private void setFields (Object parent, Class<?> parentClass, String name, Object value) throws Exception {
        Field field = parentClass.getDeclaredField(name);
        field.setAccessible(true);
        field.set(parent, value);
    }

    private void declaredFields () throws Exception {
        // Server instance
        setFields(null, Bukkit.class, "server", mockServer);
        setFields(null, CraftItemFactory.class, "instance", mockItemFactory);
        // RPG instance
        configManager = spy(new ConfigManager(asukaRPG));
        setFields(asukaRPG, AsukaRPG.class, "configManager", configManager);
        itemHandler = spy(new info.clo5de.asukarpg.item.Handler(asukaRPG));
        setFields(asukaRPG, AsukaRPG.class, "itemHandler", itemHandler);
        recipeHandler = spy(new info.clo5de.asukarpg.recipe.Handler(asukaRPG));
        setFields(asukaRPG, AsukaRPG.class, "recipeHandler", recipeHandler);
        itemListener = spy(new ItemListener(asukaRPG));
        setFields(asukaRPG, AsukaRPG.class, "itemListener", itemListener);

    }

    public AsukaRPG getInstance () throws Exception {
        if (!inited)
            setup();
        return asukaRPG;
    }

    public Server getServer () throws Exception {
        if (!inited)
            setup();
        return mockServer;
    }

}
