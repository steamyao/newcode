package com.steamyao.newcode.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Package com.steamyao.newcode.config
 * @date 2019/8/12 9:28
 * @description
 */
@Configuration
public class ElasticSearchConfig {

    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
}
