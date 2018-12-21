package cn.service.modules.dailyWords.model;

import java.io.Serializable;
import java.util.Date;

public class DailyWords implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;

    private Integer wordsId;

    private Date showTime;

    private String words;

    private Date createDate;

    private Integer createBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getShowTime() {
        return showTime;
    }

    public void setShowTime(Date showTime) {
        this.showTime = showTime;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words == null ? null : words.trim();
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

    public Integer getWordsId() {
        return wordsId;
    }

    public void setWordsId(Integer wordsId) {
        this.wordsId = wordsId;
    }
}