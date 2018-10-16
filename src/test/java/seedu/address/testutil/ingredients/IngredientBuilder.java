package seedu.address.testutil.ingredients;

import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientPrice;
import seedu.address.model.ingredient.IngredientUnit;
import seedu.address.model.ingredient.MinimumUnit;
import seedu.address.model.ingredient.NumUnits;

/**
 * A utility class to help with building Ingredient objects.
 */
public class IngredientBuilder {

    public static final String DEFAULT_NAME = "Granny Smith Apple";
    public static final String DEFAULT_UNIT = "packet of 5";
    public static final String DEFAULT_PRICE = "1.90";
    public static final String DEFAULT_MINIMUM = "3";
    public static final String DEFAULT_NUMUNITS = "0";

    private IngredientName name;
    private IngredientUnit unit;
    private IngredientPrice price;
    private MinimumUnit minimum;
    private NumUnits numUnits;

    public IngredientBuilder() {
        name = new IngredientName(DEFAULT_NAME);
        unit = new IngredientUnit(DEFAULT_UNIT);
        price = new IngredientPrice(DEFAULT_PRICE);
        minimum = new MinimumUnit(DEFAULT_MINIMUM);
        numUnits = new NumUnits(DEFAULT_NUMUNITS);
    }

    /**
     * Initializes the IngredientBuilder with the data of {@code ingredientToCopy}.
     */
    public IngredientBuilder(Ingredient ingredientToCopy) {
        name = ingredientToCopy.getName();
        unit = ingredientToCopy.getUnit();
        price = ingredientToCopy.getPrice();
        minimum = ingredientToCopy.getMinimum();
        numUnits = ingredientToCopy.getNumUnits();
    }

    /**
     * Sets the {@code IngredientName} of the {@code Ingredient} that we are building.
     */
    public IngredientBuilder withName(String name) {
        this.name = new IngredientName(name);
        return this;
    }

    /**
     * Sets the {@code IngredientUnit} of the {@code Ingredient} that we are building.
     */
    public IngredientBuilder withUnit(String unit) {
        this.unit = new IngredientUnit(unit);
        return this;
    }

    /**
     * Sets the {@code IngredientPrice} of the {@code Ingredient} that we are building.
     */
    public IngredientBuilder withPrice(String price) {
        this.price = new IngredientPrice(price);
        return this;
    }

    /**
     * Sets the {@code Minimum} of the {@code Minimum} that we are building.
     */
    public IngredientBuilder withMinimum(String minimum) {
        this.minimum = new MinimumUnit(minimum);
        return this;
    }

    /**
     * Sets the {@code NumUnits} of the {@code Ingredient} that we are building.
     */
    public IngredientBuilder withNumUnits(String numUnits) {
        this.numUnits = new NumUnits(numUnits);
        return this;
    }

    public Ingredient build() {
        return new Ingredient(name, unit, price, minimum, numUnits);
    }

}
