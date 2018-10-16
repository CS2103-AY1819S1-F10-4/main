package seedu.address.ui.reservation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.reservation.Reservation;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Reservation}.
 */
public class ReservationCard extends UiPart<Region> {

    private static final String FXML = "ReservationListCard.fxml";

    /**
     * The colours of the tags take on Enum values
     */
    private enum TagColourStyle {
        TEAL, RED, YELLOW, BLUE, ORANGE, BROWN, GREEN, PINK, BLACK, GREY;

        private String getColourStyle() {
            return this.toString().toLowerCase();
        }
    }

    private static final TagColourStyle[] TAG_COLOR_STYLES = TagColourStyle.values();

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Reservation reservation;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label pax;
    @FXML
    private Label dateTime;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;

    public ReservationCard(Reservation reservation, int displayedIndex) {
        super(FXML);
        this.reservation = reservation;
        id.setText(displayedIndex + ". ");
        name.setText(reservation.getName().fullName);
        pax.setText(reservation.getPax().value);
        dateTime.setText(reservation.getDateTime().toString());
        remark.setText(reservation.getRemark().value);
        initTags(reservation);
    }

    /**
     * Returns color style for {@code tagName}'s label
     */
    private String getTagColorStyleFor(String tagName) {
        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length].getColourStyle();
    }

    /**
     * Creates tag labels for {@code reservation}
     */
    private void initTags(Reservation reservation) {
        reservation.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColorStyleFor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReservationCard)) {
            return false;
        }

        // state check
        ReservationCard card = (ReservationCard) other;
        return id.getText().equals(card.id.getText())
                && reservation.equals(card.reservation);
    }
}
