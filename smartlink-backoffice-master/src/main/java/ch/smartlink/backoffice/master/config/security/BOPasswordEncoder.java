package ch.smartlink.backoffice.master.config.security;

import ch.smartlink.backoffice.common.util.ScryptHash;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by doomphantom on 21/01/2016.
 */
@Component
public class BOPasswordEncoder implements PasswordEncoder {
    public String encode(CharSequence charSequence) {
        return ScryptHash.encrypt(charSequence.toString());
    }

    public boolean matches(CharSequence charSequence, String s) {
        return ScryptHash.check(charSequence.toString(), s);
    }
}
