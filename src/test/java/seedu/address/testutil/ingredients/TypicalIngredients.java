package seedu.address.testutil.ingredients;

import static seedu.address.logic.commands.CommandTestUtil.VALID_MINIMUM_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MINIMUM_BROCCOLI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BROCCOLI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BROCCOLI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_BROCCOLI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.ingredient.Ingredient;

/**
 * A utility class containing a list of {@code Ingredient} objects to be used in tests.
 */
public class TypicalIngredients {

    public static final Ingredient AVOCADO = new IngredientBuilder().withName("Mexican Avocado")
            .withUnit("kilogram")
            .withPrice("6.00")
            .withMinimum("1").build();
    public static final Ingredient BEANS = new IngredientBuilder().withName("Baked Beans")
            .withUnit("200-gram can")
            .withPrice("0.80")
            .withMinimum("10").build();
    public static final Ingredient CABBAGE = new IngredientBuilder().withName("Chinese Cabbage")
            .withUnit("500 grams")
            .withPrice("5.50")
            .withMinimum("10").build();
    public static final Ingredient DUCK = new IngredientBuilder().withName("Duck Drumstick")
            .withUnit("50 grams")
            .withPrice("3.20")
            .withMinimum("20").build();
    public static final Ingredient EGG = new IngredientBuilder().withName("Chicken Egg")
            .withUnit("carton of 30")
            .withPrice("3.80")
            .withMinimum("5").build();
    public static final Ingredient FISH = new IngredientBuilder().withName("White Promfret")
            .withUnit("kilogram")
            .withPrice("14.00")
            .withMinimum("8").build();
    public static final Ingredient GARLIC = new IngredientBuilder().withName("Garlic Cloves")
            .withUnit("500 gram bag")
            .withPrice("2.40")
            .withMinimum("3").build();

    // Manually added
    public static final Ingredient HAM = new IngredientBuilder().withName("Bacon Ham").withUnit("kilogram")
            .withPrice("13.00").withMinimum("6").build();
    public static final Ingredient ICECREAM = new IngredientBuilder().withName("Chocolate Ice Cream").withUnit("tub")
            .withPrice("5.80").withMinimum("10").build();

    // Manually added - Ingredient's details found in {@code CommandTestUtil}
    public static final Ingredient APPLE = new IngredientBuilder().withName(VALID_NAME_APPLE)
            .withUnit(VALID_UNIT_APPLE).withPrice(VALID_PRICE_APPLE).withMinimum(VALID_MINIMUM_APPLE).build();
    public static final Ingredient BROCCOLI = new IngredientBuilder().withName(VALID_NAME_BROCCOLI)
            .withUnit(VALID_UNIT_BROCCOLI).withPrice(VALID_PRICE_BROCCOLI).withMinimum(VALID_MINIMUM_BROCCOLI).build();

    private TypicalIngredients() {} // prevents instantiation

    public static List<Ingredient> getTypicalIngredients() {
        return new ArrayList<>(Arrays.asList(AVOCADO, BEANS, CABBAGE, DUCK, EGG, FISH, GARLIC));
    }

    /**
     * Returns an {@code AddressBook} with all the typical ingredients.
     */
    public static AddressBook getTypicalAddressBookWithIngredientsOnly() {
        AddressBook ab = new AddressBook();
        for (Ingredient ingredient : getTypicalIngredients()) {
            ab.addIngredient(ingredient);
        }
        return ab;
    }
}
