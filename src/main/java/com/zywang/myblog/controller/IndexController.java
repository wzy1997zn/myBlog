package com.zywang.myblog.controller;

import com.zywang.myblog.exceptions.NotFoundException;
import com.zywang.myblog.service.BlogService;
import com.zywang.myblog.service.CategoryService;
import com.zywang.myblog.service.CommentService;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, (BlogQuery) null));
        model.addAttribute("categories", categoryService.listTopCategories(6));
        model.addAttribute("tags", tagService.listTopTags(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendTop(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
        model.addAttribute("page", blogService.listBlog(pageable,"%"+query+"%"));
        model.addAttribute("query", query);
        return "search";
    }

    @PostMapping("/searchAjax")
    public String searchAjax(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
        model.addAttribute("page", blogService.listBlog(pageable,"%"+query+"%"));
        model.addAttribute("query", query);
        return "search :: blogList";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        model.addAttribute("comments", commentService.listFirstLevelCommentsByBlogId(id));

        return "blog";
    }

    @GetMapping("/footer/newBlogs")
    public String newBlogs(Model model) {
        model.addAttribute("newBlogs",blogService.listRecommendTop(3));
        return "_fragments :: newBlogList";
    }

}
