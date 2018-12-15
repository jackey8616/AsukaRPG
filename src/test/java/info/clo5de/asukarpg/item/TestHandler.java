package info.clo5de.asukarpg.item;

import com.google.common.io.Resources;
import info.clo5de.asukarpg.AsukaRPG;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@Ignore
@RunWith(PowerMockRunner.class)
@PrepareForTest({  })
@PowerMockIgnore({"javax.management.*"})
public class TestHandler {

    //private static TestAsukaRPGBuilder builder = new TestAsukaRPGBuilder();
    private static AsukaRPG asukaRPG;

    private static File mockFile;
    private static AsukaRPG mockPlugin = mock(AsukaRPG.class);
    private static MeowItem mockItem = mock(MeowItem.class);
    private static Handler handler;

    @BeforeClass
    public static void setupBeforeClass() throws Exception {
        //asukaRPG = builder.getInstance();
        mockFile = spy(new File(Resources.getResource("test_server_folder/plugins/AsukaRPG").getFile()));
        asukaRPG = mock(AsukaRPG.class);

//        when(asukaRPG.getDataFolder()).thenAnswer(answer -> mockFile);

        mockStatic(MeowItemFactory.class);
        Map<String, MeowItem> map = new HashMap<>();
        map.put("ItemAsukaTestKey", mock(MeowItem.class));
        map.put("ItemKycTestKey", mock(MeowItem.class));
        when(MeowItemFactory.loadFromYaml(Mockito.any(File.class))).thenReturn(map);

        handler = new Handler(asukaRPG);
    }

    @AfterClass
    public static void teardown () {
        mockFile.delete();
    }

    @Test
    public void testLoad() throws Exception {
        when(mockFile.exists()).thenReturn(false);

        handler.load();
        //verify(mockFile, Mockito.never()).listFiles();
        verify(mockFile, Mockito.times(1)).mkdirs();
/*        when(mockItem.getItemRecipe()).thenReturn(mock(ItemRecipe.class));
        handler.load();
        when(mockItem.getItemRecipe()).thenReturn(null);
        handler.load();

        doReturn(false).when(mockFile).exists();
        handler.load();

        Thread.sleep(2000);
        */
    }

    @Test
    public void testGetItemMap() {
//        assertThat(handler.getItemMap().size()).isNotZero();
//        assertThat(handler.getItemMap().size()).isEqualTo(2);
    }

    @Test
    public void testGetItemByKey() {
//        assertThat(handler.getItemByKey("ItemAsukaTestKey")).isNotNull();
//        assertThat(handler.getItemByKey("ItemKycTestKey")).isNotNull();
    }

}
