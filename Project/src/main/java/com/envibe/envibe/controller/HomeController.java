package com.envibe.envibe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Handles all front-end information pages (e.g. home page, about us, etc...).
 *
 * @author ARMmaster17
 */
@Controller
public class HomeController {

    /**
     * Render endpoint for the front page.
     * @param model Object used to pass parameters to Thymeleaf template.
     * @return Reference to the index.html template.
     */
    @GetMapping("/")
    public String index(Model model) {
        // Return index.html template.
        return "index";
    }

    /**
     * Render endpoint for a sample authenticated endpoint.
     * @param model Object used to pass parameters to Thymeleaf template.
     * @return Reference to the restricted.html template.
     * @deprecated For testing purposes only. Will be removed in the future when more authenticated pages are available.
     */
    @GetMapping("/restricted")
    public String restricted(Model model) {
        return "restricted";
    }
}
