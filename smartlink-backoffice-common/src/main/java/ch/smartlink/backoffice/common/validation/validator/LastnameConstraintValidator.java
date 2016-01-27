package ch.smartlink.backoffice.common.validation.validator;


import ch.smartlink.core.utils.ValidatorUtil;
import ch.smartlink.backoffice.common.validation.constraints.ValidLastname;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LastnameConstraintValidator implements ConstraintValidator<ValidLastname, String> {

    @Override
    public void initialize(ValidLastname lastname) {
    }

    @Override
    public boolean isValid(String lastname, ConstraintValidatorContext arg1) {
        return ValidatorUtil.isValidName(lastname);
    }
}
