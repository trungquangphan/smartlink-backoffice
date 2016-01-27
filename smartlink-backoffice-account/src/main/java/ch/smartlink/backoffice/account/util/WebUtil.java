package ch.smartlink.backoffice.account.util;


import ch.smartlink.backoffice.account.business.dto.UserDto;
import ch.smartlink.backoffice.common.constant.AppConstants;
import ch.smartlink.backoffice.dao.entity.MasterUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class WebUtil {
    private static final String SPLIT = ",";
    private String supportedLanguages;
    private String supportedCountries;
    private String supportedCurrencies;
    private String unacceptableCharacters;

    private static List<String> countries;
    private static List<String> languages;
    private static List<String> currencies;
    private static List<String> characters;

    public static boolean checkMemberIsLoginUser(String memberId) {
        UserDto userDto = getUserLogined();
        return userDto.getMasterUser().getId().equalsIgnoreCase(memberId);
    }

    public static UserDto getUserLogined() {
        return getValueInSession(AppConstants.SESSION_USER);
    }

    private static <T> T getValueInSession(String key) {
        HttpSession session = getSessionWithoutCreate();
        if (session != null) {
            return (T) session.getAttribute(key);
        }
        return null;
    }

    private static HttpSession getSessionWithoutCreate() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attr != null) {
            return attr.getRequest().getSession(false);
        }
        return null;
    }

    public static String getUserLoginedName() {

        UserDto user = getUserLogined();
        String userName = "";
        if (user != null) {
            MasterUser masterUser = user.getMasterUser();
            if ((StringUtils.isNotBlank(masterUser.getFirstName())) && (StringUtils.isNotBlank(masterUser.getLastName()))) {
                userName = masterUser.getFirstName().concat(" ").concat(masterUser.getLastName());
            } else {

                userName = masterUser.getUserName();
            }
        }

        return userName;
    }

    public static boolean checkLoginUserIsMaster() {
        return (boolean) getOrCreateSession().getAttribute(AppConstants.SESSION_KEY_TENANT_IS_MASTER);
    }

    public static HttpSession getOrCreateSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attr.getRequest().getSession(true);
    }

    public static String getSelectedTenant() {
        return getValueInSession(AppConstants.HEADER_SELECTED_TENANT);
    }

    public static List<String> getCharacters() {
        return characters;
    }

    public static List<String> getSelectedTenantHasMaster() {
        String seletectTenantName = getSelectedTenant();
        List<String> seletectTenantNames = new ArrayList<>();
        seletectTenantNames.add(seletectTenantName != null ? seletectTenantName : "");
        seletectTenantNames.add(AppConstants.TENANT_MASTER);
        return seletectTenantNames;
    }
}