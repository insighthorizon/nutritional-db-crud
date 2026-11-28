package springProj.nutrDB.models.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Class-level Jakarta Validation constraint for the nutrition fields of a food DTO.
 * This is a cross-field check because it compares properties of one object.
 * A cross-parameter constraint in Jakarta Validation checks several arguments of a method or constructor.
 * {@code @Target(TYPE)} allows this annotation on a class, and {@code @Retention(RUNTIME)}
 * makes it available to the validation provider at runtime.
 * {@code @Constraint(validatedBy = ...)} tells the provider which validator to call.
 *
 * @see <a href="https://jakarta.ee/specifications/bean-validation/3.1/jakarta-validation-spec-3.1.html#constraintsdefinitionimplementation-constraintdefinition-properties">Jakarta requirements for message, groups, and payload</a>
 * @see <a href="https://jakarta.ee/specifications/bean-validation/3.1/jakarta-validation-spec-3.1.html#constraintdeclarationvalidationprocess-methodlevelconstraints-parameterconstraints-crossparameterconstraints">Jakarta example of a cross-parameter constraint</a>
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se25/html/jls-9.html#jls-9.6.1">Java annotation elements and their default values</a>
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se25/html/jls-15.html#jls-15.8.2">Java class literals such as FoodDTO.class</a>
 * @see <a href="https://docs.oracle.com/javase/tutorial/java/generics/wildcards.html">Java tutorial on wildcards such as Class&lt;?&gt; and Class&lt;? extends Payload&gt;</a>
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = FoodNutritionValidator.class)
public @interface ValidFoodNutrition {
    // Jakarta Validation requires these three annotation elements.

    /**
     * The validator supplies more specific messages for each failed rule.
     * @return the fallback violation message
     */
    String message() default "The macronutrients are inconsistent with the serving size or energy.";

    /**
     * An empty array places this constraint in the Default group.
     * @return the validation groups this constraint belongs to, given as class literals
     */
    Class<?>[] groups() default {};

    /** {@return optional metadata for validation clients, given as class literals for {@link Payload} types} */
    Class<? extends Payload>[] payload() default {};
}
