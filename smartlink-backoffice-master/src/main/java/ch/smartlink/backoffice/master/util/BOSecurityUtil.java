package ch.smartlink.backoffice.master.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * Created by doomphantom on 21/01/2016.
 */
public class BOSecurityUtil {
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal() instanceof User && authentication.isAuthenticated();
    }
}
