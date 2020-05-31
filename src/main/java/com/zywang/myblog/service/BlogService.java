package com.zywang.myblog.service;

import com.zywang.myblog.po.Blog;
import com.zywang.myblog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {
    Blog getBlog(Long id);

    /**
     * Generate a dynamic query with blog example containing properties
     * @param pageable for split page
     * @param blogExample containing properties
     * @return A page of query result
     */
    Page<Blog> listBlog(Pageable pageable, BlogQuery blogExample);

    List<Blog> listRecommendTop(Integer size);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);
}
