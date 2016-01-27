package ch.smartlink.backoffice.account.web.form;

import ch.smartlink.backoffice.common.dto.LabelValueBean;
import org.hibernate.validator.constraints.NotEmpty;
import ch.smartlink.backoffice.common.validation.constraints.*;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

public class UserForm implements Serializable {

    private static final long serialVersionUID = 9019104384127369696L;
    @NotEmpty
    @ValidUsername
    @UniqueUsername
    @Size(min = 3, max = 30)
    private String username;

    @NotEmpty
    @ValidFirstname
    @Size(min = 3, max = 30)
    private String firstname;

    @NotEmpty
    @ValidLastname
    @Size(min = 3, max = 30)
    private String lastname;

    private String password;
    private String newPassword;
    private String confirmPassword;
    private String oldPassword;

    @NotEmpty
    @ValidPhone
    private String phoneNumber;

    @NotEmpty
    @ValidEmail
    @UniqueEmail
    private String email;

    @NotEmpty
    private String country;
    private String countryName;

    @NotEmpty
    private String language;
    private String languageName;
    private String id;
    private boolean active;
    private Integer versionNo;
    private String resetPasswordLink;
    private String resetPasswordId;
    private List<LabelValueBean> groups;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Integer versionNo) {
        this.versionNo = versionNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getResetPasswordLink() {
        return resetPasswordLink;
    }

    public void setResetPasswordLink(String resetPasswordLink) {
        this.resetPasswordLink = resetPasswordLink;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public List<LabelValueBean> getGroups() {
        return groups;
    }

    public void setGroups(List<LabelValueBean> groups) {
        this.groups = groups;
    }

    public String getResetPasswordId() {
        return resetPasswordId;
    }

    public void setResetPasswordId(String resetPasswordId) {
        this.resetPasswordId = resetPasswordId;
    }
}
