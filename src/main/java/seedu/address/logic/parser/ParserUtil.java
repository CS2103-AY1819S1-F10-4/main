package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.accounts.Password;
import seedu.address.model.accounts.Username;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientPrice;
import seedu.address.model.ingredient.IngredientUnit;
import seedu.address.model.ingredient.MinimumUnit;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.salesrecord.Date;
import seedu.address.model.salesrecord.ItemName;
import seedu.address.model.salesrecord.Price;
import seedu.address.model.salesrecord.QuantitySold;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    //================ Sales Commands Parser Util ================================================================

    /**
     * Parses a {@code String date} into a {@code Date}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String name} into a {@code ItemName}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static ItemName parseItemName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!ItemName.isValidName(trimmedName)) {
            throw new ParseException(ItemName.MESSAGE_NAME_CONSTRAINTS);
        }
        return new ItemName(trimmedName);
    }

    /**
     * Parses a {@code String quantitySold} into a {@code QuantitySold}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code quantitySold} is invalid.
     */
    public static QuantitySold parseQuantitySold(String quantitySold) throws ParseException {
        requireNonNull(quantitySold);
        String trimmedQuantitySold = quantitySold.trim();
        if (!QuantitySold.isValidQuantity(trimmedQuantitySold)) {
            throw new ParseException(QuantitySold.MESSAGE_QUANTITY_CONSTRAINTS);
        }
        return new QuantitySold(trimmedQuantitySold);
    }

    /**
     * Parses a {@code String price} into a {@code Price}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code price} is invalid.
     */
    public static Price parsePrice(String price) throws ParseException {
        requireNonNull(price);
        String trimmedPrice = price.trim();
        if (!Price.isValidPrice(trimmedPrice)) {
            throw new ParseException(Price.MESSAGE_PRICE_CONSTRAINTS);
        }
        return new Price(trimmedPrice);
    }

    //================ Authentication Commands Parser Util ===================================================

    /**
     * Parses a {@code username} into a {@code Username}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code username} is invalid.
     */
    public static Username parseUsername(String username) throws ParseException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!Username.isValidUsername(trimmedUsername)) {
            throw new ParseException(Username.MESSAGE_USERNAME_CONSTRAINT);
        }
        return new Username(trimmedUsername);
    }

    /**
     * Parses a {@code password} into a {@code Password}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code password} is invalid.
     */
    public static Password parsePassword(String password) throws ParseException {
        requireNonNull(password);
        String trimmedPassword = password.trim();
        if (!Password.isValidPassword(trimmedPassword)) {
            throw new ParseException(Password.MESSAGE_PASSWORD_CONSTRAINT);
        }
        return new Password(trimmedPassword);
    }

    //================ Ingredients Parser Util ===================================================

    /**
     * Parses a {@code String name} into an {@code IngredientName}. Leading and trailing whitespaces
     * will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static IngredientName parseIngredientName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!IngredientName.isValidName(trimmedName)) {
            throw new ParseException(IngredientName.MESSAGE_NAME_CONSTRAINTS);
        }
        return new IngredientName(trimmedName);
    }

    /**
     * Parses a {@code String unit} into an {@code IngredientUnit}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code IngredientUnit} is invalid.
     */
    public static IngredientUnit parseIngredientUnit(String unit) throws ParseException {
        requireNonNull(unit);
        String trimmedUnit = unit.trim();
        if (!IngredientUnit.isValidUnit(trimmedUnit)) {
            throw new ParseException(IngredientUnit.MESSAGE_UNIT_CONSTRAINTS);
        }
        return new IngredientUnit(trimmedUnit);
    }

    /**
     * Parses a {@code String price} into an {@code IngredientPrice}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code IngredientPrice} is invalid.
     */
    public static IngredientPrice parseIngredientPrice(String price) throws ParseException {
        requireNonNull(price);
        String trimmedPrice = price.trim();
        if (!IngredientPrice.isValidPrice(trimmedPrice)) {
            throw new ParseException(IngredientPrice.MESSAGE_PRICE_CONSTRAINTS);
        }
        return new IngredientPrice(trimmedPrice);
    }

    /**
     * Parses a {@code String minimum} into an {@code MinimumUnit}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code MinimumUnit} is invalid.
     */
    public static MinimumUnit parseMinimumUnit(String minimum) throws ParseException {
        requireNonNull(minimum);
        String trimmedMinimum = minimum.trim();
        if (!MinimumUnit.isValidMinimum(trimmedMinimum)) {
            throw new ParseException(MinimumUnit.MESSAGE_MINIMUM_CONSTRAINTS);
        }
        return new MinimumUnit(trimmedMinimum);
    }
}
