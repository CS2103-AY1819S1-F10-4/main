package seedu.address.logic.commands.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Pax;
import seedu.address.model.reservation.Remark;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing reservation in the menu.
 */
public class EditReservationCommand extends Command {

    public static final String COMMAND_WORD = "edit-reservation";

    public static final String COMMAND_ALIAS = "er";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the reservation identified "
            + "by the index number used in the displayed reservation list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PAX + "PAX] "
            + "[" + PREFIX_DATETIME + "DATETIME] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PAX + "4";

    public static final String MESSAGE_EDIT_RESERVATION_SUCCESS = "Edited Reservation: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RESERVATION = "This reservation already exists.";

    private final Index index;
    private final EditReservationDescriptor editReservationDescriptor;

    /**
     * @param index of the reservation in the filtered reservation list to edit
     * @param editReservationDescriptor details to edit the reservation with
     */
    public EditReservationCommand(Index index, EditReservationDescriptor editReservationDescriptor) {
        requireNonNull(index);
        requireNonNull(editReservationDescriptor);

        this.index = index;
        this.editReservationDescriptor = new EditReservationDescriptor(editReservationDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Reservation> lastShownList = model.getFilteredReservationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
        }

        Reservation reservationToEdit = lastShownList.get(index.getZeroBased());
        Reservation editedReservation = createEditedReservation(reservationToEdit, editReservationDescriptor);

        if (!reservationToEdit.isSameReservation(editedReservation) && model.hasReservation(editedReservation)) {
            throw new CommandException(MESSAGE_DUPLICATE_RESERVATION);
        }

        model.updateReservation(reservationToEdit, editedReservation);
        model.updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_RESERVATION_SUCCESS, editedReservation));
    }

    /**
     * Creates and returns a {@code Reservation} with the details of {@code reservationToEdit}
     * edited with {@code editReservationDescriptor}.
     */
    private static Reservation createEditedReservation(Reservation reservationToEdit,
            EditReservationDescriptor editReservationDescriptor) {
        assert reservationToEdit != null;

        Name updatedName = editReservationDescriptor.getName().orElse(reservationToEdit.getName());
        Pax updatedPax = editReservationDescriptor.getPax().orElse(reservationToEdit.getPax());
        LocalDateTime updatedDateTime = editReservationDescriptor.getDateTime().orElse(reservationToEdit.getDateTime());
        Remark updatedRemark = reservationToEdit.getRemark(); // edit command does not allow editing remarks
        Set<Tag> updatedTags = editReservationDescriptor.getTags().orElse(reservationToEdit.getTags());

        return new Reservation(updatedName, updatedPax, updatedDateTime, updatedRemark, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditReservationCommand)) {
            return false;
        }

        // state check
        EditReservationCommand e = (EditReservationCommand) other;
        return index.equals(e.index)
                && editReservationDescriptor.equals(e.editReservationDescriptor);
    }

    /**
     * Stores the details to edit the reservation with. Each non-empty field value will replace the
     * corresponding field value of the reservation.
     */
    public static class EditReservationDescriptor {
        private Name name;
        private Pax pax;
        private LocalDateTime dateTime;
        private Set<Tag> tags;

        public EditReservationDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditReservationDescriptor(EditReservationDescriptor toCopy) {
            setName(toCopy.name);
            setPax(toCopy.pax);
            setDateTime(toCopy.dateTime);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, pax, dateTime, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPax(Pax pax) {
            this.pax = pax;
        }

        public Optional<Pax> getPax() {
            return Optional.ofNullable(pax);
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }

        public Optional<LocalDateTime> getDateTime() {
            return Optional.ofNullable(dateTime);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditReservationDescriptor)) {
                return false;
            }

            // state check
            EditReservationDescriptor e = (EditReservationDescriptor) other;

            return getName().equals(e.getName())
                    && getPax().equals(e.getPax())
                    && getDateTime().equals(e.getDateTime())
                    && getTags().equals(e.getTags());
        }
    }
}
