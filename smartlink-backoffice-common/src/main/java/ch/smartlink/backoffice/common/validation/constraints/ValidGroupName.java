package ch.smartlink.backoffice.common.validation.constraints;


import ch.smartlink.backoffice.common.validation.validator.GroupNameConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GroupNameConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidGroupName {

    String message() default "The groupname is not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
