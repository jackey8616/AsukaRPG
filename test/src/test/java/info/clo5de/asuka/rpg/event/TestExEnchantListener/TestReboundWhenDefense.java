package info.clo5de.asuka.rpg.event.TestExEnchantListener;

import info.clo5de.asuka.rpg.AsukaRPG;
import info.clo5de.asuka.rpg.TestAsukaRPGBuilder;
import info.clo5de.asuka.rpg.event.ExEnchantListener;
import info.clo5de.asuka.rpg.item.ExEnchant;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemFactory;
import org.bukkit.entity.LivingEntity;
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
public class TestReboundWhenDefense {

    private static TestAsukaRPGBuilder builder = new TestAsukaRPGBuilder();
    private static AsukaRPG asukaRPG;
    private static ExEnchantListener eel;

    private static double damage;
    private static LivingEntity attacker;
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
        attacker = mock(LivingEntity.class);

        mockMap = spy(new HashMap<>());
        mockRD = spy(new ExEnchant("REBOUND_DAMAGE", 1.0D, 10.0D));
        mockRDP = spy(new ExEnchant("REBOUND_DAMAGE_P", 1.0D, 10.0D));

    }

    @Test
    public void testReboundDamage () {
        mockMap.put("REBOUND_DAMAGE", mockRD);
        when(mockRD.isTriggered()).thenReturn(true);
        when(mockMap.containsKey("REBOUND_DAMAGE")).thenReturn(true);
        eel.reboundWhenDefense(attacker, damage, mockMap);
        verify(attacker, Mockito.times(1)).damage(mockRD.getEffect());

        resetAll();
        mockMap.put("REBOUND_DAMAGE", mockRD);
        when(mockMap.containsKey("REBOUND_DAMAGE")).thenReturn(false);
        eel.reboundWhenDefense(attacker, damage, mockMap);
        verify(attacker, Mockito.never()).damage(Mockito.anyDouble());

        resetAll();
        mockMap.put("REBOUND_DAMAGE", mockRD);
        when(mockRD.isTriggered()).thenReturn(false);
        when(mockMap.containsKey("REBOUND_DAMAGE")).thenReturn(true);
        eel.reboundWhenDefense(attacker, damage, mockMap);
        verify(attacker, Mockito.never()).damage(Mockito.anyDouble());

        resetAll();
        mockMap.put("REBOUND_DAMAGE", mockRD);
        when(mockMap.containsKey("REBOUND_DAMAGE")).thenReturn(false);
        eel.reboundWhenDefense(attacker, damage, mockMap);
        verify(attacker, Mockito.never()).damage(Mockito.anyDouble());
    }

    @Test
    public void testReboundDamageP () {
        mockMap.put("REBOUND_DAMAGE_P", mockRDP);
        when(mockRDP.isTriggered()).thenReturn(true);
        when(mockMap.containsKey("REBOUND_DAMAGE_P")).thenReturn(true);
        eel.reboundWhenDefense(attacker, damage, mockMap);
        verify(attacker, Mockito.times(1)).damage(damage * mockRDP.getEffect());

        resetAll();
        mockMap.put("REBOUND_DAMAGE_P", mockRDP);
        when(mockMap.containsKey("REBOUND_DAMAGE_P")).thenReturn(false);
        eel.reboundWhenDefense(attacker, damage, mockMap);
        verify(attacker, Mockito.never()).damage(Mockito.anyDouble());

        resetAll();
        mockMap.put("REBOUND_DAMAGE_P", mockRDP);
        when(mockRDP.isTriggered()).thenReturn(false);
        when(mockMap.containsKey("REBOUND_DAMAGE_P")).thenReturn(true);
        eel.reboundWhenDefense(attacker, damage, mockMap);
        verify(attacker, Mockito.never()).damage(Mockito.anyDouble());

        resetAll();
        mockMap.put("REBOUND_DAMAGE_P", mockRDP);
        when(mockMap.containsKey("REBOUND_DAMAGE_P")).thenReturn(false);
        eel.reboundWhenDefense(attacker, damage, mockMap);
        verify(attacker, Mockito.never()).damage(Mockito.anyDouble());
    }
}
