package com.steamyao.newcode.web;

import com.steamyao.newcode.Utils.ForumUtil;
import com.steamyao.newcode.Utils.JedisAdapter;
import com.steamyao.newcode.Utils.RedisKeyUtil;
import com.steamyao.newcode.asyn.EventModel;
import com.steamyao.newcode.asyn.EventProducer;
import com.steamyao.newcode.asyn.EventType;
import com.steamyao.newcode.dataobject.CommentDO;
import com.steamyao.newcode.dataobject.QuestionDO;
import com.steamyao.newcode.dataobject.SortDO;
import com.steamyao.newcode.dataobject.UserDO;
import com.steamyao.newcode.model.EntityType;
import com.steamyao.newcode.model.HostHolder;
import com.steamyao.newcode.model.ViewObject;
import com.steamyao.newcode.service.CommentService;
import com.steamyao.newcode.service.FollowService;
import com.steamyao.newcode.service.LikeService;
import com.steamyao.newcode.service.QuestionService;
import com.steamyao.newcode.service.SortService;
import com.steamyao.newcode.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Package com.steamyao.newcode.web
 * @date 2019/8/6 10:15
 * @description
 */
@Controller
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);


    @Autowired
    private QuestionService questionService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private FollowService followService;
    @Autowired
    private EventProducer eventProducer;
    @Autowired
    private LikeService likeService;
    @Autowired
    private SortService sortService;
    @Autowired
    private JedisAdapter adapter;




    @RequestMapping("/question/add")
    @ResponseBody
    public String addQuestion(String title,String content){
        if (hostHolder.getUser() == null) {
            // 前端收到999会执行登陆跳转
            return ForumUtil.getJsonString(999);
        }
        try {
            // 创建Question对象
            QuestionDO question = new QuestionDO();
            question.setContent(content);
            question.setCommentCount(0);
            question.setCreatDate(new Date());
            question.setTitle(title);
            question.setUserId(hostHolder.getUser().getId());
            // Question对象入库
            int qid = questionService.addQuestions(question);
            if ( qid > 0) {

                //添加SortDO对象
                SortDO sortDO = new SortDO();
                sortDO.setCreatDate(new Date());
                sortDO.setQuesId(qid);
                sortService.addSortDo(sortDO);


                //发送异步事件，通知关注当前用户的人
                eventProducer.putEvent(new EventModel(EventType.ADD_QUESTION)
                        .setActorId(question.getUserId()).setEntityId(question.getId())
                .setExts("title", question.getTitle()).setExts("content", question.getContent()));
                return ForumUtil.getJsonString(0);
            }
        } catch (Exception e) {
            logger.error("增加题目失败" + e.getMessage());
        }
        return ForumUtil.getJsonString(1, "失败");
    }

    @GetMapping(value = "/question/{qid}")
    public String questionDetail(Model model, @PathVariable("qid") int qid) {
        QuestionDO questionDO = questionService.selectById(qid);
        model.addAttribute("question",questionDO);

        //增加问题的浏览数
        adapter.addQuesViewNum(RedisKeyUtil.getQuesViewKey(qid));


        //显示评论
        List<CommentDO> commentList = commentService.selectAllCommentByEntity(qid, EntityType.ENTITY_QUESTION);
        List<ViewObject> comments = new ArrayList<>();
        for(CommentDO comment:commentList){
            ViewObject vo = new ViewObject();
            vo.set("comment",comment);
            vo.set("user",userService.getUserById(comment.getUserId()));
            if (hostHolder.getUser()==null){
                vo.set("liked",0);
            }else {
                vo.set("liked",likeService.getLikeStatus(hostHolder.getUser().getId(),EntityType.ENTITY_COMMENT,comment.getId()));
            }
            vo.set("likeCount",likeService.getLikeCount(EntityType.ENTITY_COMMENT,comment.getId()));

            comments.add(vo);
        }
        model.addAttribute("comments",comments);

        // 获取关注的用户信息
        List<ViewObject> followUsers = new ArrayList<>();
        List<Integer> users = followService.getFollowers(EntityType.ENTITY_QUESTION, qid, 20);
        for (Integer userId : users) {
            ViewObject vo = new ViewObject();
            UserDO u = userService.getUserById(userId);
            if (u == null) {
                continue;
            }
            vo.set("name", u.getName());
            vo.set("headUrl", u.getHeadUrl());
            vo.set("id", u.getId());
            followUsers.add(vo);
        }
        model.addAttribute("followUsers", followUsers);

        //关注按钮的显示
        if (hostHolder.getUser() != null) {
            model.addAttribute("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, qid));
        } else {
            model.addAttribute("followed", false);
        }
        return "detail";

    }


}
