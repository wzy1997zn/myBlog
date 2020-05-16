package com.zywang.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Only for test routing
 */
@Controller
public class TestController {

    @GetMapping("/details")
    public String details() {
        return "blog";
    }

    @GetMapping("/test")
    public String test() {
//        String blog = null;
//        if (blog == null) {
//            throw new NotFoundException("Blog not found.");
//        }
//        int i = 9/0;
        return "admin/index";
    }

    @GetMapping("/logTest/{id}/{name}")
    public String logTest(@PathVariable Integer id, @PathVariable  String name) {
        System.out.println("----------index-----------");
        return "index";
    }
}
