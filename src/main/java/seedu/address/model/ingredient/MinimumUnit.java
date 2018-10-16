package seedu.address.model.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the minimum threshold in terms of number of available units for an Ingredient in the restaurant
 * management app to be considered "low in stock count".
 * Guarantees: immutable; is valid as declared in {@link #isValidMinimum(String)}
 */
public class MinimumUnit {

    public static final String MESSAGE_MINIMUM_CONSTRAINTS =
            "Minimum unit should only contain numeric characters, and it should not be blank";

    /*
     * The first character of the price must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String MINIMUM_VALIDATION_REGEX = "^\\d+$";

    public final String minimumUnit;

    /**
     * Constructs a {@code MinimumUnit}.
     *
     * @param minimum A valid minimum number of units.
     */
    public MinimumUnit(String minimum) {
        requireNonNull(minimum);
        checkArgument(isValidMinimum(minimum), MESSAGE_MINIMUM_CONSTRAINTS);
        minimumUnit = minimum;
    }

    /**
     * Returns true if a given string is a valid minimum number of units.
     */
    public static boolean isValidMinimum(String test) {
        return test.matches(MINIMUM_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return minimumUnit;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MinimumUnit // instanceof handles nulls
                && minimumUnit.equals(((MinimumUnit) other).minimumUnit)); // state check
    }

    @Override
    public int hashCode() {
        return minimumUnit.hashCode();
    }
}
