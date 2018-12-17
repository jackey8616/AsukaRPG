package info.clo5de.asukarpg.event.TestExEnchantListener;

import info.clo5de.asukarpg.AsukaRPG;
import info.clo5de.asukarpg.TestAsukaRPGBuilder;
import info.clo5de.asukarpg.event.ExEnchantListener;
import info.clo5de.asukarpg.item.ExEnchant;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemFactory;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CraftItemFactory.class, PluginDescriptionFile.class, JavaPluginLoader.class })
@PowerMockIgnore({"javax.management.*"})
public class TestRemovalWhenDefense {

    private static TestAsukaRPGBuilder builder = new TestAsukaRPGBuilder();
    private static AsukaRPG asukaRPG;
    private static ExEnchantListener eel;
    private static EntityDamageByEntityEvent edbee;

    private static double damage;
    private static Map<String, ExEnchant> mockMap;
    private static ExEnchant mockRD;

    @BeforeClass
    public static void setup () throws Exception {
        asukaRPG = builder.getInstance();
        eel = new ExEnchantListener(asukaRPG);
        new TestReduceWhenDefense().resetAll();
    }

    @Before
    public void resetAll () {
        damage = 10.0D;
        edbee = mock(EntityDamageByEntityEvent.class);

        mockMap = spy(new HashMap<>());
        mockRD = spy(new ExEnchant("REMOVAL_DAMAGE", 1.0D));
        mockMap.put("REMOVAL_DAMAGE", mockRD);
    }

    @Test
    public void testRemovalWhenDefense () {
        when(mockRD.isTriggered()).thenReturn(true);
        when(mockMap.containsKey("REMOVAL_DAMAGE")).thenReturn(true);
        eel.removalWhenDefense(edbee, damage, mockMap);
        verify(edbee, Mockito.times(1)).setDamage(0.0D);

        resetAll();
        when(mockMap.containsKey("REMOVAL_DAMAGE")).thenReturn(false);
        eel.removalWhenDefense(edbee, damage, mockMap);
        verify(edbee, Mockito.never()).setDamage(Mockito.anyDouble());

        resetAll();
        when(mockRD.isTriggered()).thenReturn(false);
        when(mockMap.containsKey("REMOVAL_DAMAGE")).thenReturn(true);
        eel.removalWhenDefense(edbee, damage, mockMap);
        verify(edbee, Mockito.never()).setDamage(Mockito.anyDouble());

        resetAll();
        when(mockMap.containsKey("REMOVAL_DAMAGE")).thenReturn(false);
        eel.removalWhenDefense(edbee, damage, mockMap);
        verify(edbee, Mockito.never()).setDamage(Mockito.anyDouble());
    }
}
