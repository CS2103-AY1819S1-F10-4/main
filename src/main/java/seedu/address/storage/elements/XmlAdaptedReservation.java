package seedu.address.storage.elements;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Pax;
import seedu.address.model.reservation.Remark;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.tag.Tag;
import seedu.address.storage.XmlAdaptedTag;
/**
 * JAXB-friendly version of the Reservation.
 */
public class XmlAdaptedReservation {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reservation's %s field is missing!";
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String pax;
    @XmlElement(required = true)
    private String dateTime;
    @XmlElement(required = true)
    private String remark;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedReservation() {}
    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedReservation(String name, String pax, String dateTime, String remark, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.pax = pax;
        this.dateTime = dateTime;
        this.remark = remark;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }
    /**
     * Converts a given Reservation into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedReservation
     */
    public XmlAdaptedReservation(Reservation source) {
        name = source.getName().fullName;
        pax = source.getPax().value;
        dateTime = source.getDateTime().toString();
        remark = source.getRemark().value;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }
    /**
     * Converts this jaxb-friendly adapted reservation object into the model's Reservation object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted reservation
     */
    public Reservation toModelType() throws IllegalValueException {
        final List<Tag> reservationTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            reservationTags.add(tag.toModelType());
        }
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);
        if (pax == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Pax.class.getSimpleName()));
        }
        if (!Pax.isValidPax(pax)) {
            throw new IllegalValueException(Pax.MESSAGE_PAX_CONSTRAINTS);
        }
        final Pax modelPax = new Pax(pax);
        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LocalDateTime.class.getSimpleName()));
        }
        final LocalDateTime modelDateTime = LocalDateTime.parse(dateTime);
        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        final Remark modelRemark = new Remark(remark);
        final Set<Tag> modelTags = new HashSet<>(reservationTags);
        return new Reservation(modelName, modelPax, modelDateTime, modelRemark, modelTags);
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof XmlAdaptedReservation)) {
            return false;
        }
        XmlAdaptedReservation otherReservation = (XmlAdaptedReservation) other;
        return Objects.equals(name, otherReservation.name)
                && Objects.equals(pax, otherReservation.pax)
                && Objects.equals(dateTime, otherReservation.dateTime)
                && tagged.equals(otherReservation.tagged);
    }
}
