package com.zywang.myblog.service;

import com.zywang.myblog.dao.CategoryRepository;
import com.zywang.myblog.exceptions.NotFoundException;
import com.zywang.myblog.po.Category;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Category> listCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public List<Category> listTopCategories(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0,size, sort);
        return categoryRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public List<Category> listAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    @Override
    public Category updateCategory(Long id, Category category) {
        Category c = categoryRepository.findById(id).orElse(null);
        if (c == null) {
            throw new NotFoundException("No such category");
        }
        BeanUtils.copyProperties(category, c);
        return categoryRepository.save(c);
    }

    @Transactional
    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
