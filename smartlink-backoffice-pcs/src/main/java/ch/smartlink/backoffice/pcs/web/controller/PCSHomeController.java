package ch.smartlink.backoffice.pcs.web.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/pcs")
@Controller
public class PCSHomeController {
    @RequestMapping
    @Secured("ROLE_%s_USER")
    public String index() {
        return "smartlink-backoffice-pcs/index";
    }
}
