package ch.smartlink.backoffice.common.validation.constraints;

import ch.smartlink.backoffice.common.validation.validator.UniqueTcpTokenConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueTcpTokenConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueTcpToken {

    String message() default "This TCP token is exist in database.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
