package seedu.address.model.reservation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESERVATION_PAX_BILLY;
import static seedu.address.testutil.reservation.TypicalReservations.ANDREW;
import static seedu.address.testutil.reservation.TypicalReservations.BILLY;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.reservation.exceptions.DuplicateReservationException;
import seedu.address.model.reservation.exceptions.ReservationNotFoundException;
import seedu.address.testutil.reservation.ReservationBuilder;

public class UniqueReservationListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueReservationList uniqueReservationList = new UniqueReservationList();

    @Test
    public void contains_nullReservation_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReservationList.contains(null);
    }

    @Test
    public void contains_reservationNotInList_returnsFalse() {
        assertFalse(uniqueReservationList.contains(ANDREW));
    }

    @Test
    public void contains_reservationInList_returnsTrue() {
        uniqueReservationList.add(ANDREW);
        assertTrue(uniqueReservationList.contains(ANDREW));
    }

    @Test
    public void contains_reservationWithSameIdentityFieldsInList_returnsTrue() {
        uniqueReservationList.add(ANDREW);
        Reservation editedAndrew =
                new ReservationBuilder(ANDREW).withPax(VALID_RESERVATION_PAX_BILLY).build();
        assertTrue(uniqueReservationList.contains(editedAndrew));
    }

    @Test
    public void add_nullReservation_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReservationList.add(null);
    }

    @Test
    public void add_duplicateReservation_throwsDuplicateReservationException() {
        uniqueReservationList.add(ANDREW);
        thrown.expect(DuplicateReservationException.class);
        uniqueReservationList.add(ANDREW);
    }

    @Test
    public void setReservation_nullTargetReservation_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReservationList.setReservation(null, ANDREW);
    }

    @Test
    public void setReservation_nullEditedReservation_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReservationList.setReservation(ANDREW, null);
    }

    @Test
    public void setReservation_targetReservationNotInList_throwsReservationNotFoundException() {
        thrown.expect(ReservationNotFoundException.class);
        uniqueReservationList.setReservation(ANDREW, ANDREW);
    }

    @Test
    public void setReservation_editedReservationIsSameReservation_success() {
        uniqueReservationList.add(ANDREW);
        uniqueReservationList.setReservation(ANDREW, ANDREW);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        expectedUniqueReservationList.add(ANDREW);
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setReservation_editedReservationHasSameIdentity_success() {
        uniqueReservationList.add(ANDREW);
        Reservation editedAndrew = new ReservationBuilder(ANDREW).withPax(VALID_RESERVATION_PAX_BILLY).build();
        uniqueReservationList.setReservation(ANDREW, editedAndrew);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        expectedUniqueReservationList.add(editedAndrew);
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setReservation_editedReservationHasDifferentIdentity_success() {
        uniqueReservationList.add(ANDREW);
        uniqueReservationList.setReservation(ANDREW, BILLY);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        expectedUniqueReservationList.add(BILLY);
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setReservation_editedReservationHasNonUniqueIdentity_throwsDuplicateReservationException() {
        uniqueReservationList.add(ANDREW);
        uniqueReservationList.add(BILLY);
        thrown.expect(DuplicateReservationException.class);
        uniqueReservationList.setReservation(ANDREW, BILLY);
    }

    @Test
    public void remove_nullReservation_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReservationList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsReservationNotFoundException() {
        thrown.expect(ReservationNotFoundException.class);
        uniqueReservationList.remove(ANDREW);
    }

    @Test
    public void remove_existingReservation_removesReservation() {
        uniqueReservationList.add(ANDREW);
        uniqueReservationList.remove(ANDREW);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setReservations_nullUniqueReservationList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReservationList.setReservations((UniqueReservationList) null);
    }

    @Test
    public void setReservations_uniqueReservationList_replacesOwnListWithProvidedUniqueReservationList() {
        uniqueReservationList.add(ANDREW);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        expectedUniqueReservationList.add(BILLY);
        uniqueReservationList.setReservations(expectedUniqueReservationList);
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setReservations_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReservationList.setReservations((List<Reservation>) null);
    }

    @Test
    public void setReservations_list_replacesOwnListWithProvidedList() {
        uniqueReservationList.add(ANDREW);
        List<Reservation> personList = Collections.singletonList(BILLY);
        uniqueReservationList.setReservations(personList);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        expectedUniqueReservationList.add(BILLY);
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setReservations_listWithDuplicateReservations_throwsDuplicateReservationException() {
        List<Reservation> listWithDuplicateReservations = Arrays.asList(ANDREW, ANDREW);
        thrown.expect(DuplicateReservationException.class);
        uniqueReservationList.setReservations(listWithDuplicateReservations);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueReservationList.asUnmodifiableObservableList().remove(0);
    }
}
