package com.zywang.myblog.service;

import com.zywang.myblog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTags(Pageable pageable);

    List<Tag> listAllTags();

    Tag updateTag(Long id, Tag tag);

    void deleteTag(Long id);
}
