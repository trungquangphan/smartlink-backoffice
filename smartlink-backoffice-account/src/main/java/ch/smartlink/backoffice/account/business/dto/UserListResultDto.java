package ch.smartlink.backoffice.account.business.dto;

import java.io.Serializable;
import java.util.List;

public class UserListResultDto implements Serializable {

    private static final long serialVersionUID = -8122231365354747962L;
    private List<UserDetailDto> userList;
    private int currentPage;
    private int totalRecord;

    public List<UserDetailDto> getUserList() {
        return userList;
    }

    public void setUserList(List<UserDetailDto> userList) {
        this.userList = userList;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

}
