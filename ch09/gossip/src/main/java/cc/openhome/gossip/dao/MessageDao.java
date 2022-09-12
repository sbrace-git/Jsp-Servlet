package cc.openhome.gossip.dao;

import cc.openhome.gossip.model.Message;

import java.util.List;

public interface MessageDao {

    List<Message> getMessageByUsername(String username);

    void createMessage(Message message);

    void deleteMessage(String username, Long millis);

    List<Message> newMessageList();

}
