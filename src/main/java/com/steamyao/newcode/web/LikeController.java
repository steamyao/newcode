package com.steamyao.newcode.web;

import com.steamyao.newcode.Utils.ForumUtil;
import com.steamyao.newcode.asyn.EventModel;
import com.steamyao.newcode.asyn.EventProducer;
import com.steamyao.newcode.asyn.EventType;
import com.steamyao.newcode.dataobject.CommentDO;
import com.steamyao.newcode.model.EntityType;
import com.steamyao.newcode.model.HostHolder;
import com.steamyao.newcode.service.CommentService;
import com.steamyao.newcode.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Package com.steamyao.newcode.web
 * @date 2019/8/8 8:33
 * @description
 */
@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private CommentService commentService;
    @Autowired
    private EventProducer producer;


    @RequestMapping("/like")
    @ResponseBody
    public String like(int commentId){
        if(hostHolder.getUser() == null){
            return ForumUtil.getJsonString(999);
        }
        // 获取点赞的那条评论
        CommentDO comment = commentService.getCmmentById(commentId);
        // 异步队列发送私信给被赞人
        producer.putEvent(new EventModel(EventType.LIKE)
                .setActorId(hostHolder.getUser().getId()).setEntityId(commentId)
                .setEntityType(EntityType.ENTITY_COMMENT).setEventOwnerId(comment.getUserId())
                .setExts("questionId", String.valueOf(comment.getEntityId()))
                .setExts("content",comment.getContent()));
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return ForumUtil.getJsonString(0, String.valueOf(likeCount));
    }


    @RequestMapping("/dislike")
    @ResponseBody
    public String disLike(int commentId) {
        if (hostHolder.getUser() == null) {
            return ForumUtil.getJsonString(999);
        }
        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return ForumUtil.getJsonString(0, String.valueOf(likeCount));
    }
}
