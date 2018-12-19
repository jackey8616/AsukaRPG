package info.clo5de.asuka.rpg.event.TestExEnchantListener;

import info.clo5de.asuka.rpg.AsukaRPG;
import info.clo5de.asuka.rpg.TestAsukaRPGBuilder;
import info.clo5de.asuka.rpg.event.ExEnchantListener;
import info.clo5de.asuka.rpg.item.ExEnchant;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemFactory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.util.Vector;
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
public class TestFlyOut {

    private static TestAsukaRPGBuilder builder = new TestAsukaRPGBuilder();
    private static AsukaRPG asukaRPG;
    private static ExEnchantListener eel;

    private static LivingEntity defender;
    private static Map<String, ExEnchant> mockMap;
    private static ExEnchant mockHIH;

    @BeforeClass
    public static void setup () throws Exception {
        asukaRPG = builder.getInstance();
        eel = new ExEnchantListener(asukaRPG);
        new TestFlyOut().resetAll();
    }

    @Before
    public void resetAll () {
        defender = mock(LivingEntity.class);

        when(defender.getHealth()).thenReturn(20.0D);
        when(defender.getMaxHealth()).thenReturn(20.0D);

        mockMap = spy(new HashMap<>());
        mockHIH = spy(new ExEnchant("HIT_FLY_HIT", 1.0D));
        mockMap.put("HIT_FLY_HIT", mockHIH);
    }

    @Test
    public void testFlyOut () {

        when(mockHIH.isTriggered()).thenReturn(true);
        when(mockMap.containsKey("HIT_FLY_HIT")).thenReturn(true);
        eel.flyyyyyyyyyyyyyyyyyyyyyyyOut(defender, mockMap);
        verify(defender, Mockito.times(1)).setVelocity(new Vector(0.0F, 0.75F, 0.0F));

        resetAll();
        when(mockMap.containsKey("HIT_FLY_HIT")).thenReturn(false);
        eel.flyyyyyyyyyyyyyyyyyyyyyyyOut(defender, mockMap);
        verify(defender, Mockito.never()).setVelocity(Mockito.any(Vector.class));

        resetAll();
        when(mockHIH.isTriggered()).thenReturn(false);
        when(mockMap.containsKey("HIT_FLY_HIT")).thenReturn(true);
        eel.flyyyyyyyyyyyyyyyyyyyyyyyOut(defender, mockMap);
        verify(defender, Mockito.never()).setVelocity(Mockito.any(Vector.class));

        resetAll();
        when(mockMap.containsKey("HIT_FLY_HIT")).thenReturn(false);
        eel.flyyyyyyyyyyyyyyyyyyyyyyyOut(defender, mockMap);
        verify(defender, Mockito.never()).setVelocity(Mockito.any(Vector.class));
    }

}
