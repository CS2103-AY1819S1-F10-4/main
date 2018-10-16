package seedu.address.logic.commands.menu;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the menu.
 */
public class ClearMenuCommand extends Command {

    public static final String COMMAND_WORD = "clear-menu";
    public static final String COMMAND_ALIAS = "cm";
    public static final String MESSAGE_SUCCESS = "Menu has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetMenuData(new AddressBook());
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
