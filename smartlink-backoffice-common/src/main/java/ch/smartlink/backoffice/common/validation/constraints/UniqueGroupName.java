package ch.smartlink.backoffice.common.validation.constraints;

import ch.smartlink.backoffice.common.validation.validator.UniqueGroupNameConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueGroupNameConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueGroupName {

    String message() default "This group name exist in database.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
