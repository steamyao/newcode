package com.steamyao.newcode.asyn;

import com.alibaba.fastjson.JSON;
import com.steamyao.newcode.Utils.JedisAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Package com.steamyao.newcode.asyn
 * @date 2019/8/8 10:26
 * @description
 */
@Component
public class EventProducer {

    @Autowired
    private JedisAdapter jedisAdapter;

    public boolean putEvent(EventModel eventModel){
        try {
            String json = JSON.toJSONString(eventModel);
            jedisAdapter.sendMsg("FEED_TOPIC",json);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

}
