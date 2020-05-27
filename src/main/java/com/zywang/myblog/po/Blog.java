package com.zywang.myblog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "t_blog")
public class Blog {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @Basic(fetch = FetchType.LAZY)
    @Lob // for long text
    private String content;
    private String coverPicture;
    private String flag; //origin,reproduce...
    private Integer views;
    private boolean canAppreciation;
    private boolean hasCopyrightStatement;
    private boolean canComment;
    private boolean isPublished;
    private boolean isRecommended;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Transient // will not appear in database
    private String tagIds;

    @ManyToOne
    private Category category;

    @ManyToMany(cascade = CascadeType.PERSIST) // can add new tag when new blog use new tag
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    public Blog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isCanAppreciation() {
        return canAppreciation;
    }

    public void setCanAppreciation(boolean canAppreciation) {
        this.canAppreciation = canAppreciation;
    }

    public boolean isHasCopyrightStatement() {
        return hasCopyrightStatement;
    }

    public void setHasCopyrightStatement(boolean hasCopyrightStatement) {
        this.hasCopyrightStatement = hasCopyrightStatement;
    }

    public boolean isCanComment() {
        return canComment;
    }

    public void setCanComment(boolean canComment) {
        this.canComment = canComment;
    }

    public boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(boolean published) {
        isPublished = published;
    }

    public boolean getIsRecommended() {
        return isRecommended;
    }

    public void setIsRecommended(boolean recommended) {
        isRecommended = recommended;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", coverPicture='" + coverPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", canAppreciation=" + canAppreciation +
                ", hasCopyrightStatement=" + hasCopyrightStatement +
                ", canComment=" + canComment +
                ", isPublished=" + isPublished +
                ", isRecommended=" + isRecommended +
                ", createdTime=" + createdTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
