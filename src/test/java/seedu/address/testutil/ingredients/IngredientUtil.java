package seedu.address.testutil.ingredients;

import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_MINIMUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_UNIT;

import seedu.address.logic.commands.ingredients.AddIngredientCommand;
import seedu.address.logic.commands.ingredients.EditIngredientCommand.EditIngredientDescriptor;
import seedu.address.model.ingredient.Ingredient;

/**
 * A utility class for Ingredient.
 */
public class IngredientUtil {

    /**
     * Returns an add command string for adding the {@code ingredient}.
     */
    public static String getAddIngredientCommand(Ingredient ingredient) {
        return AddIngredientCommand.COMMAND_WORD + " " + getIngredientDetails(ingredient);
    }

    /**
     * Returns the part of command string for the given {@code ingredient}'s details.
     */
    public static String getIngredientDetails(Ingredient ingredient) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_INGREDIENT_NAME + ingredient.getName().fullName + " ");
        sb.append(PREFIX_INGREDIENT_UNIT + ingredient.getUnit().unitName + " ");
        sb.append(PREFIX_INGREDIENT_PRICE + ingredient.getPrice().pricePerUnit + " ");
        sb.append(PREFIX_INGREDIENT_MINIMUM + ingredient.getMinimum().minimumUnit + " ");

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditIngredientDescriptor}'s details.
     */
    public static String getEditIngredientDescriptorDetails(EditIngredientDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_INGREDIENT_NAME).append(name.fullName).append(" "));
        descriptor.getUnit().ifPresent(unit -> sb.append(PREFIX_INGREDIENT_UNIT).append(unit.unitName).append(" "));
        descriptor.getPrice().ifPresent(price -> sb.append(PREFIX_INGREDIENT_PRICE)
                .append(price.pricePerUnit).append(" "));
        descriptor.getMinimum().ifPresent(minimum -> sb.append(PREFIX_INGREDIENT_MINIMUM)
                .append(minimum.minimumUnit).append(" "));
        return sb.toString();
    }
}
