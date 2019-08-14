package com.steamyao.newcode.web;

import com.steamyao.newcode.dataobject.QuestionDO;
import com.steamyao.newcode.dataobject.UserDO;
import com.steamyao.newcode.model.EntityType;
import com.steamyao.newcode.model.HostHolder;
import com.steamyao.newcode.model.ViewObject;
import com.steamyao.newcode.service.CommentService;
import com.steamyao.newcode.service.FollowService;
import com.steamyao.newcode.service.QuestionService;
import com.steamyao.newcode.service.SortService;
import com.steamyao.newcode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    @Autowired
    FollowService followService;
    @Autowired
    CommentService commentService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    private SortService sortService;

    @GetMapping(value = "/user/{userId}")
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getQuestions(userId, 0, 10));
        // 显示关注和被关注列表
        UserDO user = userService.getUserById(userId);
        ViewObject vo = new ViewObject();
        vo.set("user", user);
        vo.set("commentCount", commentService.getCommentCount(userId,EntityType.ENTITY_USER));
        vo.set("followeeCount", followService.getFolloweeCount(userId, EntityType.ENTITY_USER));
        vo.set("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, userId));
        if (hostHolder.getUser() != null) {
            vo.set("followed", followService.isFollower(hostHolder.getUser().getId(), userId, EntityType.ENTITY_USER));
        } else {
            vo.set("followed", false);
        }
        model.addAttribute("profileUser", vo);
        model.addAttribute("vos", getQuestions(userId, 0, 10));
        return "profile";
    }

    @GetMapping(path = {"/", "/index"})
    public String home(Model model) {
        model.addAttribute("vos", getQuestions(0, 0, 10));
        return "index";
    }

    private List<ViewObject> getQuestions(int userId, int offset, int limit) {
        //直接获取最新发布的问题
//        List<QuestionDO> questionList = questionService.selectLatestQuestions(userId, offset, limit);
        List<QuestionDO> questionList = new ArrayList<>();


        //获取经过评分的问题列表
        List<Integer> sortList = sortService.getSortedList();
        for (Integer qId : sortList) {
            questionList.add(questionService.selectById(qId));
        }


        List<ViewObject> vos = new ArrayList<>();
        for (QuestionDO question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            //问题关注的数量
            vo.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, question.getId()));
            vo.set("user", userService.getUserById(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }


    @RequestMapping(value = "/sort")
    @ResponseBody
    public String sort(){
        //对最近刚更新的20个进行打分
        sortService.sortSoreDo(0,20);
        return "success";
    }
}