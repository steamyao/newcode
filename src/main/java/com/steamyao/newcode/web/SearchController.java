package com.steamyao.newcode.web;

import com.steamyao.newcode.Utils.ForumUtil;
import com.steamyao.newcode.dataobject.QuestionDO;
import com.steamyao.newcode.model.EntityType;
import com.steamyao.newcode.model.QuestionModel;
import com.steamyao.newcode.model.ViewObject;
import com.steamyao.newcode.service.FollowService;
import com.steamyao.newcode.service.QuestionService;
import com.steamyao.newcode.service.SearchService;
import com.steamyao.newcode.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Package com.steamyao.newcode.web
 * @date 2019/8/12 8:57
 * @description
 */
@Controller
public class SearchController {

    @Autowired
    SearchService searchService;
    @Autowired
    FollowService followService;
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    // 保存数据库所有question到es
    @GetMapping(value = "/saveAll")
    @ResponseBody
    public String saveAll() {
        try {
            List<QuestionDO> questions = questionService.selectLatestQuestions(0, 0, 500);
            List<QuestionModel> list = new ArrayList<>();
            for (QuestionDO question : questions) {
                list.add(convertFromDataObject(question));
            }
            searchService.saveAll(list);
            return ForumUtil.getJsonString(0);
        }catch (Exception e){
            System.out.println("加入索引库失败");
            e.printStackTrace();
            return ForumUtil.getJsonString(-1);
        }

    }

    @GetMapping(value = "/save")
    @ResponseBody
    public String save(){
        try {
            QuestionModel questionModel = new QuestionModel();
            questionModel.setId(Long.valueOf(17));
            questionModel.setComment_count(10);
            questionModel.setContent("这里是内容");
            questionModel.setTitle("这里是标题");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            String format = simpleDateFormat.format(now);

            questionModel.setCreat_date(format);
            questionModel.setUser_id(10);
            searchService.save(questionModel);
            return "success";
        }catch (Exception e){
            System.out.println("加入索引库失败");
            e.printStackTrace();
            return "false";
        }

    }

    @GetMapping(value = "/search")
    public String search(Model model, @RequestParam("q") String keyword,
                                      @RequestParam(value = "p",required = false,defaultValue ="1") int page,
                                      @RequestParam(value = "size",required = false,defaultValue = "10")int size) {
        try {
            List<QuestionModel> questionList = searchService.search(keyword, page, size);
            List<ViewObject> vos = new ArrayList<>();
            for (QuestionModel question : questionList) {
                ViewObject vo = new ViewObject();
                vo.set("question", question);
                vo.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION,question.getId().intValue()));
                vo.set("user", userService.getUserById(question.getUser_id()));
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("keyword", keyword);
        } catch (Exception e) {
            logger.error("搜索失败" + e.getMessage());
        }
        return "result";
    }

    private QuestionModel convertFromDataObject(QuestionDO questionDO){
        if (questionDO == null){
            return null;
        }
        QuestionModel questionModel = new QuestionModel();
        questionModel.setUser_id(questionDO.getUserId());
        questionModel.setComment_count(questionDO.getCommentCount());
        questionModel.setId(questionDO.getId().longValue());
        questionModel.setTitle(questionDO.getTitle());
        questionModel.setContent(questionDO.getContent());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        questionModel.setCreat_date(simpleDateFormat.format(questionDO.getCreatDate()));
        return questionModel;
    }
}
