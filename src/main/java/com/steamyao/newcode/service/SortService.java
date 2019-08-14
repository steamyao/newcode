package com.steamyao.newcode.service;

import com.steamyao.newcode.dataobject.SortDO;

import java.util.Date;
import java.util.List;

/**
 * @Package com.steamyao.newcode.service
 * @date 2019/8/14 9:56
 * @description
 */
public interface SortService {


    //获取已经排序好的questionId
    List<Integer> getSortedList();

    //对最近更新过排序计分，给前端接口
    void sortSoreDo(int offset,int limit);

     //定时任务
     void sortSoreDo();


    //添加进数据库
    int addSortDo(SortDO sortDO);




    //更新最新评论时间
    void updateCommontTime(int qId, Date updateTime);


    //计分算法
    double computeScore(SortDO sortDO);


}
