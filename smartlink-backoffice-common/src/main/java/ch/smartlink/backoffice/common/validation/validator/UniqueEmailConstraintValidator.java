package ch.smartlink.backoffice.common.validation.validator;


import ch.smartlink.backoffice.common.util.BeanUtil;
import ch.smartlink.backoffice.common.validation.constraints.UniqueEmail;
import ch.smartlink.backoffice.dao.repository.IMasterUserRepository;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailConstraintValidator implements ConstraintValidator<UniqueEmail, String> {

    private IMasterUserRepository masterUserRepository = BeanUtil.getBean("IMasterUserRepository");

    @Override
    public boolean isValid(String uniqueEmail, ConstraintValidatorContext arg1) {
        if (StringUtils.isBlank(uniqueEmail)) {
            return false;
        } else {
            return masterUserRepository.findByEmail(uniqueEmail).size() == 0;
        }
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {

    }

}
