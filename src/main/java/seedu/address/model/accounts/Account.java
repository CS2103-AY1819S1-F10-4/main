package seedu.address.model.accounts;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a user account. Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Account {

    private Username username;
    private Password password;
    //TODO: Add user role

    /**
     * Every field must be present and not null.
     */
    public Account(Username username, Password password) {
        requireAllNonNull(username, password);
        this.username = username;
        this.password = password;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    /**
     * Returns true if both accounts have the same username.
     */
    public boolean isSameUsername(Account otherAccount) {
        if (otherAccount == this) {
            return true;
        }

        return otherAccount != null && otherAccount.getUsername().equals(getUsername());
    }

    /**
     * Returns true if both accounts have the same identity and data fields. This defines a stronger notion of equality
     * between two account.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Account)) {
            return false;
        }

        Account otherAccount = (Account) other;
        return otherAccount.getUsername().equals(getUsername())
                && otherAccount.getPassword().equals(getPassword());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return getUsername().toString();
    }
}
