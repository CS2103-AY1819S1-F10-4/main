package seedu.address.storage.elements;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.accounts.Account;
import seedu.address.model.accounts.Password;
import seedu.address.model.accounts.Username;

/**
 * JAXB-friendly version of the Account.
 */
public class XmlAdaptedAccount {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Account's %s field is missing!";

    @XmlElement(required = true)
    private String username;
    @XmlElement(required = true)
    private String password;

    /**
     * Constructs an XmlAdaptedAccount. This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAccount() {}

    /**
     * Constructs an {@code XmlAdaptedAccount} with the given account details.
     */
    public XmlAdaptedAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Converts a given Account into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAccount
     */
    public XmlAdaptedAccount(Account source) {
        //TODO: some error handling here
        username = source.getUsername().toString();
        password = source.getPassword().toString();
    }

    /**
     * Verifies if the {@code username} is valid.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted account.
     */
    private void verifyUsername() throws IllegalValueException {
        if (username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Username.class.getSimpleName()));
        }
        if (!Username.isValidUsername(username)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINT);
        }
    }

    /**
     * Verifies if the {@code password} is valid.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted account.
     */
    private void verifyPassword() throws IllegalValueException {
        if (password == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Password.class.getSimpleName()));
        }
        if (!Password.isValidPassword(password)) {
            throw new IllegalValueException(Password.MESSAGE_PASSWORD_CONSTRAINT);
        }
    }

    /**
     * Converts this jaxb-friendly adapted account object into the model's Account object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted account.
     */
    public Account toModelType() throws IllegalValueException {

        verifyUsername();
        final Username modelUsername = new Username(username);

        verifyPassword();
        final Password modelPassword = new Password(password);

        return new Account(modelUsername, modelPassword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAccount)) {
            return false;
        }

        XmlAdaptedAccount otherAccount = (XmlAdaptedAccount) other;
        return Objects.equals(username, otherAccount.username)
                && Objects.equals(password, otherAccount.password);
    }

}
