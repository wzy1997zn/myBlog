package com.zywang.myblog.controller.admin;

import com.zywang.myblog.po.Category;
import com.zywang.myblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
    public String input(Model model) {
        model.addAttribute("category", new Category());
        return "admin/edit_category";
    }
    @GetMapping("/category/{id}/input")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.getCategory(id));
        return "admin/edit_category";
    }

    @PostMapping("/category")
    public String post(@Valid Category category, BindingResult result, RedirectAttributes attributes) {
        Category category1 = categoryService.getCategoryByName(category.getName());
        if (category1 != null) {
            // duplicated
            result.rejectValue("name", "nameError", category.getName() + " already exists.");
            // define reject error: related field, error name, message.
        }
        if (result.hasErrors()) {
            return "admin/edit_category";
        }
        Category c = categoryService.saveCategory(category);
        if (c == null) {
            // failed
            attributes.addFlashAttribute("message", "Option failed");
        } else {
            // success
            attributes.addFlashAttribute("message", "Succeed!");
        }
        return "redirect:/admin/categories";
    }

    @PostMapping("/category/{id}")
    public String post(@Valid Category category, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        Category category1 = categoryService.getCategoryByName(category.getName());
        if (category1 != null) {
            // duplicated
            result.rejectValue("name", "nameError", category.getName() + " already exists.");
            // define reject error: related field, error name, message.
        }
        if (result.hasErrors()) {
            return "admin/edit_category";
        }
        Category c = categoryService.updateCategory(id, category);
        if (c == null) {
            // failed
            attributes.addFlashAttribute("message", "Option failed");
        } else {
            // success
            attributes.addFlashAttribute("message", "Succeed!");
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("/category/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        categoryService.deleteCategory(id);
        attributes.addFlashAttribute("message", "Succeed!");
        return "redirect:/admin/categories";
    }
}
