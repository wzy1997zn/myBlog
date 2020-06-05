package com.zywang.myblog.service;

import com.zywang.myblog.po.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

    List<Comment> listFirstLevelCommentsByBlogId(Long blogId);

    List<Comment> listDerivativeCommentByBlogIdAndAncestorCommentId(Long blogId, Long ancestorCommentId);

}
