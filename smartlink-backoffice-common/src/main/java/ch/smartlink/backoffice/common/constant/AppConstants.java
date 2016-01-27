package ch.smartlink.backoffice.common.constant;

import java.util.Arrays;
import java.util.List;

public interface AppConstants {

	String HEADER_SELECTED_TENANT = "SelectedTenant";
	String PERMISSION_ALL = "ROLE_PERMISSION_ALL";
	String TENANT_MASTER = "MASTER";
	String SESSION_KEY_TENANT_LIST = "listTenantOfCurrentUser";
	String SESSION_KEY_TENANT_AND_MASTER_LIST = "listTenantOfCurrentUserHasMaster";
	String SESSION_KEY_TENANT_MORE_THAN_ONE = "currentUserHasMoreThanOneTenant";
	String SESSION_KEY_SELECTED_MENU = "SELECTED_MENU";
	String ROLE_ADMIN = "ADMIN";
	String SYMBOL_CONNECT = "_";
	String SYSBOL_SPACE = " ";
	String SYSBOL_SEPARATION = "; ";
	String TENANT_TEMPLATE = "%s";
	String MAIL_TEMPLATE_SEND_PASSWORD = "MailCreatePasswordTemplate.vm";
	String MAIL_TEMPLATE_RESET_PASSWORD = "MailResetPasswordTemplate.vm";
	String MAIL_TEMPLATE_NOTICE_SUBSTITUTE_KEY_REQUEST = "MailNoticeSubstituteKeyRequestTemplate.vm";
	String MAIL_TEMPLATE_NOTICE_SUBSTITUTE_KEY_REQUEST_RESULT_APPROVE = "MailNoticeSubstituteRequestResultApproveTemplate.vm";
	String MAIL_TEMPLATE_NOTICE_SUBSTITUTE_KEY_REQUEST_RESULT_REJECT = "MailNoticeSubstituteRequestResultRejectTemplate.vm";
	String MAIL_TEMPLATE_NOTICE_WMR_REFUND_TEMPLATE = "MailNoticeWMRRefundTemplate.vm";
	int DEFAULT_PASSWORD_LENGTH = 8;
	int TOTAL_RECORD_PER_PAGE = 10;
	// TABLE VIEW
	int LIMIT = 5;
	int VIEW_PAGE_COUNT = 5;

	String SORT_DIRECTOR = "ASC";
	String SORT_PROPERTY = "name";
	String SESSION_USER = "USER";
	String SESSION_KEY_TENANT_IS_MASTER = "tenantIsMaster";
	String RESET_PASSWORD_EXPIRE_IN_SECOND = "30";
	String MENU_ADMINISTRATOR = "Administrator";
	String MENU_COMPLIANCE = "Compliance";
	String MENU_ACCOUNT = "Account";
	String HEADER_KEY_REQUEST_STATUS_FAILURE = "RequestStatus";
	String DILIMITER = "//";
	int EXPIRED_DAY_PASSWORD = 90;
	int LIMIT_TIME_LOCKED_USER_IN_MINUTE = 30;
	int MAX_ATTEMP_LOGIN = 6;
	String COMMON_PASSWORD_FILE_LOCATION = "/usr/local/smartlink/backoffice/most_common_password.txt";
	String SUBSTITUTE_KEY_REQUEST_PENDING = "Pending";

	Integer[] RSA_KEY_SIZES = new Integer[] { 2048, 4096 };
	Integer[] AES_KEY_SIZES = new Integer[]{128,192,256};
	int DEFAULT_KEY_SIZE = 2048;
	int DEFAULT_YEAR_VALID = 1;

	String JOB_LAUCHER_PATH = "/job/joblauncher";
	String JOB_INFORMATION_PATH = "/job/getJobInformation";
	String CHANGE_KEY_JOB = "changeKeyJob";
	String NOT_AVAILABLE = "Not Avaiable";
	String SLASH = "/";
	String NA = "N/A";
	public static final List<String> FILE_CONTENT_TYPE = Arrays
			.asList(new String[]{"text/csv", "application/vnd.ms-excel"});
	public static char CSV_SPIT_BY = ',';

	public static char CSV_TEXT_DETIMITER = '"';
	public static final String[] CSV_PHONE_MAPPING = { "phoneNumber" };
	public static final String PLUS = "+";
	public final static String CARD_FONT = "front";
	public final static String CARD_BACK = "back";
	public static final String IMAGE_DIR = "/pub/tc-kycserver/images";
	public final static String DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";

    String OS_ANDROID = "ANDROID";
    String OS_IOS = "iOS";
}
