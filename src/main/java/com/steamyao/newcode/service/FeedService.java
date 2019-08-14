package com.steamyao.newcode.service;

import com.steamyao.newcode.dataobject.FeedDO;

import java.util.List;

/**
 * @Package com.steamyao.newcode.service
 * @date 2019/8/9 9:47
 * @description
 */
public interface FeedService {

    //拉取
    List<FeedDO> getUserFeeds(int maxId, List<Integer> userIds, int count);

    FeedDO getFeedById(int id);

    boolean addFeed(FeedDO feedDO);
}


