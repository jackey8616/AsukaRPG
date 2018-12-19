package info.clo5de.asuka.rpg;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class TestConfigManager {

    private static AsukaRPG asukaRPG = mock(AsukaRPG.class);
    private static FileConfiguration mockConfig = mock(FileConfiguration.class);
    private static ConfigManager configManager;

    @BeforeClass
    public static void setupBeforeClass () {
        // Config mock
        when(mockConfig.options()).thenReturn(mock(FileConfigurationOptions.class));
        // AsukaRPG mock
        when(asukaRPG.getConfig()).thenReturn(mockConfig);

        configManager = new ConfigManager(asukaRPG);
    }

    @Test
    public void testLoad () {
        configManager.load();
    }

}
