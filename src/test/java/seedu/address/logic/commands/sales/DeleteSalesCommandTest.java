package seedu.address.logic.commands.sales;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
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
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.salesrecord.SalesRecord;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for {@code
 * DeleteSalesCommand}.
 */
public class DeleteSalesCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        SalesRecord recordToDelete = model.getFilteredRecordList().get(INDEX_FIRST.getZeroBased());
        DeleteSalesCommand deleteSalesCommand = new DeleteSalesCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteSalesCommand.MESSAGE_DELETE_SALES_SUCCESS, recordToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteRecord(recordToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteSalesCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecordList().size() + 1);
        DeleteSalesCommand deleteSalesCommand = new DeleteSalesCommand(outOfBoundIndex);

        assertCommandFailure(deleteSalesCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showRecordAtIndex(model, INDEX_FIRST);

        SalesRecord recordToDelete = model.getFilteredRecordList().get(INDEX_FIRST.getZeroBased());
        DeleteSalesCommand deleteSalesCommand = new DeleteSalesCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteSalesCommand.MESSAGE_DELETE_SALES_SUCCESS, recordToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteRecord(recordToDelete);
        expectedModel.commitAddressBook();
        showNoRecord(expectedModel);

        assertCommandSuccess(deleteSalesCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showRecordAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of restaurant book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getRecordList().size());

        DeleteSalesCommand deleteSalesCommand = new DeleteSalesCommand(outOfBoundIndex);

        assertCommandFailure(deleteSalesCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        SalesRecord recordToDelete = model.getFilteredRecordList().get(INDEX_FIRST.getZeroBased());
        DeleteSalesCommand deleteSalesCommand = new DeleteSalesCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteRecord(recordToDelete);
        expectedModel.commitAddressBook();

        // delete -> first record deleted
        deleteSalesCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered record list to show all records
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first record deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecordList().size() + 1);
        DeleteSalesCommand deleteSalesCommand = new DeleteSalesCommand(outOfBoundIndex);

        // execution failed -> restaurant book state not added into model
        assertCommandFailure(deleteSalesCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);

        // single restaurant book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code SalesRecord} from a filtered list. 2. Undo the deletion. 3. The unfiltered list should be
     * shown now. Verify that the index of the previously deleted record in the unfiltered list is different from the
     * index at the filtered list. 4. Redo the deletion. This ensures {@code RedoCommand} deletes the record object
     * regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameRecordDeleted() throws Exception {
        DeleteSalesCommand deleteSalesCommand = new DeleteSalesCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showRecordAtIndex(model, INDEX_SECOND);
        SalesRecord recordToDelete = model.getFilteredRecordList().get(INDEX_FIRST.getZeroBased());
        expectedModel.deleteRecord(recordToDelete);
        expectedModel.commitAddressBook();

        // delete -> deletes second record in unfiltered record list / first record in filtered record list
        deleteSalesCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered record list to show all records
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(recordToDelete, model.getFilteredRecordList().get(INDEX_FIRST.getZeroBased()));

        // redo -> deletes same second record in unfiltered record list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteSalesCommand deleteSalesFirstCommand = new DeleteSalesCommand(INDEX_FIRST);
        DeleteSalesCommand deleteSalesSecondCommand = new DeleteSalesCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteSalesFirstCommand.equals(deleteSalesFirstCommand));

        // same values -> returns true
        DeleteSalesCommand deleteSalesFirstCommandCopy = new DeleteSalesCommand(INDEX_FIRST);
        assertTrue(deleteSalesFirstCommand.equals(deleteSalesFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteSalesFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteSalesFirstCommand.equals(null));

        // different record -> returns false
        assertFalse(deleteSalesFirstCommand.equals(deleteSalesSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no record.
     */
    private void showNoRecord(Model model) {
        model.updateFilteredRecordList(p -> false);
        assertTrue(model.getFilteredRecordList().isEmpty());
    }
}
