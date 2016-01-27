package ch.smartlink.backoffice.common.validation.validator;


import ch.smartlink.core.utils.ValidatorUtil;
import ch.smartlink.backoffice.common.validation.constraints.ValidGroupName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GroupNameConstraintValidator implements ConstraintValidator<ValidGroupName, String> {

    @Override
    public void initialize(ValidGroupName groupName) {
    }

    @Override
    public boolean isValid(String groupName, ConstraintValidatorContext arg1) {
        return ValidatorUtil.isValidName(groupName);
    }
}
