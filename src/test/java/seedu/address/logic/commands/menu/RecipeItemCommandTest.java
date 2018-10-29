package seedu.address.logic.commands.menu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_RECIPE_FRIES;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.menu.MenuCommandTestUtil.showItemAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.menu.Item;
import seedu.address.model.menu.Recipe;
import seedu.address.testutil.menu.ItemBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RecipeItemCommand.
 */
public class RecipeItemCommandTest {

    private static final String RECIPE_STUB = "Some recipe";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_deleteRecipeUnfilteredList_success() {
        Item firstItem = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        Item editedItem = new ItemBuilder(firstItem).withRecipe("").build();

        RecipeItemCommand recipeItemCommand = new RecipeItemCommand(INDEX_FIRST,
                new Recipe(editedItem.getRecipe().toString()));

        String expectedMessage = String.format(RecipeItemCommand.MESSAGE_DELETE_RECIPE_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateItem(firstItem, editedItem);
        expectedModel.commitAddressBook();

        assertCommandSuccess(recipeItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showItemAtIndex(model, INDEX_FIRST);

        Item firstItem = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        Item editedItem = new ItemBuilder(model.getFilteredItemList().get(INDEX_FIRST.getZeroBased()))
                .withRecipe(RECIPE_STUB).build();

        RecipeItemCommand recipeItemCommand = new RecipeItemCommand(INDEX_FIRST,
                new Recipe(editedItem.getRecipe().toString()));

        String expectedMessage = String.format(RecipeItemCommand.MESSAGE_ADD_RECIPE_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateItem(firstItem, editedItem);
        expectedModel.commitAddressBook();

        assertCommandSuccess(recipeItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidItemIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RecipeItemCommand recipeItemCommand = new RecipeItemCommand(outOfBoundIndex,
                new Recipe(VALID_ITEM_RECIPE_FRIES));

        assertCommandFailure(recipeItemCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list, but smaller than size of menu
     */
    @Test
    public void execute_invalidItemIndexFilteredList_failure() {
        showItemAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getItemList().size());

        RecipeItemCommand recipeItemCommand = new RecipeItemCommand(outOfBoundIndex,
                new Recipe(VALID_ITEM_RECIPE_FRIES));
        assertCommandFailure(recipeItemCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Item itemToModify = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        Item modifiedItem = new ItemBuilder(itemToModify).withRecipe(RECIPE_STUB).build();

        RecipeItemCommand recipeItemCommand = new RecipeItemCommand(INDEX_FIRST, new Recipe(RECIPE_STUB));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateItem(itemToModify, modifiedItem);
        expectedModel.commitAddressBook();

        // recipe -> first item recipe changed
        recipeItemCommand.execute(model, commandHistory);

        // undo -> reverts restaurant book back to previous state and filtered item list to show all items
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first item modified again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        RecipeItemCommand recipeItemCommand = new RecipeItemCommand(outOfBoundIndex, new Recipe(""));

        // execution failed -> restaurant book state not added into model
        assertCommandFailure(recipeItemCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        // single restaurant book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }


    @Test
    public void equals() {
        final RecipeItemCommand standardCommand = new RecipeItemCommand(INDEX_FIRST,
                new Recipe(VALID_ITEM_RECIPE_FRIES));

        // same values -> returns true
        RecipeItemCommand commandWithSameValues = new RecipeItemCommand(INDEX_FIRST,
                new Recipe(VALID_ITEM_RECIPE_FRIES));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearMenuCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RecipeItemCommand(INDEX_SECOND, new Recipe(VALID_ITEM_RECIPE_FRIES))));

        // different recipe -> returns false
        assertFalse(standardCommand.equals(new RecipeItemCommand(INDEX_FIRST, new Recipe("Other recipe"))));
    }
}
