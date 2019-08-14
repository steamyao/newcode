package com.steamyao.newcode.service.Impl;


import com.steamyao.newcode.dao.QuestionDOMapper;
import com.steamyao.newcode.dataobject.QuestionDO;
import com.steamyao.newcode.service.QuestionService;
import com.steamyao.newcode.service.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionDOMapper questionDOMapper;
    @Autowired
    SensitiveService sensitiveService;

    @Override
    public List<QuestionDO> selectLatestQuestions(int userId, int offset, int limit) {
        return questionDOMapper.selectLatestQuestions(userId, offset, limit);
    }


     //添加问题
    @Override
    public int addQuestions(QuestionDO question) {
        // HTML代码过滤，就是把html语言进行转义
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        //敏感词过滤
        question.setContent(sensitiveService.filter(question.getContent()));
        question.setTitle(sensitiveService.filter(question.getTitle()));
        // 这里question.getId()就是存进数据库后对应的Id了
        return questionDOMapper.insertSelective(question) > 0 ? question.getId() : 0;
    }

    @Override
    public QuestionDO selectById(int id) {
            return questionDOMapper.selectByPrimaryKey(id);
    }


    public QuestionDO selectQuestionById(int id) {
        return questionDOMapper.selectByPrimaryKey(id);
    }

    //更新评论的数量
    @Override
    public int updateCommentCount(int qId, int commentCount) {
        return questionDOMapper.updateCommentCount(qId, commentCount);
    }
}