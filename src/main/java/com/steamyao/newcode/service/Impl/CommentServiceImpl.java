package com.steamyao.newcode.service.Impl;

import com.steamyao.newcode.dao.CommentDOMapper;
import com.steamyao.newcode.dataobject.CommentDO;
import com.steamyao.newcode.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Package com.steamyao.newcode.service.Impl
 * @date 2019/8/6 16:49
 * @description
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDOMapper commentDOMapper;

    @Override
    public int addComment(CommentDO commentDO) {
        return commentDOMapper.insertSelective(commentDO);
    }

    @Override
    public int getCommentCount(int entityId, int entityType) {
        return commentDOMapper.getCommentCount(entityId,entityType);
    }

    @Override
    public List<CommentDO> selectAllCommentByEntity(int entityId, int entityType) {
        return commentDOMapper.selectAllCommentByEntity(entityId,entityType);
    }

    @Override
    public CommentDO getCmmentById(int commentId) {
        return commentDOMapper.selectByPrimaryKey(commentId);
    }
}
