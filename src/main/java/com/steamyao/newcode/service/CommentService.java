package com.steamyao.newcode.service;

import com.steamyao.newcode.dataobject.CommentDO;

import java.util.List;

/**
 * @Package com.steamyao.newcode.service
 * @date 2019/8/6 16:46
 * @description
 */
public interface CommentService {

    //增加评论
    int addComment(CommentDO commentDO);

    //查询评论的数量
    int getCommentCount(int entityId,int entityType);


    //查询某一帖子或评论下面的所有评论
    List<CommentDO> selectAllCommentByEntity(int entityId, int entityType);

    //查讯莫一条评论
    CommentDO getCmmentById(int commentId);

}
