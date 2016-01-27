package ch.smartlink.backoffice.account.web.form;

import java.io.Serializable;

public class UserStatusChangeForm implements Serializable {

    private static final long serialVersionUID = -4318754542722586611L;
    private String userId;
    private boolean active;
    private int versionNo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(int versionNo) {
        this.versionNo = versionNo;
    }

}
