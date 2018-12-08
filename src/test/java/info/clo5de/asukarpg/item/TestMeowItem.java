package info.clo5de.asukarpg.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/*
@RunWith(PowerMockRunner.class)
@PrepareForTest({ Bukkit.class, ItemFactory.class, ItemMeta.class })
@PowerMockIgnore({"javax.management.*"})
*/
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
        MemorySection asukaConfig = (MemorySection) YamlConfiguration.loadConfiguration(
                new File(getClass().getClassLoader().getResource("item/AsukaTest.yml").getFile())
        ).get("AsukaRPG");
        assertThat(MeowItem.fromConfig(
                "ItemAsukaTestKey", (MemorySection) asukaConfig.get("ItemAsukaTestKey"))).isNotNull();
    }

    @Test
    public void testFromKycConfig () {
        MemorySection kycConfig = (MemorySection) YamlConfiguration.loadConfiguration(
                new File(getClass().getClassLoader().getResource("item/KycTest.yml").getFile())
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
        //assertThat(kycItem.getDisplayName().equals("ItemKycTestName")).isTrue();
    }

    @Test
    public void testGetItemID () {
        assertThat(asukaItem.getItemID()).isEqualTo(mockID);
        //assertThat(kycItem.getItemID().equals(new ItemID(Material.matchMaterial("stone"), (byte) 1))).isTrue();
    }

    @Test
    public void testGetMaterial () {
        assertThat(asukaItem.getMaterial()).isEqualTo(mockID.getMaterial());
        //assertThat(kycItem.getMaterial()).isNotNull();
    }

    @Test
    public void testGetItemColor () {
        assertThat(asukaItem.getItemColor()).isEqualTo(mockColor);
        //assertThat(kycItem.getItemColor()).isNull();
    }

    @Test
    public void testGetItemLore () {
        assertThat(asukaItem.getItemLore()).isEqualTo(mockLore);
        //assertThat(kycItem.getItemLore()).isNotNull();
    }

    @Test
    public void testGetItemEnchant () {
        assertThat(asukaItem.getItemEnchant().equals(mockEnchant)).isNotNull();
        //assertThat(kycItem.getItemEnchant()).isNotNull();
    }

    @Test
    public void testGetItemRecipe () {
        assertThat(asukaItem.getItemRecipe().equals(mockRecipe)).isNotNull();
        //assertThat(kycItem.getItemRecipe()).isNotNull();
    }

}
