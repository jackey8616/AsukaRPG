package info.clo5de.asukarpg.item;

import com.google.common.io.Resources;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class TestMeowItem {

    private static ItemFactory itemFactory;

    private static ItemID mockID = mock(ItemID.class);
    private static ItemColor mockColor = mock(ItemColor.class);
    private static ItemLore mockLore = mock(ItemLore.class);
    private static ItemEnchant mockEnchant = mock(ItemEnchant.class);
    private static ItemRecipe mockRecipe = mock(ItemRecipe.class);
    private static ItemMeta mockMeta = mock(ItemMeta.class);
    private static ItemStack mockStack = mock(ItemStack.class);

    private static MeowItem asukaItem;

    @BeforeClass
    public static void setupBeforeClass () throws Exception {
        when(mockStack.getItemMeta()).thenReturn(mockMeta);
        when(mockID.makeItemStack()).thenReturn(mockStack);

        asukaItem = new MeowItem("ItemAsukaTestKey", "ItemAsukaTestName", mockID, mockColor,
                mockLore, mockEnchant, mockRecipe, 1, true, false, false);
    }

    @Test
    public void testFromConfig () {
        MemorySection asukaConfig = (MemorySection) YamlConfiguration.loadConfiguration(new File(
                Resources.getResource("test_server_folder/plugins/AsukaRPG/item/AsukaTest.yml").getFile())
        ).get("AsukaRPG");
        assertThat(MeowItem.fromConfig(
                "ItemAsukaTestKey", (MemorySection) asukaConfig.get("ItemAsukaTestKey"))).isNotNull();
    }

    @Test
    public void testFromKycConfig () {
        MemorySection kycConfig = (MemorySection) YamlConfiguration.loadConfiguration(new File(
                Resources.getResource("test_server_folder/plugins/AsukaRPG/item/KycTest.yml").getFile())
        ).get("CustomCrafterEx");
        assertThat(MeowItem.fromKycConfig(
                "ItemKycTestName", (MemorySection) kycConfig.get("ItemKycTestName"))).isNotNull();
    }

    @Test
    public void testBuildItemStack () {
        ItemStack asukaStack = asukaItem.buildItemStack();
        assertThat(asukaStack).isNotNull();
    }

    @Test
    public void testBuildRecipe () {
        assertThat(asukaItem.buildItemRecipe()).isEqualTo(mockMeta);
    }

    @Test
    public void testGetDisplayName () {
        assertThat(asukaItem.getDisplayName().equals("ItemAsukaTestName")).isTrue();
    }

    @Test
    public void testGetItemID () {
        assertThat(asukaItem.getItemID()).isEqualTo(mockID);
    }

    @Test
    public void testGetMaterial () {
        assertThat(asukaItem.getMaterial()).isEqualTo(mockID.getMaterial());
    }

    @Test
    public void testGetItemColor () {
        assertThat(asukaItem.getItemColor()).isEqualTo(mockColor);
    }

    @Test
    public void testGetItemLore () {
        assertThat(asukaItem.getItemLore()).isEqualTo(mockLore);
    }

    @Test
    public void testGetItemEnchant () {
        assertThat(asukaItem.getItemEnchant().equals(mockEnchant)).isNotNull();
    }

    @Test
    public void testGetItemRecipe () {
        assertThat(asukaItem.getItemRecipe().equals(mockRecipe)).isNotNull();
    }

}
