package seedu.address.commons.events.ui.reservation;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.reservation.Reservation;

/**
 * Represents a selection change in the Reservation List Panel
 */
public class ReservationPanelSelectionChangedEvent extends BaseEvent {


    private final Reservation newSelection;

    public ReservationPanelSelectionChangedEvent(Reservation newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Reservation getNewSelection() {
        return newSelection;
    }
}
