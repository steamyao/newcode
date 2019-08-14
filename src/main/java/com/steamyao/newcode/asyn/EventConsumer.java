package com.steamyao.newcode.asyn;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.steamyao.newcode.Utils.JedisAdapter;
import com.steamyao.newcode.web.UserController;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package com.steamyao.newcode.asyn.handler
 * @date 2019/8/8 10:26
 * @description
 */
@Component
public class EventConsumer implements InitializingBean, ApplicationContextAware, MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private JedisAdapter jedisAdapter;

    private ApplicationContext applicationContext;

    private Map<EventType,List<EventHandler>> config = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);

        if (beans != null){
            for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
                List<EventType> types = entry.getValue().getSupportEventTypes();
                for (EventType type : types) {
                    if(!config.containsKey(type)){
                        config.put(type,new ArrayList<>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        RedisSerializer<String> serializer = jedisAdapter.getRedisTemplate().getStringSerializer();
        String msg = serializer.deserialize(message.getBody());

        //处理字符串的转义
        msg = StringEscapeUtils.unescapeJava(msg);
        msg = msg.substring(1,msg.length()-1);

        EventModel eventModel = JSON.parseObject(msg, EventModel.class);

        if (!config.containsKey(eventModel.getEventType())) {
            System.out.println(eventModel.getEventType());
            logger.error("不能识别的事件");
        }
        // 找到handler list，一个个去处理这个event
        for (EventHandler handler : config.get(eventModel.getEventType())) {
            handler.doHandler(eventModel);
        }
    }
}
