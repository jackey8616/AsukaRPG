package info.clo5de.asukarpg.player;

import info.clo5de.asukarpg.AsukaRPG;
import info.clo5de.asukarpg.item.ExEnchant;
import info.clo5de.asukarpg.item.Handler;
import info.clo5de.asukarpg.item.MeowItemFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ AsukaPlayer.class })
@PowerMockIgnore({"javax.management.*"})
public class TestAsukaPlayer {

    private static Player player;
    private static AsukaPlayer asukaPlayer;
    private static HashMap<String, ExEnchant> spyMap;

    @BeforeClass
    public static void setup () throws Exception {
        player = mock(Player.class);
        spyMap = spy(new HashMap<>());

        whenNew(HashMap.class).withNoArguments().thenReturn(spyMap);
        asukaPlayer = new AsukaPlayer(player);
    }

    //@Test
    public void testContructor () {
        given(new AsukaPlayer(null)).willThrow(new NullPointerException("The validated object is null"));
    }

    @Test
    public void testGetVanillaPlayer () {
        assertThat(asukaPlayer.getVanillaPlayer()).isEqualTo(player);
    }

    @Test
    public void testGetHealth () {
        when(player.getHealth()).thenReturn(20.0D);
        assertThat(asukaPlayer.getHealth()).isEqualTo(20.0D);
    }

    //@Test
    public void testSetHealth () {
        asukaPlayer.setHealth(50.0D);
        assertThat(asukaPlayer.getHealth()).isEqualTo(50.0D);
    }

    @Test
    public void testGetItemInMainHand () {
        ItemStack itemInMainHand = mock(ItemStack.class);
        PlayerInventory mockInv = mock(PlayerInventory.class);
        when(mockInv.getItemInMainHand()).thenReturn(itemInMainHand);

        when(player.getInventory()).thenReturn(mockInv);
        assertThat(asukaPlayer.getItemInMainHand()).isEqualTo(itemInMainHand);
    }

    @Test
    public void testGetExEnchantMap () {
        assertThat(asukaPlayer.getExEnchantMap()).isEqualTo(spyMap);
    }

}
