package ch.smartlink.backoffice.common.validation.validator;


import ch.smartlink.backoffice.common.validation.constraints.UniqueSap;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueSapConstraintValidator implements ConstraintValidator<UniqueSap, String> {

//    private IIcpSmartlinkProductRepository masterUserRepository = BeanUtil.getBean("IIcpSmartlinkProductRepository");

    @Override
    public boolean isValid(String uniqueSap, ConstraintValidatorContext arg1) {
//        if (StringUtils.isBlank(uniqueSap)) {
//            return false;
//        } else {
//            return masterUserRepository.findBySap(uniqueSap).size() == 0;
//        }
        return false;
    }

    @Override
    public void initialize(UniqueSap constraintAnnotation) {

    }

}
