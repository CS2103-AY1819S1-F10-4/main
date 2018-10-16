package seedu.address.logic.parser.menu;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ITEM_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_NAME_DESC_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_NAME_DESC_FRIES;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_PRICE_DESC_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_PRICE_DESC_FRIES;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_TAG_DESC_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NAME_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_PRICE_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_TAG_CHEESE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.menu.TypicalItems.BURGER;

import org.junit.Test;

import seedu.address.logic.commands.menu.AddItemCommand;
import seedu.address.model.menu.Item;
import seedu.address.model.menu.Name;
import seedu.address.model.menu.Price;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.menu.ItemBuilder;

public class AddItemCommandParserTest {
    private AddItemCommandParser parser = new AddItemCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Item expectedItem = new ItemBuilder(BURGER).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ITEM_NAME_DESC_BURGER + ITEM_PRICE_DESC_BURGER,
                new AddItemCommand(expectedItem));

        // multiple names - last name accepted
        assertParseSuccess(parser,
                ITEM_NAME_DESC_FRIES + ITEM_NAME_DESC_BURGER + ITEM_PRICE_DESC_BURGER,
                new AddItemCommand(expectedItem));

        // multiple prices - last price accepted
        assertParseSuccess(parser, ITEM_NAME_DESC_BURGER + ITEM_PRICE_DESC_FRIES + ITEM_PRICE_DESC_BURGER,
                new AddItemCommand(expectedItem));

        // multiple tags - all accepted
        Item expectedItemMultipleTags = new ItemBuilder(BURGER).withTags(VALID_ITEM_TAG_CHEESE).build();
        assertParseSuccess(parser, ITEM_NAME_DESC_BURGER + ITEM_PRICE_DESC_BURGER + ITEM_TAG_DESC_CHEESE,
                new AddItemCommand(expectedItemMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Item expectedItem = new ItemBuilder(BURGER).withTags().build();
        assertParseSuccess(parser, ITEM_NAME_DESC_BURGER + ITEM_PRICE_DESC_BURGER,
                new AddItemCommand(expectedItem));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddItemCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_ITEM_NAME_BURGER + ITEM_PRICE_DESC_BURGER,
                expectedMessage);

        // missing price prefix
        assertParseFailure(parser, ITEM_NAME_DESC_BURGER + VALID_ITEM_PRICE_BURGER,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_ITEM_NAME_BURGER + VALID_ITEM_NAME_BURGER,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_ITEM_NAME_DESC + ITEM_PRICE_DESC_BURGER,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser, ITEM_NAME_DESC_BURGER + INVALID_PRICE_DESC,
                Price.MESSAGE_PRICE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, ITEM_NAME_DESC_BURGER + ITEM_PRICE_DESC_BURGER
                + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_ITEM_NAME_DESC + INVALID_PRICE_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + ITEM_NAME_DESC_BURGER + ITEM_PRICE_DESC_BURGER,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddItemCommand.MESSAGE_USAGE));
    }
}
