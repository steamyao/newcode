package com.steamyao.newcode.web;

import com.steamyao.newcode.Utils.JedisAdapter;
import com.steamyao.newcode.Utils.RedisKeyUtil;
import com.steamyao.newcode.dataobject.FeedDO;
import com.steamyao.newcode.model.EntityType;
import com.steamyao.newcode.model.FeedModel;
import com.steamyao.newcode.model.HostHolder;
import com.steamyao.newcode.service.FeedService;
import com.steamyao.newcode.service.FollowService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package com.steamyao.newcode.web
 * @date 2019/8/9 9:58
 * @description    todo 由于前端页面问题，获取Feed时，不能显示发表问题，只显示评论或关注问题。
 */
@Controller
public class FeedController {

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private FeedService feedService;
    @Autowired
    private FollowService followService;
    @Autowired
    private JedisAdapter jedisAdapter;

    @GetMapping("/pushfeeds")
    public String pushFeeds(Model model){
        //todo 没有实现(已解决，feedDO,没有插入ID)
        int userId = hostHolder.getUser()!=null? hostHolder.getUser().getId() : 0;
        //获取我关注的人的FeedId
        //获取我的feed 列表
        List<String> feeds = jedisAdapter.lrange(RedisKeyUtil.getTimelineKey(userId), 0, 10);
        List<FeedModel> feedList = new ArrayList<>();
        //根据我的列表里面的feedId，依次去数据库查询
        for(String feed:feeds){
            FeedDO feedDO = feedService.getFeedById(Integer.valueOf(feed));
            if(feedDO!=null){
                //DO -> Model
                feedList.add(convertFromDO(feedDO));
            }
        }

        model.addAttribute("feeds", feedList);
        return "feeds";
    }


    @GetMapping("/pullfeeds")
    public String pullFeeds(Model model){
        int userId = hostHolder.getUser()!=null? hostHolder.getUser().getId() : 0;
        List<Integer> followees = new ArrayList<>();;
        if(userId!=0){
            //获取我关注的人
           followees = followService.getFollowees(userId, EntityType.ENTITY_USER,10);
        }
        //查询我关注的人的动态
        List<FeedDO> feeds = feedService.getUserFeeds(Integer.MAX_VALUE, followees, 10);
        List<FeedModel> feedList = new ArrayList<>();
        //DO -> Model
        for (FeedDO feedDO : feeds) {
             feedList.add(convertFromDO(feedDO));
        }
        model.addAttribute("feeds", feedList);
        return "feeds";
    }

    private FeedModel convertFromDO(FeedDO feedDO){
        if (feedDO == null){
            return null;
        }
        FeedModel feedModel = new FeedModel();
        BeanUtils.copyProperties(feedDO,feedModel);
        feedModel.setData(feedDO.getData());
        return feedModel;
    }
}
