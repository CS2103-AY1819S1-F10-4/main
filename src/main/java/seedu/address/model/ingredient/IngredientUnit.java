package seedu.address.model.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Ingredient's unit in the restaurant management app.
 * Guarantees: immutable; is valid as declared in {@link #isValidUnit(String)}
 */
public class IngredientUnit {

    public static final String MESSAGE_UNIT_CONSTRAINTS =
            "Ingredient units should only contain alphanumeric characters, hyphens or spaces, and it "
                    + "should not be blank";

    /*
     * The first character of the unit must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String UNIT_VALIDATION_REGEX = "[\\p{Alnum}[-]][\\p{Alnum}[-] ]*";

    public final String unitName;

    /**
     * Constructs an {@code IngredientUnit}.
     *
     * @param unit A valid ingredient unit.
     */
    public IngredientUnit(String unit) {
        requireNonNull(unit);
        checkArgument(isValidUnit(unit), MESSAGE_UNIT_CONSTRAINTS);
        unitName = unit;
    }

    /**
     * Returns true if a given string is a valid ingredient unit.
     */
    public static boolean isValidUnit(String test) {
        return test.matches(UNIT_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return unitName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IngredientUnit // instanceof handles nulls
                    && unitName.equals(((IngredientUnit) other).unitName)); // state check
    }

    @Override
    public int hashCode() {
        return unitName.hashCode();
    }
}
