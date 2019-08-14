package com.steamyao.newcode.web;

import com.steamyao.newcode.asyn.EventModel;
import com.steamyao.newcode.asyn.EventProducer;
import com.steamyao.newcode.asyn.EventType;
import com.steamyao.newcode.dataobject.CommentDO;
import com.steamyao.newcode.model.EntityType;
import com.steamyao.newcode.model.HostHolder;
import com.steamyao.newcode.service.CommentService;
import com.steamyao.newcode.service.QuestionService;
import com.steamyao.newcode.service.SortService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    HostHolder hostHolder;
    @Autowired
    CommentService commentService;
    @Autowired
    QuestionService questionService;
    @Autowired
    EventProducer eventProducer;
    @Autowired
    private SortService sortService;



    @PostMapping(value = {"/addComment"})
    public String addComment(int questionId, String content) {
        try {
            if (hostHolder.getUser() == null) {
                return "login";
            }
            // 增加question的评论
            CommentDO comment = new CommentDO();
            comment.setContent(content);
            comment.setCreatDate(new Date());
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setEntityId(questionId);
            comment.setUserId(hostHolder.getUser().getId());
            commentService.addComment(comment);


            //更新最新评论时间
            sortService.updateCommontTime(questionId,new Date());

            // 更新question的评论数
            int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
            questionService.updateCommentCount(comment.getEntityId(), count);
            // 推送异步事件
            eventProducer.putEvent(new EventModel(EventType.COMMENT).setActorId(comment.getUserId()).setEntityId(questionId));
        } catch (Exception e) {
            logger.error("增加评论失败" + e.getMessage());
        }
        return "redirect:/question/" + questionId;
    }
}