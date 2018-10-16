package seedu.address.logic.parser.ingredients;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_MINIMUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_UNIT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ingredients.EditIngredientCommand;
import seedu.address.logic.commands.ingredients.EditIngredientCommand.EditIngredientDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditIngredientCommandParser implements Parser<EditIngredientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditIngredientCommand
     * and returns an EditIngredientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditIngredientCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INGREDIENT_NAME, PREFIX_INGREDIENT_UNIT,
                        PREFIX_INGREDIENT_PRICE, PREFIX_INGREDIENT_MINIMUM);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditIngredientCommand.MESSAGE_USAGE), pe);
        }

        EditIngredientDescriptor editIngredientDescriptor = new EditIngredientDescriptor();
        if (argMultimap.getValue(PREFIX_INGREDIENT_NAME).isPresent()) {
            editIngredientDescriptor.setName(
                    ParserUtil.parseIngredientName(argMultimap.getValue(PREFIX_INGREDIENT_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_INGREDIENT_UNIT).isPresent()) {
            editIngredientDescriptor.setUnit(
                    ParserUtil.parseIngredientUnit(argMultimap.getValue(PREFIX_INGREDIENT_UNIT).get()));
        }
        if (argMultimap.getValue(PREFIX_INGREDIENT_PRICE).isPresent()) {
            editIngredientDescriptor.setPrice(
                    ParserUtil.parseIngredientPrice(argMultimap.getValue(PREFIX_INGREDIENT_PRICE).get()));
        }
        if (argMultimap.getValue(PREFIX_INGREDIENT_MINIMUM).isPresent()) {
            editIngredientDescriptor.setMinimum(
                    ParserUtil.parseMinimumUnit(argMultimap.getValue(PREFIX_INGREDIENT_MINIMUM).get()));
        }

        if (!editIngredientDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditIngredientCommand.MESSAGE_NOT_EDITED);
        }

        return new EditIngredientCommand(index, editIngredientDescriptor);
    }

}
