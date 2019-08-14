package com.steamyao.newcode.service.Impl;

import com.steamyao.newcode.dao.FeedDOMapper;
import com.steamyao.newcode.dataobject.FeedDO;
import com.steamyao.newcode.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Package com.steamyao.newcode.service.Impl
 * @date 2019/8/9 9:49
 * @description
 */
@Service
public class FeedServiceImpl implements FeedService {


    @Autowired
    private FeedDOMapper feedDOMapper;

    @Override
    public List<FeedDO> getUserFeeds(int maxId, List<Integer> userIds, int count) {
        return feedDOMapper.selectUserFeeds(maxId,userIds,count);
    }

    @Override
    public FeedDO getFeedById(int id) {
        return feedDOMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean addFeed(FeedDO feedDO) {
        return feedDOMapper.insertSelective(feedDO)>0 ? true :false;
    }
}
