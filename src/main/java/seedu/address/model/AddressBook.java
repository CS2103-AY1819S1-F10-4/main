package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.accounts.Account;
import seedu.address.model.accounts.UniqueAccountList;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.UniqueIngredientList;
import seedu.address.model.menu.Item;
import seedu.address.model.menu.UniqueItemList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.reservation.UniqueReservationList;
import seedu.address.model.salesrecord.SalesRecord;
import seedu.address.model.salesrecord.UniqueRecordList;
import seedu.address.model.tag.Tag;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueReservationList reservations;
    private final UniqueRecordList records;
    private final UniqueAccountList accounts;
    private final UniqueIngredientList ingredients;
    private final UniqueItemList items;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        reservations = new UniqueReservationList();
        records = new UniqueRecordList();
        accounts = new UniqueAccountList();
        ingredients = new UniqueIngredientList();
        items = new UniqueItemList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}. {@code persons} must not contain duplicate
     * persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setReservations(newData.getReservationList());
        setRecords(newData.getRecordList());
        setAccounts(newData.getAccountList());
        setIngredients(newData.getIngredientList());
        setItems(newData.getItemList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book. The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}. {@code target} must exist in the
     * address book. The person identity of {@code editedPerson} must not be the same as another existing person in the
     * address book.
     */
    public void updatePerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Removes {@code tag} from {@code person} in this {@code AddressBook}.
     *
     * @param person whose tag is being removed.
     * @param tag to be removed.
     */
    private void removeTagForPerson(Person person, Tag tag) {
        Set<Tag> tags = new HashSet<>(person.getTags());

        if (!tags.remove(tag)) {
            return;
        }

        Person newPerson = new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                person.getRemark(), tags);
        updatePerson(person, newPerson);
    }

    /**
     * Removes {@code tag} from all {@code person} in this {@code AddressBook}.
     *
     * @param tag to be removed.
     */
    public void removeTag(Tag tag) {
        persons.forEach(person -> removeTagForPerson(person, tag));
    }

    // Reservation Management

    /**
     * Replaces the contents of the reservation list with {@code reservations}. {@code reservations} must not contain
     * duplicate reservations.
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations.setReservations(reservations);
    }

    /**
     * Returns true if a reservation with the same identity as {@code reservation} exists in the address book.
     */
    public boolean hasReservation(Reservation reservation) {
        requireNonNull(reservation);
        return reservations.contains(reservation);
    }

    /**
     * Adds a reservation to the address book. The reservation must not already exist in the address book.
     */
    public void addReservation(Reservation r) {
        reservations.add(r);
    }

    /**
     * Replaces the given reservation {@code target} in the list with {@code editedReservation}. {@code target} must
     * exist in the address book. The person identity of {@code editedReservation} must not be the same as
     * another existing person in the address book.
     */
    public void updateReservation(Reservation target, Reservation editedReservation) {
        requireNonNull(editedReservation);

        reservations.setReservation(target, editedReservation);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in the address book.
     */
    public void removeReservation(Reservation key) {
        reservations.remove(key);
    }

    @Override
    public ObservableList<Reservation> getReservationList() {
        return reservations.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    //// sales record-level operation

    /**
     * Replaces the contents of the record list with {@code records}. {@code records} must not contain duplicate
     * records.
     */
    public void setRecords(List<SalesRecord> records) {
        this.records.setRecords(records);
    }


    /**
     * Returns true if a record with the same identity as {@code record} exists in the sales book.
     */
    public boolean hasRecord(SalesRecord record) {
        requireNonNull(record);
        return records.contains(record);
    }

    /**
     * Adds a record to the address book. The record must not already exist in the sales book.
     */
    public void addRecord(SalesRecord r) {
        records.add(r);
    }

    /**
     * Replaces the given record {@code target} in the list with {@code editedRecord}. {@code target} must exist in the
     * sales book. The record identity of {@code editedRecord} must not be the same as another existing record in the
     * sales book.
     */
    public void updateRecord(SalesRecord target, SalesRecord editedRecord) {
        requireNonNull(editedRecord);

        records.setRecord(target, editedRecord);
    }

    /**
     * Removes {@code key} from this {@code SalesBook}. {@code key} must exist in the sales book.
     */
    public void removeRecord(SalesRecord key) {
        records.remove(key);
    }

    @Override
    public ObservableList<SalesRecord> getRecordList() {
        return records.asUnmodifiableObservableList();
    }

    //// account-level operations

    /**
     * Replaces the contents of the person list with {@code persons}. {@code persons} must not contain duplicate
     * persons.
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts.setAccounts(accounts);
    }

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasAccount(Account account) {
        return accounts.contains(account);
    }

    /**
     * Adds a account to the account record. The account must not already exist in the account record.
     */
    public void addAccount(Account account) {
        account.getPassword().hash(account.getUsername().toString());
        accounts.add(account);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}. {@code target} must exist in the
     * address book. The person identity of {@code editedPerson} must not be the same as another existing person in the
     * address book.
     */
    public void updateAccount(Account target, Account editedAccount) {
        //TODO: Handle hashing of new password here as well
        accounts.update(target, editedAccount);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in the address book.
     */
    public void removeAccount(Account key) {
        accounts.remove(key);
    }

    //// ingredient-level operations

    /**
     * Replaces the contents of the ingredient list with {@code ingredients}. {@code ingredients} must not
     * contain duplicate ingredients.
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients.setIngredients(ingredients);
    }

    /**
     * Returns true if an ingredient with the same identity as {@code ingredient} exists in the address book.
     */
    public boolean hasIngredient(Ingredient ingredient) {
        return ingredients.contains(ingredient);
    }

    /**
     * Adds an ingredient to the address book. The ingredient must not already exist in the address book.
     */
    public void addIngredient(Ingredient i) {
        ingredients.add(i);
    }

    /**
     * Replaces the given ingredient {@code target} in the list with {@code editedIngredients}. {@code target} must
     * exist in the address book. The ingredient identity of {@code editedIngredient} must not be the same as
     * another existing ingredient in the address book.
     */
    public void updateIngredient(Ingredient target, Ingredient editedIngredient) {
        requireNonNull(editedIngredient);

        ingredients.setIngredient(target, editedIngredient);
    }

    /**
     * Removes ingredient with {@code key} from this {@code AddressBook}. {@code key} must exist in the address book.
     */
    public void removeIngredient(Ingredient key) {
        ingredients.remove(key);
    }

    @Override
    public ObservableList<Account> getAccountList() {
        return accounts.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Ingredient> getIngredientList() {
        return ingredients.asUnmodifiableObservableList();
    }

    // Menu Management

    /**
     * Replaces the contents of the person list with {@code persons}. {@code persons} must not contain duplicate
     * persons.
     */
    public void setItems(List<Item> items) {
        this.items.setItems(items);
    }

    /**
     * Returns true if a item with the same identity as {@code item} exists in the menu.
     */
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return items.contains(item);
    }

    /**
     * Adds an item to the menu. The item must not already exist in the menu.
     */
    public void addItem(Item i) {
        items.add(i);
    }

    /**
     * Replaces the given item {@code target} in the list with {@code editedItem}. {@code target} must exist in the
     * menu. The item identity of {@code editedItem} must not be the same as another existing item in the menu.
     */
    public void updateItem(Item target, Item editedItem) {
        requireNonNull(editedItem);

        items.setItem(target, editedItem);
    }

    /**
     * Removes {@code key} from this {@code Menu}. {@code key} must exist in the menu.
     */
    public void removeItem(Item key) {
        items.remove(key);
    }

    /**
     * Removes {@code tag} from {@code item} in this {@code Menu}.
     *
     * @param item whose tag is being removed.
     * @param tag to be removed.
     */
    private void removeTagForItem(Item item, Tag tag) {
        Set<Tag> tags = new HashSet<>(item.getTags());

        if (!tags.remove(tag)) {
            return;
        }

        Item newItem = new Item(item.getName(), item.getPrice(), item.getRemark(), tags);
        updateItem(item, newItem);
    }

    /**
     * Removes {@code tag} from all {@code item} in this {@code AddressBook}.
     *
     * @param tag to be removed.
     */
    public void removeTagForMenu(Tag tag) {
        items.forEach(item -> removeTagForItem(item, tag));
    }

    /**
     * Resets the menu data of this {@code AddressBook} with {@code newData}.
     */
    public void resetMenuData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setItems(newData.getItemList());
    }

    @Override
    public ObservableList<Item> getItemList() {
        return items.asUnmodifiableObservableList();
    }

    //// util methods

    @Override
    public String toString() {
        return String.valueOf(persons.asUnmodifiableObservableList().size()) + " persons\n"
                + accounts.asUnmodifiableObservableList().size() + " accounts\n"
                + ingredients.asUnmodifiableObservableList().size() + " ingredients\n"
                + items.asUnmodifiableObservableList().size() + " items\n"
                + records.asUnmodifiableObservableList().size() + " records";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        return persons.equals(((AddressBook) other).persons)
                && accounts.equals(((AddressBook) other).accounts)
                && ingredients.equals(((AddressBook) other).ingredients)
                && items.equals(((AddressBook) other).items)
                && records.equals(((AddressBook) other).records);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, accounts, ingredients, items, records);
    }
}
