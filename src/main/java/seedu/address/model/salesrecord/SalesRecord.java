package seedu.address.model.salesrecord;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.person.Name;

/**
 * Represents a sales record in the sales book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class SalesRecord {

    // Identity field
    private final Date date;
    private final Name name;   // uses name from person

    // Data fields
    private final QuantitySold quantitySold;
    private final Price price;

    /**
     * Every field must be present and not null.
     */
    public SalesRecord(Date date, Name name, QuantitySold quantitySold, Price price) {
        requireAllNonNull(date, name, quantitySold, price);
        this.date = date;
        this.name = name;
        this.quantitySold = quantitySold;
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public Name getName() {
        return name;
    }

    public QuantitySold getQuantitySold() {
        return quantitySold;
    }

    public Price getPrice() {
        return price;
    }

    /**
     * Returns true if both records have the same identity field (i.e. same date and name).
     * This defines a weaker notion of equality between two records.
     */
    public boolean isSameRecord(SalesRecord otherRecord) {
        return getName().equals(otherRecord.getName()) && getDate().equals(otherRecord.getDate());
    }

    /**
     * Returns true if both records have the same identity and data fields.
     * This defines a stronger notion of equality between two records.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SalesRecord)) {
            return false;
        }

        SalesRecord otherRecord = (SalesRecord) other;
        return otherRecord.getDate().equals(getDate())
                && otherRecord.getName().equals(getName())
                && otherRecord.getQuantitySold().equals(getQuantitySold())
                && otherRecord.getPrice().equals(getPrice());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, name, quantitySold, price);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Date: ")
                .append(getDate())
                .append(", Item Name:  ")
                .append(getName())
                .append(", Quantity Sold: ")
                .append(getQuantitySold())
                .append(", Item Price: $")
                .append(getPrice());
        return builder.toString();
    }
}