package com.zywang.myblog.controller.admin;

import com.zywang.myblog.po.Category;
import com.zywang.myblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String listCategories(@PageableDefault(size = 2, sort = {"id"},direction = Sort.Direction.DESC)
                                             Pageable pageable, Model model) {
        model.addAttribute("page", categoryService.listCategories(pageable));
        return "admin/manage_category";
    }

    @GetMapping("/category/input")
    public String input() {
        return "admin/edit_category";
    }

    @PostMapping("/category")
    public String post(Category category) {
        Category c = categoryService.saveCategory(category);
        if (c == null) {
            // failed
        } else {
            // success
        }
        return "redirect:/admin/categories";
    }
}
