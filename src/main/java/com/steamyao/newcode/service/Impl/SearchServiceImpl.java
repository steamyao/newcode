package com.steamyao.newcode.service.Impl;

import com.google.common.collect.Lists;
import com.steamyao.newcode.model.QuestionModel;
import com.steamyao.newcode.repository.QuestionRepository;
import com.steamyao.newcode.service.SearchService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @Package com.steamyao.newcode.service.Impl
 * @date 2019/8/12 8:16
 * @description
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void saveAll(List<QuestionModel> questions) {
        try {
            for (QuestionModel question : questions) {
                questionRepository.save(question);
            }
        }catch (Exception e){
            System.out.println("加入索引库失败 !");
            e.printStackTrace();
        }

    }

    @Override
    public void save(QuestionModel questionModel) {
        try{
            questionRepository.save(questionModel);
        }catch (Exception e){
            System.out.println("加入索引库失败 !");
            e.printStackTrace();
        }

    }

    @Override
    public List<QuestionModel> search(String keywords, int page, int size) {
       //对内容与标题设置了keyword，所以能搜索出两者

        //todo 现在是全部匹配，要改成只要包含就能查询
        int offset = (page-1)*size;
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(keywords);
        Pageable pageable = PageRequest.of(offset, size, Sort.by(Sort.Direction.DESC, "creat_date"));
        Page<QuestionModel> pages = questionRepository.search(builder, pageable);

        return pages.getContent();
    }
}
