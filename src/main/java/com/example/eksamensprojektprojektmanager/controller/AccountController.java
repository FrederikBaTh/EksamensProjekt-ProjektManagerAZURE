package com.example.eksamensprojektprojektmanager.controller;

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
            String user_Id = accountService.getUserIdByUsername(username);
            request.getSession().setAttribute("user_Id", user_Id);
            return "redirect:/project";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid login");
            return "redirect:/login";
        }
    }
}
