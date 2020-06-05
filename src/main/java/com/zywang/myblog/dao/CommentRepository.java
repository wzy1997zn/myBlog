package com.zywang.myblog.dao;

import com.zywang.myblog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBlogId(Long blogId, Sort sort);

    List<Comment> findByBlogIdAndAncestorCommentIsNull(Long blogId, Sort sort);

    List<Comment> findByBlogIdAndAncestorCommentId(Long blogId, Long ancestorCommentId, Sort sort);
}
