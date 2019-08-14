package com.steamyao.newcode.service.Impl;

import com.steamyao.newcode.Utils.JedisAdapter;
import com.steamyao.newcode.Utils.RedisKeyUtil;
import com.steamyao.newcode.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Package com.steamyao.newcode.service.Impl
 * @date 2019/8/8 8:21
 * @description
 */
@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private JedisAdapter jedisAdapter;

    @Override
    public long getLikeCount(int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        return jedisAdapter.scard(likeKey);
    }

    @Override
    public long getDisLikeCount(int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        return jedisAdapter.scard(likeKey);
    }

    @Override
    public int getLikeStatus(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if(jedisAdapter.sismember(likeKey,String.valueOf(userId))){
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        return jedisAdapter.sismember(disLikeKey,String.valueOf(userId)) ? -1 : 0;
    }


    @Override
    public long like(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.sadd(likeKey,String.valueOf(userId));
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.srem(disLikeKey,String.valueOf(userId));
        //返回喜欢的人数
        return jedisAdapter.scard(likeKey);
    }

    @Override
    public long disLike(int userId, int entityType, int entityId) {
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.sadd(disLikeKey,String.valueOf(userId));
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.srem(likeKey,String.valueOf(userId));
        return jedisAdapter.scard(disLikeKey);
    }
}
