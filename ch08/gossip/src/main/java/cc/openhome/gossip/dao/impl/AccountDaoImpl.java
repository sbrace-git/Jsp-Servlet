package cc.openhome.gossip.dao.impl;

import cc.openhome.gossip.dao.AccountDao;
import cc.openhome.gossip.model.Account;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class AccountDaoImpl implements AccountDao {

    private final String USERS;

    public AccountDaoImpl(String USERS) {
        this.USERS = USERS;
    }

    @Override
    public void createAccount(Account account) throws IOException {
        String name = account.getName();
        String email = account.getEmail();
        String password = account.getPassword();
        String salt = account.getSalt();
        Path userHome = Paths.get(USERS, name);
        Files.createDirectories(userHome);
        Path profile = userHome.resolve("profile");
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(profile);) {
            bufferedWriter.write(String.format("%s\t%s\t%s", email, password, salt));
        }
    }



    @Override
    public Optional<Account> getAccountByName(String name) {
        if (name == null || name.length() == 0) {
            return Optional.empty();
        }
        Path userHome = Paths.get(USERS, name);
        if (Files.notExists(userHome)) {
            return Optional.empty();
        }
        Path profile = userHome.resolve("profile");
        try (BufferedReader bufferedReader = Files.newBufferedReader(profile)) {
            String[] data = bufferedReader.readLine().split("\t");
            String email = data[0];
            String password = data[1];
            String salt = data[2];
            Account account = new Account();
            account.setName(name);
            account.setEmail(email);
            account.setPassword(password);
            account.setSalt(salt);
            return Optional.of(account);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
