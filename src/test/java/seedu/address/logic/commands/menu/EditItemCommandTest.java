package seedu.address.logic.commands.menu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.DESC_FRIES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NAME_FRIES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_PRICE_FRIES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_TAG_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.menu.MenuCommandTestUtil.showItemAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.menu.EditItemCommand.EditItemDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.menu.Item;
import seedu.address.testutil.menu.EditItemDescriptorBuilder;
import seedu.address.testutil.menu.ItemBuilder;
import seedu.address.testutil.menu.TypicalItems;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditItemCommandTest {

    private Model model = new ModelManager(TypicalItems.getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Item editedItem = new ItemBuilder().build();
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder(editedItem).build();
        EditItemCommand editItemCommand = new EditItemCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditItemCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateItem(model.getFilteredItemList().get(0), editedItem);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastItem = Index.fromOneBased(model.getFilteredItemList().size());
        Item lastItem = model.getFilteredItemList().get(indexLastItem.getZeroBased());

        ItemBuilder itemInList = new ItemBuilder(lastItem);
        Item editedItem = itemInList.withName(VALID_ITEM_NAME_FRIES).withPrice(VALID_ITEM_PRICE_FRIES)
                .withTags(VALID_ITEM_TAG_CHEESE).build();

        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withName(VALID_ITEM_NAME_FRIES)
                .withPrice(VALID_ITEM_PRICE_FRIES).withTags(VALID_ITEM_TAG_CHEESE).build();
        EditItemCommand editItemCommand = new EditItemCommand(indexLastItem, descriptor);

        String expectedMessage = String.format(EditItemCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateItem(lastItem, editedItem);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditItemCommand editItemCommand = new EditItemCommand(INDEX_FIRST, new EditItemDescriptor());
        Item editedItem = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditItemCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showItemAtIndex(model, INDEX_FIRST);

        Item itemInFilteredList = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        Item editedItem = new ItemBuilder(itemInFilteredList).withName(VALID_ITEM_NAME_FRIES).build();
        EditItemCommand editItemCommand = new EditItemCommand(INDEX_FIRST,
                new EditItemDescriptorBuilder().withName(VALID_ITEM_NAME_FRIES).build());

        String expectedMessage = String.format(EditItemCommand.MESSAGE_EDIT_ITEM_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateItem(model.getFilteredItemList().get(0), editedItem);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateItemUnfilteredList_failure() {
        Item firstItem = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder(firstItem).build();
        EditItemCommand editCommand = new EditItemCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditItemCommand.MESSAGE_DUPLICATE_ITEM);
    }

    @Test
    public void execute_duplicateItemFilteredList_failure() {
        showItemAtIndex(model, INDEX_FIRST);

        // edit item in filtered list into a duplicate in menu
        Item itemInList = model.getAddressBook().getItemList().get(INDEX_SECOND.getZeroBased());
        EditItemCommand editItemCommand = new EditItemCommand(INDEX_FIRST,
                new EditItemDescriptorBuilder(itemInList).build());

        assertCommandFailure(editItemCommand, model, commandHistory, EditItemCommand.MESSAGE_DUPLICATE_ITEM);
    }

    @Test
    public void execute_invalidItemIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withName(VALID_ITEM_NAME_FRIES).build();
        EditItemCommand editItemCommand = new EditItemCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editItemCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list, but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showItemAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getItemList().size());

        EditItemCommand editItemCommand = new EditItemCommand(outOfBoundIndex,
                new EditItemDescriptorBuilder().withName(VALID_ITEM_NAME_FRIES).build());

        assertCommandFailure(editItemCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Item editedItem = new ItemBuilder().build();
        Item itemToEdit = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder(editedItem).build();
        EditItemCommand editItemCommand = new EditItemCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateItem(itemToEdit, editedItem);
        expectedModel.commitAddressBook();

        // edit -> first item edited
        editItemCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered item list to show all items
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first item edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withName(VALID_ITEM_NAME_FRIES).build();
        EditItemCommand editItemCommand = new EditItemCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editItemCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Item} from a filtered list. 2. Undo the edit. 3. The unfiltered list should be shown now.
     * Verify that the index of the previously edited item in the unfiltered list is different from the index at the
     * filtered list. 4. Redo the edit. This ensures {@code RedoCommand} edits the item object regardless of
     * indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameItemEdited() throws Exception {
        Item editedItem = new ItemBuilder().build();
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder(editedItem).build();
        EditItemCommand editItemCommand = new EditItemCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showItemAtIndex(model, INDEX_SECOND);
        Item itemToEdit = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        expectedModel.updateItem(itemToEdit, editedItem);
        expectedModel.commitAddressBook();

        // edit -> edits second item in unfiltered item list / first item in filtered item list
        editItemCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered item list to show all items
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredItemList().get(INDEX_FIRST.getZeroBased()), itemToEdit);
        // redo -> edits same second item in unfiltered item list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditItemCommand standardCommand = new EditItemCommand(INDEX_FIRST, DESC_FRIES);

        // same values -> returns true
        EditItemDescriptor copyDescriptor = new EditItemDescriptor(DESC_FRIES);
        EditItemCommand commandWithSameValues = new EditItemCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearMenuCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditItemCommand(INDEX_SECOND, DESC_FRIES)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditItemCommand(INDEX_FIRST, DESC_BURGER)));
    }

}
