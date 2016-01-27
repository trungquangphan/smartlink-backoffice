package ch.smartlink.backoffice.account.business.service;

import ch.smartlink.backoffice.account.business.dto.*;
import ch.smartlink.backoffice.account.web.form.EditUserForm;
import ch.smartlink.backoffice.account.web.form.UserForm;
import ch.smartlink.backoffice.account.web.form.UserStatusChangeForm;
import ch.smartlink.backoffice.dao.entity.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IMasterUserService {

    UserDto authenticateByUsernameAndPassword(String username, String password);

    UserDto findUserDtoById(String id);

    void addUser(UserForm userForm);

    UserDto findMasterUserByUsername(String userName);

    MasterUser updateUser(EditUserForm editUserForm) throws Exception;

    UserListResultDto findUsers(UserListRequestDto userListRequest);

    void updateUser(MasterUser masterUser);

    MasterUser findMasterUserbyId(String id);

    void changeUserStatus(UserStatusChangeForm userStatusChangeForm);

    List<MasterUser> findAllUser();

    List<MasterUser> findNonMembers(String masterGroupId);

    UserListResultDto findAllUserCanBeAddToGroup(String masterGroupId);

    UserListResultDto findAllUserCanBeAddToGroup(UserListRequestDto listRequestDto);

    void sendLinkResetPasswordToUser(UserDetailDto userDetailDto, HttpServletRequest request);

    List<MasterGroupUser> findMasterGroupUserByGroupId(String id);

    void checkValidResetPasswordIdAndGetUserId(String resetPasswordId, UserForm user) throws Exception;

    void checkValidResetPasswordAndUpdateForUser(UserForm user) throws Exception;

    String sendLinkResetPasswordToUser(String usernameOrEmail, HttpServletRequest request) throws Exception;

    void removeUser(String userId);

    void checkValidPasswords(String id, String oldPassword, String newPassword, String confirmPassword);

    void updatePasswordForUser(String id, String newPassword);

    UserDto findUserDtoByIdAndTenantNames(String id, List<String> tenantNames);

    UserListResultDto removeGroupIsNotInUserTenant(List<String> userTenants, UserListResultDto userDto);

    UserListResultDto removeUserIsNotInUserTenants(UserListResultDto userDto);

    String getSelectedTenantOfUser(String id);

    UserListResultDto removeGroupIsNotInUserTenant(UserListResultDto userDto);

    void saveSelectedTenantToDB(String selectedTenant);

    boolean isFirstLoginOfUser(String userId);

    boolean isPasswordUsedInTheLast4Changes(String userId, String newPassword);

    UserListResultDto findUserForListing(UserPagingDto pagingDto);

    List<String> findAllEmailsBelongMasterUsers();

    List<MasterUser> findAllUsersBelongTenantMasters();


}
