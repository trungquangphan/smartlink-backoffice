package ch.smartlink.backoffice.pcs.web.controller;

import ch.smartlink.backoffice.pcs.exception.PCSException;
import ch.smartlink.core.log.LOG;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by doomphantom on 22/01/2016.
 */
@Controller
@ControllerAdvice(basePackages = {"ch.smartlink.backoffice.pcs.web.controller"})
public class PCSErrorHandlingController {
    @ExceptionHandler(PCSException.class)
    public Object proccessPCSException(PCSException ex, HttpServletRequest request) {
        LOG.error(ex.getMessage(), ex);
        return "error/500";
    }
}
