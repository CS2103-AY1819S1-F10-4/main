package seedu.address.model.salesrecord;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.salesrecords.TypicalRecords.RECORD_DEFAULT;
import static seedu.address.testutil.salesrecords.TypicalRecords.RECORD_ONE;
import static seedu.address.testutil.salesrecords.TypicalRecords.RECORD_THREE;
import static seedu.address.testutil.salesrecords.TypicalRecords.RECORD_TWO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.salesrecord.exceptions.DuplicateRecordException;
import seedu.address.model.salesrecord.exceptions.SalesRecordNotFoundException;
import seedu.address.testutil.salesrecords.RecordBuilder;
import seedu.address.testutil.salesrecords.ReportBuilder;

public class UniqueRecordListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final UniqueRecordList uniqueRecordList = new UniqueRecordList();

    @Test
    public void contains_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.contains(null);
    }
    @Test
    public void contains_recordNotInList_returnsFalse() {
        assertFalse(uniqueRecordList.contains(RECORD_DEFAULT));
    }
    @Test
    public void contains_recordInList_returnsTrue() {
        uniqueRecordList.add(RECORD_ONE);
        assertTrue(uniqueRecordList.contains(RECORD_ONE));
    }
    @Test
    public void add_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.add(null);
    }
    @Test
    public void add_duplicateRecord_throwsDuplicateRecordException() {
        uniqueRecordList.add(RECORD_ONE);
        thrown.expect(DuplicateRecordException.class);
        uniqueRecordList.add(RECORD_ONE);
    }
    @Test
    public void setRecord_nullTargetAudience_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecord(null, RECORD_DEFAULT);
    }
    @Test
    public void setRecord_nullEditedRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecord(RECORD_ONE, null);
    }
    @Test
    public void setRecord_targetRecordNotInList_throwsRecordNotFoundException() {
        thrown.expect(SalesRecordNotFoundException.class);
        uniqueRecordList.setRecord(RECORD_THREE, RECORD_DEFAULT);
    }
    @Test
    public void setRecord_editedSameRecord_success() {
        uniqueRecordList.add(RECORD_DEFAULT);
        uniqueRecordList.setRecord(RECORD_DEFAULT, RECORD_DEFAULT);
        UniqueRecordList expectedRecordList = new UniqueRecordList();
        expectedRecordList.add(RECORD_DEFAULT);
        assertEquals(expectedRecordList, uniqueRecordList);
    }
    @Test
    public void setRecord_editedRecordAlreadyExists_throwsDuplicateRecordException() {
        uniqueRecordList.add(RECORD_DEFAULT);
        uniqueRecordList.add(RECORD_ONE);
        thrown.expect(DuplicateRecordException.class);
        uniqueRecordList.setRecord(RECORD_DEFAULT, RECORD_ONE);
    }
    @Test
    public void remove_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.remove(null);
    }
    @Test
    public void remove_recordDoesNotExist_throwsRecordNotFoundException() {
        thrown.expect(SalesRecordNotFoundException.class);
        uniqueRecordList.remove(RECORD_DEFAULT);
    }
    @Test
    public void remove_existingRecord_removesRecord() {
        uniqueRecordList.add(RECORD_DEFAULT);
        uniqueRecordList.remove(RECORD_DEFAULT);
        UniqueRecordList expectedRecordList = new UniqueRecordList();
        assertEquals(expectedRecordList, uniqueRecordList);
    }
    @Test
    public void setRecords_nullUniqueRecordList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecords((UniqueRecordList) null);
    }
    @Test
    public void setRecords_uniqueRecordList_replacesOwnListWithProvidedUniqueRecordList() {
        uniqueRecordList.add(RECORD_DEFAULT);
        UniqueRecordList expectedRecordList = new UniqueRecordList();
        expectedRecordList.add(RECORD_ONE);
        uniqueRecordList.setRecords(expectedRecordList);
        assertEquals(expectedRecordList, uniqueRecordList);
    }
    @Test
    public void setRecords_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecords((List<SalesRecord>) null);
    }
    @Test
    public void setRecords_list_replacesOwnListWithProvidedList() {
        uniqueRecordList.add(RECORD_DEFAULT);
        List<SalesRecord> records = Collections.singletonList(RECORD_ONE);
        uniqueRecordList.setRecords(records);
        UniqueRecordList expectedRecordList = new UniqueRecordList();
        expectedRecordList.add(RECORD_ONE);
        assertEquals(expectedRecordList, uniqueRecordList);
    }
    @Test
    public void setRecords_listWithDuplicateRecords_throwsDuplicateRecordException() {
        List<SalesRecord> listWithDuplicateRecords = Arrays.asList(RECORD_DEFAULT, RECORD_DEFAULT);
        thrown.expect(DuplicateRecordException.class);
        uniqueRecordList.setRecords(listWithDuplicateRecords);
    }
    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueRecordList.asUnmodifiableObservableList().remove(0);
    }

    @Test
    public void generateSalesReport_returnsSalesReport() {
        uniqueRecordList.add(RECORD_DEFAULT);
        SalesRecord recordWithDefaultDate =
                new RecordBuilder(RECORD_ONE).withDate(RECORD_DEFAULT.getDate().toString()).build();
        uniqueRecordList.add(recordWithDefaultDate);
        uniqueRecordList.add(RECORD_TWO);
        SalesReport report = uniqueRecordList.generateSalesReport(RECORD_DEFAULT.getDate());

        ObservableList<SalesRecord> recordList = FXCollections.observableArrayList();
        recordList.add(RECORD_DEFAULT);
        recordList.add(recordWithDefaultDate);
        assertEquals(new ReportBuilder(RECORD_DEFAULT.getDate(), recordList).build(), report);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        uniqueRecordList.add(RECORD_DEFAULT);
        assertTrue(uniqueRecordList.equals(uniqueRecordList));
    }
    @Test
    public void equals_sameList_returnsTrue() {
        uniqueRecordList.add(RECORD_DEFAULT);
        uniqueRecordList.add(RECORD_THREE);
        UniqueRecordList uniqueRecordListCopy = new UniqueRecordList();
        uniqueRecordListCopy.add(RECORD_DEFAULT);
        uniqueRecordListCopy.add(RECORD_THREE);
        assertTrue(uniqueRecordList.equals(uniqueRecordListCopy));
    }
    @Test
    public void equals_differentType_returnsFalse() {
        uniqueRecordList.add(RECORD_DEFAULT);
        assertFalse(uniqueRecordList.equals(2));
    }
    @Test
    public void equals_differentList_returnsFalse() {
        uniqueRecordList.add(RECORD_DEFAULT);
        UniqueRecordList uniqueRecordListCopy = new UniqueRecordList();
        uniqueRecordListCopy.add(RECORD_THREE);
        assertFalse(uniqueRecordList.equals(uniqueRecordListCopy));
    }
}
