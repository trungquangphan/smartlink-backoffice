package ch.smartlink.backoffice.account.business.dto;

import com.smartlink.services.dao.master.entities.MasterUser;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {
    private static final long serialVersionUID = -5978417885085214681L;
    private MasterUser masterUser;

    private List<ModulePermision> modulePermisions;

    public MasterUser getMasterUser() {
        return masterUser;
    }

    public void setMasterUser(MasterUser masterUser) {
        this.masterUser = masterUser;
    }

    public List<ModulePermision> getModulePermisions() {
        return modulePermisions;
    }

    public void setModulePermisions(List<ModulePermision> modulePermisions) {
        this.modulePermisions = modulePermisions;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((masterUser == null) ? 0 : masterUser.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        UserDto other = (UserDto) obj;
        return other.getMasterUser().equals(this.getMasterUser());
    }

}
