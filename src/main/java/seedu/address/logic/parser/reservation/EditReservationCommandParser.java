package seedu.address.logic.parser.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.reservation.EditReservationCommand;
import seedu.address.logic.commands.reservation.EditReservationCommand.EditReservationDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditReservationCommand object
 */
public class EditReservationCommandParser implements Parser<EditReservationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditReservationCommand
     * and returns an EditReservationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditReservationCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PAX, PREFIX_DATETIME, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditReservationCommand.MESSAGE_USAGE), pe);
        }

        EditReservationDescriptor editReservationDescriptor = new EditReservationDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editReservationDescriptor.setName(ReservationParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PAX).isPresent()) {
            editReservationDescriptor.setPax(ReservationParserUtil.parsePax(argMultimap.getValue(PREFIX_PAX).get()));
        }
        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            editReservationDescriptor.setDateTime(ReservationParserUtil
                    .parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editReservationDescriptor::setTags);

        if (!editReservationDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditReservationCommand.MESSAGE_NOT_EDITED);
        }

        return new EditReservationCommand(index, editReservationDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
