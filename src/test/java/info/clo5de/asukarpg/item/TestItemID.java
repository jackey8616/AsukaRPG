package info.clo5de.asukarpg.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestItemID {

    private static ItemID i297, i302;
    private static ItemID h, c, l , b;
    private static ItemID one_one;

    @BeforeClass
    public static void setupBeforeClass () throws Exception {
        i297 = ItemID.fromConfig("297");
        i302 = ItemID.fromConfig("302");
        h = ItemID.fromConfig("leather_helmet:255,255,255");
        c = ItemID.fromConfig("leather_chestplate:255,255,255");
        l = ItemID.fromConfig("leather_leggings:255,255,255");
        b = ItemID.fromConfig("leather_boots:255,255,255");
        one_one = ItemID.fromConfig("1:1");
    }

    @Test
    public void testAIR () {
        ItemID air = ItemID.AIR();
        assertThat(air.getMaterial()).isEqualByComparingTo(Material.AIR);
        assertThat(air.getSubId()).isEqualTo((byte) 0);
    }

    @Test
    public void testFromConfig () {
        ItemID itemID = null;

        // id
        itemID = ItemID.fromConfig("id");
        assertThat(itemID).isEqualTo(null);
        // id:sub_id
        itemID = ItemID.fromConfig("id:sub_id");
        assertThat(itemID).isEqualTo(null);
        // stone
        itemID = ItemID.fromConfig("stone");
        assertThat(itemID.getMaterial()).isEqualByComparingTo(Material.STONE);
        assertThat(itemID.getSubId()).isEqualTo((byte) 0);
        // leather_helmet:255,255,255
        itemID = ItemID.fromConfig("leather_helmet:255,255,255");
        assertThat(itemID.getMaterial()).isEqualByComparingTo(Material.LEATHER_HELMET);
        assertThat(itemID.getSubId()).isEqualTo((byte) 0);
        // stone:1
        itemID = ItemID.fromConfig("stone:1");
        assertThat(itemID.getMaterial()).isEqualByComparingTo(Material.STONE);
        assertThat(itemID.getSubId()).isEqualTo((byte) 1);
        // 1:2
        itemID = ItemID.fromConfig("1:2");
        assertThat(itemID.getMaterial()).isEqualByComparingTo(Material.STONE);
        assertThat(itemID.getSubId()).isEqualTo((byte) 2);

    }

    @Test
    public void testIsLeather () {
        assertThat(one_one.isLeather()).isFalse();
        assertThat(i297.isLeather()).isFalse();
        assertThat(h.isLeather()).isTrue();
        assertThat(c.isLeather()).isTrue();
        assertThat(l.isLeather()).isTrue();
        assertThat(b.isLeather()).isTrue();
        assertThat(i302.isLeather()).isFalse();
    }

    @Test
    public void testHasSubId () {
        assertThat(i297.hasSubId()).isFalse();
        assertThat(one_one.hasSubId()).isTrue();
    }

    @Test
    public void testMakeItemStack () {
        assertThat(i297.makeItemStack().getType()).isEqualByComparingTo(
                new ItemStack(i297.getMaterial(), i297.getSubId()).getType());
        assertThat(one_one.makeItemStack().getType()).isEqualByComparingTo(
                new ItemStack((one_one.getMaterial()), one_one.getSubId()).getType());
        assertThat(i297.makeItemStack().getType()).isNotEqualByComparingTo(
                one_one.makeItemStack().getType());
    }
}
