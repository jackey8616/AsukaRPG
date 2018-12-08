package info.clo5de.asukarpg;

import info.clo5de.asukarpg.event.ItemListener;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Logger;

import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PluginDescriptionFile.class, JavaPluginLoader.class })
@PowerMockIgnore({"javax.management.*"})
public class TestAsukaRPG {


    public static final File pluginDirectory = new File("bin/test/server/plugins/AsukaRPG-Test");
    public static final File serverDirectory = new File("bin/test/server");

    private static Server mockServer;
    private static PluginDescriptionFile pdf;
    private static JavaPluginLoader mockJPL;
    private static AsukaRPG asukaRPG;
    private static ConfigManager configManager = mock(ConfigManager.class);
    private static info.clo5de.asukarpg.item.Handler itemHandler = mock(info.clo5de.asukarpg.item.Handler.class);
    private static info.clo5de.asukarpg.recipe.Handler recipeHandler = mock(info.clo5de.asukarpg.recipe.Handler.class);
    private static ItemListener itemListener = mock(ItemListener.class);

    @BeforeClass
    public static void setupBeforeClass() throws Exception {
        // Server mock
        mockServer = mock(Server.class);
        when(mockServer.getName()).thenReturn("TestBukkit");
        // PluginDescriptionFile mock
        pdf = spy(new PluginDescriptionFile("AsukaRPG", "0.0.1",
                "info.clo5de.asukarpg.AsukaRPG"));
        when(pdf.getAuthors()).thenReturn(new ArrayList<>());
        // JavaPluginLoader  mock
        mockJPL = mock(JavaPluginLoader.class);
        Whitebox.setInternalState(mockJPL, "server", mockServer);
        // Logger mock
        when(mockServer.getLogger()).thenReturn(Logger.getLogger("Minecraft.Asuka.RPG"));
        // AsukaRPG init
        asukaRPG = spy(new AsukaRPG(mockJPL, pdf, pluginDirectory, new File(pluginDirectory, "testPluginFile")));
        doAnswer(answer -> null).when(asukaRPG).setMetrics();
        // Declared fields
        declaredFields();
        // Replace bukkit server.
        Bukkit.setServer(mockServer);
    }

    public static void declaredFields () throws Exception {

        configManager = spy(new ConfigManager(asukaRPG));
        Field configManagerField = AsukaRPG.class.getDeclaredField("configManager");
        configManagerField.setAccessible(true);
        configManagerField.set(asukaRPG, configManager);

        itemHandler = spy(new info.clo5de.asukarpg.item.Handler(asukaRPG));
        Field itemHandlerField = AsukaRPG.class.getDeclaredField("itemHandler");
        itemHandlerField.setAccessible(true);
        itemHandlerField.set(asukaRPG, itemHandler);

        recipeHandler = spy(new info.clo5de.asukarpg.recipe.Handler(asukaRPG));
        Field recipeHandlerField = AsukaRPG.class.getDeclaredField("recipeHandler");
        recipeHandlerField.setAccessible(true);
        recipeHandlerField.set(asukaRPG, recipeHandler);

        itemListener = spy(new ItemListener(asukaRPG));
        Field itemListenerField = AsukaRPG.class.getDeclaredField("itemListener");
        itemListenerField.setAccessible(true);
        itemListenerField.set(asukaRPG, itemListener);
    }

    @Test
    public void testOnEnable () {
        asukaRPG.onEnable();
    }

    @Test
    public void testOnDisable () {
        asukaRPG.onDisable();
    }
}
