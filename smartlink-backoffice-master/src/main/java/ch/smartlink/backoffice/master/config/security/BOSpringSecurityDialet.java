package ch.smartlink.backoffice.master.config.security;

import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.extras.springsecurity4.dialect.processor.AuthenticationAttrProcessor;
import org.thymeleaf.extras.springsecurity4.dialect.processor.AuthorizeAclAttrProcessor;
import org.thymeleaf.extras.springsecurity4.dialect.processor.AuthorizeAttrProcessor;
import org.thymeleaf.extras.springsecurity4.dialect.processor.AuthorizeUrlAttrProcessor;
import org.thymeleaf.processor.IProcessor;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by doomphantom on 29/01/2016.
 */
public class BOSpringSecurityDialet extends SpringSecurityDialect {
    @Override
    public Set<IProcessor> getProcessors() {
        LinkedHashSet processors = new LinkedHashSet();
        processors.add(new AuthenticationAttrProcessor());
        processors.add(new BOAuthorizeAttrProcessor());
        processors.add(new AuthorizeAttrProcessor("authorize-expr"));
        processors.add(new AuthorizeUrlAttrProcessor());
        processors.add(new AuthorizeAclAttrProcessor());
        return processors;
    }
}
