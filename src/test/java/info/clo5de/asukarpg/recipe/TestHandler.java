package info.clo5de.asukarpg.recipe;

import com.google.common.io.Resources;
import info.clo5de.asukarpg.AsukaRPG;
import info.clo5de.asukarpg.item.ItemRecipe;
import info.clo5de.asukarpg.item.MeowItem;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ AsukaRPG.class })
@PowerMockIgnore({"javax.management.*"})
public class TestHandler {

    private static File mockFile;
    private static Map<String, MeowItem> mockMap = spy(new HashMap<>());
    private static MeowItem asukaItem = mock(MeowItem.class), kycItem = mock(MeowItem.class);
    private static AsukaRPG mockPlugin = mock(AsukaRPG.class);
    private static info.clo5de.asukarpg.item.Handler itemHandler;
    private static Handler handler;

    @BeforeClass
    public static void setupBeforeClass () throws Exception {
        // MeowItem mock
        when(asukaItem.getItemRecipe()).thenReturn(mock(ItemRecipe.class));
        when(kycItem.getItemRecipe()).thenReturn(null);
        // Map mock
        mockMap.put("ItemAsukaTestKey", asukaItem);
        mockMap.put("ItemKycTestKey", kycItem);
        // Handler mock
        itemHandler = mock(info.clo5de.asukarpg.item.Handler.class);
        when(itemHandler.getItemMap()).thenReturn(mockMap);
        // File mock
        mockFile = spy(new File(Resources.getResource("test_server_folder/plugins/AsukaRPG").getFile()));
        // AsukaRPG mock
        when(mockPlugin.getDataFolder()).thenAnswer(answer -> mockFile);
        when(mockPlugin.getItemHandler()).thenReturn(itemHandler);

        handler = new Handler(mockPlugin);
    }

    @Test
    public void testLoadItemRecipes () {
        handler.loadItemRecipes();
    }

}
