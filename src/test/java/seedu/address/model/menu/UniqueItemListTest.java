package seedu.address.model.menu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_TAG_CHEESE;
import static seedu.address.testutil.menu.TypicalItems.APPLE_JUICE;
import static seedu.address.testutil.menu.TypicalItems.BEEF_BURGER;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.menu.exceptions.DuplicateItemException;
import seedu.address.model.menu.exceptions.ItemNotFoundException;
import seedu.address.testutil.menu.ItemBuilder;

public class UniqueItemListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueItemList uniqueItemList = new UniqueItemList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.contains(null);
    }

    @Test
    public void contains_itemNotInList_returnsFalse() {
        assertFalse(uniqueItemList.contains(APPLE_JUICE));
    }

    @Test
    public void contains_itemInList_returnsTrue() {
        uniqueItemList.add(APPLE_JUICE);
        assertTrue(uniqueItemList.contains(APPLE_JUICE));
    }

    @Test
    public void contains_itemWithSameIdentityFieldsInList_returnsTrue() {
        uniqueItemList.add(APPLE_JUICE);
        Item editedApple = new ItemBuilder(APPLE_JUICE).withTags(VALID_ITEM_TAG_CHEESE).build();
        assertTrue(uniqueItemList.contains(editedApple));
    }

    @Test
    public void add_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.add(null);
    }

    @Test
    public void add_duplicateItem_throwsDuplicateItemException() {
        uniqueItemList.add(APPLE_JUICE);
        thrown.expect(DuplicateItemException.class);
        uniqueItemList.add(APPLE_JUICE);
    }

    @Test
    public void setItem_nullTargetItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.setItem(null, APPLE_JUICE);
    }

    @Test
    public void setItem_nullEditedItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.setItem(APPLE_JUICE, null);
    }

    @Test
    public void setItem_targetItemNotInList_throwsItemNotFoundException() {
        thrown.expect(ItemNotFoundException.class);
        uniqueItemList.setItem(APPLE_JUICE, APPLE_JUICE);
    }

    @Test
    public void setItem_editedItemIsSameItem_success() {
        uniqueItemList.add(APPLE_JUICE);
        uniqueItemList.setItem(APPLE_JUICE, APPLE_JUICE);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(APPLE_JUICE);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasSameIdentity_success() {
        uniqueItemList.add(APPLE_JUICE);
        Item editedApple = new ItemBuilder(APPLE_JUICE).withTags(VALID_ITEM_TAG_CHEESE).build();
        uniqueItemList.setItem(APPLE_JUICE, editedApple);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(editedApple);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasDifferentIdentity_success() {
        uniqueItemList.add(APPLE_JUICE);
        uniqueItemList.setItem(APPLE_JUICE, BEEF_BURGER);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(BEEF_BURGER);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasNonUniqueIdentity_throwsDuplicateItemException() {
        uniqueItemList.add(APPLE_JUICE);
        uniqueItemList.add(BEEF_BURGER);
        thrown.expect(DuplicateItemException.class);
        uniqueItemList.setItem(APPLE_JUICE, BEEF_BURGER);
    }

    @Test
    public void remove_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.remove(null);
    }

    @Test
    public void remove_itemDoesNotExist_throwsItemNotFoundException() {
        thrown.expect(ItemNotFoundException.class);
        uniqueItemList.remove(APPLE_JUICE);
    }

    @Test
    public void remove_existingItem_removesItem() {
        uniqueItemList.add(APPLE_JUICE);
        uniqueItemList.remove(APPLE_JUICE);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItems_nullUniqueItemList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.setItems((UniqueItemList) null);
    }

    @Test
    public void setItems_uniqueItemList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueItemList.add(APPLE_JUICE);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(BEEF_BURGER);
        uniqueItemList.setItems(expectedUniqueItemList);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItems_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.setItems((List<Item>) null);
    }

    @Test
    public void setItems_list_replacesOwnListWithProvidedList() {
        uniqueItemList.add(APPLE_JUICE);
        List<Item> itemList = Collections.singletonList(BEEF_BURGER);
        uniqueItemList.setItems(itemList);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(BEEF_BURGER);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItems_listWithDuplicateItems_throwsDuplicateItemException() {
        List<Item> listWithItemPersons = Arrays.asList(APPLE_JUICE, APPLE_JUICE);
        thrown.expect(DuplicateItemException.class);
        uniqueItemList.setItems(listWithItemPersons);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueItemList.asUnmodifiableObservableList().remove(0);
    }
}
