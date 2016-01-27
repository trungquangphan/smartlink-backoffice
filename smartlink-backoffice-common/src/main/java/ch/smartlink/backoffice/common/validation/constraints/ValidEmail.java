package ch.smartlink.backoffice.common.validation.constraints;


import ch.smartlink.backoffice.common.validation.validator.EmailConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailConstraintValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmail {

	String message() default "The email is not valid.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
