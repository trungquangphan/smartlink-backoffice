package ch.smartlink.backoffice.master.util;

import ch.smartlink.backoffice.common.constant.AppConstants;
import ch.smartlink.backoffice.common.util.WebUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;
import java.util.List;

public class BOSecurityChecker {

    public boolean hasRoleOnTenants(String[] roles, List<String> tenants) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication == null) {
            authentication = ((SecurityContextImpl) ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest().getSession()
                    .getAttribute("SPRING_SECURITY_CONTEXT"))
                    .getAuthentication();
        }
        boolean hasRole = true;
        Collection<? extends GrantedAuthority> authorities = authentication
                .getAuthorities();
        for (String role : roles) {
            hasRole = voteInTenantsEx(role, authorities, tenants);
            if (!hasRole) {
                break;
            }
        }

        return hasRole;
    }

    public boolean voteInAllTenants(String attribute,
                                    Collection<? extends GrantedAuthority> authorities) {
        List<String> tenants = WebUtil.getSelectedTenantHasMaster();
        return voteInTenants(attribute, authorities, tenants);
    }

    public boolean currentUserHasRoleOnTenant(String selectedTenant,
                                              String attribute) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication
                .getAuthorities();
        return currentUserHasRoleOnTenant(selectedTenant, attribute,
                authorities);
    }

    private boolean voteInTenants(String attribute,
                                  Collection<? extends GrantedAuthority> authorities,
                                  List<String> tenants) {
        boolean result = false;
        for (GrantedAuthority authority : authorities) {
            if (attribute.contains(AppConstants.TENANT_TEMPLATE)) {
                for (String tenant : tenants) {
                    if (getFullGrantedAuthority(tenant, attribute).equals(
                            authority.getAuthority())) {
                        result = true;
                        break;
                    }
                }
            } else if (attribute.equals(authority.getAuthority())) {
                result = true;
            }

            if (result) {
                break;
            }
        }
        return result;
    }

    private boolean voteInTenantsEx(String attribute,
                                    Collection<? extends GrantedAuthority> authorities,
                                    List<String> tenants) {
        boolean result = false;
        for (String tenant : tenants) {
            result = currentUserHasRoleOnTenant(tenant, attribute, authorities);
            if (!result) {
                break;
            }
        }
        return result;
    }

    private boolean currentUserHasRoleOnTenant(String tenant, String attribute,
                                               Collection<? extends GrantedAuthority> authorities) {
        boolean result = false;
        if (attribute.contains(AppConstants.TENANT_TEMPLATE)) {
            String role = getFullGrantedAuthority(tenant, attribute);
            result = checkRoleInAuthorities(role, authorities);
        } else {
            result = checkRoleInAuthorities(attribute, authorities);
        }
        return result;
    }

    public static boolean checkRoleInAuthorities(String role,
                                                 Collection<? extends GrantedAuthority> authorities) {
        boolean res = false;
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals(role)) {
                res = true;
            }
        }
        return res;
    }

    private String getFullGrantedAuthority(String selectedTenant,
                                           String grantedAuthority) {
        return String.format(grantedAuthority, selectedTenant).toUpperCase();
    }

}
