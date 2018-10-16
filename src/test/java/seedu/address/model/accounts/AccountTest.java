package seedu.address.model.accounts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_DEMO_ONE;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_ADMIN;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_ONE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.accounts.AccountBuilder;

public class AccountTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void check_toString() {
        Account adminAccount = new AccountBuilder(DEMO_ADMIN).build();

        // same username
        assertEquals(adminAccount.toString(), DEMO_ADMIN.getUsername().toString());

        // different username
        assertNotEquals(adminAccount.toString(), DEMO_ONE.getUsername().toString());
    }

    @Test
    public void equals() {
        // same values -> returns true
        Account adminAccount = new AccountBuilder(DEMO_ADMIN).build();
        assertEquals(DEMO_ADMIN, adminAccount);

        // same object -> returns true
        assertEquals(DEMO_ADMIN, DEMO_ADMIN);

        // null -> returns false
        assertNotEquals(null, DEMO_ADMIN);

        // different type -> returns false
        assertNotEquals(5, DEMO_ADMIN);

        // different account -> returns false
        Account demoAccount = new AccountBuilder(DEMO_ONE).build();
        assertNotEquals(DEMO_ADMIN, demoAccount);

        // different username -> returns false
        Account editedAdminAccount =
                new AccountBuilder(DEMO_ADMIN).withUsername(VALID_USERNAME_DEMO_ONE).build();
        assertNotEquals(DEMO_ADMIN, editedAdminAccount);
    }

    @Test
    public void hash_code() {
        Account adminAccount = new AccountBuilder(DEMO_ADMIN).build();

        assertEquals(adminAccount.hashCode(), adminAccount.hashCode());

        Account demoAccount = new AccountBuilder(DEMO_ONE).build();

        assertNotEquals(adminAccount.hashCode(), demoAccount.hashCode());
    }
}
