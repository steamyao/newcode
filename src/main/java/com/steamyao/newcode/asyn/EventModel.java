package com.steamyao.newcode.asyn;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package com.steamyao.newcode.asyn
 * @date 2019/8/8 10:16
 * @description
 */
public class EventModel {

    //事件类型
    private EventType eventType;
    //谁做的
    private int actorId;
    //事件对象
    private int entityType;
    private int entityId;
    //事件关联用户
    private int eventOwnerId;
    //扩展字段
    private Map<String,String> exts = new HashMap<String,String>();


    public EventModel() {
    }

    public EventModel(EventType eventType){
        this.eventType = eventType;
    }


    public EventModel setExts(String key, String value){
        this.exts.put(key,value);
        return this;
    }

    public String getExts(String key){
       return this.exts.get(key);
    }

    public EventType getEventType() {
        return eventType;
    }

    public EventModel setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
         return this;
    }

    public int getEventOwnerId() {
        return eventOwnerId;
    }

    public EventModel setEventOwnerId(int eventOwnerId) {
        this.eventOwnerId = eventOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public void setExts(Map<String, String> exts) {
        this.exts = exts;
    }
}
