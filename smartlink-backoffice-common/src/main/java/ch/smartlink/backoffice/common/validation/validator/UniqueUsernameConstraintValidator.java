package ch.smartlink.backoffice.common.validation.validator;


import ch.smartlink.backoffice.common.validation.constraints.UniqueUsername;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameConstraintValidator implements ConstraintValidator<UniqueUsername, String> {

//    private IMasterUserRepository masterUserRepository = BeanUtil.getBean("IMasterUserRepository");


    @Override
    public boolean isValid(String uniqueUsername, ConstraintValidatorContext arg1) {
//        return masterUserRepository.findByUserName(uniqueUsername) == null;
        return false;
    }

    @Override
    public void initialize(UniqueUsername uniqueUsername) {
    }

}
