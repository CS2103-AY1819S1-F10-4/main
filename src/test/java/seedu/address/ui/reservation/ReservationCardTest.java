package seedu.address.ui.reservation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysReservation;

import org.junit.Test;

import guitests.guihandles.reservation.ReservationCardHandle;
import seedu.address.model.reservation.Reservation;
import seedu.address.testutil.reservation.ReservationBuilder;
import seedu.address.ui.GuiUnitTest;

public class ReservationCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Reservation reservationWithNoTags = new ReservationBuilder().withTags(new String[0]).build();
        ReservationCard reservationCard = new ReservationCard(reservationWithNoTags, 1);
        uiPartRule.setUiPart(reservationCard);
        assertCardDisplay(reservationCard, reservationWithNoTags, 1);

        // with tags
        Reservation reservationWithTags = new ReservationBuilder().build();
        reservationCard = new ReservationCard(reservationWithTags, 2);
        uiPartRule.setUiPart(reservationCard);
        assertCardDisplay(reservationCard, reservationWithTags, 2);
    }

    @Test
    public void equals() {
        Reservation reservation = new ReservationBuilder().build();
        ReservationCard reservationCard = new ReservationCard(reservation, 0);

        // same reservation, same index -> returns true
        ReservationCard copy = new ReservationCard(reservation, 0);
        assertTrue(reservationCard.equals(copy));

        // same object -> returns true
        assertTrue(reservationCard.equals(reservationCard));

        // null -> returns false
        assertFalse(reservationCard.equals(null));

        // different types -> returns false
        assertFalse(reservationCard.equals(0));

        // different reservation, same index -> returns false
        Reservation differentReservation = new ReservationBuilder().withName("differentName").build();
        assertFalse(reservationCard.equals(new ReservationCard(differentReservation, 0)));

        // same reservation, different index -> returns false
        assertFalse(reservationCard.equals(new ReservationCard(reservation, 1)));
    }

    /**
     * Asserts that {@code reservationCard} displays the details of {@code expectedReservation} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ReservationCard reservationCard, Reservation expectedReservation, int expectedId) {
        guiRobot.pauseForHuman();

        ReservationCardHandle reservationCardHandle = new ReservationCardHandle(reservationCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", reservationCardHandle.getId());

        // verify reservation details are displayed correctly
        assertCardDisplaysReservation(expectedReservation, reservationCardHandle);
    }
}
