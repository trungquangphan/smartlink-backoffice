package ch.smartlink.backoffice.pcs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/pcs")
@Controller
public class PCSHomeController {
    @RequestMapping
    public String index(){
        return "smartlink-backoffice-pcs/index";
    }
}
