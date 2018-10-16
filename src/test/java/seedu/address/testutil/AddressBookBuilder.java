package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.accounts.Account;
import seedu.address.model.menu.Item;
import seedu.address.model.person.Person;
import seedu.address.model.salesrecord.SalesRecord;

/**
 * A utility class to help with building AddressBook objects. Example usage: <br> {@code AddressBook ab = new
 * AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        addressBook.addPerson(person);
        return this;
    }

    /**
     * Adds a new {@code Record} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withRecord(SalesRecord record) {
        addressBook.addRecord(record);
        return this;
    }

    /**
     * Adds a new {@code Account} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withAccount(Account account) {
        addressBook.addAccount(account);
        return this;
    }

    // Menu Management
    /**
     * Adds a new {@code Item} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withItem(Item item) {
        addressBook.addItem(item);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
