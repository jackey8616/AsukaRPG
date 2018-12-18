package info.clo5de.asukarpg.event;

import com.codingforcookies.armorequip.ArmorEquipEvent;
import info.clo5de.asukarpg.AsukaRPG;
import info.clo5de.asukarpg.TestAsukaRPGBuilder;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ArmorEquipEvent.class, CraftItemFactory.class, PluginDescriptionFile.class, JavaPluginLoader.class })
@PowerMockIgnore({"javax.management.*"})
public class TestPlayerListener {

    private static TestAsukaRPGBuilder builder = new TestAsukaRPGBuilder();
    private static AsukaRPG asukaRPG;
    private static PlayerListener pl;

    @BeforeClass
    public static void setup () throws Exception {
        asukaRPG = builder.getInstance();
        pl = spy(new PlayerListener(asukaRPG));
    }

    @Test
    public void testOnPlayerChangeSlot () {
        PlayerItemHeldEvent pihe = mock(PlayerItemHeldEvent.class);
        Player mockPlayer = mock(Player.class);
        PlayerInventory mockInv = mock(PlayerInventory.class);
        ItemStack mockStack = mock(ItemStack.class);

        when(mockPlayer.getInventory()).thenReturn(mockInv);
        when(mockInv.getItem(Mockito.anyInt())).thenReturn(mockStack);
        when(pihe.getPlayer()).thenReturn(mockPlayer);
        when(pihe.getNewSlot()).thenReturn(1);

        pl.onPlayerChangeSlot(pihe);
    }

    @Test
    public void testOnPlayerEquip () {
        ArmorEquipEvent aee = mock(ArmorEquipEvent.class);
        Player mockPlayer = mock(Player.class);
        ItemStack mockStack = mock(ItemStack.class);

        when(aee.getPlayer()).thenReturn(mockPlayer);
        when(aee.getNewArmorPiece()).thenReturn(mockStack);

        pl.onPlayerEquip(aee);
    }

}
