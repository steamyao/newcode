package com.steamyao.newcode.asyn.handler;

import com.steamyao.newcode.Utils.ForumUtil;
import com.steamyao.newcode.asyn.EventHandler;
import com.steamyao.newcode.asyn.EventModel;
import com.steamyao.newcode.asyn.EventType;
import com.steamyao.newcode.dataobject.MessageDO;
import com.steamyao.newcode.dataobject.UserDO;
import com.steamyao.newcode.model.EntityType;
import com.steamyao.newcode.service.MessageService;
import com.steamyao.newcode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
 /**
  * 功能描述: 当我 or 我的问题被关注时，给我发消息。
  * @date: 2019/8/13 8:39
  */
@Component
public class FollowHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandler(EventModel model) {
        // 给被关注的人发私信
        int fromId= ForumUtil.SYSTEMCONTROLLER_USERID;
        int toId = model.getEventOwnerId();
        MessageDO message = new MessageDO();
        message.setFromId(fromId);
        message.setToId(toId);
        message.setCreatedDate(new Date());
        message.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));
        UserDO user = userService.getUserById(model.getActorId());
        if (model.getEntityType() == EntityType.ENTITY_QUESTION) {
            message.setContent("用户'" + user.getName() + "'关注了你的问题,http://127.0.0.1:8080/question/" + model.getEntityId());
        } else if (model.getEntityType() == EntityType.ENTITY_USER) {
            message.setContent("用户'" + user.getName() + "'关注了你,http://127.0.0.1:8080/user/" + model.getActorId());
        }
        messageService.addMessage(message);
    }



    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.FOLLOW);
    }
}