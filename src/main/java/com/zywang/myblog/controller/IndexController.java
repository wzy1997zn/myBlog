package com.zywang.myblog.controller;

import com.zywang.myblog.exceptions.NotFoundException;
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

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, null));
        model.addAttribute("categories", categoryService.listTopCategories(6));
        model.addAttribute("tags", tagService.listTopTags(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendTop(8));
        return "index";
    }

}
