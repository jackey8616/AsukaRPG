package info.clo5de.asuka.rpg.version.v1_11_R1.item;

import net.minecraft.server.v1_11_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.*;

@Ignore
@RunWith(PowerMockRunner.class)
@PrepareForTest({ CraftItemStack.class, net.minecraft.server.v1_11_R1.ItemStack.class })
@PowerMockIgnore({"javax.management.*"})
public class TestItemNMSHandler {

    private static ItemNMSHandler itemNMSHandler;
    private static org.bukkit.inventory.ItemStack mockStack;
    private static net.minecraft.server.v1_11_R1.ItemStack mockNMS;
    private static NBTTagCompound mockNBT;

    @BeforeClass
    public static void setup () throws Exception {
        itemNMSHandler = new ItemNMSHandler();
        mockStack = spy(new ItemStack(Material.STONE));
        mockNMS = mock(net.minecraft.server.v1_11_R1.ItemStack.class);
        mockNBT = spy(new NBTTagCompound());
        mockStatic(CraftItemStack.class);
        when(CraftItemStack.asNMSCopy(Mockito.any(ItemStack.class))).thenReturn(mockNMS);
        when(mockNMS.getTag()).thenReturn(mockNBT);
        whenNew(NBTTagCompound.class).withNoArguments().thenReturn(mockNBT);
    }

    @Test
    public void testGetItemKeyFromNBT () {
        when(mockNMS.hasTag()).thenReturn(false);
        assertThat(itemNMSHandler.getItemKeyFromNBT(mockStack)).isNull();

        when(mockNMS.hasTag()).thenReturn(true);
        assertThat(itemNMSHandler.getItemKeyFromNBT(mockStack)).isEqualTo("");

        mockNBT.setString("ItemKey", "TestKey");
        assertThat(itemNMSHandler.getItemKeyFromNBT(mockStack)).isEqualTo("TestKey");
    }

    @Test
    public void testWriteItemKeyToItemStackNBT () {
        when(mockNMS.hasTag()).thenReturn(false);
        itemNMSHandler.writeItemKeyToItemStackNBT(mockStack, "TestKey");
        assertThat(mockNBT.getString("ItemKey")).isEqualTo("TestKey");

        when(mockNMS.hasTag()).thenReturn(true);
        itemNMSHandler.writeItemKeyToItemStackNBT(mockStack, "TestKey");
        assertThat(mockNBT.getString("ItemKey")).isEqualTo("TestKey");
    }
}
