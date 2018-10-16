package seedu.address.model.accounts;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Represents an account password. Guarantees: immutable; is valid as declared in {@link #isValidPassword(String)}
 */
public class Password {

    /*
     * In actual practice, it is recommended not to restrict the max password length. In the case
     * of this module, let us assume that the average user's password length of between 6 to 20 characters.
     */
    public static final String MESSAGE_PASSWORD_CONSTRAINT =
            "Password should not contain spaces, and must be between length 6 and 20";

    /*
     * The first character of the password must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    private static final String PASSWORD_VALIDATION_REGEX = "[\\p{ASCII}&&[\\S]]{6,20}";

    private static final int MAX_SALT_LENGTH = 16;

    private String password;

    /**
     * Constructs a {@code Password}.
     *
     * @param password A valid password.
     */
    public Password(String password) {
        // maybe should not hash here since we create new account obj ...?
        requireNonNull(password);
        checkArgument(isValidPassword(password), MESSAGE_PASSWORD_CONSTRAINT);
        this.password = password;
    }

    /**
     * Returns true if a given string is a valid password, including both raw and hashed password.
     *
     * @param password Password to validate.
     */
    public static boolean isValidPassword(String password) {
        if (isHashed(password)) { // if it's already a hash, we assume it to be valid
            return true;
        }
        return password.matches(PASSWORD_VALIDATION_REGEX);
    }

    /**
     * Verifies if the password matches the hash.
     *
     * @param password The raw password provided by the user.
     * @param hash The hash of the raw password that is already in the storage.
     * @return true if the password matches the hash. Otherwise, returns false.
     */
    public static boolean verifyPassword(String password, byte[] hash) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.getBytes(StandardCharsets.UTF_8), hash);
        return result.verified;
    }

    /**
     * Checks if the password is already a hash.
     *
     * @return true if password is already a hash. Otherwise, returns false.
     */
    public static boolean isHashed(String password) {
        return password.contains("$2a$06$") && password.length() > 20;
    }

    /**
     * Hash the password.
     *
     * @param username The username to generate the salt from.
     */
    public void hash(String username) {
        if (!isHashed(password)) {
            byte[] salt = generateSalt(username);

            //TODO: Removing the salt certain test cases to fail due to mismatch of hashes.
            byte[] hash = BCrypt.withDefaults().hash(6, salt, password.getBytes());
            password = new String(hash);
        }
    }

    /**
     * Generates a 16-bytes long salt using the {@code username}. If the {@code username}'s length is more than 16, it
     * will be truncated to use only the first 16 characters to generate the salt. Otherwise, the remaining spaces will
     * be filled up with the ASCII character 'A'.
     *
     * @param username The username to generate the salt from.
     * @return a sequence of bytes whose length is exactly 16.
     */
    private byte[] generateSalt(String username) {
        String usernameToProcess = username;

        if (usernameToProcess.length() > MAX_SALT_LENGTH) {
            usernameToProcess = usernameToProcess.substring(0, MAX_SALT_LENGTH);
            assert usernameToProcess.getBytes().length == MAX_SALT_LENGTH;
            return usernameToProcess.getBytes();
        }

        // username is shorter than 16 characters, fill up the remaining gaps
        char[] usernameArray = Arrays.copyOf(usernameToProcess.toCharArray(), MAX_SALT_LENGTH);
        for (int i = username.length(); i < MAX_SALT_LENGTH; i++) {
            usernameArray[i] = 'A';
        }

        byte[] result = String.valueOf(usernameArray).getBytes();
        assert result.length == MAX_SALT_LENGTH;
        return result;
    }

    @Override
    public String toString() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // handles null as well
        if (!(other instanceof Password)) {
            return false;
        }

        return password.equals(((Password) other).password); // always return false
    }

    @Override
    public int hashCode() {
        return password.hashCode();
    }
}
