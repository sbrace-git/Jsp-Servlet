package cc.openhome.gossip.service.impl;

import cc.openhome.gossip.dao.AccountDao;
import cc.openhome.gossip.dao.MessageDao;
import cc.openhome.gossip.model.Account;
import cc.openhome.gossip.model.Message;
import cc.openhome.gossip.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class UserServiceImpl implements UserService {

    private final AccountDao accountDao;
    private final MessageDao messageDao;

    public UserServiceImpl(AccountDao accountDao, MessageDao messageDao) {
        this.accountDao = accountDao;
        this.messageDao = messageDao;
    }

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
        int salt = ThreadLocalRandom.current().nextInt();
        String encrypt = String.valueOf(salt + password.hashCode());
        Account account = new Account();
        account.setName(username);
        account.setEmail(email);
        account.setSalt(String.valueOf(salt));
        account.setPassword(encrypt);
        accountDao.createAccount(account);
        return Optional.of(account);
    }

    @Deprecated
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

    @Override
    public Optional<String> encryptedPassword(String username, String password) {
        return accountDao.getAccountByName(username)
                .map(Account::getSalt)
                .map(salt -> Integer.parseInt(salt) + password.hashCode())
                .map(String::valueOf);
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
        int salt = ThreadLocalRandom.current().nextInt();
        String encrypt = String.valueOf(salt + password.hashCode());
        accountDao.resetPassword(name, encrypt, String.valueOf(salt));
    }
}
