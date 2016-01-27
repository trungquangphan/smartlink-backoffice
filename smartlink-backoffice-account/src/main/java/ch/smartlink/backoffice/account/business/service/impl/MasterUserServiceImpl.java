package ch.smartlink.backoffice.account.business.service.impl;

import ch.smartlink.backoffice.account.business.dto.*;
import ch.smartlink.backoffice.account.business.service.IMasterUserService;
import ch.smartlink.backoffice.account.util.WebUtil;
import ch.smartlink.backoffice.account.web.form.EditUserForm;
import ch.smartlink.backoffice.account.web.form.UserForm;
import ch.smartlink.backoffice.account.web.form.UserStatusChangeForm;
import ch.smartlink.backoffice.common.dto.LabelValueBean;
import ch.smartlink.backoffice.common.exception.BOValidationException;
import ch.smartlink.backoffice.common.util.MessageUtil;
import ch.smartlink.core.utils.DateTimeUtil;
import ch.smartlink.core.utils.JsonUtil;
import ch.smartlink.core.utils.ScryptHash;
import ch.smartlink.core.utils.ValidatorUtil;
import com.smartlink.services.dao.master.entities.*;
import com.smartlink.services.dao.master.repositories.*;
import ch.smartlink.backoffice.common.constant.AppConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("masterUserService")
public class MasterUserServiceImpl implements IMasterUserService {

    private IMasterUserRepository masterUserRepository;
    private IMasterGroupRepository masterGroupRepository;
    private IMasterGroupUserRepository masterGroupUserRepository;
    private IMasterGroupModuleRepository masterGroupModuleRepository;
    private ICommonPasswordRepository commonPasswordRepository;

    @Autowired
    public MasterUserServiceImpl(IMasterUserRepository masterUserRepository, IMasterGroupUserRepository masterGroupUserRepository,
                                 IMasterGroupModuleRepository masterGroupModuleRepository, IMasterGroupRepository masterGroupRepository, ICommonPasswordRepository commonPasswordRepository) {
        this.masterUserRepository = masterUserRepository;
        this.masterGroupUserRepository = masterGroupUserRepository;
        this.masterGroupModuleRepository = masterGroupModuleRepository;
        this.masterGroupRepository = masterGroupRepository;
        this.commonPasswordRepository = commonPasswordRepository;

    }

    @Override
    public UserDto authenticateByUsernameAndPassword(String username, String password) {
        UserDto userDto = new UserDto();
        MasterUser masterUser = masterUserRepository.findByUserName(username);
        checkUsernameAndPassword(masterUser);
        checkUserActive(masterUser);
        checkUserLocked(masterUser);
        checkUsernameAndPassword(masterUser, password);
        List<MasterGroupUser> masterGroupUsers = masterGroupUserRepository.findByUser(masterUser);
        userDto.setMasterUser(masterUser);
        userDto.setModulePermisions(getModuleAndPermissionList(masterGroupUsers));
        return userDto;
    }

    private void checkUserLocked(MasterUser masterUser) {
        Date lastAttempTime = masterUser.getLastAttempTime();
        if (lastAttempTime != null) {
            int diffInMinute = DateTimeUtil.getDiffInMinute(Calendar.getInstance().getTime(), lastAttempTime);
            if (diffInMinute < AppConstants.LIMIT_TIME_LOCKED_USER_IN_MINUTE) {
                if (masterUser.isLocked()) {
                    String message = MessageUtil.getMessage("login.error.user.locked.and.last.attemp.time", AppConstants.LIMIT_TIME_LOCKED_USER_IN_MINUTE,
                            masterUser.getLastAttempTime());
                    throw new BOValidationException(message);
                }
            } else {
                masterUser.setLocked(false);
                masterUser.setNumOfAttemp(0);
                masterUserRepository.save(masterUser);
            }
        }
    }

    private void checkUsernameAndPassword(MasterUser masterUser, String password) {
        masterUser = masterUserRepository.findByUserName(masterUser.getUserName());
        if (!ScryptHash.check(password, masterUser.getPassword())) {
            increaseNumOfAttempt(masterUser);
            throw new BOValidationException("login.error.user.wrong.username.or.password");
        } else {
            masterUser.setLocked(false);
            masterUser.setNumOfAttemp(0);
            masterUserRepository.save(masterUser);
        }
    }

    private void increaseNumOfAttempt(MasterUser masterUser) {
        int numOfAttemp = masterUser.getNumOfAttemp();
        numOfAttemp++;
        if (numOfAttemp == AppConstants.MAX_ATTEMP_LOGIN) {
            masterUser.setLastAttempTime(Calendar.getInstance().getTime());
            masterUser.setLocked(true);
            masterUser.setNumOfAttemp(numOfAttemp);
            masterUserRepository.save(masterUser);
            String message = MessageUtil.getMessage("login.error.user.locked", AppConstants.LIMIT_TIME_LOCKED_USER_IN_MINUTE);
            throw new BOValidationException(message);
        } else if (numOfAttemp < AppConstants.MAX_ATTEMP_LOGIN) {
            masterUser.setLastAttempTime(Calendar.getInstance().getTime());
            masterUser.setNumOfAttemp(numOfAttemp);
            masterUserRepository.save(masterUser);
        }

    }

    private void checkUsernameAndPassword(MasterUser masterUser) {
        if (masterUser == null) {
            throw new BOValidationException("login.error.user.wrong.username.or.password");
        }
    }

    private void checkUserActive(MasterUser masterUser) {
        if (!masterUser.isActive()) {
            throw new BOValidationException("login.error.user.inactive");
        }
    }

    private List<ModulePermision> getModuleAndPermissionList(List<MasterGroupUser> masterGroupUsers) {
        List<ModulePermision> modulePermisions = new ArrayList<ModulePermision>();
        for (MasterGroupUser groupUser : masterGroupUsers) {
            addModuleAndPermissionForUserByGroup(groupUser.getGroup(), modulePermisions);
        }
        return modulePermisions;
    }

    private void addModuleAndPermissionForUserByGroup(MasterGroup group, List<ModulePermision> modulePermisions) {
        List<MasterGroupModule> groupModules = masterGroupModuleRepository.findByGroup(group);
        for (MasterGroupModule masterGroupModule : groupModules) {
            MasterGroupModulePK id = masterGroupModule.getId();
            ModulePermision modulePermision = new ModulePermision();
            modulePermision.setModule(id.getModuleId());
            modulePermision.setPermission(id.getPermission());
            modulePermision.setTenantName(group.getTenantName());
            modulePermisions.add(modulePermision);

        }
    }

    @Override
    @Transactional(value = "transactionManagerMaster", isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addUser(UserForm userForm) {
        generatePasswordAndSetToUserForm(userForm);
        createMasterUser(userForm);
        sendPasswordToUser(userForm);
    }

    private void generatePasswordAndSetToUserForm(UserForm userForm) {
        String password = RandomStringUtils.randomAlphanumeric(AppConstants.DEFAULT_PASSWORD_LENGTH);
        userForm.setPassword(password);
    }

    private MasterUser createMasterUser(UserForm userForm) {
        String encrypedPass = ScryptHash.encrypt(userForm.getPassword().trim());
        MasterUser user = new MasterUser();
        user.setActive(userForm.isActive());
        user.setCountry(userForm.getCountry());
        user.setEmail(userForm.getEmail().trim());
        user.setFirstName(userForm.getFirstname().trim());
        user.setLanguage(userForm.getLanguage());
        user.setLastName(userForm.getLastname().trim());
        user.setPassword(encrypedPass);
        user.setPhoneNumber(userForm.getPhoneNumber().trim());
        user.setUserName(userForm.getUsername().trim());
        user.setNewUser(true);
        user.setLastPasswords(JsonUtil.convertObjectToJson(Arrays.asList(new String[]{encrypedPass})));
        user.setNumOfAttemp(0);
        user.setLocked(false);
        return masterUserRepository.save(user);
    }

    private void sendPasswordToUser(UserForm userForm) {
//        MailHeader mailHeader = new MailHeader();
//        MailContent mailContent = new MailContent();
//
//        mailHeader.addTo(userForm.getEmail());
//        mailHeader.setSubject(MessageUtil.getMessage("mail.subject.create.password"));
//        mailContent.setMailBody(getSendMailPasswordContent(userForm));
//        mailSenderService.sendMail(mailHeader, mailContent);
    }

    private void sendResetPasswordToUser(UserForm userForm) {
//        MailHeader mailHeader = new MailHeader();
//        MailContent mailContent = new MailContent();
//
//        mailHeader.addTo(userForm.getEmail());
//        mailHeader.setSubject(MessageUtil.getMessage("mail.subject.reset.password"));
//        mailContent.setMailBody(getSendMailResetPasswordContent(userForm));
//        try {
//            mailSenderService.sendMail(mailHeader, mailContent);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new BackOfficeException(MessageUtil.getMessage("user.reset.password.fail"));
//        }
    }

    private String getSendMailResetPasswordContent(UserForm userForm) {
//        Map<String, Object> model = new HashMap<String, Object>();
//        model.put("lastName", userForm.getLastname());
//        model.put("firstName", userForm.getFirstname());
//        model.put("resetPasswordLink", userForm.getResetPasswordLink());
//        return emailTemplateHelper.getContent(AppConstants.MAIL_TEMPLATE_RESET_PASSWORD, model, LocaleContextHolder.getLocale());
        return "";
    }

    private String getSendMailPasswordContent(UserForm userForm) {

//        Map<String, Object> model = new HashMap<String, Object>();
//        model.put("lastName", userForm.getLastname());
//        model.put("password", userForm.getPassword());
//        model.put("username", userForm.getUsername());
//        model.put("firstName", userForm.getFirstname());
//        return emailTemplateHelper.getContent(AppConstants.MAIL_TEMPLATE_SEND_PASSWORD, model, LocaleContextHolder.getLocale());
        return "";

    }

    @Override
    public UserDto findMasterUserByUsername(String userName) {
        MasterUser masterUser = masterUserRepository.findByUserName(userName);
        checkUsernameAndPassword(masterUser);
        checkUserActive(masterUser);

        UserDto userDto = new UserDto();
        List<MasterGroupUser> masterGroupUsers = masterGroupUserRepository.findByUser(masterUser);
        userDto.setMasterUser(masterUser);
        userDto.setModulePermisions(getModuleAndPermissionList(masterGroupUsers));
        return userDto;
    }

    @Override
    public MasterUser updateUser(EditUserForm editUserForm) throws Exception {
        MasterUser masterUser = masterUserRepository.findByUserName(editUserForm.getUsername());
        checkIfUsernameIsChanged(masterUser, editUserForm);
        checkEmailIsExitsInDb(masterUser, editUserForm);
        return udpateMasterUserWithEditUserForm(masterUser, editUserForm);

    }

    private void checkIfUsernameIsChanged(MasterUser masterUser, EditUserForm editUserForm) throws Exception {
        if (!masterUser.getUserName().equals(editUserForm.getUsername())) {
            throw new Exception();
        }
    }

    private void checkEmailIsExitsInDb(MasterUser masterUser, EditUserForm editUserForm) throws Exception {
        List<MasterUser> masterUsers = masterUserRepository.findByEmail(editUserForm.getEmail());
        if (masterUsers.size() >= 2) {
            throw new Exception();
        } else if (masterUsers.size() == 1) {
            if (!masterUsers.get(0).getId().equals(masterUser.getId())) {
                throw new BOValidationException("UniqueEmail");
            }
        }
    }

    private MasterUser udpateMasterUserWithEditUserForm(MasterUser masterUser, EditUserForm editUserForm) {
        masterUser.setActive(editUserForm.isActive());
        masterUser.setCountry(editUserForm.getCountry());
        masterUser.setEmail(editUserForm.getEmail().trim());
        masterUser.setFirstName(editUserForm.getFirstname().trim());
        masterUser.setLanguage(editUserForm.getLanguage());
        masterUser.setLastName(editUserForm.getLastname().trim());
        masterUser.setPhoneNumber(editUserForm.getPhoneNumber().trim());
        masterUser.setLastUpdated(Calendar.getInstance().getTime());
        masterUser.setVersionNo(editUserForm.getVersionNo());
        return masterUserRepository.save(masterUser);
    }

    @Override
    public UserListResultDto findUsers(UserListRequestDto userListRequest) {
//        UserListResultDto resultDto = new UserListResultDto();
//        List<MasterUser> users = new ArrayList<>();
//        Long count = (long) 0;
//        if (validateSearchCondition(userListRequest)) {
//            CriteriaQuery criteriaQuery = FindUserListServiceBuilder.buildSearchUser(userListRequest);
//            CriteriaQuery countUserCriteriaQuery = FindUserListServiceBuilder.buildCountUser(userListRequest);
//            count = genericDao.count(countUserCriteriaQuery);
//            users = genericDao.excuteQueryAndReturnListEntity(criteriaQuery, MasterUser.class);
//        }
//        resultDto.setUserList(convertListUserToDto(users));
//        resultDto.setTotalRecord(count.intValue());
//        resultDto.setCurrentPage(userListRequest.getPage());
//        return resultDto;
        return null;
    }

    private boolean validateSearchCondition(UserListRequestDto userListRequest) {
        boolean res = true;
        if (CollectionUtils.isEmpty(userListRequest.getTenants()) && CollectionUtils.isEmpty(userListRequest.getGroupIds())
                && !userListRequest.isSearchUserWithNotInAnyGroup()) {
            res = false;
        }
        return res;
    }

    private List<UserDetailDto> convertListUserToDto(List<MasterUser> users) {
        List<UserDetailDto> resultList = new ArrayList<UserDetailDto>();
        for (MasterUser user : users) {
            UserDetailDto dto = new UserDetailDto();
            dto.setActive(user.isActive());
            dto.setEmail(StringUtils.isBlank(user.getEmail()) ? "" : user.getEmail().toLowerCase());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setPhoneNumber(user.getPhoneNumber());
            dto.setGroups(getListGroupAsValueBeanByUser(user));
            dto.setUserId(user.getId());
            dto.setUserName(user.getUserName());
            dto.setVersionNo(user.getVersionNo());
            resultList.add(dto);
        }
        return resultList;
    }

    private List<LabelValueBean> getListGroupAsValueBeanByUser(MasterUser masterUser) {
        List<LabelValueBean> resultList = new ArrayList<LabelValueBean>();
        List<MasterGroupUser> groupUsers = masterGroupUserRepository.findByUser(masterUser);
        for (MasterGroupUser groupUser : groupUsers) {
            LabelValueBean valueBean = new LabelValueBean();
            MasterGroup group = groupUser.getGroup();
            valueBean.setCode(group.getId());
            valueBean.setValue(group.getName());
            resultList.add(valueBean);
        }
        return resultList;
    }

    @Override
    @Transactional(value = "transactionManagerMaster", isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateUser(MasterUser masterUser) {
        masterUserRepository.save(masterUser);
    }

    @Override
    public MasterUser findMasterUserbyId(String id) {
        MasterUser masterUser = masterUserRepository.findOne(id);
        if (masterUser == null) {
            throw new BOValidationException("user.not.exists");
        }
        return masterUser;
    }

    @Override
    public void changeUserStatus(UserStatusChangeForm userStatusChangeForm) {
        MasterUser masterUser = getMasterUserByUserId(userStatusChangeForm.getUserId());
        checkStatusOfUserWithRequestStatus(masterUser, userStatusChangeForm.isActive());
        masterUser.setActive(userStatusChangeForm.isActive());
        masterUser.setVersionNo(userStatusChangeForm.getVersionNo());
        updateUser(masterUser);
    }

    private void checkStatusOfUserWithRequestStatus(MasterUser masterUser, boolean requestStatus) {
        if (masterUser.isActive() == requestStatus) {
            throw new BOValidationException("user.active.change.already." + masterUser.isActive(), masterUser.getUserName());
        }
    }

    private MasterUser getMasterUserByUserId(String userId) {
        MasterUser masterUser = masterUserRepository.findOne(userId);
        if (masterUser == null) {
            throw new BOValidationException("user.not.exists");
        }
        return masterUser;
    }

    @Override
    public List<MasterUser> findAllUser() {
        return masterUserRepository.findAll();
    }

    @Override
    public List<MasterUser> findNonMembers(String masterGroupId) {
        return masterUserRepository.findAll();
    }

    @Override
    public UserListResultDto findAllUserCanBeAddToGroup(String groupId) {
        UserListResultDto resultDto = new UserListResultDto();
        UserListRequestDto requestDTO = new UserListRequestDto();
        requestDTO.setSearchUserWithNotInAnyGroup(true);
        requestDTO.setUsedForAddRemoveUser(true);
        requestDTO.setGroupId(groupId);
        requestDTO.setUsedForFirstLoadUser(true);
        requestDTO.setUserDoSearchId(WebUtil.getUserLogined().getMasterUser().getId());
        if (WebUtil.checkLoginUserIsMaster()) {
            // find all available user in any group in any tenant
            resultDto = this.findUsers(requestDTO);
        } else {
            // find all available user in any group belong to one tenant
            String tenantName = masterGroupRepository.findTenantName(groupId);
            List<String> tenants = new ArrayList<>();
            tenants.add(tenantName);
            requestDTO.setTenants(tenants);
            resultDto = this.findUsers(requestDTO);
        }
        return resultDto;
    }

    @Override
    @Transactional(value = "transactionManagerMaster", isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sendLinkResetPasswordToUser(UserDetailDto userDetailDto, HttpServletRequest request) {
//        UserForm userForm = BackOfficeUtility.convertToUserForm(userDetailDto);
//        setResetPasswordLink(userForm, request);
//        if (saveResetPasswordIdToDb(userForm)) {
//            sendResetPasswordToUser(userForm);
//        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(value = "transactionManagerMaster", isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String sendLinkResetPasswordToUser(String usernameOrEmail, HttpServletRequest request) throws Exception {
        List<MasterUser> masterUsers = new ArrayList<>();
        if (StringUtils.isBlank(usernameOrEmail)) {
            throw new BOValidationException("username.or.email.is.empty");
        } else if (ValidatorUtil.isValidEmail(usernameOrEmail)) {
            masterUsers = masterUserRepository.findByEmail(usernameOrEmail);
            return processForgotPassword(masterUsers, request);
        } else if (ValidatorUtil.isValidUserName(usernameOrEmail)) {
            MasterUser user = masterUserRepository.findByUserName(usernameOrEmail);
            masterUsers = (List<MasterUser>) ((user == null) ? Collections.emptyList() : Arrays.asList(user));
            return processForgotPassword(masterUsers, request);
        } else {
            throw new BOValidationException("username.or.email.is.invalid", usernameOrEmail);
        }

    }

    private String processForgotPassword(List<MasterUser> masterUsers, HttpServletRequest request) throws Exception {
//        if (masterUsers.isEmpty()) {
//            throw new BOValidationException("username.or.email.is.not.exits");
//        } else {
//            UserForm userForm = BackOfficeUtility.convertToUserForm(masterUsers.get(0));
//            setResetPasswordLink(userForm, request);
//            if (saveResetPasswordIdToDb(userForm)) {
//                sendResetPasswordToUser(userForm);
//            }
//            return userForm.getEmail();
//        }
        return "";
    }

    private boolean saveResetPasswordIdToDb(UserForm userForm) {
        MasterUser masterUser = masterUserRepository.findOne(userForm.getId());
        if (masterUser == null) {
            throw new BOValidationException("user.not.exists");
        } else {
            generatePasswordAndSetToUserForm(userForm);
            String resetPasswordLink = userForm.getResetPasswordLink();
            masterUser.setResetPasswordId(resetPasswordLink.substring(resetPasswordLink.indexOf("resetPasswordId=") + 16));
            masterUser.setId(userForm.getId());
            masterUser.setPassword(ScryptHash.encrypt(userForm.getPassword().trim()));
            masterUserRepository.save(masterUser);
            return true;
        }
    }

    @SuppressWarnings("deprecation")
    private void setResetPasswordLink(UserForm userForm, HttpServletRequest request) {
//        StringBuilder resetLink = new StringBuilder(WebUtil.getServerUrlWithContextPath(request) + "/reset-password-action");
//        StringBuilder param = new StringBuilder("");
//        param.append(DigestUtils.sha256Hex(userForm.getId()));
//        resetLink.append("?resetPasswordId=" + param.toString());
//        userForm.setResetPasswordLink(resetLink.toString());
    }

    @Override
    public UserListResultDto findAllUserCanBeAddToGroup(UserListRequestDto listRequestDto) {
        String loginUserId = WebUtil.getUserLogined().getMasterUser().getId();
        listRequestDto.setUserDoSearchId(loginUserId);
        UserListResultDto resultDto = new UserListResultDto();
        if (WebUtil.checkLoginUserIsMaster()) {
            // find all available user in any group in any tenant
            resultDto = this.findUsers(listRequestDto);
        } else {
            // find all available user in any group belong to one tenant
            String masterGroupId = listRequestDto.getGroupId();
            String tenantName = masterGroupRepository.findTenantName(masterGroupId);
            List<String> tenants = new ArrayList<>();
            tenants.add(tenantName);
            listRequestDto.setTenants(tenants);
            resultDto = this.findUsers(listRequestDto);
        }
        return resultDto;
    }

    @Override
    public List<MasterGroupUser> findMasterGroupUserByGroupId(String id) {
        return masterGroupUserRepository.findByGroupId(id);
    }

    @Override
    public void checkValidResetPasswordIdAndGetUserId(String resetPasswordId, UserForm user) throws Exception {
        String userId = getUserIdFromEncryptStringAndCheckExpireResetPasswordId(resetPasswordId);
        user.setId(userId);
        MasterUser masterUser = masterUserRepository.findOne(userId);
        if (masterUser == null) {
            throw new Exception();
        }
        compareResetPasswordIdSaveInDb(resetPasswordId, masterUser);
    }

    private void compareResetPasswordIdSaveInDb(String resetPasswordId, MasterUser masterUser) throws Exception {
        String savedResetPasswordId = masterUser.getResetPasswordId();
        if (!savedResetPasswordId.equals(resetPasswordId) || savedResetPasswordId == null || savedResetPasswordId.equals("")) {
            throw new Exception();
        }
    }

    private String getUserIdFromEncryptStringAndCheckExpireResetPasswordId(String resetPasswordId) throws Exception {
        String userId = "";
        List<MasterUser> masterUsers = masterUserRepository.findByresetPasswordId(resetPasswordId);
        if (masterUsers.size() == 1) {
            userId = masterUsers.get(0).getId();
        }
        return userId;
    }

    @Override
    public void checkValidResetPasswordAndUpdateForUser(UserForm user) throws Exception {
        checkValidResetPasswordIdAndGetUserId(user.getResetPasswordId(), user);
        checkIsValidPassword(user.getNewPassword());
        checkPasswordStartWithCommonPassword(user.getNewPassword());
        checkPasswordContainUnacceptableCharacter(user.getNewPassword());
        checkPasswordIsUsedInTheLast4Changes(user.getId(), user.getNewPassword());
        checkPasswordAndConfirmPassword(user.getNewPassword(), user.getConfirmPassword());
        MasterUser masterUser = masterUserRepository.findOne(user.getId());
        if (masterUser == null) {
            throw new BOValidationException("user.not.exists");
        } else {
            String encryptedPassword = ScryptHash.encrypt(user.getNewPassword());
            masterUser.setPassword(encryptedPassword);
            List<String> lastPasswords = renewLastPasswordList(masterUser.getLastPasswords(), encryptedPassword);
            masterUser.setLastPasswords(JsonUtil.convertObjectToJson(lastPasswords));
            masterUser.setResetPasswordId("");
            masterUser.setLastUpdatedPassword(Calendar.getInstance().getTime());
            masterUser.setNewUser(false);
            masterUser.setLocked(false);
            masterUser.setNumOfAttemp(0);
            masterUserRepository.save(masterUser);
        }
    }

    private void checkIsValidPassword(String password) {
        if (!ValidatorUtil.isValidPassword(password)) {
            throw new BOValidationException("password.pattern.invalid");
        }
    }

    private void checkPasswordAndConfirmPassword(String newPassword, String confirmPassword) {
        if (newPassword == null || newPassword.equals("") || confirmPassword == null || confirmPassword.equals("") || !confirmPassword.equals(newPassword)) {
            throw new BOValidationException("editUser.confirmPassword.notmatched");
        }
    }

    @Override
    public UserDto findUserDtoById(String id) {
        MasterUser masterUser = masterUserRepository.findOne(id);
        if (masterUser == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        List<MasterGroupUser> masterGroupUsers = getMasterGroupUsers(id);
        userDto.setMasterUser(masterUser);
        userDto.setModulePermisions(getModuleAndPermissionList(masterGroupUsers));
        return userDto;
    }

    @Override
    public UserDto findUserDtoByIdAndTenantNames(String id, List<String> tenantNames) {
        MasterUser masterUser = masterUserRepository.findOne(id);
        if (masterUser == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        List<MasterGroupUser> masterGroupUsers = getMasterGroupUsers(id, tenantNames);
        userDto.setMasterUser(masterUser);
        userDto.setModulePermisions(getModuleAndPermissionList(masterGroupUsers));
        return userDto;
    }

    private List<MasterGroupUser> getMasterGroupUsers(String id) {
        List<MasterGroupUser> masterGroupUsers = new ArrayList<>();
        if (WebUtil.checkLoginUserIsMaster()) {
            masterGroupUsers = masterGroupUserRepository.findByUserId(id);
        } else {
            String tenantName = WebUtil.getSelectedTenant();
            masterGroupUsers = masterGroupUserRepository.findByUserIdAndTenantName(id, tenantName);
        }
        return masterGroupUsers;
    }

    private List<MasterGroupUser> getMasterGroupUsers(String id, List<String> tenantNames) {
        List<MasterGroupUser> masterGroupUsers = new ArrayList<>();
        if (WebUtil.checkLoginUserIsMaster()) {
            masterGroupUsers = masterGroupUserRepository.findByUserId(id);
        } else {
            for (String tenantName : tenantNames) {
                masterGroupUsers.addAll(masterGroupUserRepository.findByUserIdAndTenantName(id, tenantName));
            }
        }
        return masterGroupUsers;
    }

    @Transactional(value = "transactionManagerMaster", isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void removeUser(String userId) {
//        if (masterUserRepository.findOne(userId) == null) {
//            throw new BackOfficeException(MessageUtil.getMessage("cannot.find.user", userId));
//        } else {
//            masterGroupUserRepository.deleteByUserId(userId);
//            masterUserRepository.delete(userId);
//        }
    }

    @Override
    public void checkValidPasswords(String userId, String oldPassword, String newPassword, String confirmPassword) {
        checkOldPassword(userId, oldPassword);
        checkIsValidPassword(newPassword);
        checkPasswordIsUsedInTheLast4Changes(userId, newPassword);
        checkPasswordStartWithCommonPassword(newPassword);
        checkPasswordContainUnacceptableCharacter(newPassword);
        checkPasswordAndConfirmPassword(newPassword, confirmPassword);
    }

    private void checkPasswordContainUnacceptableCharacter(String newPassword) {
        List<String> blockCharacters = WebUtil.getCharacters();
        if (CollectionUtils.isNotEmpty(blockCharacters)) {
            for (String blockCharacter : blockCharacters) {
                if (StringUtils.contains(newPassword, blockCharacter)) {
                    throw new BOValidationException("password.cannot.contain.unacceptable.character");
                }
            }
        }
    }

    private void checkPasswordStartWithCommonPassword(String newPassword) {
        List<CommonPassword> commonPasswords = commonPasswordRepository.findCommonpasswordIsContainedInNewPassword(newPassword);
        if (commonPasswords.size() > 0) {
            throw new BOValidationException("password.contain.common.password");
        }
    }

    private void checkPasswordIsUsedInTheLast4Changes(String userId, String newPassword) {
        if (isPasswordUsedInTheLast4Changes(userId, newPassword)) {
            throw new BOValidationException("password.used");
        }
    }

    @Override
    public boolean isPasswordUsedInTheLast4Changes(String userId, String newPassword) {
        String lastPasswords = this.findMasterUserbyId(userId).getLastPasswords();
        return isLastPasswordsContain(lastPasswords, newPassword);
    }

    private boolean isLastPasswordsContain(String lastPasswords, String newPassword) {
        boolean res = false;
        if (!StringUtils.isBlank(lastPasswords)) {
            List<String> listLastPasswords = getListLastPasswords(lastPasswords);
            res = checkContain(listLastPasswords, newPassword);
        }
        return res;
    }

    private boolean checkContain(List<String> listLastPasswords, String newPassword) {
        boolean res = false;
        for (String pw : listLastPasswords) {
            if (ScryptHash.check(newPassword, pw)) {
                res = true;
                break;
            }
        }
        return res;
    }

    private void checkOldPassword(String userId, String oldPassword) {
        String pwInDB = this.findMasterUserbyId(userId).getPassword();
        if (!ScryptHash.check(oldPassword, pwInDB)) {
            throw new BOValidationException("editUser.password.incorrect");
        }
    }

    @Override
    public void updatePasswordForUser(String id, String newPassword) {
//        boolean needToReSaveUserToSession = false;
//        MasterUser masterUser = this.findMasterUserbyId(id);
//        String encryptedPassword = ScryptHash.encrypt(newPassword);
//        masterUser.setPassword(encryptedPassword);
//        masterUser.setResetPasswordId("");
//        List<String> lastPasswords = renewLastPasswordList(masterUser.getLastPasswords(), encryptedPassword);
//        masterUser.setLastPasswords(JsonUtil.convertObjectToJson(lastPasswords));
//        if (BackOfficeUtility.isForcingChangePasswordSituation(masterUser.isNewUser(), Calendar.getInstance().getTime(), masterUser.getLastUpdatedPassword())) {
//            masterUser.setNewUser(false);
//            masterUser.setLastUpdatedPassword(Calendar.getInstance().getTime());
//            needToReSaveUserToSession = true;
//        }
//        masterUser = masterUserRepository.save(masterUser);
//        saveUserInformationToSession(masterUser, needToReSaveUserToSession);
    }

    private List<String> renewLastPasswordList(String lastPasswords, String encryptedPassword) {
        List<String> res = getListLastPasswords(lastPasswords);
        this.updateLastPasswords(res, encryptedPassword);
        return res;
    }

    private void saveUserInformationToSession(MasterUser masterUser, boolean needToReSaveUserToSession) {
//        if (needToReSaveUserToSession) {
//            UserDto userDto = WebUtil.getUserLogined();
//            UserDto newUserDto = new UserDto();
//            newUserDto.setModulePermisions(userDto.getModulePermisions());
//            newUserDto.setMasterUser(masterUser);
//            WebUtil.storeUserIntoSession(newUserDto);
//        }
    }

    private List<String> getListLastPasswords(String lastPasswords) {
        List<String> res = new ArrayList<>();
        if (!StringUtils.isBlank(lastPasswords)) {
            res = JsonUtil.convertJsonToCollection(lastPasswords, String.class);
        }
        return res;
    }

    private void updateLastPasswords(List<String> lastPasswords, String encryptedPassword) {
        lastPasswords.add(encryptedPassword);
        if (lastPasswords.size() > 4) {
            lastPasswords.remove(0);
        }
    }

    @Override
    public UserListResultDto removeGroupIsNotInUserTenant(List<String> userTenants, UserListResultDto userDto) {
        List<UserDetailDto> userList = userDto.getUserList();
        for (UserDetailDto userDetailDto : userList) {
            removeGroupForUser(userDetailDto, userTenants);
        }
        userDto.setUserList(userList);
        return userDto;
    }

    private void removeGroupForUser(UserDetailDto userDetailDto, List<String> userTenants) {
        List<LabelValueBean> groups = new ArrayList<>();
        List<MasterGroup> masterGroups = masterGroupRepository.findByTenants(userTenants);
        for (MasterGroup masterGroup : masterGroups) {
            if (userTenants.contains(masterGroup.getTenantName())) {
                addGroup(masterGroup, groups, userDetailDto.getGroups());
            }
        }
        userDetailDto.setGroups(groups);
    }

    private void addGroup(MasterGroup masterGroup, List<LabelValueBean> newGroups, List<LabelValueBean> oldGroups) {
        for (LabelValueBean labelValueBean : oldGroups) {
            if (labelValueBean.getValue().equals(masterGroup.getName())) {
                labelValueBean.setCode("");
                if (!newGroups.contains(labelValueBean)) {
                    newGroups.add(labelValueBean);
                }
            }
        }
    }

    @Override
    public UserListResultDto removeUserIsNotInUserTenants(UserListResultDto userDto) {
        return null;
    }

    @Override
    public String getSelectedTenantOfUser(String id) {
        return masterUserRepository.findOne(id).getSelectedTenant();
    }

    @Override
    public UserListResultDto removeGroupIsNotInUserTenant(UserListResultDto userDto) {
        if (WebUtil.checkLoginUserIsMaster()) {
            return userDto;
        }
        List<String> userTenants = Arrays.asList(WebUtil.getSelectedTenant());
        userDto = this.removeGroupIsNotInUserTenant(userTenants, userDto);
        return userDto;
    }

    @Override
    public void saveSelectedTenantToDB(String selectedTenant) {
//        String userId = WebUtil.getUserLoginedId();
//        MasterUser masterUser = masterUserRepository.findOne(userId);
//        masterUser.setSelectedTenant(selectedTenant);
//        masterUserRepository.save(masterUser);
    }

    @Override
    public boolean isFirstLoginOfUser(String userId) {
        boolean result = false;
        MasterUser masterUser = masterUserRepository.findOne(userId);
        if (masterUser != null) {
            result = masterUser.isNewUser();
        }
        return result;
    }

    @Override
    public UserListResultDto findUserForListing(UserPagingDto pagingDto) {
        UserListResultDto resultDto = new UserListResultDto();
//        CriteriaQuery criteriaQuery = FindUserListServiceBuilder.buildSearchEverything(pagingDto);
//        CriteriaQuery countUserCriteriaQuery = FindUserListServiceBuilder.buildCountUser(pagingDto);
//        Long count = genericDao.count(countUserCriteriaQuery);
//        List<MasterUser> users = genericDao.excuteQueryAndReturnListEntity(criteriaQuery, MasterUser.class);
//        resultDto.setUserList(convertListUserToDto(users));
//        resultDto.setTotalRecord(count.intValue());
//        resultDto.setCurrentPage(pagingDto.getPage());
        return resultDto;
    }

    @Override
    public List<String> findAllEmailsBelongMasterUsers() {
        List<String> emails = new ArrayList<String>();
        List<MasterUser> res = this.findAllUsersBelongTenantMasters();
        for (MasterUser masterUser : res) {
            emails.add(masterUser.getEmail());
        }
        return emails;
    }

    @Override
    public List<MasterUser> findAllUsersBelongTenantMasters() {
        return null;
    }

}
