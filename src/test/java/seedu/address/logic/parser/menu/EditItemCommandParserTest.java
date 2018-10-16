package seedu.address.logic.parser.menu;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ITEM_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_NAME_DESC_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_NAME_DESC_FRIES;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_PRICE_DESC_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_PRICE_DESC_FRIES;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_TAG_DESC_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_TAG_DESC_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NAME_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NAME_FRIES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_PRICE_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_PRICE_FRIES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_TAG_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_TAG_CHEESE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.menu.EditItemCommand;
import seedu.address.logic.commands.menu.EditItemCommand.EditItemDescriptor;
import seedu.address.model.menu.Name;
import seedu.address.model.menu.Price;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.menu.EditItemDescriptorBuilder;

public class EditItemCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditItemCommand.MESSAGE_USAGE);

    private EditItemCommandParser parser = new EditItemCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_ITEM_NAME_FRIES, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditItemCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + ITEM_NAME_DESC_FRIES, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + ITEM_NAME_DESC_FRIES, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_ITEM_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PRICE_DESC, Price.MESSAGE_PRICE_CONSTRAINTS); // invalid rice
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // invalid name followed by valid price
        assertParseFailure(parser, "1" + INVALID_ITEM_NAME_DESC + VALID_ITEM_PRICE_FRIES,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // valid price followed by invalid price. The test case for invalid price followed by valid price
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + ITEM_PRICE_DESC_BURGER + INVALID_PRICE_DESC,
                Price.MESSAGE_PRICE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Item} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + ITEM_TAG_DESC_CHEESE + ITEM_TAG_DESC_BURGER + TAG_EMPTY,
                Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + ITEM_TAG_DESC_CHEESE + TAG_EMPTY + ITEM_TAG_DESC_BURGER,
                Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + ITEM_TAG_DESC_CHEESE + ITEM_TAG_DESC_BURGER,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_ITEM_NAME_DESC + INVALID_PRICE_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + ITEM_PRICE_DESC_BURGER + ITEM_TAG_DESC_BURGER
                + ITEM_NAME_DESC_FRIES + ITEM_TAG_DESC_CHEESE;

        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withName(VALID_ITEM_NAME_FRIES)
                .withPrice(VALID_ITEM_PRICE_BURGER)
                .withTags(VALID_ITEM_TAG_BURGER, VALID_ITEM_TAG_CHEESE).build();
        EditItemCommand expectedCommand = new EditItemCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + ITEM_NAME_DESC_FRIES;
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withName(VALID_ITEM_NAME_FRIES).build();
        EditItemCommand expectedCommand = new EditItemCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // price
        userInput = targetIndex.getOneBased() + ITEM_PRICE_DESC_FRIES;
        descriptor = new EditItemDescriptorBuilder().withPrice(VALID_ITEM_PRICE_FRIES).build();
        expectedCommand = new EditItemCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + ITEM_TAG_DESC_CHEESE;
        descriptor = new EditItemDescriptorBuilder().withTags(VALID_ITEM_TAG_CHEESE).build();
        expectedCommand = new EditItemCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + ITEM_PRICE_DESC_FRIES + ITEM_TAG_DESC_CHEESE
                + ITEM_PRICE_DESC_FRIES + ITEM_TAG_DESC_CHEESE
                + ITEM_PRICE_DESC_BURGER + ITEM_TAG_DESC_BURGER;

        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withPrice(VALID_ITEM_PRICE_BURGER)
                .withTags(VALID_ITEM_TAG_CHEESE, VALID_ITEM_TAG_BURGER).build();
        EditItemCommand expectedCommand = new EditItemCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_PRICE_DESC + ITEM_PRICE_DESC_BURGER;
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withPrice(VALID_ITEM_PRICE_BURGER).build();
        EditItemCommand expectedCommand = new EditItemCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + ITEM_NAME_DESC_BURGER + INVALID_PRICE_DESC + ITEM_PRICE_DESC_BURGER;
        descriptor = new EditItemDescriptorBuilder().withName(VALID_ITEM_NAME_BURGER)
                .withPrice(VALID_ITEM_PRICE_BURGER).build();
        expectedCommand = new EditItemCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditItemDescriptor descriptor = new EditItemDescriptorBuilder().withTags().build();
        EditItemCommand expectedCommand = new EditItemCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
