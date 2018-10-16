package seedu.address.logic.commands.menu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.DESC_FRIES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NAME_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_PRICE_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_TAG_BURGER;

import org.junit.Test;

import seedu.address.logic.commands.menu.EditItemCommand.EditItemDescriptor;
import seedu.address.testutil.menu.EditItemDescriptorBuilder;

public class EditItemDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditItemDescriptor descriptorWithSameValues = new EditItemDescriptor(DESC_FRIES);
        assertTrue(DESC_FRIES.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_FRIES.equals(DESC_FRIES));

        // null -> returns false
        assertFalse(DESC_FRIES.equals(null));

        // different types -> returns false
        assertFalse(DESC_FRIES.equals(5));

        // different values -> returns false
        assertFalse(DESC_FRIES.equals(DESC_BURGER));

        // different name -> returns false
        EditItemDescriptor editedFries = new EditItemDescriptorBuilder(DESC_FRIES)
                .withName(VALID_ITEM_NAME_BURGER).build();
        assertFalse(DESC_FRIES.equals(editedFries));

        // different price -> returns false
        editedFries = new EditItemDescriptorBuilder(DESC_FRIES).withPrice(VALID_ITEM_PRICE_BURGER).build();
        assertFalse(DESC_FRIES.equals(editedFries));

        // different tags -> returns false
        editedFries = new EditItemDescriptorBuilder(DESC_FRIES).withTags(VALID_ITEM_TAG_BURGER).build();
        assertFalse(DESC_FRIES.equals(editedFries));
    }
}
