package seedu.address.logic.commands.reservation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ANDREW;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BILLY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESERVATION_NAME_BILLY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESERVATION_PAX_BILLY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESERVATION_TAG_BILLY;
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
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.reservation.EditReservationCommand.EditReservationDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reservation.Reservation;
import seedu.address.testutil.reservation.EditReservationDescriptorBuilder;
import seedu.address.testutil.reservation.ReservationBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditReservationCommand.
 */
public class EditReservationCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Reservation editedReservation = new ReservationBuilder().build();
        EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder(editedReservation).build();
        EditReservationCommand editReservationCommand = new EditReservationCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditReservationCommand.MESSAGE_EDIT_RESERVATION_SUCCESS,
                editedReservation);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateReservation(model.getFilteredReservationList().get(0), editedReservation);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editReservationCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastReservation = Index.fromOneBased(model.getFilteredReservationList().size());
        Reservation lastReservation = model.getFilteredReservationList().get(indexLastReservation.getZeroBased());

        ReservationBuilder reservationInList = new ReservationBuilder(lastReservation);
        Reservation editedReservation = reservationInList.withName(VALID_RESERVATION_NAME_BILLY)
                .withPax(VALID_RESERVATION_PAX_BILLY).withTags(VALID_RESERVATION_TAG_BILLY).build();

        EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder()
                .withName(VALID_RESERVATION_NAME_BILLY).withPax(VALID_RESERVATION_PAX_BILLY)
                .withTags(VALID_RESERVATION_TAG_BILLY).build();
        EditReservationCommand editReservationCommand = new EditReservationCommand(indexLastReservation, descriptor);

        String expectedMessage = String.format(EditReservationCommand.MESSAGE_EDIT_RESERVATION_SUCCESS,
                editedReservation);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateReservation(lastReservation, editedReservation);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editReservationCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditReservationCommand editReservationCommand = new EditReservationCommand(INDEX_FIRST,
                new EditReservationDescriptor());
        Reservation editedReservation = model.getFilteredReservationList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditReservationCommand.MESSAGE_EDIT_RESERVATION_SUCCESS,
                editedReservation);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editReservationCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showReservationAtIndex(model, INDEX_FIRST);

        Reservation reservationInFilteredList = model.getFilteredReservationList().get(INDEX_FIRST.getZeroBased());
        Reservation editedReservation = new ReservationBuilder(reservationInFilteredList)
                .withName(VALID_RESERVATION_NAME_BILLY).build();
        EditReservationCommand editReservationCommand = new EditReservationCommand(INDEX_FIRST,
                new EditReservationDescriptorBuilder().withName(VALID_RESERVATION_NAME_BILLY).build());

        String expectedMessage = String.format(EditReservationCommand.MESSAGE_EDIT_RESERVATION_SUCCESS,
                editedReservation);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateReservation(model.getFilteredReservationList().get(0), editedReservation);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editReservationCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateReservationUnfilteredList_failure() {
        Reservation firstReservation = model.getFilteredReservationList().get(INDEX_FIRST.getZeroBased());
        EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder(firstReservation).build();
        EditReservationCommand editReservationCommand = new EditReservationCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editReservationCommand, model, commandHistory,
                EditReservationCommand.MESSAGE_DUPLICATE_RESERVATION);
    }

    @Test
    public void execute_duplicateReservationFilteredList_failure() {
        showReservationAtIndex(model, INDEX_FIRST);

        // edit reservation in filtered list into a duplicate in restaurant book
        Reservation reservationInList = model.getAddressBook().getReservationList().get(INDEX_SECOND.getZeroBased());
        EditReservationCommand editReservationCommand = new EditReservationCommand(INDEX_FIRST,
                new EditReservationDescriptorBuilder(reservationInList).build());

        assertCommandFailure(editReservationCommand, model, commandHistory,
                EditReservationCommand.MESSAGE_DUPLICATE_RESERVATION);
    }

    @Test
    public void execute_invalidReservationIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReservationList().size() + 1);
        EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder()
                .withName(VALID_RESERVATION_NAME_BILLY).build();
        EditReservationCommand editReservationCommand = new EditReservationCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editReservationCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list, but smaller than size of restaurant book
     */
    @Test
    public void execute_invalidReservationIndexFilteredList_failure() {
        showReservationAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of restaurant book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getReservationList().size());

        EditReservationCommand editReservationCommand = new EditReservationCommand(outOfBoundIndex,
                new EditReservationDescriptorBuilder().withName(VALID_RESERVATION_NAME_BILLY).build());

        assertCommandFailure(editReservationCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Reservation editedReservation = new ReservationBuilder().build();
        Reservation reservationToEdit = model.getFilteredReservationList().get(INDEX_FIRST.getZeroBased());
        EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder(editedReservation).build();
        EditReservationCommand editReservationCommand = new EditReservationCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateReservation(reservationToEdit, editedReservation);
        expectedModel.commitAddressBook();

        // edit -> first reservation edited
        editReservationCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered reservation list to show all reservations
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first reservation edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReservationList().size() + 1);
        EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder()
                .withName(VALID_RESERVATION_NAME_BILLY).build();
        EditReservationCommand editReservationCommand = new EditReservationCommand(outOfBoundIndex, descriptor);

        // execution failed -> restaurant book state not added into model
        assertCommandFailure(editReservationCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);

        // single restaurant book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Reservation} from a filtered list. 2. Undo the edit. 3.
     * The unfiltered list should be shown now. Verify that the index of the previously edited reservation in the
     * unfiltered list is different from the index at the filtered list. 4. Redo the edit. This ensures
     * {@code RedoCommand} edits the reservation object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameReservationEdited() throws Exception {
        Reservation editedReservation = new ReservationBuilder().build();
        EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder(editedReservation).build();
        EditReservationCommand editReservationCommand = new EditReservationCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showReservationAtIndex(model, INDEX_SECOND);
        Reservation reservationToEdit = model.getFilteredReservationList().get(INDEX_FIRST.getZeroBased());
        expectedModel.updateReservation(reservationToEdit, editedReservation);
        expectedModel.commitAddressBook();

        // edit -> edits second reservation in unfiltered list / first reservation in filtered list
        editReservationCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered reservation list to show all reservations
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredReservationList().get(INDEX_FIRST.getZeroBased()), reservationToEdit);
        // redo -> edits same second reservation in unfiltered reservation list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditReservationCommand standardCommand = new EditReservationCommand(INDEX_FIRST, DESC_ANDREW);

        // same values -> returns true
        EditReservationDescriptor copyDescriptor = new EditReservationDescriptor(DESC_ANDREW);
        EditReservationCommand commandWithSameValues = new EditReservationCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditReservationCommand(INDEX_SECOND, DESC_ANDREW)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditReservationCommand(INDEX_FIRST, DESC_BILLY)));
    }

}
