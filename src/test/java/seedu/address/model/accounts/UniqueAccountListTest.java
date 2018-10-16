package seedu.address.model.accounts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_ADMIN;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_ONE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.accounts.exceptions.AccountNotFoundException;
import seedu.address.model.accounts.exceptions.DuplicateAccountException;
import seedu.address.testutil.accounts.AccountBuilder;

public class UniqueAccountListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueAccountList uniqueAccountList = new UniqueAccountList();

    @Test
    public void contains_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAccountList.contains(null);
    }

    @Test
    public void contains_accountNotInList_returnsFalse() {
        assertFalse(uniqueAccountList.contains(DEMO_ADMIN));
    }

    @Test
    public void contains_accountInList_returnsTrue() {
        uniqueAccountList.add(DEMO_ADMIN);
        assertTrue(uniqueAccountList.contains(DEMO_ADMIN));
    }

    @Test
    public void add_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAccountList.add(null);
    }

    @Test
    public void add_duplicateAccount_throwsDuplicateAccountException() {
        uniqueAccountList.add(DEMO_ADMIN);
        thrown.expect(DuplicateAccountException.class);
        uniqueAccountList.add(DEMO_ADMIN);
    }

    @Test
    public void updateAccount_nullTargetAudience_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAccountList.update(null, DEMO_ADMIN);
    }

    @Test
    public void updateAccount_nullEditedAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAccountList.update(DEMO_ADMIN, null);
    }

    @Test
    public void updateAccount_targetAccountNotInList_throwsAccountNotFoundException() {
        thrown.expect(AccountNotFoundException.class);
        uniqueAccountList.update(DEMO_ADMIN, DEMO_ADMIN);
    }

    @Test
    public void updateAccount_editedSameAccount_success() {
        uniqueAccountList.add(DEMO_ADMIN);
        uniqueAccountList.update(DEMO_ADMIN, DEMO_ADMIN);
        UniqueAccountList expectedAccountList = new UniqueAccountList();
        expectedAccountList.add(DEMO_ADMIN);
        assertEquals(expectedAccountList, uniqueAccountList);
    }

    @Test
    public void updateAccount_editedAccountHasSameUsername_success() {
        uniqueAccountList.add(DEMO_ADMIN);
        Account editedAdmin = new AccountBuilder(DEMO_ADMIN)
                .withUsername(DEMO_ONE.getUsername().toString())
                .build();
        uniqueAccountList.update(DEMO_ADMIN, editedAdmin);
        UniqueAccountList expectedAccountList = new UniqueAccountList();
        expectedAccountList.add(editedAdmin);
        assertEquals(expectedAccountList, uniqueAccountList);
    }

    @Test
    public void updateAccount_editedAccountAlreadyExists_throwsDuplicateAccountException() {
        uniqueAccountList.add(DEMO_ADMIN);
        uniqueAccountList.add(DEMO_ONE);
        thrown.expect(DuplicateAccountException.class);
        uniqueAccountList.update(DEMO_ADMIN, DEMO_ONE);
    }

    @Test
    public void remove_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAccountList.remove(null);
    }

    @Test
    public void remove_accountDoesNotExist_throwsAccountNotFoundException() {
        thrown.expect(AccountNotFoundException.class);
        uniqueAccountList.remove(DEMO_ADMIN);
    }

    @Test
    public void remove_existingAccount_removesAccount() {
        uniqueAccountList.add(DEMO_ADMIN);
        uniqueAccountList.remove(DEMO_ADMIN);
        UniqueAccountList expectedAccountList = new UniqueAccountList();
        assertEquals(expectedAccountList, uniqueAccountList);
    }

    @Test
    public void setAccounts_nullUniqueAccountList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAccountList.setAccounts((UniqueAccountList) null);
    }

    @Test
    public void setAccounts_uniqueAccountList_replacesOwnListWithProvidedUniqueAccountList() {
        uniqueAccountList.add(DEMO_ADMIN);
        UniqueAccountList expectedAccountList = new UniqueAccountList();
        expectedAccountList.add(DEMO_ONE);
        uniqueAccountList.setAccounts(expectedAccountList);
        assertEquals(expectedAccountList, uniqueAccountList);
    }

    @Test
    public void setAccount_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAccountList.setAccounts((List<Account>) null);
    }

    @Test
    public void updateAccounts_list_replacesOwnListWithProvidedList() {
        uniqueAccountList.add(DEMO_ADMIN);
        List<Account> accounts = Collections.singletonList(DEMO_ONE);
        uniqueAccountList.setAccounts(accounts);
        UniqueAccountList expectedAccountList = new UniqueAccountList();
        expectedAccountList.add(DEMO_ONE);
        assertEquals(expectedAccountList, uniqueAccountList);
    }

    @Test
    public void setAccounts_listWithDuplicateAccounts_throwsDuplicateAccountException() {
        List<Account> listWithDuplicateAccounts = Arrays.asList(DEMO_ADMIN, DEMO_ADMIN);
        thrown.expect(DuplicateAccountException.class);
        uniqueAccountList.setAccounts(listWithDuplicateAccounts);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueAccountList.asUnmodifiableObservableList().remove(0);
    }
}
