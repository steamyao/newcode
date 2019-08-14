package com.steamyao.newcode.model;

import com.steamyao.newcode.dataobject.UserDO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public class HostHolder {
    // ThreadLocal为变量在每个线程中都创建了一个副本，那么每个线程可以访问自己内部的副本变量
    private static ThreadLocal<UserDO> users = new ThreadLocal<>();

    public UserDO getUser() {
        return users.get();
    }

    public void setUser(UserDO user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}