package com.zywang.myblog.controller;

import com.zywang.myblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HistoryShowingController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("historyMap",blogService.historyBlog());
        model.addAttribute("count",blogService.countBlog());
        return "history";
    }
}
