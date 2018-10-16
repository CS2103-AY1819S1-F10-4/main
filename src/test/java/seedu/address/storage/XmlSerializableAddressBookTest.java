package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalAddressBook;
import seedu.address.testutil.ingredients.TypicalIngredients;
import seedu.address.testutil.menu.TypicalItems;


public class XmlSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "XmlSerializableAddressBookTest");

    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalAddressBook.xml");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonOnlyAddressBook.xml");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonOnlyAddressBook.xml");
    private static final Path INVALID_RECORD_FILE = TEST_DATA_FOLDER.resolve("invalidRecordAddressBook.xml");
    private static final Path DUPLICATE_RECORD_FILE = TEST_DATA_FOLDER.resolve("duplicateRecordAddressBook.xml");
    private static final Path INVALID_ACCOUNT_FILE = TEST_DATA_FOLDER.resolve("invalidAccountOnlyAddressBook.xml");
    private static final Path DUPLICATE_ACCOUNT_FILE = TEST_DATA_FOLDER.resolve("duplicateAccountOnlyAddressBook.xml");
    private static final Path TYPICAL_INGREDIENTS_FILE = TEST_DATA_FOLDER.resolve("typicalIngredientsOnlyAddressBook"
            + ".xml");
    private static final Path INVALID_INGREDIENT_FILE = TEST_DATA_FOLDER.resolve("invalidIngredientOnlyAddressBook"
            + ".xml");
    private static final Path DUPLICATE_INGREDIENT_FILE = TEST_DATA_FOLDER.resolve("duplicateIngredientOnlyAddressBook"
            + ".xml");
    private static final Path TYPICAL_DUPLICATE_FILE = TEST_DATA_FOLDER.resolve("typicalDuplicateAddressBook.xml");
    // Menu Management
    private static final Path TYPICAL_ITEMS_FILE = TEST_DATA_FOLDER.resolve("typicalItemsOnlyAddressBook.xml");
    private static final Path INVALID_ITEM_FILE = TEST_DATA_FOLDER.resolve("invalidItemOnlyAddressBook.xml");
    private static final Path DUPLICATE_ITEM_FILE = TEST_DATA_FOLDER.resolve("duplicateItemOnlyAddressBook.xml");
    // Reservation Management
    private static final Path INVALID_RESERVATION_FILE = TEST_DATA_FOLDER.resolve(
            "invalidReservationOnlyAddressBook.xml");
    private static final Path DUPLICATE_RESERVATION_FILE = TEST_DATA_FOLDER.resolve(
            "duplicateReservationOnlyAddressBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private XmlSerializableAddressBook dataFromFile = null;

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE, XmlSerializableAddressBook.class);
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = TypicalAddressBook.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(INVALID_PERSON_FILE, XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_PERSON_FILE, XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableAddressBook.MESSAGE_DUPLICATE_PERSON);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidRecordFile_throwsIllegalValueException() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(INVALID_RECORD_FILE, XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateRecords_throwsIllegalValueException() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_RECORD_FILE, XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableAddressBook.MESSAGE_DUPLICATE_RECORD);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidIngredientFile_throwsIllegalValueException() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(INVALID_INGREDIENT_FILE, XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateIngredients_throwsIllegalValueException() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_INGREDIENT_FILE, XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableAddressBook.MESSAGE_DUPLICATE_INGREDIENT);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_typicalIngredientsOnlyFile_success() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_INGREDIENTS_FILE,
                XmlSerializableAddressBook.class);
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalIngredientsAddressBook = TypicalIngredients.getTypicalAddressBookWithIngredientsOnly();
        assertEquals(addressBookFromFile, typicalIngredientsAddressBook);
    }

    @Test
    public void toModelType_invalidAccountFile_throwsIllegalValueException() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(INVALID_ACCOUNT_FILE, XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateAccounts_throwsIllegalValueException() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_ACCOUNT_FILE, XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableAddressBook.MESSAGE_DUPLICATE_ACCOUNT);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateAccountInTypicalAddressBook_throwsIllegalValueException() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(TYPICAL_DUPLICATE_FILE, XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableAddressBook.MESSAGE_DUPLICATE_ACCOUNT);
        dataFromFile.toModelType();
    }

    // Menu Management
    @Test
    public void toModelType_typicalItemsOnlyFile_success() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_ITEMS_FILE,
                XmlSerializableAddressBook.class);
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalItemsAddressBook = TypicalItems.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalItemsAddressBook);
    }

    @Test
    public void toModelType_invalidItemOnlyFile_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(INVALID_ITEM_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateItemsOnly_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_ITEM_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableAddressBook.MESSAGE_DUPLICATE_ITEM);
        dataFromFile.toModelType();
    }

    //Reservation Management
    @Test
    public void toModelType_invalidReservationOnlyFile_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(INVALID_RESERVATION_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateReservationOnly_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_RESERVATION_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableAddressBook.MESSAGE_DUPLICATE_RESERVATION);
        dataFromFile.toModelType();
    }

    @Test
    public void equals() throws Exception {
        dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_PERSON_FILE, XmlSerializableAddressBook.class);

        // same object
        assertTrue(dataFromFile.equals(dataFromFile));

        // different type
        assertFalse(dataFromFile.equals(null));
        assertFalse(dataFromFile.equals(0));
    }
}
