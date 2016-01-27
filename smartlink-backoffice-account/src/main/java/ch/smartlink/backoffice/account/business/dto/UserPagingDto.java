package ch.smartlink.backoffice.account.business.dto;


import java.io.Serializable;

public class UserPagingDto extends PagingDTO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 5947579145808080377L;
    private String searchValue = "";
    private String userDoSearchId;

    public UserPagingDto() {
        this.setOrderProperty("firstName");
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getUserDoSearchId() {
        return userDoSearchId;
    }

    public void setUserDoSearchId(String userDoSearchId) {
        this.userDoSearchId = userDoSearchId;
    }

}
