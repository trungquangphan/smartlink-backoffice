package ch.smartlink.backoffice.common.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class MessageUtil {

    private static MessageSource messageSource;

    static {
        messageSource = BeanUtil.getBean("messageSource");
    }

    public static String getMessage(String code, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        String result = messageSource.getMessage(code, args, locale);
        return result;
    }
}
