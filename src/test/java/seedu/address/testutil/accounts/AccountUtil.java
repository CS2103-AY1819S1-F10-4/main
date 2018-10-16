package seedu.address.testutil.accounts;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.address.logic.commands.accounts.RegisterCommand;
import seedu.address.model.accounts.Account;

/**
 * A utility class for {@code Account}.
 */
public class AccountUtil {

    /**
     * Returns a create command string for adding the {@code account}.
     */
    public static String getCreateCommand(Account account) {
        return RegisterCommand.COMMAND_WORD + " " + getAccountDetails(account);
    }

    /**
     * Returns the part of command string for the given {@code account}'s details.
     */
    public static String getAccountDetails(Account account) {
        return PREFIX_ID + account.getUsername().toString() + " "
                + PREFIX_PASSWORD + account.getPassword().toString();
    }
}
