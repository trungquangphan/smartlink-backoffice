package ch.smartlink.backoffice.common.util;

import ch.smartlink.backoffice.common.constant.AppConstants;
import ch.smartlink.backoffice.dao.entity.MasterTenant;
import ch.smartlink.backoffice.dao.repository.IMasterTenantRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by doomphantom on 26/01/2016.
 */
public class TenantUtil {
    @Autowired
    private IMasterTenantRepository tenantRepository;
    private static Map<String, MasterTenant> tenantMap = new HashMap<>();

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

    public static Set<String> getSupportedTenants() {
        return tenantMap.keySet();
    }

    @PostConstruct
    public void postConstruct() {
        List<MasterTenant> tenants = tenantRepository.findAll();
        tenants.forEach(tenant -> tenantMap.put(tenant.getName(), tenant));
    }

}
