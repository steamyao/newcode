package com.steamyao.newcode.dao;

import com.steamyao.newcode.dataobject.QuestionDO;

import java.util.List;

public interface QuestionDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table question
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table question
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    int insert(QuestionDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table question
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    int insertSelective(QuestionDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table question
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    QuestionDO selectByPrimaryKey(Integer id);

    List<QuestionDO> selectLatestQuestions(Integer userId, Integer offset, Integer limit);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table question
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    int updateByPrimaryKeySelective(QuestionDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table question
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    int updateByPrimaryKey(QuestionDO record);


    int updateCommentCount(Integer id,Integer commentCount);
}