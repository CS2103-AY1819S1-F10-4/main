package seedu.address.model.salesrecord;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.salesrecord.exceptions.DuplicateRecordException;
import seedu.address.model.salesrecord.exceptions.SalesRecordNotFoundException;

/**
 * A list of records that enforces uniqueness between its elements and does not allow nulls.
 * A record is considered unique by comparing using {@code SalesRecord#isSameRecord(SalesRecord)}. As such, adding and
 * updating of
 * records uses SalesRecord#isSameRecord(SalesRecord) for equality so as to ensure that the record being added or
 * updated is
 * unique in terms of identity in the UniqueRecordList. However, the removal of a record uses SalesRecord#equals
 * (Object) so
 * as to ensure that the record with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see SalesRecord#isSameRecord(SalesRecord)
 */
public class UniqueRecordList implements Iterable<SalesRecord> {

    private final ObservableList<SalesRecord> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent record as the given argument.
     */
    public boolean contains(SalesRecord toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameRecord);
    }

    /**
     * Adds a record to the list.
     * The record must not already exist in the list.
     */
    public void add(SalesRecord toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRecordException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the record {@code target} in the list with {@code editedRecord}.
     * {@code target} must exist in the list.
     * The record identity of {@code editedRecord} must not be the same as another existing record in the list.
     */
    public void setRecord(SalesRecord target, SalesRecord editedRecord) {
        requireAllNonNull(target, editedRecord);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new SalesRecordNotFoundException();
        }

        if (!target.isSameRecord(editedRecord) && contains(editedRecord)) {
            throw new DuplicateRecordException();
        }

        internalList.set(index, editedRecord);
    }

    /**
     * Removes the equivalent record from the list.
     * The record must exist in the list.
     */
    public void remove(SalesRecord toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new SalesRecordNotFoundException();
        }
    }

    public void setRecords(UniqueRecordList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code records}.
     * {@code records} must not contain duplicate records.
     */
    public void setRecords(List<SalesRecord> records) {
        requireAllNonNull(records);
        if (!recordsAreUnique(records)) {
            throw new DuplicateRecordException();
        }

        internalList.setAll(records);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<SalesRecord> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<SalesRecord> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueRecordList // instanceof handles nulls
                && internalList.equals(((UniqueRecordList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code records} contains only unique records.
     */
    private boolean recordsAreUnique(List<SalesRecord> records) {
        for (int i = 0; i < records.size() - 1; i++) {
            for (int j = i + 1; j < records.size(); j++) {
                if (records.get(i).isSameRecord(records.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
