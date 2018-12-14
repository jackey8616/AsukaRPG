package info.clo5de.asukarpg.item;

import info.clo5de.asukarpg.AsukaRPG;
import info.clo5de.asukarpg.TestAsukaRPGBuilder;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CraftItemFactory.class, JavaPluginLoader.class, PluginDescriptionFile.class })
@PowerMockIgnore({"javax.management.*"})
public class TestMeowItem {

    private static TestAsukaRPGBuilder builder = new TestAsukaRPGBuilder();
    private static AsukaRPG asukaRPG;

    private static ItemID mockID;
    private static ItemType itemType = ItemType.ASUKA;
    private static ItemColor mockColor = mock(ItemColor.class);
    private static ItemLore mockLore = mock(ItemLore.class);
    private static ItemEnchant mockEnchant = mock(ItemEnchant.class);
    private static ItemExEnchant mockExEnchat = mock(ItemExEnchant.class);
    private static ItemRecipe mockRecipe = mock(ItemRecipe.class);

    private static MeowItem asukaItem;

    @BeforeClass
    public static void setupBeforeClass () throws Exception {
        asukaRPG = builder.getInstance();
        mockID = spy(new ItemID(Material.STONE, (byte) 0));

        asukaItem = new MeowItem("ItemAsukaTestKey", "ItemAsukaTestName", itemType, mockID,
                mockColor, mockLore, mockEnchant, mockExEnchat,  mockRecipe, 1, true,
                false, false);
    }

    @Test
    public void testBuildItemStack () {
        ItemStack asukaStack = asukaItem.buildItemStack();
        assertThat(asukaStack).isNotNull();
    }

    @Test
    public void testBuildRecipe () {
        asukaItem.buildItemStack();
        assertThat(asukaItem.buildItemRecipe()).isNotNull();
    }

    @Test
    public void testGetItemKey () {
        assertThat(asukaItem.getItemKey().equals("ItemAsukaTestKey")).isTrue();
    }

    @Test
    public void testGetDisplayName () {
        assertThat(asukaItem.getDisplayName().equals("ItemAsukaTestName")).isTrue();
    }

    @Test
    public void testGetItemType () {
        assertThat(asukaItem.getItemType().equals(ItemType.ASUKA)).isTrue();
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
    public void testGetItemExEnchant () {
        assertThat(asukaItem.getItemExEnchant().equals(mockExEnchat)).isNotNull();
    }

    @Test
    public void testGetItemRecipe () {
        assertThat(asukaItem.getItemRecipe().equals(mockRecipe)).isNotNull();
    }

    @Test
    public void testGetItemStack () {
        asukaItem.buildItemStack();
        ItemStack getStack = asukaItem.getItemStack();
        assertThat(getStack.getType().equals(mockID.getMaterial())).isTrue();
    }

    @Test
    public void testSetItemStack () {
        ItemStack setStack = mock(ItemStack.class);
        when(setStack.clone()).thenReturn(setStack);

        asukaItem.setItemStack(setStack);
        assertThat(asukaItem.getItemStack().isSimilar(setStack));
    }

    @Test
    public void testClone () {
        asukaItem.buildItemStack();
        MeowItem clone = asukaItem.clone();
        assertThat(clone.getItemKey().equals(asukaItem.getItemKey())).isTrue();
        assertThat(clone.getDisplayName().equals(asukaItem.getDisplayName())).isTrue();
        assertThat(clone.getItemID()).isEqualTo(asukaItem.getItemID());
        assertThat(clone.getMaterial()).isEqualTo(asukaItem.getMaterial());
        assertThat(clone.getItemColor().equals(asukaItem.getItemColor())).isTrue();
        assertThat(clone.getItemLore().equals(asukaItem.getItemLore())).isTrue();
        assertThat(clone.getItemEnchant().equals(asukaItem.getItemEnchant())).isTrue();
        assertThat(clone.getItemRecipe().equals(asukaItem.getItemRecipe())).isTrue();
        assertThat(clone.getItemStack().getType().equals(asukaItem.getMaterial())).isTrue();
    }

}
