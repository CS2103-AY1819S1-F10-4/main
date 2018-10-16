package seedu.address.model.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Ingredient's price per unit in dollar in the restaurant management app.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class IngredientPrice {

    public static final String MESSAGE_PRICE_CONSTRAINTS =
            "Ingredient price (per unit) should only contain numeric characters or decimal separator '.' "
                    + "up to 2 decimal places, and it should not be blank";

    /*
     * The first character of the price must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String PRICE_VALIDATION_REGEX = "^\\d+(.\\d{1,2})?$";

    public final String pricePerUnit;

    /**
     * Constructs an {@code IngredientPrice}.
     *
     * @param price A valid ingredient price per unit.
     */
    public IngredientPrice(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_PRICE_CONSTRAINTS);
        pricePerUnit = price;
    }

    /**
     * Returns true if a given string is a valid ingredient price.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(PRICE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return pricePerUnit;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IngredientPrice // instanceof handles nulls
                    && pricePerUnit.equals(((IngredientPrice) other).pricePerUnit)); // state check
    }

    @Override
    public int hashCode() {
        return pricePerUnit.hashCode();
    }
}
