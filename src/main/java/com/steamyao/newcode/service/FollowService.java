package com.steamyao.newcode.service;

import java.util.List;

/**
 * @Package com.steamyao.newcode.service
 * @date 2019/8/5 14:50
 * @description
 */
public interface FollowService {

    //关注某个东西
    boolean follow(int userId, int entityType, int entityId);

    //取消关注
    boolean unfollow(int userId, int entityType, int entityId);

    //获取关注者
    List<Integer> getFollowers(int entityType, int entityId, int count);
    //获取关注者，带分页
    List<Integer> getFollowers(int entityType, int entityId, int offset, int count);

    //查看关注的列表
    List<Integer> getFollowees(int userId, int entityType, int count);
    List<Integer> getFollowees(int userId, int entityType,int offect, int count);

    //获取关注者的数量
    long getFollowerCount(int entityType, int entityId);

    //查看我关注的数量
    long getFolloweeCount(int userId, int entityType);

    //判断两者之间的关注关系
    boolean isFollower(int userId, int entityType, int entityId);
}
