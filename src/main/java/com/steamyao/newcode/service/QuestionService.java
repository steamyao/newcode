package com.steamyao.newcode.service;

import com.steamyao.newcode.dataobject.QuestionDO;

import java.util.List;

/**
 * @Package com.steamyao.newcode.service
 * @date 2019/8/5 14:48
 * @description
 */
public interface QuestionService {

  List<QuestionDO> selectLatestQuestions(int userId, int offset, int limit);

  int addQuestions(QuestionDO questionDO);

    QuestionDO selectById(int id);

    //增加评论数量
   int updateCommentCount(int qId,int count);
}
