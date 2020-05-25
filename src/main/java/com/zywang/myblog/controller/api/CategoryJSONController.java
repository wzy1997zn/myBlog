package com.zywang.myblog.controller.api;

import com.zywang.myblog.po.Category;
import com.zywang.myblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CategoryJSONController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/categories")
    public Map<String, Object> categories() {
        Map<String, Object> json = new HashMap<>();
        json.put("success", true);
        json.put("results", categoryService.listAllCategories());
        return json;
    }
}
