package com.steamyao.newcode.dataobject;

import java.util.Date;

public class CommentDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.id
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.content
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.user_id
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.creat_date
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    private Date creatDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.entity_id
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    private Integer entityId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.entity_type
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    private Integer entityType;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.id
     *
     * @return the value of comment.id
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.id
     *
     * @param id the value for comment.id
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.content
     *
     * @return the value of comment.content
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.content
     *
     * @param content the value for comment.content
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.user_id
     *
     * @return the value of comment.user_id
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.user_id
     *
     * @param userId the value for comment.user_id
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.creat_date
     *
     * @return the value of comment.creat_date
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    public Date getCreatDate() {
        return creatDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.creat_date
     *
     * @param creatDate the value for comment.creat_date
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.entity_id
     *
     * @return the value of comment.entity_id
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    public Integer getEntityId() {
        return entityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.entity_id
     *
     * @param entityId the value for comment.entity_id
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }
}