package com.steamyao.newcode.service;

import com.steamyao.newcode.dataobject.MessageDO;

import java.util.List;

/**
 * @Package com.steamyao.newcode.service
 * @date 2019/8/7 9:15
 * @description
 */
public interface MessageService  {

   int addMessage(MessageDO message);

    List<MessageDO> getConversationDetail(String conversationId, int offset, int limit);

    List<MessageDO> getConversationList(int userId, int offset, int limit);

    void setConversationHasRead(int userId, String conversationId);

    int getConversationUnreadCount(int userId, String conversationId);
}
