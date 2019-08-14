package com.steamyao.newcode.service.Impl;

import com.steamyao.newcode.Utils.ForumUtil;
import com.steamyao.newcode.dao.LoginTicketDOMapper;
import com.steamyao.newcode.dao.UserDOMapper;
import com.steamyao.newcode.dataobject.LoginTicketDO;
import com.steamyao.newcode.dataobject.UserDO;
import com.steamyao.newcode.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * @Package com.steamyao.newcode.service.Impl
 * @date 2019/8/4 16:31
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDOMapper userDOMapper;
    @Autowired
    LoginTicketDOMapper loginTicketDOMapper;

    // 注册
    @Override
    public Map<String, String> register(String username, String password) {
        Map<String, String> map = new HashMap<>();
        // 后台的简单判断
        if (StringUtils.isEmpty(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isEmpty(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }
        UserDO userDO = userDOMapper.selectByName(username);
        if (userDO != null) {
            map.put("msg", "用户名已被注册");
            return map;
        }
        // 符合条件便注册用户
        userDO = new UserDO();
        userDO.setName(username);
        userDO.setSalt(UUID.randomUUID().toString().substring(0, 5));
        userDO.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        // 密码加salt再用MD5加密，安全性更高
        userDO.setPassword(ForumUtil.MD5(password + userDO.getSalt()));
        userDOMapper.insertSelective(userDO);
        // 注册完成下发ticket之后自动登录
        String ticket = addLoginTicket(userDO.getId());
        map.put("ticket", ticket);
        return map;
    }

    @Override
    public UserDO getUserById(Integer id) {
        return userDOMapper.selectByPrimaryKey(id);
    }

    //登陆
    @Override
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        // 后台简单判断
        if (StringUtils.isEmpty(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isEmpty(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }
        UserDO user = userDOMapper.selectByName(username);
        if (user == null) {
            map.put("msg", "用户名不存在");
            return map;
        }
        if (!ForumUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "密码错误");
            return map;
        }
        // 登陆成功下发ticket之后自动登录
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    public String addLoginTicket(int userId) {
        LoginTicketDO ticket = new LoginTicketDO();
        ticket.setUserId(userId);
        Date nowDate = new Date();
        nowDate.setTime(60 * 60 * 1000 + nowDate.getTime());
        ticket.setExpired(nowDate);
        ticket.setStatus(0);
        // UUID生成的随机ticket有"_"，要替换掉
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDOMapper.insertSelective(ticket);
        // 返回ticket
        return ticket.getTicket();
    }

    @Override
    public void logout(String ticket) {
        loginTicketDOMapper.updateStatus(ticket, 1);

    }

    @Override
    public UserDO selectUserByName(String name) {
        return userDOMapper.selectByName(name);
    }



}