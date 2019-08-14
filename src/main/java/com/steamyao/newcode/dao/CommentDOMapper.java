package com.steamyao.newcode.dao;

import com.steamyao.newcode.dataobject.CommentDO;

import java.util.List;

public interface CommentDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    int insert(CommentDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    int insertSelective(CommentDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    CommentDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    int updateByPrimaryKeySelective(CommentDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Sun Aug 04 10:26:57 CST 2019
     */
    int updateByPrimaryKey(CommentDO record);

    //筛选某一条评论下面的数量
    int getCommentCount(int entityId,int entityType);

    //选取某一条评论下面的所有评论
    List<CommentDO> selectAllCommentByEntity(int entityId,int entityType);
}