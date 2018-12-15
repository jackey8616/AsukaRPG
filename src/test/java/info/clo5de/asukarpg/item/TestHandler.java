package info.clo5de.asukarpg.item;

import com.google.common.io.Resources;
import info.clo5de.asukarpg.AsukaRPG;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
@PrepareForTest({ Handler.class, AsukaRPG.class, MeowItemFactory.class })
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
        map.put("ItemAsukaTestKey", asukaItem);
        map.put("ItemKycTestKey", kycItem);

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

}
