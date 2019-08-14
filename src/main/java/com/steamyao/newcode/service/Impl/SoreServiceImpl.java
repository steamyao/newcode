package com.steamyao.newcode.service.Impl;

import com.steamyao.newcode.Utils.JedisAdapter;
import com.steamyao.newcode.Utils.RedisKeyUtil;
import com.steamyao.newcode.dao.SortDOMapper;
import com.steamyao.newcode.dataobject.CommentDO;
import com.steamyao.newcode.dataobject.SortDO;
import com.steamyao.newcode.model.EntityType;
import com.steamyao.newcode.service.CommentService;
import com.steamyao.newcode.service.LikeService;
import com.steamyao.newcode.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Package com.steamyao.newcode.service.Impl
 * @date 2019/8/14 9:58
 * @description
 */
@Service
@EnableScheduling
public class SoreServiceImpl implements SortService {

    @Autowired
    private SortDOMapper sortDOMapper;
    @Autowired
    private JedisAdapter jedisAdapter;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeService likeService;

    private static long date;
    private int answerNum;
    private int viewNum;
    private int likeCount;
    private ExecutorService service;
    private int offset;
    private int limit;

    @PostConstruct
    public void init(){
        date = System.currentTimeMillis();
        service = Executors.newSingleThreadExecutor();
    }



    @Override
    public List<Integer> getSortedList() {
        return sortDOMapper.getSortedList(0,10);
    }



    @Override
    public void sortSoreDo(int off,int lim) {
        if (off == 0){
            offset = 0;
        }else {
            offset = off;
        }
        if (lim == 0){
            limit = 20;
        }else {
            limit = lim;
        }

        service.submit(new Runnable() {
            @Override
            public void run() {
                List<SortDO> list = sortDOMapper.selectSoreDo(offset,limit);
                double  score;
                for (SortDO sortDO : list) {
                    score = computeScore(sortDO);
                    sortDO.setScore(score*200);
                    sortDO.setComCount(answerNum);
                    sortDO.setLikeCount(likeCount);
                    sortDO.setViewCount(viewNum);
                    sortDOMapper.updateByPrimaryKeySelective(sortDO);
                }
            }
        });

    }


    //开启定时任务，10分钟算一次分
    @Scheduled(fixedDelay = 600000)
    public void sortSoreDo() {
        //todo  特点
        service.submit(new Runnable() {
            @Override
            public void run() {
                List<SortDO> list = sortDOMapper.selectSoreDo(0,20);
                double  score;
                for (SortDO sortDO : list) {
                    score = computeScore(sortDO);
                    sortDO.setScore(score*200);
                    sortDO.setComCount(answerNum);
                    sortDO.setLikeCount(likeCount);
                    sortDO.setViewCount(viewNum);
                    sortDOMapper.updateByPrimaryKeySelective(sortDO);
                    System.out.println(sortDO.getId()+" : "+sortDO.getScore());
                }
            }
        });

    }








    @Override
    public double computeScore(SortDO sortDO) {
        if (sortDO == null){
            return -1;
        }
        viewNum = 0;
        answerNum = 0;
        likeCount = 0;

        //todo 可以适当的修改参数，此时分子偏小，分母偏大，对发布时间长的不利

        viewNum = jedisAdapter.getViewNum(RedisKeyUtil.getQuesViewKey(sortDO.getQuesId()));
        List<CommentDO> answerList = commentService.selectAllCommentByEntity(sortDO.getQuesId(),EntityType.ENTITY_QUESTION);
        answerNum = answerList.size();
        for (CommentDO commentDO : answerList) {
           likeCount += likeService.getLikeCount(EntityType.ENTITY_COMMENT,commentDO.getId());
           likeCount -= likeService.getDisLikeCount(EntityType.ENTITY_COMMENT,commentDO.getId());
        }

         double fz = Math.log10(viewNum)*4+answerNum*6+likeCount*8;
         //时间差
         long time = (date-sortDO.getCreatDate().getTime())/3600000;
         long updatetime = (date-sortDO.getUpdateTime().getTime())/3600000;
         double fm = Math.pow((time+1)-(time-updatetime)/10,0.4);
        return fz/fm;
    }



    @Override
    public int addSortDo(SortDO sortDO) {
        return sortDOMapper.insertSelective(sortDO)>0?sortDO.getId():-1;
    }

    @Override
    public void updateCommontTime(int qId, Date updateTime) {
        sortDOMapper.updateCommontTime(qId,updateTime);
    }


}
