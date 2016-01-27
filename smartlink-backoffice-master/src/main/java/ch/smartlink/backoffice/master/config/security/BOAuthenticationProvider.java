package ch.smartlink.backoffice.master.config.security;

import ch.smartlink.backoffice.account.business.dto.ModulePermision;
import ch.smartlink.backoffice.account.business.dto.UserDto;
import ch.smartlink.backoffice.account.business.service.IMasterUserService;
import ch.smartlink.backoffice.common.exception.BOValidationException;
import ch.smartlink.backoffice.common.util.MessageUtil;
import ch.smartlink.core.log.LOG;
import ch.smartlink.backoffice.common.constant.AppConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(value = "authenticationProvider ")
public class BOAuthenticationProvider implements AuthenticationProvider {

    private static final String ROLE_PREFIX = "ROLE_";
    @Autowired
    private IMasterUserService userService;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        LOG.info("Entering AuthenticationProvider.authenticate().");

        checkUsernameAndPassword(authentication);

        String username = authentication.getName().trim();
        String password = authentication.getCredentials().toString().trim();
        try {
            UserDto userDto = userService.authenticateByUsernameAndPassword(username, password);
            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userDto, authentication.getCredentials(),
                    getGrantedAuthorities(userDto));

            LOG.info("Username [{}] Leaving AuthenticationProvider.authenticate() successful.", username);
            return result;
        } catch (BOValidationException validateException) {
            LOG.info("Username [{}] Login unsuccessful cause of reason: {}", username, validateException.getMessageCode());
            String errorMessage = MessageUtil.getMessage(validateException.getMessageCode());
            throw new BadCredentialsException(errorMessage, validateException);
        } catch (Exception e) {
            LOG.error(String.format("Username [%s] Login unsuccessful cause of system error: %s", username, e.getMessage()), e);
            String errorMessage = MessageUtil.getMessage("login.error.unknown.reason");
            throw new BadCredentialsException(errorMessage);
        }
    }

    private void checkUsernameAndPassword(Authentication authentication) {
        if (StringUtils.isBlank(authentication.getPrincipal().toString())) {
            throw new BadCredentialsException("login.error.username.is.empty");
        }
        if (StringUtils.isBlank(authentication.getCredentials().toString())) {
            throw new BadCredentialsException("login.error.password.is.empty");
        }

    }

    private List<GrantedAuthority> getGrantedAuthorities(UserDto userDto) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (ModulePermision modulePermision : userDto.getModulePermisions()) {
            StringBuilder role = new StringBuilder(ROLE_PREFIX);
            role.append(modulePermision.getTenantName().toUpperCase()).append(AppConstants.SYMBOL_CONNECT);
            role.append(modulePermision.getModule().name()).append(AppConstants.SYMBOL_CONNECT);
            role.append(modulePermision.getPermission().name().toUpperCase());
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }
        return authorities;
    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }

}
