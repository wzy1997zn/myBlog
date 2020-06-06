package com.zywang.myblog.service;

import com.zywang.myblog.dao.CommentRepository;
import com.zywang.myblog.po.Comment;
import com.zywang.myblog.util.PlaceholderImgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");
        return commentRepository.findByBlogId(blogId, sort);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            Comment parentComment = commentRepository.findById(parentCommentId).orElse(null);
            if (parentComment != null) {
                if (parentComment.getAncestorComment() == null) {
                    comment.setAncestorComment(parentComment);
                } else {
                    comment.setAncestorComment(parentComment.getAncestorComment());
                }
            }
            comment.setParentComment(parentComment);
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
//        comment.setAvatar(PlaceholderImgUtil.getRandomImg(100,100));

        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> listFirstLevelCommentsByBlogId(Long blogId) {
        return commentRepository.findByBlogIdAndAncestorCommentIsNull(blogId, Sort.by(Sort.Direction.ASC, "createTime"));
    }

    @Override
    public List<Comment> listDerivativeCommentByBlogIdAndAncestorCommentId(Long blogId, Long ancestorCommentId) {
        return commentRepository.findByBlogIdAndAncestorCommentId(blogId,ancestorCommentId,Sort.by(Sort.Direction.DESC, "createTime"));
    }

}
