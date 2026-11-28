package springProj.nutrDB.data.entities;

/**
 * Reference amount used for a food's nutritional values.
 */
public enum NutritionBasis {
    /** Nutritional values are given per 100 grams of food. */
    PER_100_GRAMS("per 100 g"),

    /** Nutritional values are given per 100 milliliters of food. */
    PER_100_MILLILITERS("per 100 ml");

    private final String label;

    NutritionBasis(String label) {
        this.label = label;
    }

    /** {@return the display label for this reference amount} */
    public String getLabel() {
        return label;
    }
}
