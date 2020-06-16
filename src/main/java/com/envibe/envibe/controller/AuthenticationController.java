package com.envibe.envibe.controller;

import javax.servlet.http.HttpServletRequest;
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

/**
 * Handles all endpoints related to authentication and session modification.
 *
 * @author ARMmaster17
 */
@Controller
public class AuthenticationController {

    /**
     * Injected link to our own implementation of the Spring Security authentication service. See {@link com.envibe.envibe.service.UserAuthService}.
     */
    @Autowired
    UserRegistrationService userRegistrationService;

    /**
     * Render endpoint for the login page.
     * @param model Object used to pass parameters to Thymeleaf template.
     * @param error List of errors carried over from previously failed POST request.
     * @param logout Flag raised when user is redirected from a successful session invalidation at /logout. See {@link AuthenticationController#logout(Model, HttpServletRequest)}.
     * @return Reference to the login.html template.
     */
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

    /**
     * Endpoint that handles session invalidation.
     * @param model Object used to pass parameters to Thymeleaf template.
     * @param request Proxy object to manipulate cookies and other session data.
     * @return Redirects to /login with the logout flag. See {@link AuthenticationController#login(Model, String, String)}.
     */
    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request) {
        // Invalidate the user session and their cookies through the Spring Security framework.
        request.getSession().invalidate();
        // Redirect to login page.
        return "redirect:/login?logout";
    }

    /**
     * Render endpoint for the registration page.
     * @param model Object used to pass parameters to Thymeleaf template.
     * @return Reference to the register.html template.
     */
    @GetMapping("/register")
    public String registration(Model model) {
        // Return register template.
        // TODO: Forward any validation errors from POST:/register.
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * Endpoint that handles user validation and registration.
     * @param userDto Data transfer object that contains the raw new user data.
     * @param request Proxy object to manipulate cookies and other session data.
     * @param errors List of errors carried over from previously failed POST request.
     * @return Redirect to /login on success, otherwise back to /register with error flag (not yet implemented).
     * @see UserRegistrationService#registerNewUserAccount(User)
     */
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
