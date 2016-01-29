package ch.smartlink.backoffice.common.util;


import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by doomphantom on 21/01/2016.
 */
public class WebUtil {
    public static boolean isAjaxRequest(HttpServletRequest webRequest) {
        String requestedWith = webRequest.getHeader("X-Requested-With");
        return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
    }

    public static String getServerUrlWithContextPath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    public static boolean isLogOutRequest(HttpServletRequest request) {
        return StringUtils.equals("/logout", request.getRequestURI().substring(request.getContextPath().length()));
    }

}
