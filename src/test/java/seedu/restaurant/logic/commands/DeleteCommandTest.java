package seedu.restaurant.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.Test;

import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.model.person.Person;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for {@code
 * DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitRestaurantBook();
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of restaurant book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRestaurantBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitRestaurantBook();

        // delete -> first person deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts restaurantbook back to previous state and filtered person list to show all persons
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> restaurant book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single restaurant book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Person} from a filtered list. 2. Undo the deletion. 3. The unfiltered list should be shown
     * now. Verify that the index of the previously deleted person in the unfiltered list is different from the index at
     * the filtered list. 4. Redo the deletion. This ensures {@code RedoCommand} deletes the person object regardless of
     * indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND);
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitRestaurantBook();

        // delete -> deletes second person in unfiltered person list / first person in filtered person list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts restaurantbook back to previous state and filtered person list to show all persons
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(personToDelete, model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased()));
        // redo -> deletes same second person in unfiltered person list
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}