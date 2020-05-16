package com.zywang.myblog.controller.admin;

import com.zywang.myblog.po.User;
import com.zywang.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {


    private final UserService userService;

    @Autowired
    public LoginController(UserService service) {
        userService = service;
    }

    @GetMapping()
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username,password); // check there is the user
        if (user != null) {
            user.setPassword(null); // for safety
            session.setAttribute("user", user); //remember this user
            return "admin/index"; // go to the success page
        } else {
            attributes.addFlashAttribute("message", "Wrong email or password"); // redirect with message
            // Model can not be used for redirect because redirect is actually another request.
            // All data in first request on server will lost.
            return "redirect:/admin"; //go back to login page
        }
    }

    @GetMapping("/login")
    public String loginWithoutForm() {
        return "redirect:/admin"; //go back to login page
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user"); // clear session
        return "redirect:/admin";
    }
}
