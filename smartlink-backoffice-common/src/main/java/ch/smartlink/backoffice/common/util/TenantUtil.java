package ch.smartlink.backoffice.common.util;

import ch.smartlink.backoffice.common.constant.AppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doomphantom on 26/01/2016.
 */
public class TenantUtil {

    public static String getSelectedTenant() {
        return (String) SessionUtil.get(AppConstants.HEADER_SELECTED_TENANT);
    }

    public static void setSelectedTenant(String selectedTenant) {
        SessionUtil.set(AppConstants.HEADER_SELECTED_TENANT, selectedTenant);
    }

    public static List<String> getSelectedTenantHasMaster() {
        String seletectTenantName = getSelectedTenant();
        List<String> seletectTenantNames = new ArrayList<>();
        seletectTenantNames.add(seletectTenantName != null ? seletectTenantName : "");
        seletectTenantNames.add(AppConstants.TENANT_MASTER);
        return seletectTenantNames;
    }
}
