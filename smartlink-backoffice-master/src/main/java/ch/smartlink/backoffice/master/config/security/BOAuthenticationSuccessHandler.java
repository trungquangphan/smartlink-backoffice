package ch.smartlink.backoffice.master.config.security;

import ch.smartlink.backoffice.account.business.dto.UserDto;
import ch.smartlink.backoffice.common.constant.AppConstants;
import ch.smartlink.core.log.LOG;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by doomphantom on 21/01/2016.
 */
@Component
public class BOAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        LOG.info("onAuthenticationSuccess");
        HttpSession session = httpServletRequest.getSession();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDto) {
            UserDto userDto = (UserDto) principal;
            session.setAttribute(AppConstants.HEADER_SELECTED_TENANT, userDto.getMasterUser().getSelectedTenant());
        }
        super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
    }
}
