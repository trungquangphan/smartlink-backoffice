package ch.smartlink.backoffice.common.validation.constraints;


import ch.smartlink.backoffice.common.validation.validator.FirstnameConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FirstnameConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFirstname {

    String message() default "The first name is not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
