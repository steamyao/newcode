package com.steamyao.newcode.service;

import com.steamyao.newcode.dataobject.UserDO;

import java.util.Map;

/**
 * @Package com.steamyao.newcode.service
 * @date 2019/8/4 16:13
 * @description
 */
public interface UserService {

     Map<String, Object> login(String username, String password);

    Map<String, String> register(String username, String password);

    UserDO getUserById(Integer id);

    UserDO selectUserByName(String name);

    void logout(String ticket);
}