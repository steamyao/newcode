package com.steamyao.newcode.service;

/**
 * @Package com.steamyao.newcode.service
 * @date 2019/8/8 8:18
 * @description
 */
public interface LikeService {

    //获取当前喜欢人数
    long getLikeCount(int entityType,int entityId);

    //获取当前不喜欢人数
    long getDisLikeCount(int entityType,int entityId);

    //获取当前用户喜欢状态 0未知 1喜欢  -1不喜欢
    int getLikeStatus(int userId,int entityType,int entityId);

    //返回喜欢的人数
    long like(int userId, int entityType, int entityId);

    //返回不喜欢的人数
    long disLike(int userId, int entityType, int entityId);

}
