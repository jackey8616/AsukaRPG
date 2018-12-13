package info.clo5de.asukarpg.item;


import com.google.common.io.Resources;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;

import java.io.File;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TestMeowItemFactory {

    @Test
    public void testFromConfig () {
        MemorySection asukaConfig = (MemorySection) YamlConfiguration.loadConfiguration(new File(
                Resources.getResource("test_server_folder/plugins/AsukaRPG/item/AsukaTest.yml").getFile())
        ).get("AsukaRPG");
        assertThat(MeowItemFactory.fromConfig(
                "ItemAsukaTestKey", (MemorySection) asukaConfig.get("ItemAsukaTestKey"))).isNotNull();
    }

    @Test
    public void testFromKycConfig () {
        MemorySection kycConfig = (MemorySection) YamlConfiguration.loadConfiguration(new File(
                Resources.getResource("test_server_folder/plugins/AsukaRPG/item/KycTest.yml").getFile())
        ).get("CustomCrafterEx");
        assertThat(MeowItemFactory.fromKycConfig(
                "ItemKycTestName", (MemorySection) kycConfig.get("ItemKycTestName"))).isNotNull();
    }

    @Test
    public void testLoadFromYamlOrigin () {
        File file = new File(Resources.getResource(
                "test_server_folder/plugins/AsukaRPG/item/KycTest.yml").getFile());
        Map<String, MeowItem> map = MeowItemFactory.loadFromYaml(file);
        assertThat(map.containsKey("ItemKycTestKey")).isTrue();

        file = new File(Resources.getResource(
                "test_server_folder/plugins/AsukaRPG/item/AsukaTest.yml").getFile());
        map = MeowItemFactory.loadFromYaml(file);
        assertThat(map.containsKey("ItemAsukaTestKey")).isTrue();
    }

    @Test
    public void testLoadFromKycYaml () {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(new File(Resources.getResource(
                "test_server_folder/plugins/AsukaRPG/item/KycTest.yml").getFile()));
        Map<String, MeowItem> map = MeowItemFactory.loadFromKycYaml((MemorySection) yaml.get("CustomCrafterEx"));
        assertThat(map.containsKey("ItemKycTestKey")).isTrue();
    }


    @Test
    public void testLoadFromYaml () {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(new File(Resources.getResource(
                "test_server_folder/plugins/AsukaRPG/item/AsukaTest.yml").getFile()));
        Map<String, MeowItem> map = MeowItemFactory.loadFromYaml(yaml);
        assertThat(map.containsKey("ItemAsukaTestKey")).isTrue();


        yaml = YamlConfiguration.loadConfiguration(new File(Resources.getResource(
                "test_server_folder/plugins/AsukaRPG/item/KycTest.yml").getFile()));
        map = MeowItemFactory.loadFromYaml(yaml);
        assertThat(map.containsKey("ItemKycTestKey")).isTrue();
    }
}
