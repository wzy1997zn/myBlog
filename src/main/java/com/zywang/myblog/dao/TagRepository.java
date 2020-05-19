package com.zywang.myblog.dao;

import com.zywang.myblog.po.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag , Long> {
    Tag findByName(String name);
}
