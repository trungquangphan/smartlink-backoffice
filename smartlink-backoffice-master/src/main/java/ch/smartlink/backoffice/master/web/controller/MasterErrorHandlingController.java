package ch.smartlink.backoffice.master.web.controller;

import ch.smartlink.core.log.LOG;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by doomphantom on 22/01/2016.
 */
@Controller
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class MasterErrorHandlingController {
    @ExceptionHandler(Exception.class)
    public Object proccessGlobalException(Exception ex, HttpServletRequest request) {
        LOG.error(ex.getMessage(), ex);
        return "error/500";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Object processNoHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest request) {
        LOG.info(ex.getMessage());
        return "error/page-not-found";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Object proccessAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        LOG.error("Access Denied when accessed to [{}]. Detail message: [{}]", request.getRequestURI(), ex.getMessage());
        return "error/access-denied";
    }
}
