package com.zywang.myblog.service;

import com.zywang.myblog.po.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Category saveCategory(Category category);

    Category getCategory(Long id);

    Page<Category> listCategories(Pageable pageable);

    Category updateCategory(Long id, Category category);

    void deleteCategory(Long id);
}
