package seedu.address.logic.commands.menu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.menu.MenuCommandTestUtil.showItemAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import java.util.HashMap;
import java.util.Map;

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
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.menu.Item;
import seedu.address.testutil.menu.ItemBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RequiredIngredientsItemCommand.
 */
public class RequiredIngredientsItemCommandTest {
    private static final Map<IngredientName, Integer> REQUIRED_INGREDIENTS_STUB = new HashMap<>();
    static {
        REQUIRED_INGREDIENTS_STUB.put(new IngredientName("Apple"), 3);
    }

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_deleteRequiredIngredientsUnfilteredList_success() {
        Item firstItem = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        Item editedItem = new ItemBuilder(firstItem).withRequiredIngredients(new HashMap<>()).build();

        RequiredIngredientsItemCommand requiredIngredientsItemCommand =
                new RequiredIngredientsItemCommand(INDEX_FIRST, editedItem.getRequiredIngredients());

        String expectedMessage =
                String.format(RequiredIngredientsItemCommand.MESSAGE_DELETE_REQUIRED_INGREDIENT_SUCCESS, editedItem);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateItem(firstItem, editedItem);
        expectedModel.commitAddressBook();

        assertCommandSuccess(requiredIngredientsItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showItemAtIndex(model, INDEX_FIRST);

        Item firstItem = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        Item editedItem = new ItemBuilder(model.getFilteredItemList().get(INDEX_FIRST.getZeroBased()))
                .withRequiredIngredients(Map.of("Apple", "3")).build();

        RequiredIngredientsItemCommand requiredIngredientsItemCommand = new RequiredIngredientsItemCommand(INDEX_FIRST,
                editedItem.getRequiredIngredients());

        String expectedMessage = String.format(RequiredIngredientsItemCommand.MESSAGE_ADD_REQUIRED_INGREDIENT_SUCCESS,
                editedItem);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateItem(firstItem, editedItem);
        expectedModel.commitAddressBook();

        assertCommandSuccess(requiredIngredientsItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidItemIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        RequiredIngredientsItemCommand requiredIngredientsItemCommand =
                new RequiredIngredientsItemCommand(outOfBoundIndex, REQUIRED_INGREDIENTS_STUB);

        assertCommandFailure(requiredIngredientsItemCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
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

        RequiredIngredientsItemCommand requiredIngredientsItemCommand =
                new RequiredIngredientsItemCommand(outOfBoundIndex, REQUIRED_INGREDIENTS_STUB);
        assertCommandFailure(requiredIngredientsItemCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Item itemToModify = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        Item modifiedItem = new ItemBuilder(itemToModify)
                .withRequiredIngredients(Map.of("Apple", "3")).build();

        RequiredIngredientsItemCommand requiredIngredientsItemCommand =
                new RequiredIngredientsItemCommand(INDEX_FIRST, REQUIRED_INGREDIENTS_STUB);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateItem(itemToModify, modifiedItem);
        expectedModel.commitAddressBook();

        // required ingredients -> first item required ingredients changed
        requiredIngredientsItemCommand.execute(model, commandHistory);

        // undo -> reverts restaurant book back to previous state and filtered item list to show all items
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first item modified again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);

        RequiredIngredientsItemCommand requiredIngredientsItemCommand =
                new RequiredIngredientsItemCommand(outOfBoundIndex, new HashMap<>());

        // execution failed -> restaurant book state not added into model
        assertCommandFailure(requiredIngredientsItemCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        // single restaurant book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }


    @Test
    public void equals() {
        final RequiredIngredientsItemCommand standardCommand = new RequiredIngredientsItemCommand(INDEX_FIRST,
                REQUIRED_INGREDIENTS_STUB);

        // same values -> returns true
        RequiredIngredientsItemCommand commandWithSameValues = new RequiredIngredientsItemCommand(INDEX_FIRST,
                REQUIRED_INGREDIENTS_STUB);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearMenuCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RequiredIngredientsItemCommand(INDEX_SECOND,
                REQUIRED_INGREDIENTS_STUB)));

        // different recipe -> returns false
        assertFalse(standardCommand.equals(new RequiredIngredientsItemCommand(INDEX_FIRST, new HashMap<>())));
    }
}
