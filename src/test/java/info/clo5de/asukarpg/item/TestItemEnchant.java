package info.clo5de.asukarpg.item;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class TestItemEnchant {

    private static Map<Enchantment, Integer> enchantMap = new HashMap<>();
    private static ItemEnchant itemEnchant;

    @BeforeClass
    public static void setupBeforeClass () throws Exception {
        enchantMap.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        enchantMap.put(Enchantment.PROTECTION_EXPLOSIONS, 1);

        itemEnchant = new ItemEnchant(enchantMap);
    }

    @Test
    public void testFromConfig () {
        assertThat(itemEnchant).isNotNull();
        assertThat(ItemEnchant.fromConfig(null)).isNull();
        assertThat(ItemEnchant.fromConfig(new ArrayList<>())).isNull();

        List<String> list = new ArrayList<>();
        list.add("PROTECTION_ENVIRONMENTAL:1");
        list.add("PROTECTION_EXPLOSIONS:");
        list.add("PROTECTION_PROJECTILE");
        assertThat(ItemEnchant.fromConfig(list)).isNotNull();
    }

    @Test
    public void testApplyEnchant () {
        ItemMeta im = mock(ItemMeta.class);
        when(im.getEnchants()).thenReturn(enchantMap);
        itemEnchant.applyEnchant(im);
        assertThat(im.getEnchants()).isEqualTo(enchantMap);
    }

}
