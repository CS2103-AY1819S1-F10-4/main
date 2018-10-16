package seedu.address.model.salesrecord;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.collections.ObservableList;

/**
 * Represents a sales report of a specific date in the sales book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class SalesReport {

    private final Date date;
    private final ObservableList<SalesRecord> records;
    private final double totalRevenue;

    /**
     * Every field must be present and not null.
     */
    public SalesReport(Date date, ObservableList<SalesRecord> records) {
        requireAllNonNull(date, records);
        this.date = date;
        this.records = records;
        this.totalRevenue = computeTotalRevenue(records);
    }

    public Date getDate() {
        return date;
    }

    public ObservableList<SalesRecord> getRecords() {
        return records;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    /**
     * Computes the total revenue for the day using each SalesRecord's revenue
     * @param records List of SalesRecord belonging to the day of interest
     * @return the total revenue for the day
     */
    private double computeTotalRevenue(ObservableList<SalesRecord> records) {
        double total = 0;
        for (SalesRecord s: records) {
            total += s.getRevenue();
        }
        return total;
    }

    /**
     * Returns true if both reports have the same fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SalesReport)) {
            return false;
        }

        SalesReport otherReport = (SalesReport) other;
        return otherReport.getDate().equals(getDate())
                && otherReport.getRecords().equals(getRecords())
                && otherReport.getTotalRevenue() == getTotalRevenue();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, records, totalRevenue);
    }

    @Override
    public String toString() {
        return "Sales Report for " + getDate();
    }
}
