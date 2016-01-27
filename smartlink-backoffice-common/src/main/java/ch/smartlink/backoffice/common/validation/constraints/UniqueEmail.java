package ch.smartlink.backoffice.common.validation.constraints;

import ch.smartlink.backoffice.common.validation.validator.UniqueEmailConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {

    String message() default "This email is exist in database.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
