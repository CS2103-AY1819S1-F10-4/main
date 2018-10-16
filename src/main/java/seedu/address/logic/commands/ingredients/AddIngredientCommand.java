package seedu.address.logic.commands.ingredients;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_MINIMUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_UNIT;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;

/**
 * Adds an ingredient to the address book.
 */
public class AddIngredientCommand extends Command {

    public static final String COMMAND_WORD = "add-ingredient";

    public static final String COMMAND_ALIAS = "add-ing";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an ingredient to the address book. "
            + "Parameters: "
            + PREFIX_INGREDIENT_NAME + "NAME "
            + PREFIX_INGREDIENT_UNIT + "UNIT "
            + PREFIX_INGREDIENT_PRICE + "PRICE "
            + PREFIX_INGREDIENT_MINIMUM + "MINIMUM\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INGREDIENT_NAME + "Cod Fish "
            + PREFIX_INGREDIENT_UNIT + "kilogram "
            + PREFIX_INGREDIENT_PRICE + "20.00 "
            + PREFIX_INGREDIENT_MINIMUM + "5 ";

    public static final String MESSAGE_SUCCESS = "New ingredient added: %1$s";
    public static final String MESSAGE_DUPLICATE_INGREDIENT = "This ingredient already exists in the address book";

    private final Ingredient toAdd;

    /**
     * Creates an AddIngredientCommand to add the specified {@code Ingredient}
     */
    public AddIngredientCommand(Ingredient ingredient) {
        requireNonNull(ingredient);
        toAdd = ingredient;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasIngredient(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_INGREDIENT);
        }

        model.addIngredient(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddIngredientCommand // instanceof handles nulls
                && toAdd.equals(((AddIngredientCommand) other).toAdd));
    }
}
