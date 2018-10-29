package seedu.address.logic.parser.menu;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_NAME_DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_NAME_DESC_BROCCOLI;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INGREDIENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BROCCOLI;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NUM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.menu.RequiredIngredientsItemCommandParser.MESSAGE_DUPLICATE_INGREDIENT_NAME;
import static seedu.address.logic.parser.menu.RequiredIngredientsItemCommandParser.MESSAGE_INTEGER_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.menu.RequiredIngredientsItemCommand;
import seedu.address.model.ingredient.IngredientName;

public class RequiredIngredientsItemCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RequiredIngredientsItemCommand.MESSAGE_USAGE);
    private static final String VALID_NUMBER_OF_INGREDIENTS = "3";
    private static final Map<IngredientName, Integer> REQUIRED_INGREDIENTS_STUB = new HashMap<>();
    static {
        REQUIRED_INGREDIENTS_STUB.put(new IngredientName(VALID_NAME_APPLE),
                Integer.parseInt(VALID_NUMBER_OF_INGREDIENTS));
    }
    private static final Map<IngredientName, Integer> REQUIRED_INGREDIENTS_MULITPLE_STUB = new HashMap<>();
    static {
        REQUIRED_INGREDIENTS_MULITPLE_STUB.put(new IngredientName(VALID_NAME_APPLE),
                Integer.parseInt(VALID_NUMBER_OF_INGREDIENTS));
        REQUIRED_INGREDIENTS_MULITPLE_STUB.put(new IngredientName(VALID_NAME_BROCCOLI),
                Integer.parseInt(VALID_NUMBER_OF_INGREDIENTS));
    }
    private RequiredIngredientsItemCommandParser parser = new RequiredIngredientsItemCommandParser();
    private Index targetIndex = INDEX_FIRST;

    @Test
    public void parse_noFields_failure() {
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, "-5" + INGREDIENT_NAME_DESC_APPLE + " "
                + PREFIX_INGREDIENT_NUM + VALID_NUMBER_OF_INGREDIENTS, MESSAGE_INVALID_FORMAT);

        // zero starting index
        assertParseFailure(parser, "0" + INGREDIENT_NAME_DESC_APPLE + " "
                + PREFIX_INGREDIENT_NUM + VALID_NUMBER_OF_INGREDIENTS, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validIndexInvalidArgumentPair_failure() {
        // duplicated ingredient name
        assertParseFailure(parser, targetIndex.getOneBased() + INGREDIENT_NAME_DESC_APPLE + " "
                + PREFIX_INGREDIENT_NUM + VALID_NUMBER_OF_INGREDIENTS + INGREDIENT_NAME_DESC_APPLE + " "
                + PREFIX_INGREDIENT_NUM + VALID_NUMBER_OF_INGREDIENTS, MESSAGE_DUPLICATE_INGREDIENT_NAME);

        // invalid ingredient name
        assertParseFailure(parser, targetIndex.getOneBased() + INVALID_INGREDIENT_NAME_DESC + " "
                + PREFIX_INGREDIENT_NUM + VALID_NUMBER_OF_INGREDIENTS, IngredientName.MESSAGE_NAME_CONSTRAINTS);

        // zero number of ingredients
        assertParseFailure(parser, targetIndex.getOneBased() + INGREDIENT_NAME_DESC_APPLE + " "
                + PREFIX_INGREDIENT_NUM + "0", MESSAGE_INTEGER_CONSTRAINTS);

        // negative number of ingredients
        assertParseFailure(parser, targetIndex.getOneBased() + INGREDIENT_NAME_DESC_APPLE + " "
                + PREFIX_INGREDIENT_NUM + "-3", MESSAGE_INTEGER_CONSTRAINTS);

        // missing ingredient name
        assertParseFailure(parser, targetIndex.getOneBased() + " " + PREFIX_INGREDIENT_NUM
                + VALID_NUMBER_OF_INGREDIENTS, MESSAGE_INVALID_FORMAT);

        // missing number of ingredient
        assertParseFailure(parser, targetIndex.getOneBased() + " " + INGREDIENT_NAME_DESC_APPLE,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validIndexAndValidArgumentPair_success() {
        RequiredIngredientsItemCommand expectedCommand =
                new RequiredIngredientsItemCommand(targetIndex, REQUIRED_INGREDIENTS_STUB);
        String userInput = targetIndex.getOneBased() + INGREDIENT_NAME_DESC_APPLE + " "
                + PREFIX_INGREDIENT_NUM + VALID_NUMBER_OF_INGREDIENTS;
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validIndexAndValidMultipleArgumentPairs_success() {
        RequiredIngredientsItemCommand expectedCommand =
                new RequiredIngredientsItemCommand(targetIndex, REQUIRED_INGREDIENTS_MULITPLE_STUB);
        String userInput = targetIndex.getOneBased() + INGREDIENT_NAME_DESC_APPLE + " "
                + PREFIX_INGREDIENT_NUM + VALID_NUMBER_OF_INGREDIENTS + INGREDIENT_NAME_DESC_BROCCOLI + " "
                + PREFIX_INGREDIENT_NUM + VALID_NUMBER_OF_INGREDIENTS;
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
