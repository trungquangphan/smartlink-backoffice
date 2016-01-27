package ch.smartlink.backoffice.common.validation.validator;


import ch.smartlink.core.utils.ValidatorUtil;
import ch.smartlink.backoffice.common.validation.constraints.Nominal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NominalValidator implements ConstraintValidator<Nominal, String> {

    @Override
    public boolean isValid(String validNumber, ConstraintValidatorContext arg1) {
        return ValidatorUtil.isPositiveNumeric(validNumber);
    }

    @Override
    public void initialize(Nominal constraintAnnotation) {

    }
}
