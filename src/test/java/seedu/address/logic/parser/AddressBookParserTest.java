package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_PERCENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_RECORD_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_PERCENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_ONE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.accounts.DeregisterCommand;
import seedu.address.logic.commands.accounts.LoginCommand;
import seedu.address.logic.commands.accounts.LogoutCommand;
import seedu.address.logic.commands.accounts.RegisterCommand;
import seedu.address.logic.commands.ingredients.AddIngredientCommand;
import seedu.address.logic.commands.ingredients.DeleteIngredientByIndexCommand;
import seedu.address.logic.commands.ingredients.DeleteIngredientByNameCommand;
import seedu.address.logic.commands.ingredients.DeleteIngredientCommand;
import seedu.address.logic.commands.ingredients.EditIngredientByIndexCommand;
import seedu.address.logic.commands.ingredients.EditIngredientByNameCommand;
import seedu.address.logic.commands.ingredients.EditIngredientCommand;
import seedu.address.logic.commands.ingredients.EditIngredientCommand.EditIngredientDescriptor;
import seedu.address.logic.commands.ingredients.ListIngredientsCommand;
import seedu.address.logic.commands.menu.AddItemCommand;
import seedu.address.logic.commands.menu.ClearMenuCommand;
import seedu.address.logic.commands.menu.DeleteItemCommand;
import seedu.address.logic.commands.menu.DiscountItemCommand;
import seedu.address.logic.commands.menu.EditItemCommand;
import seedu.address.logic.commands.menu.EditItemCommand.EditItemDescriptor;
import seedu.address.logic.commands.menu.FilterMenuCommand;
import seedu.address.logic.commands.menu.ListItemsCommand;
import seedu.address.logic.commands.menu.RecipeItemCommand;
import seedu.address.logic.commands.menu.SelectItemCommand;
import seedu.address.logic.commands.menu.SortMenuCommand;
import seedu.address.logic.commands.menu.SortMenuCommand.SortMethod;
import seedu.address.logic.commands.menu.TodaySpecialCommand;
import seedu.address.logic.commands.sales.DeleteSalesCommand;
import seedu.address.logic.commands.sales.DisplaySalesCommand;
import seedu.address.logic.commands.sales.EditSalesCommand;
import seedu.address.logic.commands.sales.EditSalesCommand.EditRecordDescriptor;
import seedu.address.logic.commands.sales.RecordSalesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.accounts.Account;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.menu.Item;
import seedu.address.model.menu.Recipe;
import seedu.address.model.menu.TagContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.salesrecord.Date;
import seedu.address.model.salesrecord.SalesRecord;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.accounts.AccountBuilder;
import seedu.address.testutil.ingredients.EditIngredientDescriptorBuilder;
import seedu.address.testutil.ingredients.IngredientBuilder;
import seedu.address.testutil.ingredients.IngredientUtil;
import seedu.address.testutil.menu.EditItemDescriptorBuilder;
import seedu.address.testutil.menu.ItemBuilder;
import seedu.address.testutil.menu.ItemUtil;
import seedu.address.testutil.salesrecords.EditRecordDescriptorBuilder;
import seedu.address.testutil.salesrecords.RecordBuilder;
import seedu.address.testutil.salesrecords.RecordUtil;

public class AddressBookParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
        command = (AddCommand) parser.parseCommand(AddCommand.COMMAND_ALIAS
                + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST), command);
        command = (DeleteCommand) parser.parseCommand(DeleteCommand.COMMAND_ALIAS
                + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST, descriptor), command);
        command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
        command = (FindCommand) parser.parseCommand(FindCommand.COMMAND_ALIAS + " "
                + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS + " 3") instanceof HistoryCommand);
        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST), command);
        command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD + " 1") instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS + " 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + " 3") instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS + " 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_remark() throws Exception {
        final Remark remark = new Remark("Some remark.");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + PREFIX_REMARK + remark.value);
        assertEquals(new RemarkCommand(INDEX_FIRST, remark), command);
    }

    @Test
    public void parseCommand_recordSales() throws Exception {
        SalesRecord record = new RecordBuilder().build();
        RecordSalesCommand command = (RecordSalesCommand) parser.parseCommand(RecordUtil.getRecordSalesCommand(record));
        assertEquals(new RecordSalesCommand(record), command);
        // alias test
        command = (RecordSalesCommand) parser.parseCommand(RecordSalesCommand.COMMAND_ALIAS
                + " " + RecordUtil.getRecordDetails(record));
        assertEquals(new RecordSalesCommand(record), command);
    }

    @Test
    public void parseCommand_deleteSales() throws Exception {
        DeleteSalesCommand command = (DeleteSalesCommand) parser.parseCommand(
                DeleteSalesCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteSalesCommand(INDEX_FIRST), command);
        // alias test
        command = (DeleteSalesCommand) parser.parseCommand(DeleteSalesCommand.COMMAND_ALIAS
                + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteSalesCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_editSales() throws Exception {
        SalesRecord salesRecord = new RecordBuilder().build();
        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder(salesRecord).build();
        EditSalesCommand command = (EditSalesCommand) parser.parseCommand(EditSalesCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + RecordUtil.getEditRecordDescriptorDetails(descriptor));
        assertEquals(new EditSalesCommand(INDEX_FIRST, descriptor), command);
        // alias test
        command = (EditSalesCommand) parser.parseCommand(EditSalesCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST.getOneBased() + " " + RecordUtil.getEditRecordDescriptorDetails(descriptor));
        assertEquals(new EditSalesCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_display_sales() throws Exception {
        Date date = new Date(VALID_DATE_RECORD_ONE);
        DisplaySalesCommand command = (DisplaySalesCommand) parser.parseCommand(
                DisplaySalesCommand.COMMAND_WORD + " " + VALID_DATE_RECORD_ONE);
        assertEquals(new DisplaySalesCommand(date), command);
        // alias test
        command = (DisplaySalesCommand) parser.parseCommand(DisplaySalesCommand.COMMAND_ALIAS + " "
                + VALID_DATE_RECORD_ONE);
        assertEquals(new DisplaySalesCommand(date), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }

    @Test
    public void parseCommand_register() throws ParseException {
        Account account = new AccountBuilder().build();
        RegisterCommand command = (RegisterCommand) parser.parseCommand(RegisterCommand.COMMAND_WORD + " "
                + PREFIX_ID + account.getUsername().toString() + " "
                + PREFIX_PASSWORD + account.getPassword().toString());
        assertEquals(new RegisterCommand(account), command);
        RegisterCommand commandAlias = (RegisterCommand) parser.parseCommand(RegisterCommand.COMMAND_ALIAS
                + " " + PREFIX_ID + account.getUsername().toString() + " "
                + PREFIX_PASSWORD + account.getPassword().toString());
        assertEquals(new RegisterCommand(account), commandAlias);
    }

    @Test
    public void parseCommand_deregister() throws ParseException {
        Account account = new Account(DEMO_ONE.getUsername());
        DeregisterCommand command = (DeregisterCommand) parser.parseCommand(DeregisterCommand.COMMAND_WORD
                + " " + PREFIX_ID + account.getUsername().toString());
        assertEquals(new DeregisterCommand(account), command);
        DeregisterCommand commandAlias = (DeregisterCommand) parser.parseCommand(DeregisterCommand.COMMAND_ALIAS
                + " " + PREFIX_ID + account.getUsername().toString());
        assertEquals(new DeregisterCommand(account), commandAlias);
    }

    @Test
    public void parseCommand_login() throws ParseException {
        Account account = new AccountBuilder().build();
        LoginCommand command = (LoginCommand) parser.parseCommand(LoginCommand.COMMAND_WORD + " "
                + PREFIX_ID + account.getUsername().toString() + " "
                + PREFIX_PASSWORD + account.getPassword().toString());
        assertEquals(new LoginCommand(account), command);
    }

    @Test
    public void parseCommand_logout() throws ParseException {
        assertTrue(parser.parseCommand(LogoutCommand.COMMAND_WORD) instanceof LogoutCommand);
    }

    @Test
    public void parseCommand_createAccount_notEquals() throws ParseException {
        Account accountOneCommand = new AccountBuilder().build();
        Account accountTwoCommand = new AccountBuilder().withUsername("demo1").withPassword("1122qq").build();
        RegisterCommand commandOne = (RegisterCommand) parser.parseCommand(RegisterCommand.COMMAND_WORD + " "
                + PREFIX_ID + accountOneCommand.getUsername().toString() + " "
                + PREFIX_PASSWORD + accountOneCommand.getPassword().toString());
        RegisterCommand commandTwo = (RegisterCommand) parser.parseCommand(RegisterCommand.COMMAND_WORD + " "
                + PREFIX_ID + accountTwoCommand.getUsername().toString() + " "
                + PREFIX_PASSWORD + accountTwoCommand.getPassword().toString());
        assertNotEquals(commandOne, commandTwo);
    }

    @Test
    public void parseCommand_addIngredient() throws Exception {
        Ingredient ingredient = new IngredientBuilder().build();
        AddIngredientCommand command =
                (AddIngredientCommand) parser.parseCommand(IngredientUtil.getAddIngredientCommand(ingredient));
        assertEquals(new AddIngredientCommand(ingredient), command);
        command = (AddIngredientCommand) parser.parseCommand(AddIngredientCommand.COMMAND_ALIAS
                + " " + IngredientUtil.getIngredientDetails(ingredient));
        assertEquals(new AddIngredientCommand(ingredient), command);
    }

    //TODO: Add test for EditItemCommandParser

    @Test
    public void parseCommand_listIngredients() throws Exception {
        assertTrue(parser.parseCommand(ListIngredientsCommand.COMMAND_WORD) instanceof ListIngredientsCommand);
        assertTrue(parser
                .parseCommand(ListIngredientsCommand.COMMAND_WORD + " 3") instanceof ListIngredientsCommand);
        assertTrue(parser.parseCommand(ListIngredientsCommand.COMMAND_ALIAS) instanceof ListIngredientsCommand);
        assertTrue(parser
                .parseCommand(ListIngredientsCommand.COMMAND_ALIAS + " 3") instanceof ListIngredientsCommand);
    }

    @Test
    public void parseCommand_deleteIngredient() throws Exception {
        DeleteIngredientCommand command = (DeleteIngredientCommand) parser.parseCommand(
                DeleteIngredientCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteIngredientByIndexCommand(INDEX_FIRST), command);
        command = (DeleteIngredientCommand) parser.parseCommand(
                DeleteIngredientCommand.COMMAND_ALIAS + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteIngredientByIndexCommand(INDEX_FIRST), command);
        command = (DeleteIngredientCommand) parser.parseCommand(
                DeleteIngredientCommand.COMMAND_WORD + " " + "Chicken Thigh");
        assertEquals(new DeleteIngredientByNameCommand(new IngredientName("Chicken Thigh")), command);
        command = (DeleteIngredientCommand) parser.parseCommand(
                DeleteIngredientCommand.COMMAND_ALIAS + " " + "Chicken Thigh");
        assertEquals(new DeleteIngredientByNameCommand(new IngredientName("Chicken Thigh")), command);
    }

    @Test
    public void parseCommand_editIngredient() throws Exception {
        Ingredient ingredient = new IngredientBuilder().build();
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder(ingredient).build();

        // full command name, edit by index
        EditIngredientCommand command =
                (EditIngredientByIndexCommand) parser.parseCommand(
                        EditIngredientByIndexCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased()
                                + " " + IngredientUtil.getEditIngredientDescriptorDetails(descriptor));
        assertEquals(new EditIngredientByIndexCommand(INDEX_FIRST, descriptor), command);

        // command alias, edit by index
        command = (EditIngredientByIndexCommand) parser.parseCommand(
                EditIngredientByIndexCommand.COMMAND_ALIAS + " " + INDEX_FIRST.getOneBased()
                        + " " + IngredientUtil.getEditIngredientDescriptorDetails(descriptor));
        assertEquals(new EditIngredientByIndexCommand(INDEX_FIRST, descriptor), command);

        // full command name, edit by name
        command = (EditIngredientByNameCommand) parser.parseCommand(
                EditIngredientByNameCommand.COMMAND_WORD + " " + "Chicken Thigh"
                                + " " + IngredientUtil.getEditIngredientDescriptorDetails(descriptor));
        assertEquals(new EditIngredientByNameCommand(new IngredientName("Chicken Thigh"), descriptor), command);

        // command alias, edit by name
        command = (EditIngredientByNameCommand) parser.parseCommand(
                EditIngredientByNameCommand.COMMAND_ALIAS + " " + "Chicken Thigh"
                        + " " + IngredientUtil.getEditIngredientDescriptorDetails(descriptor));
        assertEquals(new EditIngredientByNameCommand(new IngredientName("Chicken Thigh"), descriptor), command);
    }

    @Test
    public void parseCommand_addItem() throws Exception {
        Item item = new ItemBuilder().build();
        AddItemCommand command = (AddItemCommand) parser.parseCommand(ItemUtil.getAddItemCommand(item));
        assertEquals(new AddItemCommand(item), command);
        command = (AddItemCommand) parser.parseCommand(AddItemCommand.COMMAND_ALIAS
                + " " + ItemUtil.getItemDetails(item));
        assertEquals(new AddItemCommand(item), command);
    }

    @Test
    public void parseCommand_deleteItem() throws Exception {
        DeleteItemCommand command = (DeleteItemCommand) parser.parseCommand(
                DeleteItemCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteItemCommand(INDEX_FIRST), command);
        command = (DeleteItemCommand) parser.parseCommand(DeleteItemCommand.COMMAND_ALIAS
                + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteItemCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_editItem() throws Exception {
        Item item = new ItemBuilder().build();
        EditItemDescriptor descriptor = new EditItemDescriptorBuilder(item).build();
        EditItemCommand command = (EditItemCommand) parser.parseCommand(EditItemCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + ItemUtil.getEditItemDescriptorDetails(descriptor));
        assertEquals(new EditItemCommand(INDEX_FIRST, descriptor), command);
        command = (EditItemCommand) parser.parseCommand(EditItemCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST.getOneBased() + " " + ItemUtil.getEditItemDescriptorDetails(descriptor));
        assertEquals(new EditItemCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_listItems() throws Exception {
        assertTrue(parser.parseCommand(ListItemsCommand.COMMAND_WORD) instanceof ListItemsCommand);
        assertTrue(parser.parseCommand(ListItemsCommand.COMMAND_ALIAS) instanceof ListItemsCommand);
        assertTrue(parser.parseCommand(ListItemsCommand.COMMAND_WORD + " 3") instanceof ListItemsCommand);
        assertTrue(parser.parseCommand(ListItemsCommand.COMMAND_ALIAS + " 3") instanceof ListItemsCommand);
    }

    @Test
    public void parseCommand_selectItem() throws Exception {
        SelectItemCommand command = (SelectItemCommand) parser.parseCommand(
                SelectItemCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new SelectItemCommand(INDEX_FIRST), command);
        command = (SelectItemCommand) parser.parseCommand(
                SelectItemCommand.COMMAND_ALIAS + " " + INDEX_FIRST.getOneBased());
        assertEquals(new SelectItemCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_recipeItem() throws Exception {
        final Recipe recipe = new Recipe("Some recipe.");
        RecipeItemCommand command = (RecipeItemCommand) parser.parseCommand(RecipeItemCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + PREFIX_REMARK + recipe.toString());
        assertEquals(new RecipeItemCommand(INDEX_FIRST, recipe), command);
        command = (RecipeItemCommand) parser.parseCommand(RecipeItemCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST.getOneBased() + " " + PREFIX_REMARK + recipe.toString());
        assertEquals(new RecipeItemCommand(INDEX_FIRST, recipe), command);
    }

    @Test
    public void parseCommand_sortMenu() throws Exception {
        SortMenuCommand command = (SortMenuCommand) parser.parseCommand(
                SortMenuCommand.COMMAND_WORD + " name");
        assertEquals(new SortMenuCommand(SortMethod.NAME), command);
        command = (SortMenuCommand) parser.parseCommand(
                SortMenuCommand.COMMAND_ALIAS + " price");
        assertEquals(new SortMenuCommand(SortMethod.PRICE), command);
    }

    @Test
    public void parseCommand_filterMenu() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FilterMenuCommand command = (FilterMenuCommand) parser.parseCommand(
                FilterMenuCommand.COMMAND_WORD + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FilterMenuCommand(new TagContainsKeywordsPredicate(keywords)), command);
        command = (FilterMenuCommand) parser.parseCommand(FilterMenuCommand.COMMAND_ALIAS + " "
                + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FilterMenuCommand(new TagContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_todaySpecial() throws Exception {
        assertTrue(parser.parseCommand(TodaySpecialCommand.COMMAND_WORD) instanceof TodaySpecialCommand);
        assertTrue(parser.parseCommand(TodaySpecialCommand.COMMAND_ALIAS) instanceof TodaySpecialCommand);
        assertTrue(parser.parseCommand(TodaySpecialCommand.COMMAND_WORD + " 3") instanceof TodaySpecialCommand);
        assertTrue(parser.parseCommand(TodaySpecialCommand.COMMAND_ALIAS + " 3") instanceof TodaySpecialCommand);
    }

    @Test
    public void parseCommand_discountItem() throws Exception {
        DiscountItemCommand command = (DiscountItemCommand) parser.parseCommand(
                DiscountItemCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + ITEM_PERCENT_DESC);
        assertEquals(new DiscountItemCommand(INDEX_FIRST, INDEX_FIRST,
                Double.parseDouble(VALID_ITEM_PERCENT), false), command);
        command = (DiscountItemCommand) parser.parseCommand(DiscountItemCommand.COMMAND_ALIAS
                + " " + INDEX_FIRST.getOneBased() + ITEM_PERCENT_DESC);
        assertEquals(new DiscountItemCommand(INDEX_FIRST, INDEX_FIRST,
                Double.parseDouble(VALID_ITEM_PERCENT), false), command);
    }

    @Test
    public void parseCommand_clearMenu() throws Exception {
        assertTrue(parser.parseCommand(ClearMenuCommand.COMMAND_WORD) instanceof ClearMenuCommand);
        assertTrue(parser.parseCommand(ClearMenuCommand.COMMAND_ALIAS) instanceof ClearMenuCommand);
        assertTrue(parser.parseCommand(ClearMenuCommand.COMMAND_WORD + " 3") instanceof ClearMenuCommand);
        assertTrue(parser.parseCommand(ClearMenuCommand.COMMAND_ALIAS + " 3") instanceof ClearMenuCommand);
    }
}
