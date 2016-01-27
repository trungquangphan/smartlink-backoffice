package ch.smartlink.backoffice.master.config.security;

import ch.smartlink.core.log.LOG;
import ch.smartlink.backoffice.dao.entity.MasterUser;
import ch.smartlink.backoffice.dao.repository.IMasterUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by doomphantom on 21/01/2016.
 */
@Component
public class BOUserDetailService implements UserDetailsService {
    @Autowired
    private IMasterUserRepository masterUserRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG.info("Load user by name: " + username);
        MasterUser masterUser = masterUserRepository.findByUserName(username);
        return new User(masterUser.getEmail(), masterUser.getPassword(), buildGrantedAuthority());
    }

    private Collection<? extends GrantedAuthority> buildGrantedAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_PCSFR_ADMIN"));
    }
}
