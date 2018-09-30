package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.AccountRecordChangedEvent;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.accounts.ReadOnlyAccountRecord;
import seedu.address.storage.accounts.AccountRecordStorage;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, AccountRecordStorage, UserPrefsStorage {

    // ================ UserPrefs methods ==============================

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    // ================ AddressBook methods ==============================

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk. Creates the data file if it is missing. Raises
     * {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);

    // ================ AccountRecord methods ==============================

    @Override
    Path getAccountRecordFilePath();

    @Override
    Optional<ReadOnlyAccountRecord> readAccountRecord() throws DataConversionException, IOException;

    @Override
    void saveAccountRecord(ReadOnlyAccountRecord accountRecord) throws IOException;

    /**
     * Saves the current version of the Account Record to the hard disk. Creates the data file if it is missing. Raises
     * {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAccountRecordChangedEvent(AccountRecordChangedEvent arce);
}
