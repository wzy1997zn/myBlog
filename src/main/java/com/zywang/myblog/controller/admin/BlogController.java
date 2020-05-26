package com.zywang.myblog.controller.admin;

import com.zywang.myblog.po.Blog;
import com.zywang.myblog.service.BlogService;
import com.zywang.myblog.vo.BlogQuery;
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
public class BlogController {

//    private static final String INPUT = "";

    @Autowired
    private BlogService blogService;

    @GetMapping("/blogs")
    public String listBlogs(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
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
        model.addAttribute("blog", new Blog());
        return "admin/edit_blog";
    }
}
