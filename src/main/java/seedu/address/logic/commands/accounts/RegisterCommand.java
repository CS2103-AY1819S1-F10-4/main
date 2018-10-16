package seedu.address.logic.commands.accounts;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.accounts.Account;

/**
 * Adds a new user account to the account file.
 */
public class RegisterCommand extends Command {

    public static final String COMMAND_WORD = "register";
    public static final String COMMAND_ALIAS = "reg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Registers a new user account. "
            + "Parameters: "
            + PREFIX_ID + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "azhikai "
            + PREFIX_PASSWORD + "1122qq";

    public static final String MESSAGE_SUCCESS = "New account registered: %1$s!";
    public static final String MESSAGE_DUPLICATE_USERNAME = "This username already exists.";

    private final Account account;

    public RegisterCommand(Account account) {
        requireNonNull(account);
        this.account = account;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasAccount(account)) {
            throw new CommandException(MESSAGE_DUPLICATE_USERNAME);
        }

        model.addAccount(account);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, account));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RegisterCommand // instanceof handles nulls
                && account.equals(((RegisterCommand) other).account));
    }
}
