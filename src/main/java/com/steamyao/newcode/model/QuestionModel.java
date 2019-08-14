package com.steamyao.newcode.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @Package com.steamyao.newcode.model
 * @date 2019/8/12 8:09
 * @description
 */
//indexName代表索引名称,type代表表名称
@Document(indexName = "newcode", type = "question")
public class QuestionModel {

    @Id
    private Long id;

    private String content;

    private String title;

    private Integer user_id;

    //格式 ： yyyy-MM-dd HH:mm:ss
    private String creat_date;

    private Integer comment_count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getCreat_date() {
        return creat_date;
    }

    public void setCreat_date(String creat_date) {
        this.creat_date = creat_date;
    }

    public Integer getComment_count() {
        return comment_count;
    }

    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }
}
