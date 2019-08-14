package com.steamyao.newcode.dataobject;

import java.util.Date;

public class MessageDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message.id
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    private Integer id;

    //0表示未读 1表示已读
    private Integer hasRead;

    public Integer getHasRead() {
        return hasRead;
    }

    public void setHasRead(Integer hasRead) {
        this.hasRead = hasRead;
    }

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message.from_id
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    private Integer fromId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message.to_id
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    private Integer toId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message.content
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message.conversation_id
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
        private String conversationId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message.created_date
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    private Date createdDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message.id
     *
     * @return the value of message.id
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message.id
     *
     * @param id the value for message.id
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message.from_id
     *
     * @return the value of message.from_id
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    public Integer getFromId() {
        return fromId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message.from_id
     *
     * @param fromId the value for message.from_id
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message.to_id
     *
     * @return the value of message.to_id
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    public Integer getToId() {
        return toId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message.to_id
     *
     * @param toId the value for message.to_id
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    public void setToId(Integer toId) {
        this.toId = toId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message.content
     *
     * @return the value of message.content
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message.content
     *
     * @param content the value for message.content
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }


    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message.created_date
     *
     * @return the value of message.created_date
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message.created_date
     *
     * @param createdDate the value for message.created_date
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}