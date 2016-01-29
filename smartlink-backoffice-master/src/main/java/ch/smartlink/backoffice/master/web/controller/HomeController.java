package ch.smartlink.backoffice.master.web.controller;

import ch.smartlink.backoffice.master.util.BOSecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {


    @RequestMapping("/")

    public String home() throws Exception {
        return "index";
    }


    @RequestMapping("/login")
    public String login() {
        if (BOSecurityUtil.isAuthenticated()) {
            return "redirect:/";
        }
        return "login";
    }

}
