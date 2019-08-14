package com.steamyao.newcode.service;

/**
 * @Package com.steamyao.newcode.service
 * @date 2019/8/5 14:44
 * @description
 */
public interface SensitiveService {

    // 过滤敏感词
    String filter(String text);
}
