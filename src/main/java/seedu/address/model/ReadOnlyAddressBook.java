package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.accounts.Account;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.menu.Item;
import seedu.address.model.person.Person;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.salesrecord.SalesRecord;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list. This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the records list. This list will not contain any duplicate records.
     */
    ObservableList<SalesRecord> getRecordList();

    /**
     * Returns an unmodifiable view of the accounts list. This list will not contain any duplicate accounts.
     */
    ObservableList<Account> getAccountList();

    /**
     * Returns an unmodifiable view of the ingredients list. This list will not contain any duplicate ingredients.
     */
    ObservableList<Ingredient> getIngredientList();

    /**
     * Returns an unmodifiable view of the items list.
     * This list will not contain any duplicate items.
     */
    ObservableList<Item> getItemList();

    // Reservation Management
    /**
     * Returns an unmodifiable view of the reservations list.
     * This list will not contain any duplicate reservations.
     */
    ObservableList<Reservation> getReservationList();

}
