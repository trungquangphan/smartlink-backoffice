package ch.smartlink.backoffice.account.business.dto;

import ch.smartlink.backoffice.dao.constant.ModuleType;
import ch.smartlink.backoffice.dao.constant.Permission;

import java.io.Serializable;

public class ModulePermision implements Serializable {

    private static final long serialVersionUID = -2443038397444416770L;
    private ModuleType module;
    private Permission permission;
    private String tenantName;

    public ModuleType getModule() {
        return module;
    }

    public void setModule(ModuleType module) {
        this.module = module;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

}
