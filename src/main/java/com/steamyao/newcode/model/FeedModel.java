package com.steamyao.newcode.model;


import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * @Package com.steamyao.newcode.model
 * @date 2019/8/9 9:34
 * @description
 */
public class FeedModel {

    private int id;
    private int type;
    private int userId;
    private Date creatDate;
    private String data;

    // 辅助变量
    private JSONObject dataJSON = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        dataJSON = JSONObject.parseObject(data);
    }

    public JSONObject getDataJSON() {
        return dataJSON;
    }

    public void setDataJSON(JSONObject dataJSON) {
        this.dataJSON = dataJSON;
    }
}
