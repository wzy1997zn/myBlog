package com.zywang.myblog.service;

import com.zywang.myblog.po.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    Category saveCategory(Category category);

    Category getCategory(Long id);

    Category getCategoryByName(String name);

    Page<Category> listCategories(Pageable pageable);

    List<Category> listTopCategories(Integer size);

    List<Category> listAllCategories();

    Category updateCategory(Long id, Category category);

    void deleteCategory(Long id);
}
