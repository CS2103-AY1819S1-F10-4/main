package seedu.address.logic.commands.menu;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NUM;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import java.util.List;
import java.util.Map;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.menu.Item;

/**
 * Adds required ingredients to an item in the menu.
 */
public class AddRequiredIngredientsCommand extends Command {
    public static final String COMMAND_WORD = "addRequiredIngredients";
    public static final String COMMAND_ALIAS = "ari";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the required ingredients for a item identified "
            + "by the index number used in the displayed item list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_INGREDIENT_NAME + "INGREDIENT_NAME... "
            + PREFIX_INGREDIENT_NUM + "NUMBER_OF_INGREDIENTS...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_INGREDIENT_NAME + "Apple "
            + PREFIX_INGREDIENT_NUM + "3";

    public static final String MESSAGE_ADD_REQUIRED_INGREDIENT_SUCCESS = "Added required ingredient(s) to Item: %1$s";
    public static final String MESSAGE_DELETE_REQUIRED_INGREDIENT_SUCCESS = "Removed required ingredient(s) from "
            + "Item: %1$s";

    private final Index index;
    private final Map<IngredientName, Integer> requiredIngredients;

    /**
     * @param index of the item in the filtered item list to edit
     * @param requiredIngredients the requiredIngredients of the item
     */
    public AddRequiredIngredientsCommand(Index index, Map<IngredientName, Integer> requiredIngredients) {
        requireAllNonNull(index, requiredIngredients);

        this.index = index;
        this.requiredIngredients = requiredIngredients;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Item> lastShownList = model.getFilteredItemList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToEdit = lastShownList.get(index.getZeroBased());
        Item editedItem = new Item(itemToEdit.getName(), itemToEdit.getPrice(), itemToEdit.getRecipe(),
                itemToEdit.getTags(), requiredIngredients);

        model.updateItem(itemToEdit, editedItem);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitAddressBook();

        return new CommandResult(generateSuccessMessage(editedItem));
    }

    /**
     * Generates a command execution success message based on whether the recipe is added to or removed from
     * {@code itemToEdit}.
     */
    private String generateSuccessMessage(Item itemToEdit) {
        String message = !requiredIngredients.isEmpty() ? MESSAGE_ADD_REQUIRED_INGREDIENT_SUCCESS
                : MESSAGE_DELETE_REQUIRED_INGREDIENT_SUCCESS;
        return String.format(message, itemToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof AddRequiredIngredientsCommand)) {
            return false;
        }
        // state check
        AddRequiredIngredientsCommand e = (AddRequiredIngredientsCommand) other;
        return index.equals(e.index)
                && requiredIngredients.equals(e.requiredIngredients);
    }
}
