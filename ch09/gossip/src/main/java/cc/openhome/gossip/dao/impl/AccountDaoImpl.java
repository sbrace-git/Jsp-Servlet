package cc.openhome.gossip.dao.impl;

import cc.openhome.gossip.dao.AccountDao;
import cc.openhome.gossip.model.Account;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class AccountDaoImpl implements AccountDao {

    private DataSource dataSource;

    public AccountDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String INSERT_ACCOUNT_SQL =
            "insert into T_ACCOUNT (name, email, password, salt) values ( ?,?,?,? ) ";

    @Override
    public void createAccount(Account account) throws IOException {
        String name = account.getName();
        String email = account.getEmail();
        String password = account.getPassword();
        String salt = account.getSalt();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNT_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, salt);
            int i = preparedStatement.executeUpdate();
            System.out.println("AccountDaoJdbcImpl createAccount executeUpdate result = " + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String SELECT_ACCOUNT_BY_NAME_SQL =
            "select name, email, password, salt from T_ACCOUNT where NAME = ? ";

    @Override
    public Optional<Account> getAccountByName(String paramName) {
        if (paramName == null || paramName.length() == 0) {
            return Optional.empty();
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BY_NAME_SQL)) {
            preparedStatement.setString(1, paramName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String salt = resultSet.getString("salt");
                Account account = new Account();
                account.setName(name);
                account.setEmail(email);
                account.setPassword(password);
                account.setSalt(salt);
                return Optional.of(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
