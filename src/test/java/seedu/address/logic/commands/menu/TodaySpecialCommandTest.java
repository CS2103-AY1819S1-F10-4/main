package seedu.address.logic.commands.menu;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_ITEMS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.Calendar;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.menu.TagContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindItemCommand}.
 */
public class TodaySpecialCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_itemFound() {
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 1);
        TagContainsKeywordsPredicate predicate = preparePredicate();
        TodaySpecialCommand command = new TodaySpecialCommand();
        expectedModel.updateFilteredItemList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noItemFound() {
        AddressBook ab = new AddressBookBuilder().build();
        model = new ModelManager(ab, new UserPrefs());
        expectedModel = new ModelManager(ab, new UserPrefs());
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 0);
        TagContainsKeywordsPredicate predicate = preparePredicate();
        TodaySpecialCommand command = new TodaySpecialCommand();
        expectedModel.updateFilteredItemList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredItemList());
    }

    /**
     * Parses {@code userInput} into a {@code TagContainsKeywordsPredicate}.
     */
    private TagContainsKeywordsPredicate preparePredicate() {
        String str;
        switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
        case Calendar.SUNDAY:
            str = "sunday";
            break;
        case Calendar.MONDAY:
            str = "monday";
            break;
        case Calendar.TUESDAY:
            str = "tuesday";
            break;
        case Calendar.WEDNESDAY:
            str = "wednesday";
            break;
        case Calendar.THURSDAY:
            str = "thursday";
            break;
        case Calendar.FRIDAY:
            str = "friday";
            break;
        default:
            str = "saturday";
        }
        return new TagContainsKeywordsPredicate(Collections.singletonList(str));
    }
}