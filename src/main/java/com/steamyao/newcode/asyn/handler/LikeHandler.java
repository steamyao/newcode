package com.steamyao.newcode.asyn.handler;

import com.steamyao.newcode.asyn.EventHandler;
import com.steamyao.newcode.asyn.EventModel;
import com.steamyao.newcode.asyn.EventType;
import com.steamyao.newcode.dataobject.MessageDO;
import com.steamyao.newcode.dataobject.UserDO;
import com.steamyao.newcode.service.MessageService;
import com.steamyao.newcode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Package com.steamyao.newcode.asyn.handler
 * @date 2019/8/8 10:26
 * @description  当别人赞了我的评论时，给我发消息
 */
@Component
public class LikeHandler implements EventHandler {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @Override
    public void doHandler(EventModel model) {
        int fromId = model.getActorId();
        int toId = model.getEventOwnerId();
        MessageDO message = new MessageDO();
        message.setFromId(fromId);
        message.setToId(toId);
        message.setCreatedDate(new Date());
        message.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));
        UserDO user = userService.getUserById(model.getActorId());
        message.setContent("用户'" + user.getName() + "'赞了你的评论:"+model.getExts("content")+"   http://127.0.0.1:8080/question/" + model.getExts("questionId"));
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
