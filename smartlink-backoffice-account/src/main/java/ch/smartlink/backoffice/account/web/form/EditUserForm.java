package ch.smartlink.backoffice.account.web.form;


import ch.smartlink.backoffice.common.dto.LabelValueBean;
import ch.smartlink.backoffice.common.validation.constraints.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

public class EditUserForm implements Serializable {

    private static final long serialVersionUID = 9019104384127369696L;
    @NotNull
    @ValidUsername
    @Size(min = 3, max = 30)
    private String username;

    @NotNull
    @ValidFirstname
    @Size(min = 3, max = 30)
    private String firstname;

    @NotNull
    @ValidLastname
    @Size(min = 3, max = 30)
    private String lastname;

    @ValidPhone
    private String phoneNumber;

    @NotNull
    @ValidEmail
    private String email;

    @NotNull
    private String country;
    private String countryName;

    @NotNull
    private String language;
    private String languageName;

    private String id;
    private boolean active;
    private Integer versionNo;

    private LabelValueBean selectedLanguage;
    private LabelValueBean selectedCountry;
    private List<LabelValueBean> languages;
    private List<LabelValueBean> countries;

    private String password;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    private boolean isNewUser;

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

    public List<LabelValueBean> getLanguages() {
        return languages;
    }

    public void setLanguages(List<LabelValueBean> languages) {
        this.languages = languages;
    }

    public List<LabelValueBean> getCountries() {
        return countries;
    }

    public void setCountries(List<LabelValueBean> countries) {
        this.countries = countries;
    }

    public LabelValueBean getSelectedLanguage() {
        return selectedLanguage;
    }

    public void setSelectedLanguage(LabelValueBean selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
    }

    public LabelValueBean getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(LabelValueBean selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isNewUser() {
        return isNewUser;
    }

    public void setNewUser(boolean isNewUser) {
        this.isNewUser = isNewUser;
    }

}
