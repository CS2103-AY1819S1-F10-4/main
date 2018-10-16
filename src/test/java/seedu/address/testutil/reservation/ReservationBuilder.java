package seedu.address.testutil.reservation;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Pax;
import seedu.address.model.reservation.Remark;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Reservation objects.
 */
public class ReservationBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PAX = "4";
    public static final String DEFAULT_DATE = "2007-12-03T10:15:30";
    public static final String DEFAULT_REMARK = "";

    private Name name;
    private Pax pax;
    private LocalDateTime dateTime;
    private Remark remark;
    private Set<Tag> tags;

    public ReservationBuilder() {
        name = new Name(DEFAULT_NAME);
        pax = new Pax(DEFAULT_PAX);
        dateTime = LocalDateTime.parse(DEFAULT_DATE);
        remark = new Remark(DEFAULT_REMARK);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ReservationBuilder with the data of {@code reservationToCopy}.
     */
    public ReservationBuilder(Reservation reservationToCopy) {
        name = reservationToCopy.getName();
        pax = reservationToCopy.getPax();
        dateTime = reservationToCopy.getDateTime();
        remark = reservationToCopy.getRemark();
        tags = new HashSet<>(reservationToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Reservation} that we are building.
     */
    public ReservationBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code dateTime} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withDateTime(String dateTime) {
        this.dateTime = LocalDateTime.parse(dateTime);
        return this;
    }

    /**
     * Sets the {@code Pax} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withPax(String pax) {
        this.pax = new Pax(pax);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    public Reservation build() {
        return new Reservation(name, pax, dateTime, remark, tags);
    }

}
