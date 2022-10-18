package cc.openhome.gossip.dao;

import cc.openhome.gossip.model.Account;

import java.io.IOException;
import java.util.Optional;

public interface AccountDao {

    void createAccount(Account account) throws IOException;

    Optional<Account> getAccountByName(String name);

    Optional<Account> getAccountByEmail(String email);

    void activateAccount(Account account);

}
