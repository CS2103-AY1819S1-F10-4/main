package seedu.address.logic.commands.sales;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_RECORD_ONE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_RECORD_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_RECORD_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NAME_RECORD_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NAME_RECORD_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_RECORD_THREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_SOLD_RECORD_TWO;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showRecordAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.salescommands.EditSalesCommand;
import seedu.address.logic.commands.salescommands.EditSalesCommand.EditRecordDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.salesrecord.SalesRecord;
import seedu.address.testutil.salesrecords.EditRecordDescriptorBuilder;
import seedu.address.testutil.salesrecords.RecordBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditSalesCommand.
 */
public class EditSalesCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        SalesRecord editedRecord = new RecordBuilder().build();
        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder(editedRecord).build();
        EditSalesCommand editSalesCommand = new EditSalesCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditSalesCommand.MESSAGE_EDIT_RECORD_SUCCESS, editedRecord);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateRecord(model.getFilteredRecordList().get(0), editedRecord);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editSalesCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastRecord = Index.fromOneBased(model.getFilteredRecordList().size());
        SalesRecord lastRecord = model.getFilteredRecordList().get(indexLastRecord.getZeroBased());

        RecordBuilder recordInList = new RecordBuilder(lastRecord);
        SalesRecord editedRecord = recordInList
                .withName(VALID_ITEM_NAME_RECORD_ONE)
                .withQuantitySold(VALID_QUANTITY_SOLD_RECORD_TWO)
                .withPrice(VALID_PRICE_RECORD_THREE).build();

        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder()
                .withName(VALID_ITEM_NAME_RECORD_ONE)
                .withQuantitySold(VALID_QUANTITY_SOLD_RECORD_TWO)
                .withPrice(VALID_PRICE_RECORD_THREE).build();

        EditSalesCommand editSalesCommand = new EditSalesCommand(indexLastRecord, descriptor);

        String expectedMessage = String.format(EditSalesCommand.MESSAGE_EDIT_RECORD_SUCCESS, editedRecord);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateRecord(lastRecord, editedRecord);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editSalesCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditSalesCommand editSalesCommand = new EditSalesCommand(INDEX_FIRST, new EditRecordDescriptor());
        SalesRecord editedRecord = model.getFilteredRecordList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditSalesCommand.MESSAGE_EDIT_RECORD_SUCCESS, editedRecord);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editSalesCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showRecordAtIndex(model, INDEX_FIRST);

        SalesRecord recordInFilteredList = model.getFilteredRecordList().get(INDEX_FIRST.getZeroBased());
        SalesRecord editedRecord = new RecordBuilder(recordInFilteredList).withDate(VALID_DATE_RECORD_TWO).build();
        EditSalesCommand editSalesCommand = new EditSalesCommand(INDEX_FIRST,
                new EditRecordDescriptorBuilder().withDate(VALID_DATE_RECORD_TWO).build());

        String expectedMessage = String.format(EditSalesCommand.MESSAGE_EDIT_RECORD_SUCCESS, editedRecord);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateRecord(model.getFilteredRecordList().get(0), editedRecord);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editSalesCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateRecordUnfilteredList_failure() {
        SalesRecord firstRecord = model.getFilteredRecordList().get(INDEX_FIRST.getZeroBased());
        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder(firstRecord).build();
        EditSalesCommand editSalesCommand = new EditSalesCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editSalesCommand, model, commandHistory, EditSalesCommand.MESSAGE_DUPLICATE_RECORD);
    }

    @Test
    public void execute_duplicateRecordFilteredList_failure() {
        showRecordAtIndex(model, INDEX_FIRST);

        // edit record in filtered list into a duplicate in address book
        SalesRecord recordInList = model.getAddressBook().getRecordList().get(INDEX_SECOND.getZeroBased());
        EditSalesCommand editSalesCommand = new EditSalesCommand(INDEX_FIRST,
                new EditRecordDescriptorBuilder(recordInList).build());

        assertCommandFailure(editSalesCommand, model, commandHistory, EditSalesCommand.MESSAGE_DUPLICATE_RECORD);
    }

    @Test
    public void execute_invalidRecordIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecordList().size() + 1);
        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder().withName(VALID_ITEM_NAME_RECORD_TWO).build();
        EditSalesCommand editSalesCommand = new EditSalesCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editSalesCommand, model, commandHistory, Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list, but smaller than size of address book
     */
    @Test
    public void execute_invalidRecordIndexFilteredList_failure() {
        showRecordAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getRecordList().size());

        EditSalesCommand editSalesCommand = new EditSalesCommand(outOfBoundIndex,
                new EditRecordDescriptorBuilder().withName(VALID_ITEM_NAME_RECORD_TWO).build());

        assertCommandFailure(editSalesCommand, model, commandHistory, Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        SalesRecord editedRecord = new RecordBuilder().build();
        SalesRecord recordToEdit = model.getFilteredRecordList().get(INDEX_FIRST.getZeroBased());
        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder(editedRecord).build();
        EditSalesCommand editSalesCommand = new EditSalesCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateRecord(recordToEdit, editedRecord);
        expectedModel.commitAddressBook();

        // edit -> first record edited
        editSalesCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered sales record list to show all records
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first record edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecordList().size() + 1);
        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder().withName(VALID_ITEM_NAME_RECORD_TWO).build();
        EditSalesCommand editSalesCommand = new EditSalesCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editSalesCommand, model, commandHistory, Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code SalesRecord} from a filtered list. 2. Undo the edit. 3. The unfiltered list should be shown
     * now.
     * Verify that the index of the previously edited record in the unfiltered list is different from the index at the
     * filtered list. 4. Redo the edit. This ensures {@code RedoCommand} edits the record object regardless of
     * indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameRecordEdited() throws Exception {
        SalesRecord editedRecord = new RecordBuilder().withDate(VALID_DATE_RECORD_TWO).build();
        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder(editedRecord).build();
        EditSalesCommand editSalesCommand = new EditSalesCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showRecordAtIndex(model, INDEX_SECOND);
        SalesRecord recordToEdit = model.getFilteredRecordList().get(INDEX_FIRST.getZeroBased());
        expectedModel.updateRecord(recordToEdit, editedRecord);
        expectedModel.commitAddressBook();

        // edit -> edits second record in unfiltered record list / first record in filtered record list
        editSalesCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered record list to show all records
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredRecordList().get(INDEX_FIRST.getZeroBased()), recordToEdit);
        // redo -> edits same second record in unfiltered record list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditSalesCommand standardCommand = new EditSalesCommand(INDEX_FIRST, DESC_RECORD_ONE);

        // same values -> returns true
        EditRecordDescriptor copyDescriptor = new EditRecordDescriptor(DESC_RECORD_ONE);
        EditSalesCommand commandWithSameValues = new EditSalesCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditSalesCommand(INDEX_SECOND, DESC_RECORD_ONE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditSalesCommand(INDEX_FIRST, DESC_RECORD_TWO)));
    }
}
