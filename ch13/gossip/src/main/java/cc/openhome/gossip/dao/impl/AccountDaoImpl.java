package cc.openhome.gossip.dao.impl;

import cc.openhome.gossip.constant.Role;
import cc.openhome.gossip.dao.AccountDao;
import cc.openhome.gossip.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class AccountDaoImpl implements AccountDao {

    private final Logger logger = Logger.getLogger(AccountDaoImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionTemplate transactionTemplate;

    private static final String INSERT_ACCOUNT_SQL = "insert into T_ACCOUNT (name, email, password, salt) values ( ?,?,?,? ) ";
    private static final String INSERT_ACCOUNT_ROLE_SQL = "insert into T_ACCOUNT_ROLE (name, role) values ( ?,? ) ";
    private static final ResultSetExtractor<Account> ACCOUNT_RESULT_SET_EXTRACTOR = resultSet -> {
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String salt = resultSet.getString("salt");
            return new Account(name, email, password, salt);
        }
        return null;
    };

    // TODO: 2022/10/22 @Transactional
    @Override
    public void createAccount(Account account) {
        String name = account.getName();
        String email = account.getEmail();
        String password = account.getPassword();
        String salt = account.getSalt();
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    int insertAccount = jdbcTemplate.update(INSERT_ACCOUNT_SQL, name, email, password, salt);
                    int insertAccountRole = jdbcTemplate.update(INSERT_ACCOUNT_ROLE_SQL, name, Role.unverified);
                    logger.log(Level.INFO, "AccountDaoJdbcImpl createAccount insertAccount = {0}, insertAccountRole = {1}",
                            new Object[]{insertAccount, insertAccountRole});
                } catch (Exception e) {
                    status.setRollbackOnly();
                    logger.log(Level.WARNING, "createAccount error", e);
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private static final String SELECT_ACCOUNT_BY_NAME_SQL = "select name, email, password, salt from T_ACCOUNT where NAME = ? ";

    @Override
    public Optional<Account> getAccountByName(String paramName) {
        if (paramName == null || paramName.length() == 0) {
            return Optional.empty();
        }
        Account account = jdbcTemplate.query(SELECT_ACCOUNT_BY_NAME_SQL, ACCOUNT_RESULT_SET_EXTRACTOR, paramName);
        return Optional.ofNullable(account);
    }

    private static final String SELECT_ACCOUNT_BY_EMAIL_SQL = "select name, email, password, salt from T_ACCOUNT where EMAIL = ? ";

    @Override
    public Optional<Account> getAccountByEmail(String emailParam) {
        if (emailParam == null || emailParam.length() == 0) {
            return Optional.empty();
        }
        Account account = jdbcTemplate.query(SELECT_ACCOUNT_BY_EMAIL_SQL,
                ACCOUNT_RESULT_SET_EXTRACTOR, emailParam);
        return Optional.ofNullable(account);
    }

    private static final String UPDATE_ACCOUNT_ROLE_BY_NAME_SQL = "update T_ACCOUNT_ROLE SET ROLE = ? where NAME = ? and role = ?";

    @Override
    public void activateAccount(Account account) {
        String name = account.getName();
        if (null == name || name.length() == 0) {
            return;
        }
        int update = jdbcTemplate.update(UPDATE_ACCOUNT_ROLE_BY_NAME_SQL, Role.member, name, Role.unverified);
        logger.log(Level.INFO, "update = {0}", update);
    }


    private static final String SELECT_ACCOUNT_BY_NAME_EMAIL_SQL = "select name, email, password, salt from T_ACCOUNT where NAME = ? AND EMAIL = ?";

    @Override
    public Optional<Account> getAccountByNameEmail(String nameParam, String emailParam) {
        Account account = jdbcTemplate.query(SELECT_ACCOUNT_BY_NAME_EMAIL_SQL,
                ACCOUNT_RESULT_SET_EXTRACTOR, nameParam, emailParam);
        return Optional.ofNullable(account);
    }

    public static final String UPDATE_ACCOUNT_PASSWORD_SQL = "update T_ACCOUNT set password = ?, salt = ? where name = ? ";

    @Override
    public void resetPassword(String name, String password, String salt) {
        int update = jdbcTemplate.update(UPDATE_ACCOUNT_PASSWORD_SQL, password, salt, name);
        logger.log(Level.INFO, "update = {0}", update);
    }
}
