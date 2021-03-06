package com.zywang.myblog.service;

import com.zywang.myblog.po.Blog;
import com.zywang.myblog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    /**
     * Generate a dynamic query with blog example containing properties
     * @param pageable for split page
     * @param blogExample containing properties
     * @return A page of query result
     */
    Page<Blog> listBlog(Pageable pageable, BlogQuery blogExample);

    Page<Blog> listBlog(Pageable pageable, String query);

    Page<Blog> listBlog(Pageable pageable, Long tagId);

    List<Blog> listRecommendTop(Integer size);

    Map<String, List<Blog>> historyBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    Long countBlog();

    void deleteBlog(Long id);
}
