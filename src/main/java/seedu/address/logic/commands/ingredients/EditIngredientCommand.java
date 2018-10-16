package seedu.address.logic.commands.ingredients;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_MINIMUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_UNIT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INGREDIENTS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientPrice;
import seedu.address.model.ingredient.IngredientUnit;
import seedu.address.model.ingredient.MinimumUnit;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditIngredientCommand extends Command {

    public static final String COMMAND_WORD = "edit-ingredient";

    public static final String COMMAND_ALIAS = "edit-ing";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the ingredient identified "
            + "by the index number used in the displayed ingredient list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_INGREDIENT_NAME + "NAME] "
            + "[" + PREFIX_INGREDIENT_UNIT + "UNIT] "
            + "[" + PREFIX_INGREDIENT_PRICE + "PRICE] "
            + "[" + PREFIX_INGREDIENT_MINIMUM + "MINIMUM] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_INGREDIENT_PRICE + "$5.60 "
            + PREFIX_INGREDIENT_MINIMUM + "15";

    public static final String MESSAGE_EDIT_INGREDIENT_SUCCESS = "Edited Ingredient: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_INGREDIENT = "This ingredient already exists in the address book.";

    private final Index index;
    private final EditIngredientDescriptor editIngredientDescriptor;

    /**
     * @param index of the ingredient in the filtered ingredient list to edit
     * @param editIngredientDescriptor details to edit the person with
     */
    public EditIngredientCommand(Index index, EditIngredientDescriptor editIngredientDescriptor) {
        requireNonNull(index);
        requireNonNull(editIngredientDescriptor);

        this.index = index;
        this.editIngredientDescriptor = new EditIngredientDescriptor(editIngredientDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Ingredient> lastShownList = model.getFilteredIngredientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
        }

        Ingredient ingredientToEdit = lastShownList.get(index.getZeroBased());
        Ingredient editedIngredient = createEditedIngredient(ingredientToEdit, editIngredientDescriptor);

        if (!ingredientToEdit.isSameIngredient(editedIngredient) && model.hasIngredient(editedIngredient)) {
            throw new CommandException(MESSAGE_DUPLICATE_INGREDIENT);
        }

        model.updateIngredient(ingredientToEdit, editedIngredient);
        model.updateFilteredIngredientList(PREDICATE_SHOW_ALL_INGREDIENTS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_INGREDIENT_SUCCESS, editedIngredient));
    }

    /**
     * Creates and returns a {@code Ingredient} with the details of {@code ingredientToEdit}
     * edited with {@code editIngredientDescriptor}.
     */
    private static Ingredient createEditedIngredient(Ingredient ingredientToEdit,
                                                     EditIngredientDescriptor editIngredientDescriptor) {
        assert ingredientToEdit != null;

        IngredientName updatedName = editIngredientDescriptor.getName().orElse(ingredientToEdit.getName());
        IngredientUnit updatedUnit = editIngredientDescriptor.getUnit().orElse(ingredientToEdit.getUnit());
        IngredientPrice updatedPrice = editIngredientDescriptor.getPrice().orElse(ingredientToEdit.getPrice());
        MinimumUnit updatedMinUnit = editIngredientDescriptor.getMinimum().orElse(ingredientToEdit.getMinimum());

        return new Ingredient(updatedName, updatedUnit, updatedPrice, updatedMinUnit, ingredientToEdit.getNumUnits());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditIngredientCommand)) {
            return false;
        }

        // state check
        EditIngredientCommand e = (EditIngredientCommand) other;
        return index.equals(e.index)
                && editIngredientDescriptor.equals(e.editIngredientDescriptor);
    }

    /**
     * Stores the details to edit the ingredient with. Each non-empty field value will replace the
     * corresponding field value of the ingredient.
     */
    public static class EditIngredientDescriptor {
        private IngredientName name;
        private IngredientUnit unit;
        private IngredientPrice price;
        private MinimumUnit minimumUnit;

        public EditIngredientDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditIngredientDescriptor(EditIngredientDescriptor toCopy) {
            setName(toCopy.name);
            setUnit(toCopy.unit);
            setPrice(toCopy.price);
            setMinimum(toCopy.minimumUnit);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, unit, price, minimumUnit);
        }

        public void setName(IngredientName name) {
            this.name = name;
        }

        public Optional<IngredientName> getName() {
            return Optional.ofNullable(name);
        }

        public void setUnit(IngredientUnit unit) {
            this.unit = unit;
        }

        public Optional<IngredientUnit> getUnit() {
            return Optional.ofNullable(unit);
        }

        public void setPrice(IngredientPrice price) {
            this.price = price;
        }

        public Optional<IngredientPrice> getPrice() {
            return Optional.ofNullable(price);
        }

        public void setMinimum(MinimumUnit minimumUnit) {
            this.minimumUnit = minimumUnit;
        }

        public Optional<MinimumUnit> getMinimum() {
            return Optional.ofNullable(minimumUnit);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditIngredientDescriptor)) {
                return false;
            }

            // state check
            EditIngredientDescriptor e = (EditIngredientDescriptor) other;

            return getName().equals(e.getName())
                    && getUnit().equals(e.getUnit())
                    && getPrice().equals(e.getPrice())
                    && getMinimum().equals(e.getMinimum());
        }
    }
}
