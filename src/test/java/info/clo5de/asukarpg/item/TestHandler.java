package info.clo5de.asukarpg.item;

import com.google.common.io.Resources;
import info.clo5de.asukarpg.AsukaRPG;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CraftItemStack.class, net.minecraft.server.v1_12_R1.ItemStack.class, Handler.class, AsukaRPG.class, MeowItemFactory.class })
@PowerMockIgnore({"javax.management.*"})
public class TestHandler {

    private static AsukaRPG asukaRPG;

    private static File resourceFile, mockFile;
    private static MeowItem asukaItem, kycItem;
    private static Handler handler;

    @BeforeClass
    public static void setup() throws Exception {
        asukaRPG = mock(AsukaRPG.class);

        mockFile = mock(File.class);
        resourceFile = new File(
                Resources.getResource("test_server_folder/plugins/AsukaRPG/item").getFile());

        when(mockFile.listFiles()).thenReturn(resourceFile.listFiles());
        whenNew(File.class).withParameterTypes(File.class, String.class).withArguments(
                Mockito.eq(asukaRPG.getDataFolder()), Mockito.eq("item")).thenReturn(mockFile);

        Map<String, MeowItem> map = spy(new HashMap<>());
        asukaItem = mock(MeowItem.class);
        kycItem = mock(MeowItem.class);
        when(asukaItem.buildItemStack()).thenReturn(spy( new ItemStack(Material.STONE)));
        when(kycItem.buildItemStack()).thenReturn(spy(new ItemStack(Material.STONE)));
        when(asukaItem.getItemKey()).thenReturn("ItemAsukaTestKey");
        when(kycItem.getItemKey()).thenReturn("ItemKycTestKey");
        map.put(asukaItem.getItemKey(), asukaItem);
        map.put(kycItem.getItemKey(), kycItem);

        mockStatic(MeowItemFactory.class);
        when(MeowItemFactory.loadFromYaml(Mockito.any(File.class))).thenReturn(map);

        handler = new Handler(asukaRPG);
    }

    @Test
    public void testLoad() {
        when(mockFile.exists()).thenReturn(false);
        handler.load();
        verify(mockFile, Mockito.times(1)).mkdirs();

        when(mockFile.exists()).thenReturn(true);
        handler.load();
        verify(asukaItem, Mockito.times(1)).writeToItemStackNBT();
        verify(kycItem, Mockito.times(1)).writeToItemStackNBT();

    }

    @Test
    public void testGetItemMap() {
        assertThat(handler.getItemMap().size()).isEqualTo(2);
    }

    @Test
    public void testGetItemByKey() {
        assertThat(handler.getItemByKey("ItemAsukaTestKey")).isNotNull();
        assertThat(handler.getItemByKey("ItemKycTestKey")).isNotNull();
    }

    @Test
    public void testWriteToItemStackNBT () {
        mockStatic(CraftItemStack.class);
        net.minecraft.server.v1_12_R1.ItemStack mockNMS = mock(net.minecraft.server.v1_12_R1.ItemStack.class);
        when(CraftItemStack.asNMSCopy(Mockito.any(ItemStack.class))).thenReturn(mockNMS);
        when(mockNMS.hasTag()).thenReturn(false);

        assertThat(handler.getItemFromNBT(mock(ItemStack.class))).isNull();

        when(asukaItem.clone()).thenReturn(asukaItem);
        ItemStack asukaStack = spy(new ItemStack(Material.STONE));
        when(asukaItem.buildItemStack()).thenReturn(asukaStack);
        NBTTagCompound mockNBT = spy(new NBTTagCompound());
        mockNBT.setString("ItemKey", asukaItem.getItemKey());
        when(mockNMS.getTag()).thenReturn(mockNBT);
        when(mockNMS.hasTag()).thenReturn(true);
        assertThat(handler.getItemFromNBT(asukaStack)).isEqualTo(asukaItem);
    }
}
