package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.menu.Item;

/**
 * Represents a selection change in the Person List Panel
 */
public class ItemPanelSelectionChangedEvent extends BaseEvent {


    private final Item newSelection;

    public ItemPanelSelectionChangedEvent(Item newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Item getNewSelection() {
        return newSelection;
    }
}