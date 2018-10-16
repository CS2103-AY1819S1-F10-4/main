package seedu.address.logic.parser.reservation;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RESERVATION_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RESERVATION_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RESERVATION_PAX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.RESERVATION_DATETIME_DESC_ANDREW;
import static seedu.address.logic.commands.CommandTestUtil.RESERVATION_DATETIME_DESC_BILLY;
import static seedu.address.logic.commands.CommandTestUtil.RESERVATION_NAME_DESC_ANDREW;
import static seedu.address.logic.commands.CommandTestUtil.RESERVATION_NAME_DESC_BILLY;
import static seedu.address.logic.commands.CommandTestUtil.RESERVATION_PAX_DESC_ANDREW;
import static seedu.address.logic.commands.CommandTestUtil.RESERVATION_PAX_DESC_BILLY;
import static seedu.address.logic.commands.CommandTestUtil.RESERVATION_TAG_DESC_ANDREW;
import static seedu.address.logic.commands.CommandTestUtil.RESERVATION_TAG_DESC_BILLY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESERVATION_DATETIME_ANDREW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESERVATION_NAME_ANDREW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESERVATION_PAX_ANDREW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESERVATION_TAG_ANDREW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESERVATION_TAG_BILLY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.reservation.TypicalReservations.ANDREW;

import org.junit.Test;

import seedu.address.logic.commands.reservation.AddReservationCommand;
import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Pax;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.reservation.ReservationBuilder;

public class AddReservationCommandParserTest {
    private AddReservationCommandParser parser = new AddReservationCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Reservation expectedReservation = new ReservationBuilder(ANDREW).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + RESERVATION_NAME_DESC_ANDREW
                        + RESERVATION_PAX_DESC_ANDREW + RESERVATION_DATETIME_DESC_ANDREW,
                new AddReservationCommand(expectedReservation));

        // multiple names - last name accepted
        assertParseSuccess(parser,
                RESERVATION_NAME_DESC_BILLY + RESERVATION_NAME_DESC_ANDREW + RESERVATION_PAX_DESC_ANDREW
                        + RESERVATION_DATETIME_DESC_ANDREW, new AddReservationCommand(expectedReservation));

        // multiple pax - last pax accepted
        assertParseSuccess(parser, RESERVATION_NAME_DESC_ANDREW + RESERVATION_PAX_DESC_BILLY
                        + RESERVATION_PAX_DESC_ANDREW + RESERVATION_DATETIME_DESC_ANDREW,
                new AddReservationCommand(expectedReservation));

        // multiple dateTime - last dateTime accepted
        assertParseSuccess(parser, RESERVATION_NAME_DESC_ANDREW + RESERVATION_PAX_DESC_ANDREW
                + RESERVATION_DATETIME_DESC_BILLY + RESERVATION_DATETIME_DESC_ANDREW,
                new AddReservationCommand(expectedReservation));

        // multiple tags - all accepted
        Reservation expectedReservationMultipleTags = new ReservationBuilder(ANDREW)
                .withTags(VALID_RESERVATION_TAG_ANDREW, VALID_RESERVATION_TAG_BILLY).build();
        assertParseSuccess(parser,
                RESERVATION_NAME_DESC_ANDREW + RESERVATION_PAX_DESC_ANDREW + RESERVATION_DATETIME_DESC_ANDREW
                        + RESERVATION_TAG_DESC_ANDREW + RESERVATION_TAG_DESC_BILLY,
                new AddReservationCommand(expectedReservationMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Reservation expectedReservation = new ReservationBuilder(ANDREW).withTags().build();
        assertParseSuccess(parser, RESERVATION_NAME_DESC_ANDREW + RESERVATION_PAX_DESC_ANDREW
                        + RESERVATION_DATETIME_DESC_ANDREW, new AddReservationCommand(expectedReservation));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReservationCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_RESERVATION_NAME_ANDREW + RESERVATION_PAX_DESC_ANDREW
                        + RESERVATION_DATETIME_DESC_ANDREW, expectedMessage);

        // missing pax prefix
        assertParseFailure(parser, RESERVATION_NAME_DESC_ANDREW + VALID_RESERVATION_PAX_ANDREW
                        + RESERVATION_DATETIME_DESC_ANDREW,
                expectedMessage);

        // missing datetime prefix
        assertParseFailure(parser, RESERVATION_NAME_DESC_ANDREW + RESERVATION_PAX_DESC_ANDREW
                        + VALID_RESERVATION_DATETIME_ANDREW,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_RESERVATION_NAME_ANDREW + VALID_RESERVATION_NAME_ANDREW
                        + VALID_RESERVATION_DATETIME_ANDREW, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_RESERVATION_NAME_DESC + RESERVATION_PAX_DESC_ANDREW
                        + RESERVATION_DATETIME_DESC_ANDREW, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser, RESERVATION_NAME_DESC_ANDREW + INVALID_RESERVATION_PAX_DESC
                        + RESERVATION_DATETIME_DESC_ANDREW, Pax.MESSAGE_PAX_CONSTRAINTS);

        // invalid dateTime
        assertParseFailure(parser, RESERVATION_NAME_DESC_ANDREW + RESERVATION_PAX_DESC_ANDREW
                + INVALID_RESERVATION_DATETIME_DESC, "DateTime value should be in the form 2018-12-31T10:00:00");

        // invalid tag
        assertParseFailure(parser, RESERVATION_NAME_DESC_ANDREW + RESERVATION_PAX_DESC_ANDREW
                + RESERVATION_DATETIME_DESC_ANDREW + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_RESERVATION_NAME_DESC + INVALID_RESERVATION_PAX_DESC
                        + RESERVATION_DATETIME_DESC_ANDREW, Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + RESERVATION_NAME_DESC_ANDREW
                        + RESERVATION_PAX_DESC_ANDREW + RESERVATION_DATETIME_DESC_ANDREW,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReservationCommand.MESSAGE_USAGE));
    }
}
