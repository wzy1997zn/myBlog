package com.zywang.myblog.service;

import com.zywang.myblog.dao.BlogRepository;
import com.zywang.myblog.exceptions.NotFoundException;
import com.zywang.myblog.po.Blog;
import com.zywang.myblog.po.Category;
import com.zywang.myblog.util.DescriptionUtil;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
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
    public List<Blog> listRecommendTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);

        return blogRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        blog.setCreatedTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blog.setDescription(DescriptionUtil.generateDescription(blog.getContent()));
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
        blog.setDescription(DescriptionUtil.generateDescription(blog.getContent()));
        //ignore properties with null value
        BeanUtils.copyProperties(blog,b, MyBeanUtil.getNullPropertyNames(blog));
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
