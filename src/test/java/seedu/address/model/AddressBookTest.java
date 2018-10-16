package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_TAG_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_TAG_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESERVATION_PAX_BILLY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TEST;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.DYLAN;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_ADMIN;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_ONE;
import static seedu.address.testutil.menu.TypicalItems.APPLE_JUICE;
import static seedu.address.testutil.menu.TypicalItems.BEEF_BURGER;
import static seedu.address.testutil.menu.TypicalItems.BURGER;
import static seedu.address.testutil.menu.TypicalItems.CHEESE_BURGER;
import static seedu.address.testutil.menu.TypicalItems.FRIES;
import static seedu.address.testutil.reservation.TypicalReservations.ANDREW;
import static seedu.address.testutil.salesrecords.TypicalRecords.RECORD_DEFAULT;
import static seedu.address.testutil.salesrecords.TypicalRecords.RECORD_ONE;
import static seedu.address.testutil.salesrecords.TypicalRecords.RECORD_THREE;
import static seedu.address.testutil.salesrecords.TypicalRecords.RECORD_TWO;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.accounts.Account;
import seedu.address.model.accounts.exceptions.DuplicateAccountException;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.menu.Item;
import seedu.address.model.menu.exceptions.DuplicateItemException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.reservation.exceptions.DuplicateReservationException;
import seedu.address.model.salesrecord.SalesRecord;
import seedu.address.model.salesrecord.exceptions.DuplicateRecordException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.accounts.AccountBuilder;
import seedu.address.testutil.menu.ItemBuilder;
import seedu.address.testutil.reservation.ReservationBuilder;
import seedu.address.testutil.salesrecords.RecordBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();
    private AddressBook addressBookWithPersons = null;

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersonsWithRecordsAndAccounts_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE)
                .withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        List<Account> newAccounts = Arrays.asList(DEMO_ADMIN, DEMO_ONE);
        List<Item> newItems = Arrays.asList(APPLE_JUICE);
        List<Reservation> newReservations = Arrays.asList(ANDREW);
        List<SalesRecord> newRecords = Arrays.asList(RECORD_DEFAULT, RECORD_ONE);
        AddressBookStub newData = new AddressBookStub(newPersons, newAccounts, newItems, newReservations, newRecords);

        thrown.expect(DuplicatePersonException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE)
                .withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    @Test
    public void removeTag_noSuchTag_addressBookUnmodified() {
        addressBookWithPersons = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();

        addressBookWithPersons.removeTag(new Tag(VALID_TAG_TEST));

        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();

        assertEquals(addressBookWithPersons, expectedAddressBook);
    }

    @Test
    public void removeTag_fromAllPersons_addressBookModified() {
        addressBookWithPersons = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();

        addressBookWithPersons.removeTag(new Tag(VALID_TAG_FRIEND));

        Person amyWithoutTags = new PersonBuilder(AMY).withTags().build();
        Person bobWithoutFriendTag = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();

        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(amyWithoutTags)
                .withPerson(bobWithoutFriendTag)
                .build();

        assertEquals(addressBookWithPersons, expectedAddressBook);
    }

    @Test
    public void removeTag_fromOnePerson_addressBookModified() {
        addressBookWithPersons = new AddressBookBuilder()
                .withPerson(AMY)
                .withPerson(DYLAN)
                .build();

        addressBookWithPersons.removeTag(new Tag(VALID_TAG_FRIEND));

        Person amyWithoutTags = new PersonBuilder(AMY).withTags().build();

        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(amyWithoutTags)
                .withPerson(DYLAN)
                .build();

        assertEquals(addressBookWithPersons, expectedAddressBook);
    }

    @Test
    public void resetData_withDuplicateRecordsWithPersonsAndAccounts_throwsDuplicateRecordException() {
        // Two records with the same date and name
        SalesRecord record = new RecordBuilder(RECORD_ONE)
                .withDate(RECORD_DEFAULT.getDate().toString())
                .withName(RECORD_DEFAULT.getName().toString())
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, BOB);
        List<Account> newAccounts = Arrays.asList(DEMO_ADMIN, DEMO_ONE);
        List<Item> newItems = Arrays.asList(APPLE_JUICE);
        List<Reservation> newReservations = Arrays.asList(ANDREW);
        List<SalesRecord> newRecords = Arrays.asList(RECORD_DEFAULT, record);
        AddressBookStub newData = new AddressBookStub(newPersons, newAccounts, newItems, newReservations, newRecords);

        thrown.expect(DuplicateRecordException.class);
        addressBook.resetData(newData);
    }
    @Test
    public void hasRecord_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasRecord(null);
    }
    @Test
    public void hasRecord_recordNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasRecord(RECORD_DEFAULT));
    }
    @Test
    public void hasRecord_recordInAddressBook_returnsTrue() {
        addressBook.addRecord(RECORD_DEFAULT);
        assertTrue(addressBook.hasRecord(RECORD_DEFAULT));
    }
    @Test
    public void hasRecord_recordWithSameDateDifferentNameInAddressBook_returnsTrue() {
        addressBook.addRecord(RECORD_DEFAULT);
        SalesRecord record = new RecordBuilder(RECORD_TWO)
                .withDate(RECORD_DEFAULT.getDate().toString())
                .build();
        addressBook.addRecord(record);
        assertTrue(addressBook.hasRecord(record));
    }
    @Test
    public void hasRecord_recordWithDifferentDateSameNameInAddressBook_returnsTrue() {
        addressBook.addRecord(RECORD_DEFAULT);
        SalesRecord record = new RecordBuilder(RECORD_ONE)
                .withName(RECORD_DEFAULT.getName().toString())
                .build();
        addressBook.addRecord(record);
        assertTrue(addressBook.hasRecord(record));
    }
    @Test
    public void hasRecord_recordWithSameQuantitySoldSamePriceInAddressBook_returnsTrue() {
        addressBook.addRecord(RECORD_DEFAULT);
        SalesRecord record = new RecordBuilder(RECORD_THREE)
                .withQuantitySold(RECORD_DEFAULT.getQuantitySold().toString())
                .withPrice(RECORD_DEFAULT.getPrice().toString())
                .build();
        addressBook.addRecord(record);
        assertTrue(addressBook.hasRecord(record));
    }
    @Test
    public void getRecordList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getRecordList().remove(0);
    }
    @Test
    public void getSalesReport_nullDate_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.getSalesReport(null);
    }

    @Test
    public void resetData_withDuplicateAccountsWithPersonsAndRecords_throwsDuplicateAccountException() {
        // Two accounts with the same username
        Account account = new AccountBuilder(DEMO_ONE)
                .withUsername(DEMO_ADMIN.getUsername().toString())
                .build();
        List<Account> newAccounts = Arrays.asList(DEMO_ADMIN, account);
        List<Person> newPersons = Arrays.asList(ALICE, BOB);
        List<Item> newItems = Arrays.asList(APPLE_JUICE);
        List<Reservation> newReservations = Arrays.asList(ANDREW);
        List<SalesRecord> newRecords = Arrays.asList(RECORD_DEFAULT, RECORD_ONE);
        AddressBookStub newData = new AddressBookStub(newPersons, newAccounts, newItems, newReservations, newRecords);

        thrown.expect(DuplicateAccountException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasAccount_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasAccount(null);
    }

    @Test
    public void hasAccount_accountNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasAccount(DEMO_ADMIN));
    }

    @Test
    public void hasAccount_accountInAddressBook_returnsTrue() {
        addressBook.addAccount(DEMO_ADMIN);
        assertTrue(addressBook.hasAccount(DEMO_ADMIN));
    }

    @Test
    public void hasAccount_accountWithSamePasswordInAddressBook_returnsTrue() {
        // same raw password, but with different username.
        addressBook.addAccount(DEMO_ADMIN);
        Account account = new AccountBuilder(DEMO_ONE)
                .withPassword(DEMO_ADMIN.getPassword().toString())
                .build();
        addressBook.addAccount(account);
        assertTrue(addressBook.hasAccount(account));
    }

    @Test
    public void getAccountList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getAccountList().remove(0);
    }

    // Menu Management
    @Test
    public void resetData_withDuplicateItems_throwsDuplicateItemException() {
        // Two items with the same identity fields
        Item editedApple = new ItemBuilder(APPLE_JUICE).withTags(VALID_ITEM_TAG_CHEESE).build();
        List<Person> newPersons = Arrays.asList(ALICE);
        List<Account> newAccounts = Arrays.asList(DEMO_ADMIN);
        List<Item> newItems = Arrays.asList(APPLE_JUICE, editedApple);
        List<Reservation> newReservations = Arrays.asList(ANDREW);
        List<SalesRecord> newRecords = Arrays.asList(RECORD_DEFAULT, RECORD_ONE);
        AddressBookStub newData = new AddressBookStub(newPersons, newAccounts, newItems, newReservations, newRecords);

        thrown.expect(DuplicateItemException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasItem(null);
    }

    @Test
    public void hasItem_itemNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasItem(APPLE_JUICE));
    }

    @Test
    public void hasItem_itemInAddressBook_returnsTrue() {
        addressBook.addItem(APPLE_JUICE);
        assertTrue(addressBook.hasItem(APPLE_JUICE));
    }

    @Test
    public void hasItem_itemWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addItem(APPLE_JUICE);
        Item editedApple = new ItemBuilder(APPLE_JUICE).withTags(VALID_ITEM_TAG_CHEESE).build();
        assertTrue(addressBook.hasItem(editedApple));
    }

    @Test
    public void getItemList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getItemList().remove(0);
    }

    @Test
    public void removeTagForMenu_noSuchTag_addressBookUnmodified() {
        addressBookWithPersons = new AddressBookBuilder().withItem(BEEF_BURGER).withItem(BURGER).build();

        addressBookWithPersons.removeTagForMenu(new Tag(VALID_ITEM_TAG_CHEESE));

        AddressBook expectedAddressBook = new AddressBookBuilder().withItem(BEEF_BURGER).withItem(BURGER).build();

        assertEquals(addressBookWithPersons, expectedAddressBook);
    }

    @Test
    public void removeTagForMenu_fromAllItems_addressBookModified() {
        addressBookWithPersons = new AddressBookBuilder().withItem(CHEESE_BURGER).withItem(FRIES).build();

        addressBookWithPersons.removeTagForMenu(new Tag(VALID_ITEM_TAG_CHEESE));

        Item cheeseWithoutCheeseTags = new ItemBuilder(CHEESE_BURGER).withTags(VALID_ITEM_TAG_BURGER).build();
        Item friesWithoutTags = new ItemBuilder(FRIES).withTags().build();

        AddressBook expectedAddressBook = new AddressBookBuilder().withItem(cheeseWithoutCheeseTags)
                .withItem(friesWithoutTags)
                .build();

        assertEquals(addressBookWithPersons, expectedAddressBook);
    }

    @Test
    public void removeTagForMenu_fromOneItem_addressBookModified() {
        addressBookWithPersons = new AddressBookBuilder().withItem(FRIES).withItem(BURGER).build();

        addressBookWithPersons.removeTagForMenu(new Tag(VALID_ITEM_TAG_CHEESE));

        Item friesWithoutTags = new ItemBuilder(FRIES).withTags().build();

        AddressBook expectedAddressBook = new AddressBookBuilder().withItem(friesWithoutTags)
                .withItem(BURGER)
                .build();

        assertEquals(addressBookWithPersons, expectedAddressBook);
    }

    // Reservation Management
    @Test
    public void resetData_withDuplicatePersonsWithRecordsAndAccounts_throwsDuplicateReservationException() {
        // Two persons with the same identity fields
        Reservation editedAndrew = new ReservationBuilder(ANDREW)
                .withPax(VALID_RESERVATION_PAX_BILLY)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE);
        List<Account> newAccounts = Arrays.asList(DEMO_ADMIN, DEMO_ONE);
        List<Item> newItems = Arrays.asList(APPLE_JUICE);
        List<Reservation> newReservations = Arrays.asList(ANDREW, editedAndrew);
        List<SalesRecord> newRecords = Arrays.asList(RECORD_DEFAULT, RECORD_ONE);
        AddressBookStub newData = new AddressBookStub(newPersons, newAccounts, newItems, newReservations, newRecords);

        thrown.expect(DuplicateReservationException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasReservation_nullReservation_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasReservation(null);
    }

    @Test
    public void hasReservation_reservationNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasReservation(ANDREW));
    }

    @Test
    public void hasReservation_reservationInAddressBook_returnsTrue() {
        addressBook.addReservation(ANDREW);
        assertTrue(addressBook.hasReservation(ANDREW));
    }

    @Test
    public void hasReservation_reservationWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addReservation(ANDREW);
        Reservation editedAndrew = new ReservationBuilder(ANDREW)
                .withPax(VALID_RESERVATION_PAX_BILLY)
                .build();
        assertTrue(addressBook.hasReservation(editedAndrew));
    }

    @Test
    public void getReservationList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getReservationList().remove(0);
    }

    @Test
    public void equals() {
        addressBookWithPersons = new AddressBookBuilder().withPerson(AMY).withPerson(DYLAN).build();
        // same object
        assertTrue(addressBookWithPersons.equals(addressBookWithPersons));
        addressBookWithPersons.removeTag(new Tag(VALID_TAG_FRIEND));
        Person amyWithoutTags = new PersonBuilder(AMY).withTags().build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(amyWithoutTags)
                .withPerson(DYLAN)
                .build();
        assertTrue(addressBookWithPersons.equals(expectedAddressBook));
        // different type
        assertFalse(addressBookWithPersons.equals(null));
        assertFalse(addressBookWithPersons.equals(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {

        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Account> accounts = FXCollections.observableArrayList();
        private final ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
        private final ObservableList<Item> items = FXCollections.observableArrayList();
        private final ObservableList<Reservation> reservations = FXCollections.observableArrayList();
        private final ObservableList<SalesRecord> records = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<Account> accounts, Collection<Item> items,
                Collection<Reservation> reservations, Collection<SalesRecord> records) {
            this.persons.setAll(persons);
            this.accounts.setAll(accounts);
            this.ingredients.setAll(ingredients);
            this.items.setAll(items);
            this.reservations.setAll(reservations);
            this.records.setAll(records);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Account> getAccountList() {
            return accounts;
        }

        @Override
        public ObservableList<Reservation> getReservationList() {
            return reservations;
        }

        @Override
        public ObservableList<SalesRecord> getRecordList() {
            return records;
        }

        @Override
        public ObservableList<Ingredient> getIngredientList() {
            return ingredients;
        }

        @Override
        public ObservableList<Item> getItemList() {
            return items;
        }
    }

}
