package com.steamyao.newcode.service;

import com.steamyao.newcode.model.QuestionModel;

import java.util.List;

/**
 * @Package com.steamyao.newcode.service
 * @date 2019/8/12 8:12
 * @description
 */
public interface SearchService {

     void saveAll(List<QuestionModel> questions);

     void save(QuestionModel questionModel);

     List<QuestionModel> search(String title, int page, int size);
}
