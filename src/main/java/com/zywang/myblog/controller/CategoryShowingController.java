package com.zywang.myblog.controller;

import com.zywang.myblog.po.Category;
import com.zywang.myblog.service.BlogService;
import com.zywang.myblog.service.CategoryService;
import com.zywang.myblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CategoryShowingController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/categories/{id}")
    public String categories(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                             @PathVariable Long id, Model model) {
        List<Category> categories = categoryService.listTopCategories(10000);
        if (id == -1) {
            id = categories.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setCategoryId(id);
        model.addAttribute("categories",categories);
        model.addAttribute("page",blogService.listBlog(pageable, blogQuery));
        model.addAttribute("activeCategoryId",id);
        return "categories";
    }
}
