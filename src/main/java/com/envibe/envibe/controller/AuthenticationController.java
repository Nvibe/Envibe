package com.envibe.envibe.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.envibe.envibe.dao.UserDao;
import com.envibe.envibe.exception.UserAlreadyExistsException;
import com.envibe.envibe.model.User;
import com.envibe.envibe.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
public class AuthenticationController {

    @Autowired
    UserRegistrationService userRegistrationService;

    // Primary login page.
    // PARAMETERS: Boolean:error(optional) Boolean:logout(optional)
    // PATH: GET:/login
    // TEMPLATE: /resources/templates/login.html
    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout) {
        // Check if we were redirected from POST:/login with an error.
        if (error != null) {
            model.addAttribute("error", "Invalid Credentials");
        }
        // Check if we were redirected from GET:/logout.
        if (logout != null) {
            model.addAttribute("msg", "You have been successfully logged out");
        }
        // Return the login.html template.
        return "login";
    }

    // Logout function endpoint.
    // PARAMETERS: None
    // PATH: GET:/logout
    // TEMPLATE: None
    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request) {
        // Invalidate the user session and their cookies through the Spring Security framework.
        request.getSession().invalidate();
        // Redirect to login page.
        return "redirect:/login?logout";
    }

    // Registration page.
    // PARAMETERS: None
    // PATH: GET:/register
    // TEMPLATE: /resources/templates/register.html
    @GetMapping("/register")
    public String registration(Model model) {
        // Return register template.
        // TODO: Forward any validation errors from POST:/register.
        model.addAttribute("user", new User());
        return "register";
    }

    // Registration function endpoint.
    // PARAMETERS: Array<Object>:user
    // PATH: POST:/register
    // TEMPLATE: None
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User userDto, HttpServletRequest request, Errors errors) {
        // Manually assign the role of the user.
        userDto.setRole("ROLE_USER");
        // Push new account details to the user service and attempt to save it.
        try {
            userRegistrationService.registerNewUserAccount(userDto);
        } catch (UserAlreadyExistsException e) {
            // Validation error occured.
            // TODO: Pass a message to the front-end.
            return "redirect:/register";
        }
        // Redirect user to login page on save success.
        return "redirect:/login";
    }
}
