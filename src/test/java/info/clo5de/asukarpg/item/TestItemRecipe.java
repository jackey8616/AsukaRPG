package info.clo5de.asukarpg.item;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;

public class TestItemRecipe {

    private static List<String> list = new ArrayList<>();
    private static List<Ingredient> ingredientList = new ArrayList<>();
    private static ItemRecipe itemRecipe;

    @BeforeClass
    public static void setupBeforeClass () throws Exception {
        Ingredient ing = new Ingredient(
                new ItemID(Material.matchMaterial("stone"), (byte) 2), null, 1);
        Ingredient ing2 = new Ingredient(
                new ItemID(Material.matchMaterial("stone"), (byte) 2), null, 2);

        list.add("stone:2 0"); //**
        list.add("0 0");
        list.add("stone:2 0 2");
        list.add("stone:2 0");
        ingredientList.add(ing);
        ingredientList.add(Ingredient.AIR());
        ingredientList.add(ing2);
        ingredientList.add(ing);
        ingredientList.add(Ingredient.AIR());
        ingredientList.add(Ingredient.AIR());
        ingredientList.add(Ingredient.AIR());
        ingredientList.add(Ingredient.AIR());
        ingredientList.add(Ingredient.AIR());

        itemRecipe = ItemRecipe.fromKycConfig("Key", list);
    }

    @Test
    public void testFromConfig () {
        assertThat(itemRecipe).isNotNull();
        assertThat(ItemRecipe.fromKycConfig("string", null)).isNull();
        assertThat(ItemRecipe.fromKycConfig("string", new ArrayList<String>())).isNull();

    }

    @Test
    public void testBuildItemRecipe () {
        Server server = mock(Server.class);
        ItemStack mockIS = mock(ItemStack.class);
        // itemRecipe.buildItemRecipe(mockIS);
    }

    @Test
    public void testIsMultistackRecipe () {
        assertThat(itemRecipe.isMultistackRecipe()).isTrue();
    }

    @Test
    public void testIsConverted () {
        assertThat(itemRecipe.isConverted()).isFalse();
    }

    @Test
    public void testGetRecipe () {
        assertThat(itemRecipe.getRecipe()).isNull();
    }

    @Test
    public void testGetIngredient () {
        assertThat(itemRecipe.getIngredient(0).equals(ingredientList.get(0))).isTrue();
        assertThat(itemRecipe.getIngredient(1).equals(ingredientList.get(1))).isTrue();
        assertThat(itemRecipe.getIngredient(2).equals(ingredientList.get(2))).isTrue();
    }

}
