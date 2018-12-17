package info.clo5de.asukarpg.item;

import info.clo5de.asukarpg.AsukaRPG;
import info.clo5de.asukarpg.TestAsukaRPGBuilder;
import info.clo5de.asukarpg.version.v1_12_R1.item.ItemNMSHandler;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemFactory;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ net.minecraft.server.v1_12_R1.ItemStack.class,
        info.clo5de.asukarpg.version.v1_12_R1.item.ItemNMSHandler.class, CraftItemStack.class, MeowItem.class,
        CraftItemFactory.class, JavaPluginLoader.class, PluginDescriptionFile.class })
@PowerMockIgnore({"javax.management.*"})
public class TestMeowItem {

    private static TestAsukaRPGBuilder builder = new TestAsukaRPGBuilder();
    private static AsukaRPG asukaRPG;
    private static ItemNMSHandler itemNMSHandler;
    private static ItemHandler itemHandler;

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
        itemNMSHandler = spy(new ItemNMSHandler());
        itemHandler = spy(new ItemHandler(asukaRPG, itemNMSHandler));
        when(asukaRPG.getItemHandler()).thenReturn(itemHandler);

        mockID = PowerMockito.spy(new ItemID(Material.STONE, (byte) 0));

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

    @Test
    public void testWriteToItemStackNBT () throws Exception {
        mockStatic(CraftItemStack.class);
        net.minecraft.server.v1_12_R1.ItemStack mockNMS = mock(net.minecraft.server.v1_12_R1.ItemStack.class);
        when(CraftItemStack.asNMSCopy(Mockito.any(ItemStack.class))).thenReturn(mockNMS);
        NBTTagCompound mockNBT = spy(new NBTTagCompound());
        whenNew(NBTTagCompound.class).withNoArguments().thenReturn(mockNBT);

        when(mockNMS.hasTag()).thenReturn(false);
        asukaItem.writeToItemStackNBT();
        assertThat(mockNBT.getString("ItemKey")).isEqualTo(asukaItem.getItemKey());

        mockNBT = spy(new NBTTagCompound());
        when(mockNMS.getTag()).thenReturn(mockNBT);
        when(mockNMS.hasTag()).thenReturn(true);
        asukaItem.writeToItemStackNBT();
        assertThat(mockNBT.getString("ItemKey")).isEqualTo(asukaItem.getItemKey());
    }

}
