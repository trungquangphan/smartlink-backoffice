package ch.smartlink.backoffice.account.util;


import ch.smartlink.backoffice.account.business.dto.UserDto;
import ch.smartlink.backoffice.common.constant.AppConstants;
import ch.smartlink.backoffice.common.util.SessionUtil;

import java.util.ArrayList;
import java.util.List;

public class AccountUtil {
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
        return (UserDto) SessionUtil.get(AppConstants.SESSION_USER);
    }

    public static boolean checkLoginUserIsMaster() {
        return (boolean) SessionUtil.get(AppConstants.SESSION_KEY_TENANT_IS_MASTER);
    }


    public static String getSelectedTenant() {
        return (String) SessionUtil.get(AppConstants.HEADER_SELECTED_TENANT);
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