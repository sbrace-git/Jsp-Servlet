package cc.openhome.gossip.service.impl;

import cc.openhome.gossip.dao.AccountDao;
import cc.openhome.gossip.dao.MessageDao;
import cc.openhome.gossip.model.Account;
import cc.openhome.gossip.model.Message;
import cc.openhome.gossip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean userExist(String username) {
        return accountDao.getAccountByName(username).isPresent();
    }

    @Override
    public Optional<Account> tryCreateUser(String email, String username, String password) throws IOException {
        if (existEmail(email) || existUsername(username)) {
            return Optional.empty();
        }
        return createUser(username, email, password);
    }

    private boolean existEmail(String eamil) {
        return accountDao.getAccountByEmail(eamil).isPresent();
    }

    private boolean existUsername(String username) {
        return accountDao.getAccountByName(username).isPresent();
    }

    @Override
    public Optional<Account> verify(String email, String token) {
        Optional<Account> accountByEmail = accountDao.getAccountByEmail(email);
        if (accountByEmail.isPresent()) {
            Account account = accountByEmail.get();
            if (account.getPassword().equals(token)) {
                accountDao.activateAccount(account);
                return accountByEmail;
            }
        }
        return Optional.empty();
    }

    private Optional<Account> createUser(String username, String email, String password) throws IOException {
        Account account = new Account();
        account.setName(username);
        account.setEmail(email);
        account.setPassword(passwordEncoder.encode(password));
        accountDao.createAccount(account);
        return Optional.of(account);
    }

    @Override
    public List<Message> messages(String username) {
        return messageDao.getMessageByUsername(username);
    }

    @Override
    public void addMessage(String username, String blabla) {
        Message message = new Message();
        message.setUsername(username);
        message.setBlabla(blabla);
        messageDao.createMessage(message);
    }

    @Override
    public void deleteMessage(String username, String millis) {
        messageDao.deleteMessage(username, Long.valueOf(millis));
    }

    @Override
    public List<Message> newMessageList() {
        return messageDao.newMessageList();
    }

    @Override
    public Optional<Account> getAccountByNameEmail(String name, String email) {
        return accountDao.getAccountByNameEmail(name, email);
    }

    @Override
    public void resetPassword(String name, String password) {
        accountDao.resetPassword(name, passwordEncoder.encode(password));
    }
}
