package springProj.nutrDB.data.entities;

/**
 * Reference amount used for a food's nutritional values.
 */
public enum NutritionBasis {
    PER_100_GRAMS("per 100 g"),
    PER_100_MILLILITERS("per 100 ml");

    private final String label;

    NutritionBasis(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
