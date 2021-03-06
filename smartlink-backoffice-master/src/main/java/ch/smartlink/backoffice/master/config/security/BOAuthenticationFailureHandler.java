package ch.smartlink.backoffice.master.config.security;

import ch.smartlink.core.log.LOG;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by doomphantom on 21/01/2016.
 */
@Component
public class BOAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        LOG.info("onAuthenticationFailure");
        super.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
    }
}
