package cn.service.modules.dailyWords.model;

import java.util.Date;

public class ChickenSoupWords {
    private Integer id;

    private String content;

    private String sourceWebsite;

    private String sourceWebsiteUrl;

    private Date createDate;

    private Integer createBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getSourceWebsite() {
        return sourceWebsite;
    }

    public void setSourceWebsite(String sourceWebsite) {
        this.sourceWebsite = sourceWebsite == null ? null : sourceWebsite.trim();
    }

    public String getSourceWebsiteUrl() {
        return sourceWebsiteUrl;
    }

    public void setSourceWebsiteUrl(String sourceWebsiteUrl) {
        this.sourceWebsiteUrl = sourceWebsiteUrl == null ? null : sourceWebsiteUrl.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }
}