package seedu.address.logic.parser.sales;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_RECORD_ONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.sales.DisplaySalesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.salesrecord.Date;


class DisplaySalesCommandParserTest {

    private DisplaySalesCommandParser parser = new DisplaySalesCommandParser();

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DisplaySalesCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "31-02-2018", String.format(Date.MESSAGE_DATE_CONSTRAINTS));
        assertParseFailure(parser, "Today", String.format(Date.MESSAGE_DATE_CONSTRAINTS));
    }

    @Test
    public void parse_success() throws ParseException {
        DisplaySalesCommand command = parser.parse(VALID_DATE_RECORD_ONE);
        assertEquals(new DisplaySalesCommand(new Date(VALID_DATE_RECORD_ONE)), command);
    }
}
