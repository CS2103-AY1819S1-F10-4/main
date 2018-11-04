package seedu.restaurant.commons.events.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.BaseEvent;
import seedu.restaurant.commons.events.ui.reservation.JumpToReservationListRequestEvent;

public class JumpToReservationListRequestEventTest {

    @Test
    public void createEvent_success() {
        BaseEvent event = new JumpToReservationListRequestEvent(Index.fromOneBased(1));
        assertEquals("JumpToReservationListRequestEvent", event.toString());
    }
}