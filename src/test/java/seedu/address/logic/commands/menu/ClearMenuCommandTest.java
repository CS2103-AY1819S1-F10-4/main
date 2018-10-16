package seedu.address.logic.commands.menu;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.menu.TypicalItems;

public class ClearMenuCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyMenu_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ClearMenuCommand(), model, commandHistory, ClearMenuCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyMenu_success() {
        Model model = new ModelManager(TypicalItems.getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalItems.getTypicalAddressBook(), new UserPrefs());
        expectedModel.resetMenuData(new AddressBook());
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ClearMenuCommand(), model, commandHistory, ClearMenuCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

}
