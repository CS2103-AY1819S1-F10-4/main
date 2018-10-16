package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ingredient.Ingredient;

/**
 * Represents a selection change in the Ingredient List Panel
 */
public class IngredientPanelSelectionChangedEvent extends BaseEvent {


    private final Ingredient newSelection;

    public IngredientPanelSelectionChangedEvent(Ingredient newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Ingredient getNewSelection() {
        return newSelection;
    }
}
