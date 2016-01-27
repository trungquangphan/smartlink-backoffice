package ch.smartlink.backoffice.common.validation.validator;


import ch.smartlink.core.utils.ValidatorUtil;
import ch.smartlink.backoffice.common.validation.constraints.ValidFirstname;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FirstnameConstraintValidator implements ConstraintValidator<ValidFirstname, String> {

    @Override
    public void initialize(ValidFirstname firstname) {
    }

    @Override
    public boolean isValid(String firstname, ConstraintValidatorContext arg1) {
        return ValidatorUtil.isValidName(firstname);
    }
}
