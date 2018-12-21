package info.clo5de.asuka.rpg.item;


import com.google.common.io.Resources;
import info.clo5de.asuka.rpg.AsukaRPG;
import info.clo5de.asuka.rpg.utils;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class TestMeowItemFactory {

    private static AsukaRPG asukaRPG;
    private static Logger mockLogger;

    @BeforeClass
    public static void setup () throws Exception {
        asukaRPG = mock(AsukaRPG.class);
        mockLogger = mock(Logger.class);
        when(asukaRPG.logger()).thenReturn(mockLogger);
        utils.setFinalStatic(AsukaRPG.class.getField("INSTANCE"), asukaRPG);
    }

    @Test
    public void testFromConfig () throws Exception {
        File file = new File(
                Resources.getResource("test_server_folder/plugins/AsukaRPG/item/AsukaTest.yml").getFile());
        MemorySection asukaConfig = (MemorySection) YamlConfiguration.loadConfiguration(file).get("AsukaRPG");
        assertThat(MeowItemFactory.fromConfig(file,
                "ItemAsukaTestKey", (MemorySection) asukaConfig.get("ItemAsukaTestKey"))).isNotNull();
    }

    @Test
    public void testFromKycConfig () throws Exception {
        File file = new File(
                Resources.getResource("test_server_folder/plugins/AsukaRPG/item/KycTest.yml").getFile());
        MemorySection kycConfig = (MemorySection) YamlConfiguration.loadConfiguration(file).get("CustomCrafterEx");
        assertThat(MeowItemFactory.fromKycConfig(file,
                "ItemKycTestName", (MemorySection) kycConfig.get("ItemKycTestName"))).isNotNull();
    }

    @Test
    public void testLoadFromYamlOrigin () throws Exception {
        File file = new File(Resources.getResource(
                "test_server_folder/plugins/AsukaRPG/item/KycTest.yml").getFile());
        Map<String, MeowItem> map = MeowItemFactory.loadFromYaml(new HashSet<>(), file);
        assertThat(map.containsKey("ItemKycTestKey")).isTrue();

        file = new File(Resources.getResource(
                "test_server_folder/plugins/AsukaRPG/item/AsukaTest.yml").getFile());
        map = MeowItemFactory.loadFromYaml(new HashSet<>(), file);
        assertThat(map.containsKey("ItemAsukaTestKey")).isTrue();
    }

    @Test
    public void testLoadFromKycYaml () {
        File file = new File(Resources.getResource(
                "test_server_folder/plugins/AsukaRPG/item/KycTest.yml").getFile());
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        Map<String, MeowItem> map = MeowItemFactory.loadFromKycYaml(
                new HashSet<>(), file, (MemorySection) yaml.get("CustomCrafterEx"));
        assertThat(map.containsKey("ItemKycTestKey")).isTrue();
    }


    @Test
    public void testLoadFromYaml () throws Exception {
        File file = new File(Resources.getResource(
                "test_server_folder/plugins/AsukaRPG/item/AsukaTest.yml").getFile());
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        Map<String, MeowItem> map = MeowItemFactory.loadFromYaml(new HashSet<>(), file, yaml);
        assertThat(map.containsKey("ItemAsukaTestKey")).isTrue();


        file = new File(Resources.getResource(
                "test_server_folder/plugins/AsukaRPG/item/KycTest.yml").getFile());
        yaml = YamlConfiguration.loadConfiguration(file);
        map = MeowItemFactory.loadFromYaml(new HashSet<>(), file, yaml);
        assertThat(map.containsKey("ItemKycTestKey")).isTrue();
    }
}
