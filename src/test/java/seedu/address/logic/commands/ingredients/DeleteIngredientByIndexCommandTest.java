package seedu.address.logic.commands.ingredients;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showIngredientAtIndex;
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
import seedu.address.model.ingredient.Ingredient;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for {@code
 * DeleteIngredientByIndexCommand}.
 */
public class DeleteIngredientByIndexCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Ingredient ingredientToDelete = model.getFilteredIngredientList().get(INDEX_FIRST.getZeroBased());
        DeleteIngredientByIndexCommand deleteCommand = new DeleteIngredientByIndexCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteIngredientByIndexCommand.MESSAGE_DELETE_INGREDIENT_SUCCESS,
                ingredientToDelete);

        ModelManager expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteIngredient(ingredientToDelete);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredIngredientList().size() + 1);
        DeleteIngredientByIndexCommand deleteCommand = new DeleteIngredientByIndexCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showIngredientAtIndex(model, INDEX_FIRST);

        Ingredient ingredientToDelete = model.getFilteredIngredientList().get(INDEX_FIRST.getZeroBased());
        DeleteIngredientByIndexCommand deleteCommand = new DeleteIngredientByIndexCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteIngredientByIndexCommand.MESSAGE_DELETE_INGREDIENT_SUCCESS,
                ingredientToDelete);

        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteIngredient(ingredientToDelete);
        expectedModel.commitRestaurantBook();
        showNoIngredient(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showIngredientAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of restaurant book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRestaurantBook().getIngredientList().size());

        DeleteIngredientByIndexCommand deleteCommand = new DeleteIngredientByIndexCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Ingredient ingredientToDelete = model.getFilteredIngredientList().get(INDEX_FIRST.getZeroBased());
        DeleteIngredientByIndexCommand deleteCommand = new DeleteIngredientByIndexCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.deleteIngredient(ingredientToDelete);
        expectedModel.commitRestaurantBook();

        // delete -> first ingredient deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered ingredient list to show all ingredients
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first ingredient deleted again
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredIngredientList().size() + 1);
        DeleteIngredientByIndexCommand deleteCommand = new DeleteIngredientByIndexCommand(outOfBoundIndex);

        // execution failed -> restaurant book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);

        // single restaurant book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes an {@code Ingredient} from a filtered list. 2. Undo the deletion. 3. The unfiltered list should be
     * shown now. Verify that the index of the previously deleted ingredient in the unfiltered list is different from
     * the index at the filtered list. 4. Redo the deletion. This ensures {@code RedoCommand} deletes the ingredient
     * object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameIngredientDeleted() throws Exception {
        DeleteIngredientByIndexCommand deleteCommand = new DeleteIngredientByIndexCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());

        showIngredientAtIndex(model, INDEX_SECOND);
        Ingredient ingredientToDelete = model.getFilteredIngredientList().get(INDEX_FIRST.getZeroBased());
        expectedModel.deleteIngredient(ingredientToDelete);
        expectedModel.commitRestaurantBook();

        // delete -> deletes second ingredient in unfiltered ingredient list / first ingredient in filtered ingredient
        // list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered ingredient list to show all ingredients
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(ingredientToDelete, model.getFilteredIngredientList().get(INDEX_FIRST.getZeroBased()));
        // redo -> deletes same second ingredient in unfiltered ingredient list
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteIngredientByIndexCommand deleteFirstCommand = new DeleteIngredientByIndexCommand(INDEX_FIRST);
        DeleteIngredientByIndexCommand deleteSecondCommand = new DeleteIngredientByIndexCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteIngredientByIndexCommand deleteFirstCommandCopy = new DeleteIngredientByIndexCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different ingredient -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoIngredient(Model model) {
        model.updateFilteredIngredientList(p -> false);

        assertTrue(model.getFilteredIngredientList().isEmpty());
    }
}
