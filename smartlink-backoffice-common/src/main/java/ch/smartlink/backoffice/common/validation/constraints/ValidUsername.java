package ch.smartlink.backoffice.common.validation.constraints;


import ch.smartlink.backoffice.common.validation.validator.UsernameConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsername {

    String message() default "The username is not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
