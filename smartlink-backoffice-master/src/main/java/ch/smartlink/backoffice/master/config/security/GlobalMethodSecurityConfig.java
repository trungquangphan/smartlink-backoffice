package ch.smartlink.backoffice.master.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doomphantom on 22/01/2016.
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class GlobalMethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    @Override
    protected AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> voters = new ArrayList<>();
        voters.add(new CustomRoleVoter());
        AccessDecisionManager decisionManager = new AffirmativeBased(voters);
        return decisionManager;
    }


}
