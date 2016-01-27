package ch.smartlink.backoffice.account.web.controller;

import ch.smartlink.core.log.LOG;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by doomphantom on 22/01/2016.
 */
@Controller
@ControllerAdvice(basePackages = {"ch.smartlink.backoffice.account.web.controller"})
public class BOAccountErrorHandlingController {
    @ExceptionHandler(Exception.class)
    public Object proccessGlobalException(Exception ex, HttpServletRequest request) {
        LOG.error(ex.getMessage(), ex);
        return "error/500";
    }
}
