package seedu.address.logic.commands.reservation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.reservation.ReservationCommandTestUtil.showReservationAtIndex;
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
import seedu.address.model.reservation.Reservation;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for {@code
 * DeleteCommand}.
 */
public class DeleteReservationCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Reservation reservationToDelete = model.getFilteredReservationList().get(INDEX_FIRST.getZeroBased());
        DeleteReservationCommand deleteCommand = new DeleteReservationCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteReservationCommand.MESSAGE_DELETE_RESERVATION_SUCCESS,
                reservationToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteReservation(reservationToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReservationList().size() + 1);
        DeleteReservationCommand deleteCommand = new DeleteReservationCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showReservationAtIndex(model, INDEX_FIRST);

        Reservation reservationToDelete = model.getFilteredReservationList().get(INDEX_FIRST.getZeroBased());
        DeleteReservationCommand deleteCommand = new DeleteReservationCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteReservationCommand.MESSAGE_DELETE_RESERVATION_SUCCESS,
                reservationToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteReservation(reservationToDelete);
        expectedModel.commitAddressBook();
        showNoReservation(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showReservationAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getReservationList().size());

        DeleteReservationCommand deleteCommand = new DeleteReservationCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Reservation reservationToDelete = model.getFilteredReservationList().get(INDEX_FIRST.getZeroBased());
        DeleteReservationCommand deleteCommand = new DeleteReservationCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteReservation(reservationToDelete);
        expectedModel.commitAddressBook();

        // delete -> first reservation deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered reservation list to show all reservations
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first reservation deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReservationList().size() + 1);
        DeleteReservationCommand deleteCommand = new DeleteReservationCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Reservation} from a filtered list. 2. Undo the deletion. 3. The unfiltered list should be
     * shown now. Verify that the index of the previously deleted reservation in the unfiltered list is different
     * from the index at the filtered list. 4. Redo the deletion. This ensures {@code RedoCommand} deletes the
     * reservation object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameReservationDeleted() throws Exception {
        DeleteReservationCommand deleteReservationCommand = new DeleteReservationCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showReservationAtIndex(model, INDEX_SECOND);
        Reservation reservationToDelete = model.getFilteredReservationList().get(INDEX_FIRST.getZeroBased());
        expectedModel.deleteReservation(reservationToDelete);
        expectedModel.commitAddressBook();

        // delete -> deletes second reservation in unfiltered list / first reservation in filtered list
        deleteReservationCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered reservation list to show all reservations
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(reservationToDelete, model.getFilteredReservationList().get(INDEX_FIRST.getZeroBased()));
        // redo -> deletes same second reservation in unfiltered reservation list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteReservationCommand deleteFirstCommand = new DeleteReservationCommand(INDEX_FIRST);
        DeleteReservationCommand deleteSecondCommand = new DeleteReservationCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteReservationCommand deleteFirstCommandCopy = new DeleteReservationCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different reservation -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoReservation(Model model) {
        model.updateFilteredReservationList(p -> false);

        assertTrue(model.getFilteredReservationList().isEmpty());
    }
}