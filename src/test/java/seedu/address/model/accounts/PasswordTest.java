package seedu.address.model.accounts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_DEMO_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_DEMO_TWO;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PasswordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Password(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidPassword = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Password(invalidPassword));
    }

    @Test
    public void isValidPassword() {
        // null password
        Assert.assertThrows(NullPointerException.class, () -> Password.isValidPassword(null));

        // invalid password
        assertFalse(Password.isValidPassword("")); // empty string
        assertFalse(Password.isValidPassword(" ")); // spaces only
        assertFalse(Password.isValidPassword("1122")); // length 4, whereas minimum is length 6
        assertFalse(Password.isValidPassword("11 22qq")); // contains space

        // valid password, all with length 6 or more
        assertTrue(Password.isValidPassword("azhikai")); // alphabets only
        assertTrue(Password.isValidPassword("1122qq")); // numbers only
        assertTrue(Password.isValidPassword("peter2")); // alphanumeric characters
        assertTrue(Password.isValidPassword("CapitalAng")); // with capital letters
        assertTrue(Password.isValidPassword("th15isAPassword")); // long alphanumeric password
        assertTrue(Password.isValidPassword("!!@@SGMY3-1")); // alphanumeric and special characters
    }

    @Test
    public void hashPassword() {
        Password passwordOne = new Password("1122qq");
        passwordOne.hash(""); // hash with an empty username, which is not possible in actual scenario
        assertTrue(Password.isHashed(passwordOne.toString()));

        Password passwordTwo = new Password("1122qq");
        passwordTwo.hash("azhikai12345678910"); // more than length 16 for username
        assertTrue(Password.isHashed(passwordTwo.toString())); // hash version 2a, cost factor 6
    }

    @Test
    public void verifyHashPassword() {
        Password passwordOne = new Password(VALID_PASSWORD_DEMO_ONE);
        passwordOne.hash(""); // hash with an empty username, which is not possible in actual scenario

        assertTrue(Password.verifyPassword(VALID_PASSWORD_DEMO_ONE, passwordOne.toString().getBytes()));

        Password passwordTwo = new Password(VALID_PASSWORD_DEMO_ONE);
        passwordTwo.hash("azhikai12345678910"); // more than length 16 for username

        assertTrue(Password.verifyPassword(VALID_PASSWORD_DEMO_ONE, passwordTwo.toString().getBytes()));

        // different username but same password should not result in the same password hash
        assertNotEquals(passwordOne.toString(), passwordTwo.toString());
    }

    @Test
    public void equals() {
        Password passwordOne = new Password(VALID_PASSWORD_DEMO_ONE);
        passwordOne.hash(""); // hash with an empty username, which is impossible
        Password passwordTwo = new Password(VALID_PASSWORD_DEMO_ONE);
        passwordTwo.hash("azhikai12345678910"); // more than length 16 for username

        // same object
        assertTrue(passwordOne.equals(passwordOne));

        // different type
        assertFalse(passwordOne.equals(null));
        assertFalse(passwordOne.equals(0));

        // different username but same password should not result in the same password hash
        assertFalse(passwordOne.equals(passwordTwo));
    }

    @Test
    public void hash_code() {
        Password passwordOne = new Password(VALID_PASSWORD_DEMO_ONE);
        assertEquals(passwordOne.hashCode(), passwordOne.hashCode());

        Password passwordTwo = new Password(VALID_PASSWORD_DEMO_TWO);
        assertNotEquals(passwordOne.hashCode(), passwordTwo.hashCode());
    }
}
