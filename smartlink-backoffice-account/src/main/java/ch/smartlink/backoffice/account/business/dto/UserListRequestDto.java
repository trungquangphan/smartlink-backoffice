package ch.smartlink.backoffice.account.business.dto;

import ch.smartlink.core.utils.JsonUtil;

import java.io.Serializable;
import java.util.List;

public class UserListRequestDto implements Serializable {

    private static final long serialVersionUID = 7563157751938408615L;
    private int currentPage = 0;
    private int page;
    private int totalRecord = 10;
    private int itemsPerPage;
    private String username;
    private String name;
    private String phoneNumber;
    private String email;
    private List<String> groupIds;
    private List<String> tenants;
    private String orderDirection = "ASC";
    private String orderProperty;
    private boolean searchUserWithNotInAnyGroup;
    private boolean searchByGroup;
    private boolean searchByTenant;
    private String userDoSearchId;
    private String groupId;// this field is used for getting all available users to add in/out a group.
    private boolean usedForAddRemoveUser = false;
    private boolean searchInOneTenant = false;
    private boolean usedForFirstLoadUser = false;

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public boolean isUsedForFirstLoadUser() {
        return usedForFirstLoadUser;
    }

    public void setUsedForFirstLoadUser(boolean usedForFirstLoadUser) {
        this.usedForFirstLoadUser = usedForFirstLoadUser;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public String getOrderProperty() {
        return orderProperty;
    }

    public void setOrderProperty(String orderProperty) {
        this.orderProperty = orderProperty;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isSearchUserWithNotInAnyGroup() {
        return searchUserWithNotInAnyGroup;
    }

    public void setSearchUserWithNotInAnyGroup(boolean searchUserWithNotInAnyGroup) {
        this.searchUserWithNotInAnyGroup = searchUserWithNotInAnyGroup;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserDoSearchId() {
        return userDoSearchId;
    }

    public void setUserDoSearchId(String userDoSearchId) {
        this.userDoSearchId = userDoSearchId;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<String> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<String> groupIds) {
        this.groupIds = groupIds;
    }

    public List<String> getTenants() {
        return tenants;
    }

    public void setTenants(List<String> tenants) {
        this.tenants = tenants;
    }

    public boolean isSearchByGroup() {
        return searchByGroup;
    }

    public void setSearchByGroup(boolean searchByGroup) {
        this.searchByGroup = searchByGroup;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public boolean isUsedForAddRemoveUser() {
        return usedForAddRemoveUser;
    }

    public void setUsedForAddRemoveUser(boolean usedForAddRemoveUser) {
        this.usedForAddRemoveUser = usedForAddRemoveUser;
    }

    public boolean isSearchInOneTenant() {
        return searchInOneTenant;
    }

    public void setSearchInOneTenant(boolean searchInOneTenant) {
        this.searchInOneTenant = searchInOneTenant;
    }

    @Override
    public String toString() {
        return JsonUtil.convertObjectToJson(this);
    }

    public boolean isSearchByTenant() {
        return searchByTenant;
    }

    public void setSearchByTenant(boolean searchByTenant) {
        this.searchByTenant = searchByTenant;
    }

}
