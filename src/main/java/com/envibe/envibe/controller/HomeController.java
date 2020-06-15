package com.envibe.envibe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    // Landing page.
    // PARAMETERS: None
    // PATH: GET:/
    // TEMPLATE: /resources/templates/index.html
    @GetMapping("/")
    public String index(Model model) {
        // Return index.html template.
        return "index";
    }

    // Test endpoint for authentication code.
    // PARAMETERS: None
    // PATH: GET:/restricted
    // TEMPLATE: /resources/templates/index.html
    @GetMapping("/restricted")
    public String restricted(Model model) {
        return "restricted";
    }
}
