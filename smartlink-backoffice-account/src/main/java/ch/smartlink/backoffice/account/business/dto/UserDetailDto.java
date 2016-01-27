package ch.smartlink.backoffice.account.business.dto;

import ch.smartlink.backoffice.common.dto.LabelValueBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDetailDto implements Serializable {
    private static final long serialVersionUID = 4752534396359002075L;
    private String userName;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean active;
    private Integer versionNo;
    private List<LabelValueBean> groups = new ArrayList<LabelValueBean>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<LabelValueBean> getGroups() {
        return groups;
    }

    public void setGroups(List<LabelValueBean> groups) {
        this.groups = groups;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Integer versionNo) {
        this.versionNo = versionNo;
    }

}
