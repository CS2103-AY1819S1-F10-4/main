package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.accounts.Account;
import seedu.address.model.person.Person;
import seedu.address.model.reservation.Reservation;
import seedu.address.storage.elements.XmlAdaptedAccount;
import seedu.address.storage.reservation.XmlAdaptedReservation;
import seedu.address.model.salesrecord.SalesRecord;
import seedu.address.storage.elements.XmlAdaptedRecord;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_RECORD = "Records list contains duplicate record(s).";
    public static final String MESSAGE_DUPLICATE_ACCOUNT = "Account list contains duplicate account(s).";
    public static final String MESSAGE_DUPLICATE_RESERVATION = "Reservation list contains duplicate reservation(s).";

    @XmlElement
    private List<XmlAdaptedPerson> persons;

    @XmlElement
    private List<XmlAdaptedRecord> records;

    @XmlElement
    private List<XmlAdaptedAccount> accounts;

    @XmlElement
    private List<XmlAdaptedReservation> reservations;

    private AddressBook addressBook;

    /**
     * Creates an empty XmlSerializableAddressBook. This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        addressBook = new AddressBook();
        persons = new ArrayList<>();
        records = new ArrayList<>();
        accounts = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        records.addAll(src.getRecordList().stream().map(XmlAdaptedRecord::new).collect(Collectors.toList()));
        accounts.addAll(src.getAccountList().stream().map(XmlAdaptedAccount::new).collect(Collectors.toList()));
        reservations.addAll(src.getReservationList().stream()
                .map(XmlAdaptedReservation::new).collect(Collectors.toList()));
    }

    /**
     * Returns the converted model {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates record when not
     *         allowed.
     */
    public AddressBook toModelType() throws IllegalValueException {
        processPersons();
        processAccounts();
        processReservations();
        processRecords();
        return addressBook;
    }

    /**
     * Converts this addressbook's person list into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the {@code
     *         XmlAdaptedPerson}.
     */
    private void processPersons() throws IllegalValueException {
        for (XmlAdaptedPerson p : persons) {
            Person person = p.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
    }

    /**
     * Converts this addressbook's record list into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the {@code
     *         XmlAdaptedRecord}.
     */
    private void processAccounts() throws IllegalValueException {
        for (XmlAdaptedRecord r : records) {
            SalesRecord record = r.toModelType();
            if (addressBook.hasRecord(record)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RECORD);
            }
            addressBook.addRecord(record);
        }
    }

    /**
     * Converts this account record into the model's {@code Account} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the {@code
     *         XmlAdaptedAccount}.
     */
    private void processRecords() throws IllegalValueException {
        for (XmlAdaptedAccount acc : accounts) {
            Account account = acc.toModelType();
            if (addressBook.hasAccount(account)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ACCOUNT);
            }
            addressBook.addAccount(account);
        }
    }

    /**
     * Converts this reservation record into the model's {@code Reservation} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the {@code
     *         XmlAdaptedReservation}.
     */
    public void processReservations() throws IllegalValueException {
        for (XmlAdaptedReservation res : reservations) {
            Reservation reservation = res.toModelType();
            if (addressBook.hasReservation(reservation)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RESERVATION);
            }
            addressBook.addReservation(reservation);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAddressBook)) {
            return false;
        }
        return persons.equals(((XmlSerializableAddressBook) other).persons)
                && accounts.equals(((XmlSerializableAddressBook) other).accounts)
                && reservations.equals(((XmlSerializableAddressBook) other).reservations)
                && records.equals(((XmlSerializableAddressBook) other).records)
                && accounts.equals(((XmlSerializableAddressBook) other).accounts);
    }
}
