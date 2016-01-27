package ch.smartlink.backoffice.common.validation.validator;


import ch.smartlink.backoffice.common.validation.constraints.UniqueTcpToken;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueTcpTokenConstraintValidator implements ConstraintValidator<UniqueTcpToken, String> {

//    private IIcpSmartlinkProductRepository masterUserRepository = BeanUtil.getBean("IIcpSmartlinkProductRepository");

    @Override
    public boolean isValid(String uniqueTcpToken, ConstraintValidatorContext arg1) {
//        if (StringUtils.isBlank(uniqueTcpToken)) {
//            return false;
//        }
//        else {
//            return masterUserRepository.findByTcpToken(uniqueTcpToken).size() == 0;
//        }
        return false;
    }

    @Override
    public void initialize(UniqueTcpToken constraintAnnotation) {

    }

}
