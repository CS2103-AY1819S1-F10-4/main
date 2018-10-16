package seedu.address.logic.commands.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reservation.Reservation;

/**
 * Adds a reservation to the address book.
 */
public class AddReservationCommand extends Command {

    public static final String COMMAND_WORD = "add-reservation";

    public static final String COMMAND_ALIAS = "ar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reservation to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PAX + "PAX "
            + PREFIX_DATETIME + "DATETIME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PAX + "4 "
            + PREFIX_DATETIME + "2018-12-03T10:00:00 "
            + PREFIX_TAG + "Driving";

    public static final String MESSAGE_SUCCESS = "New reservation added: %1$s";
    public static final String MESSAGE_DUPLICATE_RESERVATION = "This reservation already exists in the address book";

    private final Reservation toAdd;

    /**
     * Creates an AddReservationCommand to add the specified {@code Reservation}
     */
    public AddReservationCommand(Reservation reservation) {
        requireNonNull(reservation);
        toAdd = reservation;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasReservation(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_RESERVATION);
        }

        model.addReservation(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddReservationCommand // instanceof handles nulls
                    && toAdd.equals(((AddReservationCommand) other).toAdd));
    }
}
