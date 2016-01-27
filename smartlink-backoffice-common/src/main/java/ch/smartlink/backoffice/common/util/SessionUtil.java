package ch.smartlink.backoffice.common.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SessionUtil {

    public static void setToSession(String attributeName, Object object) {
        HttpSession session = getSession();
        if (session != null) {
            session.setAttribute(attributeName, object);
        }
    }

    public static Object getFromSession(String attributeName) {
        HttpSession session = getSession();
        if (session != null) {
            return session.getAttribute(attributeName);
        }
        return null;
    }

    private static HttpSession getSession() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        HttpSession session = ((ServletRequestAttributes) requestAttributes).getRequest().getSession();
        return session;
    }


    public static void removeFromSession(String masterpassCheckout) {
        HttpSession session = getSession();
        session.removeAttribute(masterpassCheckout);
    }

    public static Map<String, Object> getAllAttribute() {
        Map<String, Object> attributes = new HashMap<>();
        HttpSession httpSession = getSession();
        Enumeration<String> attributeNames = httpSession.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            attributes.put(attributeName, httpSession.getAttribute(attributeName));
        }
        return attributes;
    }

    public static void setSessionAttribute(Map<String, Object> sessionAttributes) {
        HttpSession httpSession = getSession();
        Set<Map.Entry<String, Object>> entrySet = sessionAttributes.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            httpSession.setAttribute(entry.getKey(), entry.getValue());
        }
    }
}
