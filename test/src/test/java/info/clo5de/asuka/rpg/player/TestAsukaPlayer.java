package info.clo5de.asuka.rpg.player;

import info.clo5de.asuka.rpg.item.ExEnchant;
import info.clo5de.asuka.rpg.item.ItemExEnchant;
import info.clo5de.asuka.rpg.item.MeowItem;
import org.bukkit.entity.Player;
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
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.*;

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

    @Test(expected = NullPointerException.class)
    public void testContructor () {
        new AsukaPlayer(null);
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

    @Test
    public void testOnEquip () {
        ExEnchant ADD_CRITI_A = spy(new ExEnchant("ADD_CRITI", 0.5D, 25.0D));
        Map<String, ExEnchant> mockMap = spy(new HashMap<>());
        mockMap.put("ADD_CRITI", ADD_CRITI_A);
        ItemExEnchant mockItemExEnchant = mock(ItemExEnchant.class);
        when(mockItemExEnchant.toEntrySet()).thenAnswer(answer -> mockMap.entrySet());
        MeowItem mockItem = mock(MeowItem.class);
        when(mockItem.getItemExEnchant()).thenReturn(mockItemExEnchant);

        asukaPlayer.onEquip(mockItem);
        assertThat(spyMap.get("ADD_CRITI").getAbility()).isEqualTo(0.5D);
        assertThat(spyMap.get("ADD_CRITI").getEffect()).isEqualTo(25.0D);
        asukaPlayer.onEquip(mockItem);
        assertThat(spyMap.get("ADD_CRITI").getAbility()).isEqualTo(0.75D);
        assertThat(spyMap.get("ADD_CRITI").getEffect()).isEqualTo(50.0D);
    }

}
