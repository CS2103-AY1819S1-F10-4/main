package seedu.address.model.salesrecord;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the quantity of a menu item sold in the sales record
 * Guarantees: immutable; is valid as declared in {@link #isValidQuantity(String)}
 */
public class QuantitySold {

    public static final String MESSAGE_QUANTITY_CONSTRAINTS =
            "Quantity sold should be a positive integer.";
    public static final String QUANTITY_VALIDATION_REGEX = "\\d{1,}";

    private final int value;

    /**
     * Constructs a {@code QuantitySold}.
     *
     * @param quantitySold A valid integer of string representation
     */
    public QuantitySold(String quantitySold) {
        requireNonNull(quantitySold);
        checkArgument(isValidQuantity(quantitySold), MESSAGE_QUANTITY_CONSTRAINTS);
        value = Integer.parseInt(quantitySold);
    }

    public int getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid positive integer.
     */
    public static boolean isValidQuantity(String test) {
        return test.matches(QUANTITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuantitySold // instanceof handles nulls
                    && value == ((QuantitySold) other).value); // state check
    }

    @Override
    public int hashCode() {
        return String.valueOf(value).hashCode();
    }
}
