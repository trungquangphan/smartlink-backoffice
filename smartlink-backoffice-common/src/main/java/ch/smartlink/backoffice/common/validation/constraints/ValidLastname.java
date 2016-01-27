package ch.smartlink.backoffice.common.validation.constraints;

import ch.smartlink.backoffice.common.validation.validator.LastnameConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LastnameConstraintValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLastname {
    
    String message() default "The last name is not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
