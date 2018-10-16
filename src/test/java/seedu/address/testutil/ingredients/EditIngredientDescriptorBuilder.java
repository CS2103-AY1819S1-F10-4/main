package seedu.address.testutil.ingredients;

import seedu.address.logic.commands.ingredients.EditIngredientCommand.EditIngredientDescriptor;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientPrice;
import seedu.address.model.ingredient.IngredientUnit;
import seedu.address.model.ingredient.MinimumUnit;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditIngredientDescriptorBuilder {

    private EditIngredientDescriptor descriptor;

    public EditIngredientDescriptorBuilder() {
        descriptor = new EditIngredientDescriptor();
    }

    public EditIngredientDescriptorBuilder(EditIngredientDescriptor descriptor) {
        this.descriptor = new EditIngredientDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditIngredientDescriptor} with fields containing {@code ingredient}'s details
     */
    public EditIngredientDescriptorBuilder(Ingredient ingredient) {
        descriptor = new EditIngredientDescriptor();
        descriptor.setName(ingredient.getName());
        descriptor.setUnit(ingredient.getUnit());
        descriptor.setPrice(ingredient.getPrice());
        descriptor.setMinimum(ingredient.getMinimum());
    }

    /**
     * Sets the {@code IngredientName} of the {@code EditIngredientDescriptor} that we are building.
     */
    public EditIngredientDescriptorBuilder withName(String name) {
        descriptor.setName(new IngredientName(name));
        return this;
    }

    /**
     * Sets the {@code IngredientUnit} of the {@code EditIngredientDescriptor} that we are building.
     */
    public EditIngredientDescriptorBuilder withUnit(String unit) {
        descriptor.setUnit(new IngredientUnit(unit));
        return this;
    }

    /**
     * Sets the {@code IngredientPrice} of the {@code EditIngredientDescriptor} that we are building.
     */
    public EditIngredientDescriptorBuilder withPrice(String price) {
        descriptor.setPrice(new IngredientPrice(price));
        return this;
    }

    /**
     * Sets the {@code MinimumUnit} of the {@code EditIngredientDescriptor} that we are building.
     */
    public EditIngredientDescriptorBuilder withMinimum(String minimum) {
        descriptor.setMinimum(new MinimumUnit(minimum));
        return this;
    }

    public EditIngredientDescriptor build() {
        return descriptor;
    }
}
