package ch.smartlink.backoffice.common.validation.validator;


import ch.smartlink.backoffice.common.validation.constraints.UniqueEan;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEanConstraintValidator implements ConstraintValidator<UniqueEan, String> {

//    private IIcpSmartlinkProductRepository masterUserRepository = BeanUtil.getBean("IIcpSmartlinkProductRepository");

    @Override
    public boolean isValid(String uniqueEan, ConstraintValidatorContext arg1) {
//        if (StringUtils.isBlank(uniqueEan)) {
//            return false;
//        } else {
//            return masterUserRepository.findByEan(uniqueEan).size() == 0;
//        }
        return false;
    }

    @Override
    public void initialize(UniqueEan constraintAnnotation) {

    }

}
