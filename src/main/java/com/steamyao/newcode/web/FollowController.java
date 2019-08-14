package com.steamyao.newcode.web;

import com.steamyao.newcode.Utils.ForumUtil;
import com.steamyao.newcode.asyn.EventModel;
import com.steamyao.newcode.asyn.EventProducer;
import com.steamyao.newcode.asyn.EventType;
import com.steamyao.newcode.dataobject.QuestionDO;
import com.steamyao.newcode.dataobject.UserDO;
import com.steamyao.newcode.model.EntityType;
import com.steamyao.newcode.model.HostHolder;
import com.steamyao.newcode.model.ViewObject;
import com.steamyao.newcode.service.CommentService;
import com.steamyao.newcode.service.FollowService;
import com.steamyao.newcode.service.QuestionService;
import com.steamyao.newcode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package com.steamyao.newcode.web
 * @date 2019/8/8 15:25
 * @description
 */
@Controller
public class FollowController {

    @Autowired
    private FollowService followService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private EventProducer eventProducer;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    @PostMapping(value = "/followUser")
    @ResponseBody
    public String followUser(int userId) { // userId是被关注的人的id
        if (hostHolder.getUser() == null) {
            return ForumUtil.getJsonString(999);
        }
        boolean ret = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId);
        // 发送异步事件
        eventProducer.putEvent(new EventModel(EventType.FOLLOW)
                .setActorId(hostHolder.getUser().getId()).setEntityId(userId)
                .setEntityType(EntityType.ENTITY_USER).setEventOwnerId(userId));
        // 返回关注的人数
        return ForumUtil.getJsonString(ret ? 0 : 1, String.valueOf(followService.getFolloweeCount(hostHolder.getUser().getId(), EntityType.ENTITY_USER)));

    }

    @PostMapping(value = "/unfollowUser")
    @ResponseBody
    public String unfollowUser(int userId) { // userId是被关注的人的id
        if (hostHolder.getUser() == null) {
            return ForumUtil.getJsonString(999);
        }
        boolean ret = followService.unfollow(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId);
        // 发送异步事件
        eventProducer.putEvent(new EventModel(EventType.UNFOLLOW)
                .setActorId(hostHolder.getUser().getId()).setEntityId(userId)
                .setEntityType(EntityType.ENTITY_USER).setEventOwnerId(userId));
        // 返回关注的人数
        return ForumUtil.getJsonString(ret ? 0 : 1, String.valueOf(followService.getFolloweeCount(hostHolder.getUser().getId(), EntityType.ENTITY_USER)));
    }

    @PostMapping(value = "/followQuestion")
    @ResponseBody
    public String followQuestion(int questionId) {
        if (hostHolder.getUser() == null) {
            return ForumUtil.getJsonString(999);
        }
        // 判断问题是否存在
        QuestionDO q = questionService.selectById(questionId);
        if (q == null) {
            return ForumUtil.getJsonString(1, "问题不存在");
        }
        boolean ret = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, questionId);
        // 发送异步事件
        eventProducer.putEvent(new EventModel(EventType.FOLLOW)
                .setActorId(hostHolder.getUser().getId()).setEntityId(questionId)
                .setEntityType(EntityType.ENTITY_QUESTION).setEventOwnerId(q.getUserId()));
        // 发送用户本人信息用于页面展示
        Map<String, Object> info = new HashMap<>();
        info.put("headUrl", hostHolder.getUser().getHeadUrl());
        info.put("name", hostHolder.getUser().getName());
        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.getFollowerCount(EntityType.ENTITY_QUESTION, questionId));
        return ForumUtil.getJsonString(ret ? 0 : 1, info);
    }

    @PostMapping(value = "/unfollowQuestion")
    @ResponseBody
    public String unfollowQuestion(int questionId) {
        if (hostHolder.getUser() == null) {
            return ForumUtil.getJsonString(999);
        }
        // 判断问题是否存在
        QuestionDO q = questionService.selectById(questionId);
        if (q == null) {
            return ForumUtil.getJsonString(1, "问题不存在");
        }
        boolean ret = followService.unfollow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, questionId);
        // 发送异步事件
        eventProducer.putEvent(new EventModel(EventType.UNFOLLOW)
                .setActorId(hostHolder.getUser().getId()).setEntityId(questionId)
                .setEntityType(EntityType.ENTITY_QUESTION).setEventOwnerId(q.getUserId()));
        // 发送用户本人信息用于页面展示
        Map<String, Object> info = new HashMap<>();
        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.getFollowerCount(EntityType.ENTITY_QUESTION, questionId));
        return ForumUtil.getJsonString(ret ? 0 : 1, info);
    }

    @GetMapping(value = "/user/{uid}/followers")
    public String followers(Model model, @PathVariable("uid") int userId) {
        List<Integer> followerIds = followService.getFollowers(EntityType.ENTITY_USER, userId, 0, 10);
        if (hostHolder.getUser() != null) {
            model.addAttribute("followers", getUsersInfo(hostHolder.getUser().getId(), followerIds));
        } else {
            model.addAttribute("followers", getUsersInfo(0, followerIds));
        }
        model.addAttribute("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, userId));
        model.addAttribute("curUser", userService.getUserById(userId));
        return "followers";
    }

    @GetMapping(value = "/user/{uid}/followees")
    public String followees(Model model, @PathVariable("uid") int userId) {
        List<Integer> followeeIds = followService.getFollowees(userId, EntityType.ENTITY_USER, 0, 10);

        if (hostHolder.getUser() != null) {
            model.addAttribute("followees", getUsersInfo(hostHolder.getUser().getId(), followeeIds));
        } else {
            model.addAttribute("followees", getUsersInfo(0, followeeIds));
        }
        model.addAttribute("followeeCount", followService.getFolloweeCount(userId, EntityType.ENTITY_USER));
        model.addAttribute("curUser", userService.getUserById(userId));
        return "followees";
    }

    private List<ViewObject> getUsersInfo(int localUserId, List<Integer> userIds) {
        List<ViewObject> userInfos = new ArrayList<>();
        for (Integer uid : userIds) {
            UserDO user = userService.getUserById(uid);
            if (user == null) {
                continue;
            }
            ViewObject vo = new ViewObject();
            vo.set("user", user);
            vo.set("commentCount", commentService.getCommentCount(uid,EntityType.ENTITY_USER));
            vo.set("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, uid));
            vo.set("followeeCount", followService.getFolloweeCount(uid, EntityType.ENTITY_USER));
            if (localUserId != 0) {
                vo.set("followed", followService.isFollower(localUserId, EntityType.ENTITY_USER, uid));
            } else {
                vo.set("followed", false);
            }
            userInfos.add(vo);
        }
        return userInfos;
    }



}
