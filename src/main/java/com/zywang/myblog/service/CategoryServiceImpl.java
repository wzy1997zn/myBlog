package com.zywang.myblog.service;

import com.zywang.myblog.dao.CategoryRepository;
import com.zywang.myblog.exceptions.NotFoundException;
import com.zywang.myblog.po.Category;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
    public Page<Category> listCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
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
