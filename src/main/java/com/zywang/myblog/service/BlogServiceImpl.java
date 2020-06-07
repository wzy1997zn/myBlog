package com.zywang.myblog.service;

import com.zywang.myblog.dao.BlogRepository;
import com.zywang.myblog.exceptions.NotFoundException;
import com.zywang.myblog.po.Blog;
import com.zywang.myblog.po.Category;
import com.zywang.myblog.util.MarkdownUtil;
import com.zywang.myblog.util.MyBeanUtil;
import com.zywang.myblog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null) {
            throw new NotFoundException("No such blog");
        }
        blogRepository.updateViews(id);
        Blog tempBlog = new Blog();
        BeanUtils.copyProperties(blog, tempBlog);
        String content = blog.getContent();
        String htmlContent = MarkdownUtil.md2htmlExtension(content);
        tempBlog.setContent(htmlContent);
        //set content directly to blog may cause DB operations and change the data
        return tempBlog;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blogExample) {
        //repository extends JpaSpecificationExecutor have such method to help advanced query
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                /*
                root:           for getting the attribute of the PO "WHAT"
                criteriaQuery:  for generating the criteria    like "WHERE" in SQL
                criteriaBuilder:for describing how to query         "HOW"
                 */

                if (blogExample == null) {
                    return null;
                }
                List<Predicate> predicates = new ArrayList<>();
                //title like "%xxx"
                if (!"".equals(blogExample.getTitle()) && blogExample.getTitle() != null) {
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blogExample.getTitle() + "%"));
                }
                //category = "xxx"
                if (blogExample.getCategoryId() != null) {
                    predicates.add(criteriaBuilder.equal(root.<Category>get("category").get("id"), blogExample.getCategoryId()));
                }
                //is_recommended = "xxx"
                if (blogExample.isRecommended()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("isRecommended"), blogExample.isRecommended()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, String query) {
        return blogRepository.findQuery(query,pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, Long tagId) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public List<Blog> listRecommendTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);

        return blogRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> historyBlog() {
        List<String> years = blogRepository.findYears();
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        for (String year: years) {
            map.put(year, blogRepository.findByYear(year));
        }
        return map;
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        blog.setCreatedTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blog.setDescription(MarkdownUtil.generateDescription(blog.getContent()));
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {

        blog.setUpdateTime(new Date());
        Blog b = getBlog(id);

        if (b == null) {
            throw new NotFoundException("No such blog");
        }

//        blog.setCreatedTime(b.getCreatedTime());
//        blog.setViews(b.getViews());
        blog.setDescription(MarkdownUtil.generateDescription(blog.getContent()));
        //ignore properties with null value
        BeanUtils.copyProperties(blog,b, MyBeanUtil.getNullPropertyNames(blog));
        return blogRepository.save(b);
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
