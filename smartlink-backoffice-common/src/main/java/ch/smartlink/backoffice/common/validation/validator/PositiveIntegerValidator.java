package ch.smartlink.backoffice.common.validation.validator;


import ch.smartlink.backoffice.common.validation.constraints.PositiveInteger;
import ch.smartlink.core.utils.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PositiveIntegerValidator implements ConstraintValidator<PositiveInteger, String> {

    @Override
    public boolean isValid(String validNumber, ConstraintValidatorContext arg1) {
        return ValidatorUtil.isPositiveInteger(validNumber);
    }

    @Override
    public void initialize(PositiveInteger constraintAnnotation) {

    }
}
