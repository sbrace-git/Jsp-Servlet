package cc.openhome.gossip.service;

import cc.openhome.gossip.model.Account;
import cc.openhome.gossip.model.Message;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean userExist(String username);

    Optional<Account> tryCreateUser(String email, String username, String password) throws IOException;

    Optional<Account> verify(String email, String token);

    Optional<String> encryptedPassword(String username, String password);

    List<Message> messages(String username);

    void addMessage(String username, String blabla);

    void deleteMessage(String username, String millis);

    List<Message> newMessageList();

    Optional<Account> getAccountByNameEmail(String name, String email);

    void resetPassword(String name, String password);
}
