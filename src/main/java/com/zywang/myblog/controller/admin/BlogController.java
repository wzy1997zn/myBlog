package com.zywang.myblog.controller.admin;

import com.zywang.myblog.po.Blog;
import com.zywang.myblog.po.User;
import com.zywang.myblog.service.BlogService;
import com.zywang.myblog.service.CategoryService;
import com.zywang.myblog.service.TagService;
import com.zywang.myblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class BlogController {

//    private static final String INPUT = "";

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String listBlogs(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        getCategoriesAndTags(model);
        model.addAttribute("page", blogService.listBlog(pageable,blog));
        return "admin/manage_blog";
    }

    @PostMapping("/blogs/search")
    public String searchBlogs(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable,blog));
        return "admin/manage_blog :: blogList"; // return this thymeleaf fragment
    }

    @GetMapping("blog/input")
    public String input(Model model) {
        getCategoriesAndTags(model);
        model.addAttribute("blog", new Blog());
        return "admin/edit_blog";
    }

    @GetMapping("blog/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        getCategoriesAndTags(model);
        Blog blog = blogService.getBlog(id);
        blog.initTagIds();
        model.addAttribute("blog", blog);
        return "admin/edit_blog";
    }

    @PostMapping("/blog")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {

        blog.setUser((User)session.getAttribute("user"));
        blog.setCategory(categoryService.getCategory(blog.getCategory().getId()));
        blog.setTags(tagService.listTags(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b = blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null) {
            // failed
            attributes.addFlashAttribute("message", "Option failed");
        } else {
            // success
            attributes.addFlashAttribute("message", "Succeed!");
        }
        return "redirect:/admin/blogs";
    }

    private void getCategoriesAndTags(Model model) {
        model.addAttribute("category_list", categoryService.listAllCategories());
        model.addAttribute("tag_list", tagService.listAllTags());
    }
}
