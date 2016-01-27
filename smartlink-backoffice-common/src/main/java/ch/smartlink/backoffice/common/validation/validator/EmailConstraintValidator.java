package ch.smartlink.backoffice.common.validation.validator;


import ch.smartlink.core.utils.ValidatorUtil;
import ch.smartlink.backoffice.common.validation.constraints.ValidEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailConstraintValidator implements ConstraintValidator<ValidEmail, String> {

    @Override
    public void initialize(ValidEmail validEmail) {
    }

    @Override
    public boolean isValid(String validEmail, ConstraintValidatorContext arg1) {
        return ValidatorUtil.isValidEmail(validEmail);
    }
}
