package info.clo5de.asukarpg.item;

import info.clo5de.asukarpg.AsukaRPG;
import info.clo5de.asukarpg.utils;
import org.bukkit.Material;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class TestIngredient {

    private static AsukaRPG asukaRPG;
    private static ItemID itemID;
    private static info.clo5de.asukarpg.item.Handler handler;
    private static MeowItem meowItem;
    private static Ingredient ingredient;

    @BeforeClass
    public static void setupBeforeClass () throws Exception {
        itemID = new ItemID(Material.STONE, (byte) 1);
        asukaRPG = mock(AsukaRPG.class);
        handler = mock(info.clo5de.asukarpg.item.Handler.class);
        meowItem = mock(MeowItem.class);

        when(handler.getItem("key")).thenReturn(meowItem);
        when(meowItem.getItemID()).thenReturn(itemID);
        when(meowItem.getDisplayName()).thenReturn("name");
        when(asukaRPG.getItemHandler()).thenReturn(handler);
        utils.setFinalStatic(AsukaRPG.class.getField("INSTANCE"), asukaRPG);

        ingredient = new Ingredient(meowItem, 1);
    }

    @Test
    public void testAIR () {
        Ingredient air = Ingredient.AIR();
        assertThat(air.getMaterial()).isEqualByComparingTo(Material.AIR);
        assertThat(air.getDisplayName()).isNull();
        assertThat(air.getQuantity()).isEqualTo(0);
    }

    @Test
    public void testFromConfig () {
        assertThat(ingredient).isNotNull();
        assertThat(Ingredient.fromConfig("I:key")).isNotNull();
        assertThat(Ingredient.fromConfig("I:key 1")).isNotNull();
        when(handler.getItem("key")).thenReturn(null);
        assertThat(Ingredient.fromConfig("I:key")).isNull();
        assertThat(Ingredient.fromConfig("I:key 1")).isNull();

        assertThat(Ingredient.fromConfig("")).isNull();
        assertThat(Ingredient.fromConfig("1")).isNull();
        assertThat(Ingredient.fromConfig("1 2")).isNotNull();
        assertThat(Ingredient.fromConfig("1 2 3 4")).isNull();

        assertThat(Ingredient.fromConfig("stone")).isNull();
        assertThat(Ingredient.fromConfig("stone 0")).isNotNull();
        assertThat(Ingredient.fromConfig("stone 0 0")).isNotNull();
        assertThat(Ingredient.fromConfig("stone 0 1")).isNotNull();
        assertThat(Ingredient.fromConfig("stone 0 not_num")).isNotNull();
        assertThat(Ingredient.fromConfig("stone name")).isNotNull();
        assertThat(Ingredient.fromConfig("stone name 0")).isNotNull();
        assertThat(Ingredient.fromConfig("stone name 1")).isNotNull();
        assertThat(Ingredient.fromConfig("stone name not_num")).isNotNull();
        assertThat(Ingredient.fromConfig("not_even_exists")).isNull();
        assertThat(Ingredient.fromConfig("not_even_exists 0")).isNull();
        assertThat(Ingredient.fromConfig("not_even_exists 0 1")).isNull();
        assertThat(Ingredient.fromConfig("not_even_exists 0 not_num")).isNull();
        assertThat(Ingredient.fromConfig("not_even_exists name")).isNull();
        assertThat(Ingredient.fromConfig("not_even_exists name 1")).isNull();
        assertThat(Ingredient.fromConfig("not_even_exists name not_num")).isNull();
    }

    @Test
    public void testGetItemID () {
        assertThat(ingredient.getItemID()).isEqualTo(itemID);
    }

    @Test
    public void testGetMaterial () {
        assertThat(ingredient.getMaterial()).isEqualTo(itemID.getMaterial());
    }

    @Test
    public void testGetDisplayName () {
        assertThat(ingredient.getDisplayName()).isEqualTo(meowItem.getDisplayName());
    }

    @Test
    public void testGetQuantity () {
        assertThat(ingredient.getQuantity()).isEqualTo(1);
    }
}
