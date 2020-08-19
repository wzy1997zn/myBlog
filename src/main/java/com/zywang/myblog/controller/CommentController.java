package com.zywang.myblog.controller;

import com.zywang.myblog.po.Blog;
import com.zywang.myblog.po.Comment;
import com.zywang.myblog.po.User;
import com.zywang.myblog.service.BlogService;
import com.zywang.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {

        model.addAttribute("comments", commentService.listFirstLevelCommentsByBlogId(blogId));
        return "blog :: commentDetails";
    }

    @PostMapping("/comment")
    public String post(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        Blog b = blogService.getBlog(blogId);
        User user = (User)session.getAttribute("user");
        if (user != null && user.getId().equals(b.getUser().getId())) {
            comment.setAvatar(user.getAvatar());
            comment.setByAuthor(true);
//            comment.setNickname(user.getNickname());
        } else {
            comment.setByAuthor(false);
            comment.setAvatar(avatar);
        }
        comment.setBlog(b);

        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
