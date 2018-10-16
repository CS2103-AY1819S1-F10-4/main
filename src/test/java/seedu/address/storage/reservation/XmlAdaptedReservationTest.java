package seedu.address.storage.reservation;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.elements.XmlAdaptedReservation.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.reservation.TypicalReservations.BILLY;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Pax;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.elements.XmlAdaptedReservation;
import seedu.address.testutil.Assert;

public class XmlAdaptedReservationTest {
    private static final String INVALID_NAME = "B@ller";
    private static final String INVALID_PAX = "-6";
    // private static final String INVALID_DATETIME = "2018-12-03TJ^&:";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BILLY.getName().toString();
    private static final String VALID_PAX = BILLY.getPax().toString();
    private static final String VALID_DATETIME = BILLY.getDateTime().toString();
    private static final String VALID_REMARK = BILLY.getRemark().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BILLY.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validReservationDetails_returnsReservation() throws Exception {
        XmlAdaptedReservation reservation = new XmlAdaptedReservation(BILLY);
        assertEquals(BILLY, reservation.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedReservation reservation =
                new XmlAdaptedReservation(INVALID_NAME, VALID_PAX, VALID_DATETIME, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedReservation reservation = new XmlAdaptedReservation(null, VALID_PAX, VALID_DATETIME, VALID_REMARK,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_invalidPax_throwsIllegalValueException() {
        XmlAdaptedReservation reservation =
                new XmlAdaptedReservation(VALID_NAME, INVALID_PAX, VALID_DATETIME, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Pax.MESSAGE_PAX_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_nullPax_throwsIllegalValueException() {
        XmlAdaptedReservation reservation = new XmlAdaptedReservation(VALID_NAME, null, VALID_DATETIME, VALID_REMARK,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Pax.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedReservation reservation =
                new XmlAdaptedReservation(VALID_NAME, VALID_PAX, VALID_DATETIME, VALID_REMARK, invalidTags);
        Assert.assertThrows(IllegalValueException.class, reservation::toModelType);
    }

}
