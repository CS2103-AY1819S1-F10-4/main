package seedu.address.testutil.reservation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.reservation.Reservation;

/**
 * A utility class containing a list of {@code Reservation} objects to be used in tests.
 */
public class TypicalReservations {

    public static final Reservation ANDREW = new ReservationBuilder().withName("Andrew")
            .withPax("2")
            .withDateTime("2018-12-03T12:00:00").build();
    public static final Reservation BILLY = new ReservationBuilder().withName("Billy")
            .withPax("4")
            .withDateTime("2018-12-05T18:00:00").build();
    public static final Reservation CARL = new ReservationBuilder().withName("Carl Kurz")
            .withPax("1")
            .withDateTime("2018-10-03T15:00:00").build();
    public static final Reservation DANIEL = new ReservationBuilder().withName("Daniel Meier")
            .withPax("3")
            .withDateTime("2018-10-04T12:00:00")
            .withTags("driving").build();
    public static final Reservation ELLE = new ReservationBuilder().withName("Elle Meyer")
            .withPax("2")
            .withDateTime("2018-10-04T13:00:00").build();
    public static final Reservation FIONA = new ReservationBuilder().withName("Fiona Kunz")
            .withPax("8")
            .withDateTime("2018-10-05T18:00:00").build();
    public static final Reservation GEORGE = new ReservationBuilder().withName("George Best")
            .withPax("2")
            .withDateTime("2018-10-05T19:00:00").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalReservations() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical reservations.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Reservation reservation : getTypicalReservations()) {
            ab.addReservation(reservation);
        }
        return ab;
    }

    public static List<Reservation> getTypicalReservations() {
        return new ArrayList<>(Arrays.asList(ANDREW, BILLY, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
