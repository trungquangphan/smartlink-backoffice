package ch.smartlink.backoffice.common.validation.validator;


import ch.smartlink.core.utils.ValidatorUtil;
import ch.smartlink.backoffice.common.validation.constraints.ValidUsername;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameConstraintValidator implements ConstraintValidator<ValidUsername, String> {

    @Override
    public void initialize(ValidUsername validUsername) {
    }

    @Override
    public boolean isValid(String validUsername, ConstraintValidatorContext arg1) {
        return ValidatorUtil.isValidUserName(validUsername);
    }
}
