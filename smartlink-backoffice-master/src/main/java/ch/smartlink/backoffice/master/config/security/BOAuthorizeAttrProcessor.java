package ch.smartlink.backoffice.master.config.security;

import ch.smartlink.backoffice.common.constant.AppConstants;
import ch.smartlink.backoffice.common.util.SessionUtil;
import ch.smartlink.backoffice.common.util.WebUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.thymeleaf.Arguments;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.dom.Element;
import org.thymeleaf.exceptions.ConfigurationException;
import org.thymeleaf.extras.springsecurity4.auth.AuthUtils;
import org.thymeleaf.extras.springsecurity4.dialect.processor.AuthorizeAttrProcessor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by doomphantom on 29/01/2016.
 */
public class BOAuthorizeAttrProcessor extends AuthorizeAttrProcessor {

    private static final String DYNAMIC_TENANT_MARK = "_" + AppConstants.TENANT_TEMPLATE + "_";

    @Override
    protected boolean isVisible(Arguments arguments, Element element, String attributeName) {
        String attributeValue = element.getAttributeValue(attributeName);

        if (StringUtils.isNotBlank(attributeValue)) {
            if (StringUtils.contains(attributeValue, DYNAMIC_TENANT_MARK)) {
                attributeValue = String.format(attributeValue, WebUtil.getSelectedTenant());
            }
            IContext context = arguments.getContext();
            if (!(context instanceof IWebContext)) {
                throw new ConfigurationException("Thymeleaf execution context is not a web context (implementation of " + IWebContext.class.getName() + ". Spring Security integration can only be used in " + "web environements.");
            } else {
                IWebContext webContext = (IWebContext) context;
                HttpServletRequest request = webContext.getHttpServletRequest();
                HttpServletResponse response = webContext.getHttpServletResponse();
                ServletContext servletContext = webContext.getServletContext();
                Authentication authentication = AuthUtils.getAuthenticationObject();
                return authentication == null ? false : AuthUtils.authorizeUsingAccessExpression(arguments, attributeValue, authentication, request, response, servletContext);
            }
        } else {
            return false;
        }
    }
}