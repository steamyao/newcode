package com.steamyao.newcode.repository;

import com.steamyao.newcode.model.QuestionModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @Package com.steamyao.newcode.repository
 * @date 2019/8/12 8:03
 * @description
 */
@Component
public interface QuestionRepository  extends ElasticsearchRepository<QuestionModel,Long> {
}
