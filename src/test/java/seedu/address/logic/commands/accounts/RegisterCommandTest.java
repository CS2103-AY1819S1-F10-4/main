package seedu.address.logic.commands.accounts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_ADMIN;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.accounts.Account;
import seedu.address.testutil.accounts.AccountBuilder;

public class RegisterCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager();

    @Test
    public void executeSuccess() throws CommandException {
        Account validAccount = new AccountBuilder(DEMO_ADMIN).build();
        CommandResult commandResult = new RegisterCommand(validAccount).execute(model, commandHistory);

        assertEquals(String.format(RegisterCommand.MESSAGE_SUCCESS, validAccount), commandResult.feedbackToUser);
        Assert.assertEquals(EMPTY_COMMAND_HISTORY, commandHistory); //TODO: remove?
    }

    //TODO: Improve
}
