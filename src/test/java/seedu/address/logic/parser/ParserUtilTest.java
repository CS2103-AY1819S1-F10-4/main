package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientPrice;
import seedu.address.model.ingredient.IngredientUnit;
import seedu.address.model.ingredient.MinimumUnit;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String INVALID_INGREDIENT_NAME = "Chicken Drums+ick";
    private static final String INVALID_INGREDIENT_UNIT = "+kilograms";
    private static final String INVALID_INGREDIENT_PRICE = "9.9099";
    private static final String INVALID_INGREDIENT_MINIMUM = "10.0";

    private static final String VALID_INGREDIENT_NAME = "Chicken Drumstick";
    private static final String VALID_INGREDIENT_UNIT = "5-kilogram bag";
    private static final String VALID_INGREDIENT_PRICE = "9.90";
    private static final String VALID_INGREDIENT_MINIMUM = "10";

    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTag(null);
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTag(INVALID_TAG);
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    //============ Ingredients Parser Util Tests =============================================================

    @Test
    public void parseIngredientName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseIngredientName((String) null));
    }

    @Test
    public void parseIngredientName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseIngredientName(INVALID_INGREDIENT_NAME));
    }

    @Test
    public void parseIngredientName_validValueWithoutWhitespace_returnsName() throws Exception {
        IngredientName expectedName = new IngredientName(VALID_INGREDIENT_NAME);
        assertEquals(expectedName, ParserUtil.parseIngredientName(VALID_INGREDIENT_NAME));
    }

    @Test
    public void parseIngredientName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_INGREDIENT_NAME + WHITESPACE;
        IngredientName expectedName = new IngredientName(VALID_INGREDIENT_NAME);
        assertEquals(expectedName, ParserUtil.parseIngredientName(nameWithWhitespace));
    }

    @Test
    public void parseIngredientUnit_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseIngredientName((String) null));
    }

    @Test
    public void parseIngredientUnit_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseIngredientName(INVALID_INGREDIENT_UNIT));
    }

    @Test
    public void parseIngredientUnit_validValueWithoutWhitespace_returnsName() throws Exception {
        IngredientUnit expectedUnit = new IngredientUnit(VALID_INGREDIENT_UNIT);
        assertEquals(expectedUnit, ParserUtil.parseIngredientUnit(VALID_INGREDIENT_UNIT));
    }

    @Test
    public void parseIngredientUnit_validValueWithWhitespace_returnsTrimmedUnit() throws Exception {
        String unitWithWhitespace = WHITESPACE + VALID_INGREDIENT_UNIT + WHITESPACE;
        IngredientUnit expectedUnit = new IngredientUnit(VALID_INGREDIENT_UNIT);
        assertEquals(expectedUnit, ParserUtil.parseIngredientUnit(unitWithWhitespace));
    }

    @Test
    public void parseIngredientPrice_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseIngredientPrice((String) null));
    }

    @Test
    public void parseIngredientPrice_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseIngredientPrice(INVALID_INGREDIENT_PRICE));
    }

    @Test
    public void parseIngredientPrice_validValueWithoutWhitespace_returnsPrice() throws Exception {
        IngredientPrice expectedPrice = new IngredientPrice(VALID_INGREDIENT_PRICE);
        assertEquals(expectedPrice, ParserUtil.parseIngredientPrice(VALID_INGREDIENT_PRICE));
    }

    @Test
    public void parseIngredientPrice_validValueWithWhitespace_returnsTrimmedPrice() throws Exception {
        String priceWithWhitespace = WHITESPACE + VALID_INGREDIENT_PRICE + WHITESPACE;
        IngredientPrice expectedPrice = new IngredientPrice(VALID_INGREDIENT_PRICE);
        assertEquals(expectedPrice, ParserUtil.parseIngredientPrice(priceWithWhitespace));
    }

    @Test
    public void parseMinimumUnit_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseMinimumUnit((String) null));
    }

    @Test
    public void parseMinimumUnit_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseMinimumUnit(INVALID_INGREDIENT_MINIMUM));
    }

    @Test
    public void parseMinimumUnit_validValueWithoutWhitespace_returnsMin() throws Exception {
        MinimumUnit expectedMin = new MinimumUnit(VALID_INGREDIENT_MINIMUM);
        assertEquals(expectedMin, ParserUtil.parseMinimumUnit(VALID_INGREDIENT_MINIMUM));
    }

    @Test
    public void parseMinimumUnit_validValueWithWhitespace_returnsTrimmedMin() throws Exception {
        String minWithWhitespace = WHITESPACE + VALID_INGREDIENT_MINIMUM + WHITESPACE;
        MinimumUnit expectedMin = new MinimumUnit(VALID_INGREDIENT_MINIMUM);
        assertEquals(expectedMin, ParserUtil.parseMinimumUnit(minWithWhitespace));
    }

}
