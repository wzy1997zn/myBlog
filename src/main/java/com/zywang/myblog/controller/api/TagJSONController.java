package com.zywang.myblog.controller.api;

import com.zywang.myblog.service.CategoryService;
import com.zywang.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TagJSONController {

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/tags")
    public Map<String, Object> tags() {
        Map<String, Object> json = new HashMap<>();
        json.put("success", true);
        json.put("results", tagService.listAllTags());
        return json;
    }
}
