package ch.smartlink.backoffice.common.validation.constraints;


import ch.smartlink.backoffice.common.validation.validator.PositiveIntegerValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PositiveIntegerValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveInteger {

    String message() default "This number is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
