package cc.openhome.gossip.dao.impl;

import cc.openhome.gossip.constant.Role;
import cc.openhome.gossip.dao.AccountDao;
import cc.openhome.gossip.model.Account;
import cc.openhome.gossip.template.JdbcTemplate;

import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountDaoImpl implements AccountDao {

    private final Logger logger = Logger.getLogger(AccountDaoImpl.class.getName());

    private final JdbcTemplate jdbcTemplate;

    public AccountDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_ACCOUNT_SQL = "insert into T_ACCOUNT (name, email, password, salt) values ( ?,?,?,? ) ";
    private static final String INSERT_ACCOUNT_ROLE_SQL = "insert into T_ACCOUNT_ROLE (name, role) values ( ?,? ) ";

    @Override
    public void createAccount(Account account) {
        String name = account.getName();
        String email = account.getEmail();
        String password = account.getPassword();
        String salt = account.getSalt();
        int[] updateBatchResult = jdbcTemplate.updateBatchTransaction(new String[]{INSERT_ACCOUNT_SQL, INSERT_ACCOUNT_ROLE_SQL},
                new Object[][]{{name, email, password, salt}, {name, Role.unverified}});
        logger.log(Level.INFO, "AccountDaoJdbcImpl createAccount insertAccount = {0}, insertAccountRole = {1}",
                Arrays.stream(updateBatchResult).boxed().toArray());
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

    private static final String SELECT_ACCOUNT_BY_EMAIL_SQL = "select name, email, password, salt from T_ACCOUNT where EMAIL = ? ";

    @Override
    public Optional<Account> getAccountByEmail(String emailParam) {
        if (emailParam == null || emailParam.length() == 0) {
            return Optional.empty();
        }
        Account account = jdbcTemplate.selectForObject(SELECT_ACCOUNT_BY_EMAIL_SQL, resultSet -> {
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String salt = resultSet.getString("salt");
            return new Account(name, email, password, salt);
        }, emailParam);
        return Optional.ofNullable(account);
    }

    private static final String UPDATE_ACCOUNT_ROLE_BY_NAME_SQL = "update T_ACCOUNT SET ROLE = ? where NAME = ? ";

    @Override
    public void activateAccount(Account account) {
        String name = account.getName();
        if (null == name || name.length() == 0) {
            return;
        }
        int update = jdbcTemplate.update(UPDATE_ACCOUNT_ROLE_BY_NAME_SQL, name, Role.member);
        logger.log(Level.INFO, "update = {0}", update);
    }
}
