package com.steamyao.newcode.service.Impl;

import com.steamyao.newcode.Utils.JedisAdapter;
import com.steamyao.newcode.Utils.RedisKeyUtil;
import com.steamyao.newcode.service.FollowService;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class FollowServiceImpl  implements FollowService {
    @Autowired
    JedisAdapter jedisAdapter;

    // 用户关注了某个实体,可以关注问题,关注用户,关注评论等任何实体
    @Override
    @Transactional
    public boolean follow(int userId, int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        Date date = new Date();
        // 粉丝+1
        boolean resultA = jedisAdapter.zadd(followerKey, String.valueOf(userId), date.getTime());
        // 关注者+1
        boolean resultB = jedisAdapter.zadd(followeeKey, String.valueOf(entityId), date.getTime());
        //true 表示成功，false表示失败
        return resultA&&resultB;
    }

    // 取消关注
    @Override
    @Transactional
    public boolean unfollow(int userId, int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        Date date = new Date();
        // 粉丝-1  返回值为成功移除的数量
        long resultA = jedisAdapter.zrem(followerKey, String.valueOf(userId));
        // 关注者-1
        long resultB = jedisAdapter.zrem(followeeKey, String.valueOf(entityId));
        //true 表示成功，false表示失败
        return resultA>0&&resultB>0;

    }

    @Override
    public List<Integer> getFollowers(int entityType, int entityId, int count) {
        return getFollowers(entityType,entityId,0,count);
    }

    @Override
    public List<Integer> getFollowers(int entityType, int entityId, int offset, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return getIdsFromSet(jedisAdapter.zrevrange(followerKey, offset, offset + count));
    }


    @Override
    public List<Integer> getFollowees(int userId, int entityType, int count) {
        return getFollowees(userId,entityType,0,count);
    }

    @Override
    public List<Integer> getFollowees(int userId, int entityType, int offset, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey, offset, offset + count));
    }

    @Override
    public long getFollowerCount(int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return jedisAdapter.zcard(followerKey);
    }

    @Override
    public long getFolloweeCount(int userId, int entityType) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return jedisAdapter.zcard(followeeKey);
    }

    // 帮助函数
    private List<Integer> getIdsFromSet(Set<String> idset) {
        List<Integer> ids = new ArrayList<>();
        for (String str : idset) {
            ids.add(Integer.parseInt(str));
        }
        return ids;
    }

    // 判断用户是否关注了某个实体
    @Override
    public boolean isFollower(int userId, int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return jedisAdapter.zscore(followerKey, String.valueOf(userId)) != null;
    }
}