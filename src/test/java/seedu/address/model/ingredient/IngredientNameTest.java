package seedu.address.model.ingredient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class IngredientNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new IngredientName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new IngredientName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> IngredientName.isValidName(null));

        // invalid name
        assertFalse(IngredientName.isValidName("")); // empty string
        assertFalse(IngredientName.isValidName(" ")); // spaces only
        assertFalse(IngredientName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(IngredientName.isValidName("fish*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(IngredientName.isValidName("fish fillet")); // alphabets only
        assertTrue(IngredientName.isValidName("12345")); // numbers only
        assertTrue(IngredientName.isValidName("chicken 2")); // alphanumeric characters
        assertTrue(IngredientName.isValidName("Chicken Thigh")); // with capital letters
        assertTrue(IngredientName.isValidName("Chicken Thigh without bone")); // long names
    }
}
