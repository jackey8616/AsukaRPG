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
public class TestReduceWhenDefense {

    private static TestAsukaRPGBuilder builder = new TestAsukaRPGBuilder();
    private static AsukaRPG asukaRPG;
    private static ExEnchantListener eel;
    private static EntityDamageByEntityEvent edbee;

    private static double damage;
    private static Map<String, ExEnchant> mockMap;
    private static ExEnchant mockRD, mockRDP;

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
        mockRD = spy(new ExEnchant("REDUCE_DAMAGE", 1.0D, 10.0D));
        mockRDP = spy(new ExEnchant("REDUCE_DAMAGE_P", 1.0D, 10.0D));
    }

    @Test
    public void testReduceDamage () {
        mockMap.put("REDUCE_DAMAGE", mockRD);
        when(mockRD.isTriggered()).thenReturn(true);
        when(mockMap.containsKey("REDUCE_DAMAGE")).thenReturn(true);
        eel.reduceWhenDefense(edbee, damage, mockMap);
        verify(mockRD, Mockito.times(1)).getEffect();
        verify(edbee, Mockito.times(1)).setDamage(damage - mockRD.getEffect());

        resetAll();
        mockMap.put("REDUCE_DAMAGE", mockRD);
        when(mockMap.containsKey("REDUCE_DAMAGE")).thenReturn(false);
        eel.reduceWhenDefense(edbee, damage, mockMap);
        verify(mockRD, Mockito.never()).getEffect();
        verify(edbee, Mockito.never()).setDamage(Mockito.anyDouble());

        resetAll();
        mockMap.put("REDUCE_DAMAGE", mockRD);
        when(mockRD.isTriggered()).thenReturn(false);
        when(mockMap.containsKey("REDUCE_DAMAGE")).thenReturn(true);
        eel.reduceWhenDefense(edbee, damage, mockMap);
        verify(mockRD, Mockito.never()).getEffect();
        verify(edbee, Mockito.never()).setDamage(Mockito.anyDouble());

        resetAll();
        mockMap.put("REDUCE_DAMAGE", mockRD);
        when(mockMap.containsKey("REDUCE_DAMAGE")).thenReturn(false);
        eel.reduceWhenDefense(edbee, damage, mockMap);
        verify(mockRD, Mockito.never()).getEffect();
        verify(edbee, Mockito.never()).setDamage(Mockito.anyDouble());
    }

    @Test
    public void testReduceDamageP () {
        mockMap.put("REDUCE_DAMAGE_P", mockRDP);
        when(mockRDP.isTriggered()).thenReturn(true);
        when(mockMap.containsKey("REDUCE_DAMAGE_P")).thenReturn(true);
        eel.reduceWhenDefense(edbee, damage, mockMap);
        verify(mockRDP, Mockito.times(1)).getEffect();
        verify(edbee, Mockito.times(1)).setDamage(damage * (1.0D - mockRDP.getEffect()));

        resetAll();
        mockMap.put("REDUCE_DAMAGE_P", mockRDP);
        when(mockMap.containsKey("REDUCE_DAMAGE_P")).thenReturn(false);
        eel.reduceWhenDefense(edbee, damage, mockMap);
        verify(mockRDP, Mockito.never()).getEffect();
        verify(edbee, Mockito.never()).setDamage(Mockito.anyDouble());

        resetAll();
        mockMap.put("REDUCE_DAMAGE_P", mockRDP);
        when(mockRDP.isTriggered()).thenReturn(false);
        when(mockMap.containsKey("REDUCE_DAMAGE_P")).thenReturn(true);
        eel.reduceWhenDefense(edbee, damage, mockMap);
        verify(mockRDP, Mockito.never()).getEffect();
        verify(edbee, Mockito.never()).setDamage(Mockito.anyDouble());

        resetAll();
        mockMap.put("REDUCE_DAMAGE_P", mockRDP);
        when(mockMap.containsKey("REDUCE_DAMAGE_P")).thenReturn(false);
        eel.reduceWhenDefense(edbee, damage, mockMap);
        verify(mockRDP, Mockito.never()).getEffect();
        verify(edbee, Mockito.never()).setDamage(Mockito.anyDouble());
    }
}
