package seedu.address.testutil.menu;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NAME_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NAME_FRIES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_PRICE_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_PRICE_FRIES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_TAG_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_TAG_CHEESE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import seedu.address.model.menu.Item;

/**
 * A utility class containing a list of {@code Item} objects to be used in tests.
 */
public class TypicalItems {
    public static final Item ITEM_DEFAULT = new ItemBuilder().build();

    public static final Item APPLE_JUICE = new ItemBuilder().withName("Apple Juice")
            .withPrice("2")
            .withTags("drink", "monday").withRequiredIngredients(Map.of("Apple", "3")).build();
    public static final Item BEEF_BURGER = new ItemBuilder().withName("Beef Burger")
            .withPrice("3")
            .withTags("beef", VALID_ITEM_TAG_BURGER, "tuesday").build();
    public static final Item CHOCO_CAKE = new ItemBuilder().withName("Chocolate Cake").withPrice("2")
            .withTags("wednesday").build();
    public static final Item DUCK_RICE = new ItemBuilder().withName("Duck Rice").withPrice("3")
            .withTags("thursday").build();
    public static final Item EGG = new ItemBuilder().withName("Egg").withPrice("0.5")
            .withTags("friday").build();
    public static final Item FRUIT_CAKE = new ItemBuilder().withName("Fruit Cake").withPrice("2")
            .withTags("saturday").build();
    public static final Item GARLIC_BREAD = new ItemBuilder().withName("Garlic Bread").withPrice("1")
            .withTags("sunday").withRecipe("Bread topped with garlic and olive oil").build();

    // Manually added
    public static final Item HAINANESE = new ItemBuilder().withName("Hainanese Chicken Rice").withPrice("2.50").build();
    public static final Item ICED_TEA = new ItemBuilder().withName("Iced Tea").withPrice("1.50").build();

    // Manually added - Item's details found in {@code CommandTestUtil}
    public static final Item BURGER = new ItemBuilder().withName(VALID_ITEM_NAME_BURGER)
            .withPrice(VALID_ITEM_PRICE_BURGER).build();
    public static final Item FRIES = new ItemBuilder().withName(VALID_ITEM_NAME_FRIES)
            .withPrice(VALID_ITEM_PRICE_FRIES).withTags(VALID_ITEM_TAG_CHEESE).build();
    public static final Item CHEESE_BURGER = new ItemBuilder().withName("Cheese Burger").withPrice("3")
            .withTags(VALID_ITEM_TAG_BURGER, VALID_ITEM_TAG_CHEESE).build();

    public static final String KEYWORD_MATCHING_HAINANESE = "Hainanese"; // A keyword that matches HAINANESE

    private TypicalItems() {} // prevents instantiation

    public static List<Item> getTypicalItems() {
        return new ArrayList<>(
                Arrays.asList(APPLE_JUICE, BEEF_BURGER, CHOCO_CAKE, DUCK_RICE, EGG, FRUIT_CAKE, GARLIC_BREAD));
    }
}
