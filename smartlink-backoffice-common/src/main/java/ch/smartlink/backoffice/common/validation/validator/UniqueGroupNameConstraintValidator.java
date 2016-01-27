package ch.smartlink.backoffice.common.validation.validator;


import ch.smartlink.backoffice.common.validation.constraints.UniqueGroupName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueGroupNameConstraintValidator implements ConstraintValidator<UniqueGroupName, String> {

    //    private IMasterGroupRepository masterGroupRepository = BeanUtil.getBean("IMasterGroupRepository");
//    private IMasterPreGroupRepository masterDefaultGroupRepository = BeanUtil.getBean("IMasterPreGroupRepository");
    private Class<?> value = null;

    @Override
    public void initialize(UniqueGroupName uniqueGroupName) {
//        value = uniqueGroupName.value();
    }

    @Override
    public boolean isValid(String uniqueGroupName, ConstraintValidatorContext arg1) {
//        if (StringUtils.isBlank(uniqueGroupName)) {
//            return true;
//        }
//        if (isValidateFor(MasterGroupDto.class))
//            return masterGroupRepository.findByName(uniqueGroupName) == null;
//        else if (isValidateFor(MasterPreGroupDto.class)) {
//            return masterDefaultGroupRepository.findByName(uniqueGroupName) == null;
//        }
        return false;
    }

    private boolean isValidateFor(Class<?> classType) {
        return value.equals(classType);
    }
}
