package ch.smartlink.backoffice.common.validation.validator;


import ch.smartlink.core.utils.ValidatorUtil;
import ch.smartlink.backoffice.common.validation.constraints.ValidPhone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneConstraintValidator implements ConstraintValidator<ValidPhone, String> {

    @Override
    public void initialize(ValidPhone phone) {
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext arg1) {
        return ValidatorUtil.isValidPhoneNumber(phone);
    }
}
