package com.envibe.envibe.controller;

import com.envibe.envibe.dao.UserDao;
import com.envibe.envibe.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles pages regarding user information and edits made to their account details.
 *
 * @author ARMmaster17
 */
@Controller
public class ManageAcctController {

    /**
     * Injected data access object to run User table queries against.
     */
    @Autowired
    UserDao userDao;

    /**
     * Injected globally-configured password hashing service.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Render endpoint for the front page.
     * @param model Object used to pass parameters to Thymeleaf template.
     * @param request Proxy object to manipulate cookies and other session data.
     * @return Reference to the ManageAccount.html template with user data injected in.
     */
    @GetMapping("/ManageAccount")
    public String showAccountChanges(Model model, HttpServletRequest request) {
        // Get all the information about the currently logged-in user.
        User currentUser = userDao.read(request.getRemoteUser());
        // Strip out the hashed password.
        currentUser.setPassword("");
        model.addAttribute("user", currentUser);
        // Return ManageAccount.html template.
        return "ManageAccount";
    }

    /**
     * Handles the updating of new user data (if anything was changed). Relevant issues are forwarded to the rendered template.
     * @param userDto User information passed from the form.
     * @param model Object used to pass parameters to Thymeleaf template.
     * @param request Proxy object to manipulate cookies and other session data.
     * @return Reference to the ManageAccount.html template with user data injected in and relevant messages from the backend.
     */
    @PostMapping("/manageaccount")
    public String processAccountChanges(@ModelAttribute User userDto, Model model, HttpServletRequest request) {
        // Retrieve the original account details for comparison.
        User oldDetails = userDao.read(request.getRemoteUser());
        // Check if the user's password has been updated.
        if(userDto.getPassword() != "") {
            // A new password was entered, hash it.
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        } else {
            // No new password, import the hash from the original account data.
            userDto.setPassword(oldDetails.getPassword());
        }
        // Ensure that constraint/unique fields can't be tampered with.
        userDto.setUsername(oldDetails.getUsername());
        userDto.setRole(oldDetails.getRole());
        // Save it, passing on relevant messages to the frontend.
        try {
            userDao.update(userDto);
            model.addAttribute("msg", "Account updated successfully");
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred. Check your information and try again.");
        }
        // Strip out the password hash before we send the form data back.
        userDto.setPassword("");
        // Render the original account management page.
        model.addAttribute("user", userDto);
        return "ManageAccount";
    }


}