package cc.openhome.gossip.dao.impl;

import cc.openhome.gossip.dao.AccountDao;
import cc.openhome.gossip.model.Account;
import cc.openhome.gossip.template.JdbcTemplate;

import java.util.Optional;

public class AccountDaoImpl implements AccountDao {

    private final JdbcTemplate jdbcTemplate;

    public AccountDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_ACCOUNT_SQL = "insert into T_ACCOUNT (name, email, password, salt) values ( ?,?,?,? ) ";

    @Override
    public void createAccount(Account account) {
        String name = account.getName();
        String email = account.getEmail();
        String password = account.getPassword();
        String salt = account.getSalt();
        int update = jdbcTemplate.update(INSERT_ACCOUNT_SQL, name, email, password, salt);
        System.out.println("AccountDaoJdbcImpl createAccount update = " + update);
    }

    private static final String SELECT_ACCOUNT_BY_NAME_SQL = "select name, email, password, salt from T_ACCOUNT where NAME = ? ";

    @Override
    public Optional<Account> getAccountByName(String paramName) {
        if (paramName == null || paramName.length() == 0) {
            return Optional.empty();
        }
        Account account = jdbcTemplate.selectForObject(SELECT_ACCOUNT_BY_NAME_SQL, resultSet -> {
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String salt = resultSet.getString("salt");
            return new Account(name, email, password, salt);
        }, paramName);
        return Optional.ofNullable(account);
    }
}
