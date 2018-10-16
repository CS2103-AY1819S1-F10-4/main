package seedu.address.model.ingredient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class IngredientUnitTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new IngredientUnit(null));
    }

    @Test
    public void constructor_invalidUnit_throwsIllegalArgumentException() {
        String invalidUnit = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new IngredientUnit(invalidUnit));
    }

    @Test
    public void isValidUnit() {
        // null unit
        Assert.assertThrows(NullPointerException.class, () -> IngredientUnit.isValidUnit(null));

        // invalid unit
        assertFalse(IngredientUnit.isValidUnit("")); // empty string
        assertFalse(IngredientUnit.isValidUnit(" ")); // spaces only
        assertFalse(IngredientUnit.isValidUnit("^")); // only non-alphanumeric characters excluding hyphen
        assertFalse(IngredientUnit.isValidUnit("kilogram*")); // contains non-alphanumeric character

        // valid unit
        assertTrue(IngredientUnit.isValidUnit("kilogram")); // alphabets only
        assertTrue(IngredientUnit.isValidUnit("12345")); // numbers only
        assertTrue(IngredientUnit.isValidUnit("500 ml")); // alphanumeric characters
        assertTrue(IngredientUnit.isValidUnit("Bottles")); // with capital letters
        assertTrue(IngredientUnit.isValidUnit("5-kilogram packet")); // contains hyphen
        assertTrue(IngredientUnit.isValidUnit("10 crates of 20 50-gram cans")); // long names

    }
}
