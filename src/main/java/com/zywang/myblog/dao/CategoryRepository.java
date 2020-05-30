package com.zywang.myblog.dao;

import com.zywang.myblog.po.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    @Query("select c from t_category c")
    List<Category> findTop(Pageable pageable);
}
