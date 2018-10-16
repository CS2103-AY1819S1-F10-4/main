package seedu.address.model.ingredient;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Ingredient}'s {@code IngredientName} matches any of the keywords given.
 */
public class IngredientNameContainsKeywordsPredicate implements Predicate<Ingredient> {
    private final List<String> keywords;

    public IngredientNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Ingredient ingredient) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(ingredient.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IngredientNameContainsKeywordsPredicate // instanceof handles nulls
                    && keywords.equals(((IngredientNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
