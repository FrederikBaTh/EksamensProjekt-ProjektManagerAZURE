package com.example.eksamensprojektprojektmanager.controller;

import org.springframework.ui.Model;
import com.example.eksamensprojektprojektmanager.model.Account;
import com.example.eksamensprojektprojektmanager.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private AccountService accountService;


    @GetMapping("/")
    public RedirectView redirectToLogin() {
        return new RedirectView("/login");
    }


    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }


    @PostMapping("/register")
    public String registerAccount(Account account, @RequestParam(value = "isAdmin", required = false) Boolean isAdmin) {
        if (isAdmin == null) {
            isAdmin = false;
        }
        account.setAdmin(isAdmin);

        if (account.getName() == null) {
            account.setName("");
        }
        if (account.getCompany() == null) {
            account.setCompany("");
        }
        if (account.getJobTitle() == null) {
            account.setJobTitle("");
        }
        if (account.getDescription() == null) {
            account.setDescription("");
        }

        accountService.saveAccount(account);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
        if (accountService.isValidUser(username, password)) {
            String userId = accountService.getUserIdByUsername(username); // Retrieve user ID
            request.getSession().setAttribute("userId", userId); // Set user ID in session
            return "redirect:/seeProjects"; // Redirect to projects page after successful login
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid login");
            return "redirect:/login";
        }
    }

    @GetMapping("/usersList")
    public String showUserList(Model model) {
        List<Account> userList = accountService.getAllUsers(); // Assuming you have a method to retrieve all users
        model.addAttribute("users", userList);
        return "userList";
    }

    @GetMapping("/myAccount")
    public String showMyAccount(Model model, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        if (userId != null) {
            Account user = accountService.getUserById(Long.valueOf(userId));
            if (user != null) {
                model.addAttribute("user", user);
                return "myAccount";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/editAccount")
    public String showEditAccount(Model model, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        if (userId != null) {
            Account user = accountService.getUserById(Long.valueOf(userId));
            if (user != null) {
                model.addAttribute("user", user);
                return "editAccount";
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/editUser")
    public String editUser(@RequestParam("userId") Long userId,
                           @RequestParam("name") String name,
                           @RequestParam("company") String company,
                           @RequestParam("jobTitle") String jobTitle,
                           @RequestParam("description") String description,
                           RedirectAttributes redirectAttributes) {

        Account user = accountService.getUserById(userId);
        if (user != null) {
            user.setName(name);
            user.setCompany(company);
            user.setJobTitle(jobTitle);
            user.setDescription(description);

            accountService.updateAccount(user);

            return "redirect:/myAccount"; // Redirect to myAccount page after editing
        } else {
            // User not found, redirect to login page or handle the error accordingly
            return "redirect:/login";
        }
    }
}
