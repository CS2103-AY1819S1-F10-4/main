package seedu.address.model.ingredient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class IngredientPriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new IngredientPrice(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        String invalidPrice = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new IngredientPrice(invalidPrice));
    }

    @Test
    public void isValidPrice() {
        // null price
        Assert.assertThrows(NullPointerException.class, () -> IngredientPrice.isValidPrice(null));

        // invalid price
        assertFalse(IngredientPrice.isValidPrice("")); // empty string
        assertFalse(IngredientPrice.isValidPrice(" ")); // spaces only
        assertFalse(IngredientPrice.isValidPrice("abcde^")); // only non-numeric characters
        assertFalse(IngredientPrice.isValidPrice("1.2ab*")); // contains non-numeric characters
        assertFalse(IngredientPrice.isValidPrice("567.")); // numeric value with decimal separator but no decimal
        assertFalse(IngredientPrice.isValidPrice("1.999")); // numeric value with more than 2 decimal places

        // valid price
        assertTrue(IngredientPrice.isValidPrice("800")); // numeric value with no decimal
        assertTrue(IngredientPrice.isValidPrice("200.2")); // numeric value with one decimal place
        assertTrue(IngredientPrice.isValidPrice("321.30")); // numeric value with two decimal places
    }
}
