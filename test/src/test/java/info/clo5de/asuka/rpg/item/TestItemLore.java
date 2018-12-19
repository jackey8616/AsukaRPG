package info.clo5de.asuka.rpg.item;

import org.bukkit.inventory.meta.ItemMeta;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class TestItemLore {

    private static ArrayList<String> list;
    private static ItemLore itemLore;

    @BeforeClass
    public static void setupBeforeClass () throws Exception {
        list = new ArrayList<>();
        list.add("A");
        list.add("B");
        itemLore = new ItemLore("A", "B");
    }

    @Test
    public void testFromConfig () {
        assertThat(ItemLore.fromConfig(null)).isNull();
        assertThat(ItemLore.fromConfig((ArrayList<String>) list.clone())).isNotNull();
    }

    @Test
    public void testToList () {
        List<String> toList = itemLore.toList();
        for (int i = 0; i < toList.size(); ++i)
            assertThat(list.get(i)).isGreaterThanOrEqualTo(toList.get(i));
    }

    @Test
    public void testApplyLore () {
        ItemMeta im = mock(ItemMeta.class);
        when(im.getLore()).thenReturn(list);

        itemLore.applyLore(im);
        assert(im.getLore()).equals(list);
    }

}
