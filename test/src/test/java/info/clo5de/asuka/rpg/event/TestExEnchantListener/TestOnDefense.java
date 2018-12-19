package info.clo5de.asuka.rpg.event.TestExEnchantListener;

import info.clo5de.asuka.rpg.AsukaRPG;
import info.clo5de.asuka.rpg.TestAsukaRPGBuilder;
import info.clo5de.asuka.rpg.event.ExEnchantListener;
import info.clo5de.asuka.rpg.item.ExEnchant;
import info.clo5de.asuka.rpg.player.AsukaPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemFactory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CraftItemFactory.class, PluginDescriptionFile.class, JavaPluginLoader.class })
@PowerMockIgnore({"javax.management.*"})
public class TestOnDefense {

    private static TestAsukaRPGBuilder builder = new TestAsukaRPGBuilder();
    private static AsukaRPG asukaRPG;
    private static ExEnchantListener eel;
    private static EntityDamageByEntityEvent edbee;

    private static double damage;
    private static Player player;
    private static AsukaPlayer defender;
    private static LivingEntity attacker;
    private static Map<String, ExEnchant> mockMap;


    @BeforeClass
    public static void setup () throws Exception {
        asukaRPG = builder.getInstance();
        eel = new ExEnchantListener(asukaRPG);
        damage = 10.0D;
        edbee = mock(EntityDamageByEntityEvent.class);

        player = mock(Player.class);
        attacker = mock(LivingEntity.class);
        defender = mock(AsukaPlayer.class);

        mockMap = spy(new HashMap<>());

        when(defender.getExEnchantMap()).thenReturn(mockMap);
        when(defender.getVanillaPlayer()).thenReturn(player);
        when(edbee.getDamager()).thenReturn(attacker);
        when(edbee.getDamage()).thenReturn(damage);
    }

    @Test
    public void testOnAttack () {
        eel.onDefense(defender, edbee);
    }

}
