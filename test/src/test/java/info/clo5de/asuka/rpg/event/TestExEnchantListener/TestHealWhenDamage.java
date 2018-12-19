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
public class TestHealWhenDamage {

    private static TestAsukaRPGBuilder builder = new TestAsukaRPGBuilder();
    private static AsukaRPG asukaRPG;
    private static ExEnchantListener eel;

    private static double damage;
    private static LivingEntity attacker, defender;
    private static Map<String, ExEnchant> mockMap;
    private static ExEnchant mockRHH, mockRHPH, mockRHV;

    @BeforeClass
    public static void setup () throws Exception {
        asukaRPG = builder.getInstance();
        eel = new ExEnchantListener(asukaRPG);
        new TestHealWhenDamage().resetAll();
    }

    @Before
    public void resetAll () {
        damage = 10.0D;
        attacker = mock(LivingEntity.class);
        defender = mock(LivingEntity.class);

        when(attacker.getHealth()).thenReturn(20.0D);
        when(attacker.getMaxHealth()).thenReturn(20.0D);
        when(defender.getHealth()).thenReturn(20.0D);
        when(defender.getMaxHealth()).thenReturn(20.0D);

        mockMap = spy(new HashMap<>());
        mockRHH = spy(new ExEnchant("RESTORED_HP_HIT", 1.0D, 10.0D));
        mockRHPH = spy(new ExEnchant("RESTORED_HP_P_HIT", 1.0D, 10.0D));
        mockRHV = spy(new ExEnchant("RESTORED_HP_VAMPIRE", 1.0D, 10.0D));
    }

    @Test
    public void testRestoredHpHit () {

        mockMap.put("RESTORED_HP_HIT", mockRHH);
        when(mockRHH.isTriggered()).thenReturn(true);
        when(mockMap.containsKey("RESTORED_HP_HIT")).thenReturn(true);
        eel.healWhenDamage(attacker, defender, damage, mockMap);
        verify(mockRHH, Mockito.times(1)).getEffect();
        verify(attacker, Mockito.times(1)).setHealth(attacker.getHealth() + mockRHH.getEffect());

        resetAll();
        mockMap.put("RESTORED_HP_HIT", mockRHH);
        when(mockMap.containsKey("RESTORED_HP_HIT")).thenReturn(false);
        eel.healWhenDamage(attacker, defender, damage, mockMap);
        verify(attacker, Mockito.never()).setHealth(Mockito.anyDouble());

        resetAll();
        mockMap.put("RESTORED_HP_HIT", mockRHH);
        when(mockRHH.isTriggered()).thenReturn(false);
        when(mockMap.containsKey("RESTORED_HP_HIT")).thenReturn(true);
        eel.healWhenDamage(attacker, defender, damage, mockMap);
        verify(mockRHH, Mockito.never()).getEffect();
        verify(attacker, Mockito.times(1)).setHealth(attacker.getHealth());

        resetAll();
        mockMap.put("RESTORED_HP_HIT", mockRHH);
        when(mockMap.containsKey("RESTORED_HP_HIT")).thenReturn(false);
        eel.healWhenDamage(attacker, defender, damage, mockMap);
        verify(attacker, Mockito.never()).setHealth(Mockito.anyDouble());
    }

    @Test
    public void testRestoredHpPHit () {

        mockMap.put("RESTORED_HP_P_HIT", mockRHPH);
        when(mockRHPH.isTriggered()).thenReturn(true);
        when(mockMap.containsKey("RESTORED_HP_P_HIT")).thenReturn(true);
        eel.healWhenDamage(attacker, defender, damage, mockMap);
        verify(mockRHPH, Mockito.times(1)).getEffect();
        verify(attacker, Mockito.times(1)).setHealth(attacker.getHealth() * (mockRHPH.getEffect() + 1.0D));

        resetAll();
        mockMap.put("RESTORED_HP_P_HIT", mockRHPH);
        when(mockMap.containsKey("RESTORED_HP_P_HIT")).thenReturn(false);
        eel.healWhenDamage(attacker, defender, damage, mockMap);
        verify(attacker, Mockito.never()).setHealth(Mockito.anyDouble());

        resetAll();
        mockMap.put("RESTORED_HP_P_HIT", mockRHPH);
        when(mockRHPH.isTriggered()).thenReturn(false);
        when(mockMap.containsKey("RESTORED_HP_P_HIT")).thenReturn(true);
        eel.healWhenDamage(attacker, defender, damage, mockMap);
        verify(mockRHPH, Mockito.never()).getEffect();
        verify(attacker, Mockito.times(1)).setHealth(attacker.getHealth());

        resetAll();
        mockMap.put("RESTORED_HP_P_HIT", mockRHPH);
        when(mockMap.containsKey("RESTORED_HP_P_HIT")).thenReturn(false);
        eel.healWhenDamage(attacker, defender, damage, mockMap);
        verify(attacker, Mockito.never()).setHealth(Mockito.anyDouble());
    }

    @Test
    public void testRestoredHpVampire () {
        mockMap.put("RESTORED_HP_VAMPIRE", mockRHV);
        when(mockRHV.isTriggered()).thenReturn(true);
        when(mockMap.containsKey("RESTORED_HP_VAMPIRE")).thenReturn(true);
        eel.healWhenDamage(attacker, defender, damage, mockMap);
        verify(mockRHV, Mockito.times(1)).getEffect();
        verify(defender, Mockito.times(1)).setHealth(defender.getHealth() - damage * mockRHV.getEffect());
        verify(attacker, Mockito.times(1)).setHealth(attacker.getHealth() + damage * mockRHV.getEffect());

        resetAll();
        mockMap.put("RESTORED_HP_VAMPIRE", mockRHV);
        when(mockMap.containsKey("RESTORED_HP_VAMPIRE")).thenReturn(false);
        eel.healWhenDamage(attacker, defender, damage, mockMap);
        verify(defender, Mockito.never()).setHealth(Mockito.anyDouble());
        verify(attacker, Mockito.never()).setHealth(Mockito.anyDouble());

        resetAll();
        mockMap.put("RESTORED_HP_VAMPIRE", mockRHV);
        when(mockRHV.isTriggered()).thenReturn(false);
        when(mockMap.containsKey("RESTORED_HP_VAMPIRE")).thenReturn(true);
        eel.healWhenDamage(attacker, defender, damage, mockMap);
        verify(mockRHV, Mockito.never()).getEffect();
        verify(defender, Mockito.never()).setHealth(Mockito.anyDouble());
        verify(attacker, Mockito.times(1)).setHealth(attacker.getHealth());

        resetAll();
        mockMap.put("RESTORED_HP_VAMPIRE", mockRHV);
        when(mockMap.containsKey("RESTORED_HP_VAMPIRE")).thenReturn(false);
        eel.healWhenDamage(attacker, defender, damage, mockMap);
        verify(defender, Mockito.never()).setHealth(Mockito.anyDouble());
        verify(attacker, Mockito.never()).setHealth(Mockito.anyDouble());
    }

}