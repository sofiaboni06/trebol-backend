package com.trebol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaController {

    @RequestMapping({"/usuarios", "/usuarios/**"})
    public String forwardUsuarios() {
        // Forward to index.html so the SPA can handle the route
        return "forward:/index.html";
    }
}
