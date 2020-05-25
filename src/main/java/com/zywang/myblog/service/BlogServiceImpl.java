package com.zywang.myblog.service;

import com.zywang.myblog.dao.BlogRepository;
import com.zywang.myblog.exceptions.NotFoundException;
import com.zywang.myblog.po.Blog;
import com.zywang.myblog.po.Category;
import com.zywang.myblog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElse(null);
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

                List<Predicate> predicates = new ArrayList<>();
                //title like "%xxx"
                if (!"".equals(blogExample.getTitle()) && blogExample.getTitle() != null) {
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blogExample.getTitle()));
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
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = getBlog(id);
        if (b == null) {
            throw new NotFoundException("No such blog");
        }
        BeanUtils.copyProperties(blog,b);
        return blogRepository.save(b);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}