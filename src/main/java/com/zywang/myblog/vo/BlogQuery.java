package com.zywang.myblog.vo;

public class BlogQuery {

    private String title;
    private Long categoryId;
    private boolean isRecommended;
    private boolean isPublished;

    public BlogQuery() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isRecommended() {
        return isRecommended;
    }

    public void setIsRecommended(boolean recommended) {
        isRecommended = recommended;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setIsPublished(boolean isPublished) {
        this.isPublished = isPublished;
    }
}
