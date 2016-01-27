package ch.smartlink.backoffice.common.util;

import ch.smartlink.backoffice.common.constant.AppConstants;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by doomphantom on 21/01/2016.
 */
public class WebUtil {
    public static List<String> getSelectedTenantHasMaster() {
        String seletectTenantName = getSelectedTenant();
        List<String> seletectTenantNames = new ArrayList<>();
        seletectTenantNames.add(seletectTenantName != null ? seletectTenantName : "");
        seletectTenantNames.add(AppConstants.TENANT_MASTER);
        return seletectTenantNames;
    }

    public static String getSelectedTenant() {
        return getValueInSession(AppConstants.HEADER_SELECTED_TENANT);
    }


    @SuppressWarnings({"unchecked"})
    private static <T> T getValueInSession(String key) {
        HttpSession session = getSessionWithoutCreate();
        if (session != null) {
            return (T) session.getAttribute(key);
        }
        return null;
    }

    private static HttpSession getSessionWithoutCreate() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attr != null) {
            return attr.getRequest().getSession(false);
        }
        return null;
    }


}
