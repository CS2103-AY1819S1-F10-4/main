package seedu.address.model.menu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NAME_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_PRICE_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_TAG_CHEESE;
import static seedu.address.testutil.menu.TypicalItems.APPLE_JUICE;
import static seedu.address.testutil.menu.TypicalItems.BEEF_BURGER;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.menu.ItemBuilder;

public class ItemTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Item item = new ItemBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        item.getTags().remove(0);
    }

    @Test
    public void isSameItem() {
        // same object -> returns true
        assertTrue(APPLE_JUICE.isSameItem(APPLE_JUICE));

        // null -> returns false
        assertFalse(APPLE_JUICE.isSameItem(null));

        // different price -> returns false
        Item editedApple = new ItemBuilder(APPLE_JUICE).withPrice(VALID_ITEM_PRICE_BURGER).build();
        assertFalse(APPLE_JUICE.isSameItem(editedApple));

        // different name -> returns false
        editedApple = new ItemBuilder(APPLE_JUICE).withName(VALID_ITEM_NAME_BURGER).build();
        assertFalse(APPLE_JUICE.isSameItem(editedApple));

        // same name, same price, different attributes -> returns true
        editedApple = new ItemBuilder(APPLE_JUICE).withTags(VALID_ITEM_TAG_CHEESE).build();
        assertTrue(APPLE_JUICE.isSameItem(editedApple));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Item appleCopy = new ItemBuilder(APPLE_JUICE).build();
        assertTrue(APPLE_JUICE.equals(appleCopy));

        // same object -> returns true
        assertTrue(APPLE_JUICE.equals(APPLE_JUICE));

        // null -> returns false
        assertFalse(APPLE_JUICE.equals(null));

        // different type -> returns false
        assertFalse(APPLE_JUICE.equals(5));

        // different item -> returns false
        assertFalse(APPLE_JUICE.equals(BEEF_BURGER));

        // different name -> returns false
        Item editedApple = new ItemBuilder(APPLE_JUICE).withName(VALID_ITEM_NAME_BURGER).build();
        assertFalse(APPLE_JUICE.equals(editedApple));

        // different price -> returns false
        editedApple = new ItemBuilder(APPLE_JUICE).withPrice(VALID_ITEM_PRICE_BURGER).build();
        assertFalse(APPLE_JUICE.equals(editedApple));

        // different tags -> returns false
        editedApple = new ItemBuilder(APPLE_JUICE).withTags(VALID_ITEM_TAG_CHEESE).build();
        assertFalse(APPLE_JUICE.equals(editedApple));
    }
}
