package seedu.address.logic.parser.reservation;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Pax;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ReservationParserUtil {

    //================ Reservation Commands Parser Util ===================================================

    /**
     * Parses a {@code String name} into a {@code Name}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String pax} into a {@code Pax}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code pax} is invalid.
     */
    public static Pax parsePax(String pax) throws ParseException {
        requireNonNull(pax);
        String trimmedPhone = pax.trim();
        if (!Pax.isValidPax(trimmedPhone)) {
            throw new ParseException(Pax.MESSAGE_PAX_CONSTRAINTS);
        }
        return new Pax(trimmedPhone);
    }

    /**
     * Parses a {@code String dateTime} into a {@code LocalDateTime}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateTime} is invalid.
     */
    public static LocalDateTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        LocalDateTime parsedDateTime;
        try {
            parsedDateTime = LocalDateTime.parse(trimmedDateTime);
        } catch (DateTimeParseException e) {
            throw new ParseException("DateTime value should be in the form 2018-12-31T10:00:00");
        }
        return parsedDateTime;
    }
}
