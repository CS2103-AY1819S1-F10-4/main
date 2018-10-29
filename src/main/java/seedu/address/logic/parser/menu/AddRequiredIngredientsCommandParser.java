package seedu.address.logic.parser.menu;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NUM;

import java.util.HashMap;
import java.util.Map;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.menu.AddRequiredIngredientsCommand;
import seedu.address.logic.parser.ArgumentIndexAndPairMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.IngredientName;

/**
 * Parses input arguments and creates a new AddRequiredIngredientsCommand object
 */
public class AddRequiredIngredientsCommandParser implements Parser<AddRequiredIngredientsCommand> {
    public static final String MESSAGE_INTEGER_CONSTRAINTS =
            "Number of ingredients should only contain positive numbers";
    public static final String INTEGER_VALIDATION_REGEX = "\\d+";
    public static final String MESSAGE_DUPLICATE_INGREDIENT_NAME = "Duplicated ingredients entered";

    /**
     * Parses the given {@code String} of arguments in the context of the RecipeItemCommand
     * and returns an AddRequiredIngredientsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddRequiredIngredientsCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentIndexAndPairMultimap argMultimap =
                ArgumentTokenizer.tokenizeToIndexAndPair(args, PREFIX_INGREDIENT_NAME, PREFIX_INGREDIENT_NUM);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(0).get(new Prefix("")));
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddRequiredIngredientsCommand.MESSAGE_USAGE), pe);
        }

        int count = 1;
        Map<IngredientName, Integer> requiredIngredients = new HashMap<>();
        while (argMultimap.contains(count)) {
            Map<Prefix, String> map = argMultimap.getValue(count);

            if (!map.containsKey(PREFIX_INGREDIENT_NAME)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddRequiredIngredientsCommand.MESSAGE_USAGE));
            }
            IngredientName name = ParserUtil.parseIngredientName(map.get(PREFIX_INGREDIENT_NAME));
            if (requiredIngredients.containsKey(name)) {
                throw new ParseException(MESSAGE_DUPLICATE_INGREDIENT_NAME);
            }

            if (!map.containsKey(PREFIX_INGREDIENT_NUM)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddRequiredIngredientsCommand.MESSAGE_USAGE));
            }
            String trimmedInteger = map.get(PREFIX_INGREDIENT_NUM);
            if (!isValidNumberOfIngredients(trimmedInteger)) {
                throw new ParseException(MESSAGE_INTEGER_CONSTRAINTS);
            }
            Integer num = Integer.parseInt(trimmedInteger);

            requiredIngredients.put(name, num);
            count++;
        }

        return new AddRequiredIngredientsCommand(index, requiredIngredients);
    }

    /**
     * Returns true if a given string is a valid number of ingredient.
     */
    public static boolean isValidNumberOfIngredients(String test) {
        if (!test.matches(INTEGER_VALIDATION_REGEX)) {
            return false;
        }
        return Integer.parseInt(test) > 0;
    }

}
