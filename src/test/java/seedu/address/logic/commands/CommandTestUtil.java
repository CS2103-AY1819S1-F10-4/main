package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_MINIMUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_UNIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY_SOLD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.ingredients.EditIngredientCommand;
import seedu.address.logic.commands.menu.EditItemCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.ingredients.EditIngredientDescriptorBuilder;
import seedu.address.testutil.menu.EditItemDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_TEST = "test";
    public static final String VALID_REMARK_AMY = "Likes to drink coffee.";
    public static final String VALID_REMARK_BOB = "Likes to drink tea.";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    /** For sales records */
    public static final String VALID_DATE_RECORD_ONE = "28-02-2018";
    public static final String VALID_DATE_RECORD_TWO = "11-11-2011";
    public static final String VALID_DATE_RECORD_THREE = "25-12-2017";
    public static final String VALID_ITEM_NAME_RECORD_ONE = "Cheese Pizza";
    public static final String VALID_ITEM_NAME_RECORD_TWO = "Pasta";
    public static final String VALID_ITEM_NAME_RECORD_THREE = "Orange Juice";
    public static final String VALID_QUANTITY_SOLD_RECORD_ONE = "100";
    public static final String VALID_QUANTITY_SOLD_RECORD_TWO = "87";
    public static final String VALID_QUANTITY_SOLD_RECORD_THREE = "202";
    public static final String VALID_PRICE_RECORD_ONE = "18.50";
    public static final String VALID_PRICE_RECORD_TWO = "7.99";
    public static final String VALID_PRICE_RECORD_THREE = "2";

    public static final String PREFIX_WITH_VALID_DATE = " " + PREFIX_DATE + VALID_DATE_RECORD_ONE;
    public static final String PREFIX_WITH_VALID_ITEM_NAME = " " + PREFIX_ITEM_NAME + VALID_ITEM_NAME_RECORD_ONE;
    public static final String PREFIX_WITH_VALID_QUANTITY_SOLD =
            " " + PREFIX_QUANTITY_SOLD + VALID_QUANTITY_SOLD_RECORD_ONE;
    public static final String PREFIX_WITH_VALID_PRICE = " " + PREFIX_ITEM_PRICE + VALID_PRICE_RECORD_ONE;
    public static final String PREFIX_WITH_INVALID_DATE = " " + PREFIX_DATE + "31-02-2018"; // no such date
    public static final String PREFIX_WITH_INVALID_ITEM_NAME = " " + PREFIX_ITEM_NAME + "Fried Rice!"; // symbols not
    // allowed
    public static final String PREFIX_WITH_INVALID_QUANTITY_SOLD = " " + PREFIX_QUANTITY_SOLD + "3.5"; // positive
    // integer only
    public static final String PREFIX_WITH_INVALID_PRICE = " " + PREFIX_ITEM_PRICE + "-2"; // negative price not allowed

    /** For accounts */
    public static final String VALID_USERNAME_DEMO_ONE = "demo1";
    public static final String VALID_USERNAME_DEMO_TWO = "demo2";
    public static final String VALID_USERNAME_DEMO_THREE = "demo3";
    public static final String VALID_PASSWORD_DEMO_ONE = "1122qq";
    public static final String VALID_PASSWORD_DEMO_TWO = "22qqww";
    public static final String VALID_PASSWORD_DEMO_THREE = "abc!@#";

    public static final String PREFIX_WITH_VALID_USERNAME = " " + PREFIX_ID + VALID_USERNAME_DEMO_ONE;
    public static final String PREFIX_WITH_VALID_PASSWORD = " " + PREFIX_PASSWORD + VALID_PASSWORD_DEMO_ONE;

    public static final String PREFIX_WITH_INVALID_USERNAME = " " + PREFIX_ID + "azhi kai"; // space not allowed
    public static final String PREFIX_WITH_INVALID_PASSWORD = " " + PREFIX_PASSWORD + "11 22qq"; // space not allowed

    /** For ingredients */
    public static final String VALID_NAME_APPLE = "Granny Smith Apple";
    public static final String VALID_NAME_BROCCOLI = "Australian Broccoli";
    public static final String VALID_UNIT_APPLE = "packet of 5";
    public static final String VALID_UNIT_BROCCOLI = "kilograms";
    public static final String VALID_PRICE_APPLE = "1.90";
    public static final String VALID_PRICE_BROCCOLI = "6.50";
    public static final String VALID_MINIMUM_APPLE = "3";
    public static final String VALID_MINIMUM_BROCCOLI = "5";

    public static final String INGREDIENT_NAME_DESC_APPLE = " " + PREFIX_INGREDIENT_NAME + VALID_NAME_APPLE;
    public static final String INGREDIENT_NAME_DESC_BROCCOLI = " " + PREFIX_INGREDIENT_NAME + VALID_NAME_BROCCOLI;
    public static final String INGREDIENT_UNIT_DESC_APPLE = " " + PREFIX_INGREDIENT_UNIT + VALID_UNIT_APPLE;
    public static final String INGREDIENT_UNIT_DESC_BROCCOLI = " " + PREFIX_INGREDIENT_UNIT + VALID_UNIT_BROCCOLI;
    public static final String INGREDIENT_PRICE_DESC_APPLE = " " + PREFIX_INGREDIENT_PRICE + VALID_PRICE_APPLE;
    public static final String INGREDIENT_PRICE_DESC_BROCCOLI = " " + PREFIX_INGREDIENT_PRICE + VALID_PRICE_BROCCOLI;
    public static final String INGREDIENT_MINIMUM_DESC_APPLE = " " + PREFIX_INGREDIENT_MINIMUM + VALID_MINIMUM_APPLE;
    public static final String INGREDIENT_MINIMUM_DESC_BROCCOLI = " " + PREFIX_INGREDIENT_MINIMUM
            + VALID_MINIMUM_BROCCOLI;

    /** For menu */
    public static final String VALID_ITEM_NAME_BURGER = "Burger";
    public static final String VALID_ITEM_NAME_FRIES = "Cheese Fries";
    public static final String VALID_ITEM_PRICE_BURGER = "2.50";
    public static final String VALID_ITEM_PRICE_FRIES = "2";
    public static final String VALID_ITEM_TAG_BURGER = "burger";
    public static final String VALID_ITEM_TAG_CHEESE = "cheese";
    public static final String VALID_ITEM_REMARK_BURGER = "Burger with beef patty.";
    public static final String VALID_ITEM_REMARK_FRIES = "Fries with cheese.";

    public static final String ITEM_NAME_DESC_BURGER = " " + PREFIX_NAME + VALID_ITEM_NAME_BURGER;
    public static final String ITEM_NAME_DESC_FRIES = " " + PREFIX_NAME + VALID_ITEM_NAME_FRIES;
    public static final String ITEM_PRICE_DESC_BURGER = " " + PREFIX_PRICE + VALID_ITEM_PRICE_BURGER;
    public static final String ITEM_PRICE_DESC_FRIES = " " + PREFIX_PRICE + VALID_ITEM_PRICE_FRIES;
    public static final String ITEM_TAG_DESC_BURGER = " " + PREFIX_TAG + VALID_ITEM_TAG_BURGER;
    public static final String ITEM_TAG_DESC_CHEESE = " " + PREFIX_TAG + VALID_ITEM_TAG_CHEESE;

    public static final String INVALID_ITEM_NAME_DESC = " " + PREFIX_NAME + "Fries&"; // '&' not allowed in names
    public static final String INVALID_PRICE_DESC = " " + PREFIX_PRICE + "9.000"; // 3 decimal places not allowed in
    // prices

    /** For Reservation */
    public static final String VALID_RESERVATION_NAME_ANDREW = "Andrew";
    public static final String VALID_RESERVATION_NAME_BILLY = "Billy";
    public static final String VALID_RESERVATION_PAX_ANDREW = "2";
    public static final String VALID_RESERVATION_PAX_BILLY = "4";
    public static final String VALID_RESERVATION_DATETIME_ANDREW = "2018-12-03T12:00:00";
    public static final String VALID_RESERVATION_DATETIME_BILLY = "2018-12-05T18:00:00";
    public static final String VALID_RESERVATION_REMARK_ANDREW = "Driving";
    public static final String VALID_RESERVATION_REMARK_BILLY = "Allergies";
    public static final String VALID_RESERVATION_TAG_ANDREW = "Driving";
    public static final String VALID_RESERVATION_TAG_BILLY = "Allergies";

    public static final String RESERVATION_NAME_DESC_ANDREW = " " + PREFIX_NAME + VALID_RESERVATION_NAME_ANDREW;
    public static final String RESERVATION_NAME_DESC_BILLY = " " + PREFIX_NAME + VALID_RESERVATION_NAME_BILLY;
    public static final String RESERVATION_PAX_DESC_ANDREW = " " + PREFIX_PAX + VALID_RESERVATION_PAX_ANDREW;
    public static final String RESERVATION_PAX_DESC_BILLY = " " + PREFIX_PAX + VALID_RESERVATION_PAX_BILLY;
    public static final String RESERVATION_DATETIME_DESC_ANDREW =
            " " + PREFIX_DATETIME + VALID_RESERVATION_DATETIME_ANDREW;
    public static final String RESERVATION_DATETIME_DESC_BILLY =
            " " + PREFIX_DATETIME + VALID_RESERVATION_DATETIME_BILLY;
    public static final String RESERVATION_TAG_DESC_ANDREW = " " + PREFIX_TAG + VALID_RESERVATION_TAG_ANDREW;
    public static final String RESERVATION_TAG_DESC_BILLY = " " + PREFIX_TAG + VALID_RESERVATION_TAG_BILLY;

    public static final String INVALID_RESERVATION_NAME_DESC = " " + PREFIX_NAME + "S&shrew"; // '&' not allowed
    public static final String INVALID_RESERVATION_PAX_DESC = " " + PREFIX_PAX + "a4"; // letters not allowed
    public static final String INVALID_RESERVATION_DATETIME_DESC = " " + PREFIX_DATETIME + "2018-99"; // incomplete

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final EditItemCommand.EditItemDescriptor DESC_BURGER;
    public static final EditItemCommand.EditItemDescriptor DESC_FRIES;

    public static final EditIngredientCommand.EditIngredientDescriptor DESC_APPLE;
    public static final EditIngredientCommand.EditIngredientDescriptor DESC_BROCCOLI;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

        // Menu Management
        DESC_BURGER = new EditItemDescriptorBuilder().withName(VALID_ITEM_NAME_BURGER)
                .withPrice(VALID_ITEM_PRICE_BURGER).build();
        DESC_FRIES = new EditItemDescriptorBuilder().withName(VALID_ITEM_NAME_FRIES).withPrice(VALID_ITEM_PRICE_FRIES)
                        .withTags(VALID_ITEM_TAG_CHEESE).build();

        // Ingredient Management
        DESC_APPLE = new EditIngredientDescriptorBuilder().withName(VALID_NAME_APPLE)
                .withPrice(VALID_PRICE_APPLE).build();
        DESC_BROCCOLI =
                new EditIngredientDescriptorBuilder().withName(VALID_NAME_BROCCOLI).withPrice(VALID_PRICE_BROCCOLI)
                .withUnit(VALID_UNIT_BROCCOLI).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
        model.commitAddressBook();
    }

    /**
     * Updates {@code model}'s filtered list to show only the ingredient at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showIngredientAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredIngredientList().size());

        Ingredient ingredient = model.getFilteredIngredientList().get(targetIndex.getZeroBased());
        final String[] splitIngredient = ingredient.getName().fullName.split("\\s+");
        model.updateFilteredIngredientList(
                new IngredientNameContainsKeywordsPredicate(Arrays.asList(splitIngredient[0])));

        assertEquals(1, model.getFilteredIngredientList().size());
    }

}
