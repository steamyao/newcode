package com.steamyao.newcode.asyn.handler;

import com.alibaba.fastjson.JSONObject;
import com.steamyao.newcode.Utils.JedisAdapter;
import com.steamyao.newcode.Utils.RedisKeyUtil;
import com.steamyao.newcode.asyn.EventHandler;
import com.steamyao.newcode.asyn.EventModel;
import com.steamyao.newcode.asyn.EventType;
import com.steamyao.newcode.dataobject.FeedDO;
import com.steamyao.newcode.dataobject.QuestionDO;
import com.steamyao.newcode.dataobject.UserDO;
import com.steamyao.newcode.model.EntityType;
import com.steamyao.newcode.service.FeedService;
import com.steamyao.newcode.service.FollowService;
import com.steamyao.newcode.service.QuestionService;
import com.steamyao.newcode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package com.steamyao.newcode.asyn.handler
 * @date 2019/8/9 10:26
 * @description  当我关注的人关注了问题 or 发表了评论 or 发表了问题，我将收到消息.
 */
@Service
public class FeedHandler implements EventHandler {

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private FeedService feedService;
    @Autowired
    private FollowService followService;
    @Autowired
    private JedisAdapter jedisAdapter;

    private String buildData(EventModel eventModel){
        Map<String,String> map = new HashMap<>();
        UserDO userDO = userService.getUserById(eventModel.getActorId());
        if(userDO==null){
            return null;
        }
        map.put("userName",userDO.getName());
        map.put("userId",userDO.getId().toString());
        map.put("userHead",userDO.getHeadUrl());

        //发表评论，关注问题，发表问题
        if(eventModel.getEventType()==EventType.COMMENT
                ||(eventModel.getEventType()==EventType.FOLLOW&&eventModel.getEntityType()== EntityType.ENTITY_QUESTION)
                || eventModel.getEventType()==EventType.ADD_QUESTION){
            QuestionDO question = questionService.selectById(eventModel.getEntityId());
            if(question == null){
                return null;
            }
            // 往map里装问题信息
            map.put("questionId", String.valueOf(question.getId()));
            map.put("questionTitle", question.getTitle());
            return JSONObject.toJSONString(map);
        }
        return null;
    }

    @Override
    public void doHandler(EventModel eventModel) {
        //构造一个新的Feed事件
        FeedDO feedDO = new FeedDO();
        feedDO.setCreatDate(new Date());
        feedDO.setType(eventModel.getEventType().getValue());
        feedDO.setUserId(eventModel.getActorId());
        feedDO.setData(buildData(eventModel));
        if(feedDO.getData() == null){
            //不支持的Feed 事件
            return;
        }
        feedService.addFeed(feedDO);
        //获取所有粉丝
        List<Integer> followers = followService.getFollowers(EntityType.ENTITY_USER, eventModel.getActorId(), Integer.MAX_VALUE);
        // 给未登录用户也推送
        followers.add(0);
        //给每个粉丝的feeds列表中添加此次Feed事件的Id
        for (Integer follower : followers) {
            String timelineKey = RedisKeyUtil.getTimelineKey(follower);
            jedisAdapter.lpush(timelineKey,String.valueOf(feedDO.getId()));
        }


    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(new EventType[]{EventType.FOLLOW,EventType.COMMENT,EventType.ADD_QUESTION});
    }
}
