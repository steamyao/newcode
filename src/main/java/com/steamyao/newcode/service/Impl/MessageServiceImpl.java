package com.steamyao.newcode.service.Impl;

import com.steamyao.newcode.dao.MessageDOMapper;
import com.steamyao.newcode.dataobject.MessageDO;
import com.steamyao.newcode.service.MessageService;
import com.steamyao.newcode.service.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageDOMapper messageDOMapper;
    @Autowired
    SensitiveService sensitiveService;

    @Override
    public int addMessage(MessageDO message) {
        try {
        message.setContent(sensitiveService.filter(message.getContent()));
        return messageDOMapper.insertSelective(message) > 0 ? message.getId() : 0;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<MessageDO> getConversationDetail(String conversationId, int offset, int limit) {
        return  messageDOMapper.getConversationDetail(conversationId, offset, limit);
    }

    @Override
    public List<MessageDO> getConversationList(int userId, int offset, int limit) {
        return  messageDOMapper.getConversationList(userId, offset, limit);
    }

    @Override
    public void setConversationHasRead(int userId, String conversationId) {
        messageDOMapper.setConversationHasRead(userId,conversationId);
    }

    @Override
    public int getConversationUnreadCount(int userId, String conversationId) {
        return messageDOMapper.getConversationUnreadCount(userId, conversationId);
    }
}