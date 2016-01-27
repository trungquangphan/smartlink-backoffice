package ch.smartlink.backoffice.master.config.security;

import ch.smartlink.backoffice.master.util.BOSecurityChecker;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomRoleVoter extends RoleVoter {

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        int result = ACCESS_DENIED;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (ConfigAttribute attribute : attributes) {
            if (this.supports(attribute)) {
                if (new BOSecurityChecker().voteInAllTenants(attribute.getAttribute(), authorities)) {
                    result = ACCESS_GRANTED;
                    break;
                }

            }
        }

        return result;
    }

}
