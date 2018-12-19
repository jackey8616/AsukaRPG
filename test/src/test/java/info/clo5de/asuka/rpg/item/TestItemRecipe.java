package info.clo5de.asuka.rpg.item;

import info.clo5de.asuka.rpg.AsukaRPG;
import info.clo5de.asuka.rpg.exception.ItemConfigException;
import info.clo5de.asuka.rpg.utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Bukkit.class, NamespacedKey.class, AsukaRPG.class })
@PowerMockIgnore({"javax.management.*"})
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

    @Test(expected = ItemConfigException.class)
    public void testFromConfig () throws Exception {
        ItemRecipe.fromKycConfig("string", null);
        ItemRecipe.fromKycConfig("string", new ArrayList<String>());
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


    @Test
    public void testBuildItemRecipe () throws Exception {
        AsukaRPG asukaRPG = PowerMockito.mock(AsukaRPG.class);
        NamespacedKey namespacedKey = PowerMockito.mock(NamespacedKey.class);
        ItemStack itemStack = mock(ItemStack.class);
        ShapedRecipe recipe = mock(ShapedRecipe.class);

        // Bukkit Mock
        mockStatic(Bukkit.class);
        when(Bukkit.getServer()).thenReturn(mock(Server.class));
        // AsukaRPG mock
        utils.setFinalStatic(AsukaRPG.class.getField("INSTANCE"), asukaRPG);
        when(asukaRPG.getName()).thenReturn("AsukaRPG");
        // ShapedRecipe mock
        whenNew(NamespacedKey.class).withArguments(asukaRPG, "key").thenReturn(namespacedKey);
        whenNew(ShapedRecipe.class).withArguments(namespacedKey, itemStack).thenReturn(recipe);

        assertThat(itemRecipe.isConverted()).isFalse();
        itemRecipe.buildItemRecipe(itemStack);
        assertThat(itemRecipe.isConverted()).isTrue();
    }

}
