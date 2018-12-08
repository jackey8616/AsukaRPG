package info.clo5de.asukarpg.item;

import com.google.common.io.Resources;
import info.clo5de.asukarpg.AsukaRPG;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ AsukaRPG.class, MeowItem.class })
@PowerMockIgnore({"javax.management.*"})
public class TestHandler {

    private static File mockFile;
    private static AsukaRPG mockPlugin = mock(AsukaRPG.class);
    private static MeowItem mockItem = mock(MeowItem.class);
    private static Handler handler;

    public static Handler getHandler () {
        return handler;
    }

    @BeforeClass
    public static void setupBeforeClass () throws Exception {
        // File mock
        mockFile = spy(new File(Resources.getResource("test_server_folder/plugins/AsukaRPG").getFile()));
        // AsukaRPG mock
        when(mockPlugin.getDataFolder()).thenAnswer(answer -> mockFile);
        // MeowItem mock
        mockStatic(MeowItem.class);
        when(MeowItem.fromConfig(Mockito.any(), Mockito.any())).thenReturn(mockItem);
        when(MeowItem.fromKycConfig(Mockito.any(), Mockito.any())).thenReturn(mockItem);

        handler = new Handler(mockPlugin);
    }

    @Test
    public void testLoad() throws Exception {
        when(mockItem.getItemRecipe()).thenReturn(mock(ItemRecipe.class));
        handler.load();
        when(mockItem.getItemRecipe()).thenReturn(null);
        handler.load();

        doReturn(false).when(mockFile).exists();
        handler.load();

        Thread.sleep(1000);
    }

//    @Test
    public void testGetItemMap () {
        assertThat(handler.getItemMap().size()).isNotZero();
        assertThat(handler.getItemMap().size()).isEqualTo(2);
    }

//    @Test
    public void testGetItemByKey () {
        assertThat(handler.getItemByKey("ItemAsukaTestKey")).isNotNull();
        assertThat(handler.getItemByKey("ItemKycTestKey")).isNotNull();
    }

}
