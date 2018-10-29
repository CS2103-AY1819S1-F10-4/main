package seedu.address.storage.menu;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.elements.XmlAdaptedItem.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.menu.TypicalItems.APPLE_JUICE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.menu.Name;
import seedu.address.model.menu.Price;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.elements.XmlAdaptedItem;
import seedu.address.testutil.Assert;

public class XmlAdaptedItemTest {
    private static final String INVALID_NAME = "F@ies";
    private static final String INVALID_PRICE = "+2";
    private static final String INVALID_TAG = "#fries";
    private static final String INVALID_INGREDIENT_NAME = "@ppl3";
    private static final String INVALID_INTEGER = "0";

    private static final String VALID_NAME = APPLE_JUICE.getName().toString();
    private static final String VALID_PRICE = APPLE_JUICE.getPrice().toString();
    private static final String VALID_REMARK = APPLE_JUICE.getRecipe().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = APPLE_JUICE.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    private static final String VALID_INGREDIENT_NAME = "Apple";
    private static final String VALID_INTEGER = "3";
    private static final Map<String, String> VALID_REQUIRED_INGREDIENTS = new HashMap<>();
    static {
        VALID_REQUIRED_INGREDIENTS.put(VALID_INGREDIENT_NAME, VALID_INTEGER);
    }


    @Test
    public void toModelType_validItemDetails_returnsItem() throws Exception {
        XmlAdaptedItem item = new XmlAdaptedItem(APPLE_JUICE);
        assertEquals(APPLE_JUICE, item.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedItem item = new XmlAdaptedItem(INVALID_NAME, VALID_PRICE, VALID_REMARK, VALID_TAGS,
                VALID_REQUIRED_INGREDIENTS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedItem item = new XmlAdaptedItem(null, VALID_PRICE, VALID_REMARK, VALID_TAGS,
                VALID_REQUIRED_INGREDIENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        XmlAdaptedItem item = new XmlAdaptedItem(VALID_NAME, INVALID_PRICE, VALID_REMARK, VALID_TAGS,
                VALID_REQUIRED_INGREDIENTS);
        String expectedMessage = Price.MESSAGE_PRICE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        XmlAdaptedItem item = new XmlAdaptedItem(VALID_NAME, null, VALID_REMARK, VALID_TAGS,
                VALID_REQUIRED_INGREDIENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedItem item = new XmlAdaptedItem(VALID_NAME, VALID_PRICE, VALID_REMARK, invalidTags,
                VALID_REQUIRED_INGREDIENTS);
        Assert.assertThrows(IllegalValueException.class, item::toModelType);
    }

    @Test
    public void toModelType_invalidRequiredIngredients_throwsIllegalValueException() {
        Map<String, String> invalidRequiredIngredients = new HashMap<>();
        // Invalid IngredientName
        invalidRequiredIngredients.put(INVALID_INGREDIENT_NAME, VALID_INTEGER);
        XmlAdaptedItem item = new XmlAdaptedItem(VALID_NAME, VALID_PRICE, VALID_REMARK, VALID_TAGS,
                invalidRequiredIngredients);
        Assert.assertThrows(IllegalValueException.class, item::toModelType);

        // Invalid Integer
        invalidRequiredIngredients.clear();
        invalidRequiredIngredients.put(VALID_INGREDIENT_NAME, INVALID_INTEGER);
        item = new XmlAdaptedItem(VALID_NAME, VALID_PRICE, VALID_REMARK, VALID_TAGS,
                invalidRequiredIngredients);
        Assert.assertThrows(IllegalValueException.class, item::toModelType);

        // Invalid IngredientName and Integer
        invalidRequiredIngredients.clear();
        invalidRequiredIngredients.put(INVALID_INGREDIENT_NAME, INVALID_INTEGER);
        item = new XmlAdaptedItem(VALID_NAME, VALID_PRICE, VALID_REMARK, VALID_TAGS,
                invalidRequiredIngredients);
        Assert.assertThrows(IllegalValueException.class, item::toModelType);
    }

}
