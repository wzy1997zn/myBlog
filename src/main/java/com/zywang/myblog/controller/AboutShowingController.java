package com.zywang.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutShowingController {
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
