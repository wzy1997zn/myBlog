package com.zywang.myblog.controller;

import com.zywang.myblog.exceptions.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
//        String blog = null;
//        if (blog == null) {
//            throw new NotFoundException("Blog not found.");
//        }
//        int i = 9/0;
        return "index";
    }

    @GetMapping("/details")
    public String details() {
        return "blog";
    }

    @GetMapping("/logTest/{id}/{name}")
    public String logTest(@PathVariable Integer id,@PathVariable  String name) {
        System.out.println("----------index-----------");
        return "index";
    }
}
