package cc.openhome.gossip.service;

import cc.openhome.gossip.dao.AccountDao;
import cc.openhome.gossip.dao.MessageDao;
import cc.openhome.gossip.model.Account;
import cc.openhome.gossip.model.Message;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class UserService {

    private final AccountDao accountDao;
    private final MessageDao messageDao;

    public UserService(AccountDao accountDao, MessageDao messageDao) {
        this.accountDao = accountDao;
        this.messageDao = messageDao;
    }

    public boolean userExist(String username) {
        return accountDao.getAccountByName(username).isPresent();
    }

    public void tryCreateUser(String email, String username, String password) throws IOException {
        if (!accountDao.getAccountByName(username).isPresent()) {
            createUser(username, email, password);
        }
    }

    private void createUser(String username, String email, String password) throws IOException {
        int salt = ThreadLocalRandom.current().nextInt();
        String encrypt = String.valueOf(salt + password.hashCode());
        Account account = new Account();
        account.setName(username);
        account.setEmail(email);
        account.setSalt(String.valueOf(salt));
        account.setPassword(encrypt);
        accountDao.createAccount(account);
    }

    public boolean login(String username, String password) throws IOException {
        Optional<Account> accountByName = accountDao.getAccountByName(username);
        if (accountByName.isPresent()) {
            Account account = accountByName.get();
            int encrypt = Integer.parseInt(account.getPassword());
            int salt = Integer.parseInt(account.getSalt());
            return password.hashCode() + salt == encrypt;
        }
        return false;
    }

    public Optional<String> encryptedPassword(String username, String password) {
        return accountDao.getAccountByName(username)
                .map(Account::getSalt)
                .map(salt -> Integer.parseInt(salt) + password.hashCode())
                .map(String::valueOf);
    }


    public List<Message> messages(String username) {
        return messageDao.getMessageByUsername(username);
    }

    public void addMessage(String username, String blabla) {
        Message message = new Message();
        message.setUsername(username);
        message.setBlabla(blabla);
        messageDao.createMessage(message);
    }

    public void deleteMessage(String username, String millis) {
        messageDao.deleteMessage(username, Long.valueOf(millis));
    }

    public List<Message> newMessageList() {
        return messageDao.newMessageList();
    }
}
