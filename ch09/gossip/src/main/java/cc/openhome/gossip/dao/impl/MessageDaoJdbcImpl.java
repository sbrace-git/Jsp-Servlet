package cc.openhome.gossip.dao.impl;

import cc.openhome.gossip.dao.MessageDao;
import cc.openhome.gossip.model.Message;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageDaoJdbcImpl implements MessageDao {

    private DataSource dataSource;

    public MessageDaoJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String SELECT_MESSAGE_LIST = "select username, millis, blabla from T_MESSAGE where USERNAME = ? order by millis desc ";

    @Override
    public List<Message> getMessageByUsername(String paramUsername) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MESSAGE_LIST)) {
            preparedStatement.setString(1, paramUsername);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Message> messages = new ArrayList<>();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                long millis = resultSet.getLong("millis");
                String blabla = resultSet.getString("blabla");
                messages.add(new Message(username, millis, blabla));
            }
            return messages;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static final String INSERT_MESSAGE_SQL = "insert into T_MESSAGE (USERNAME, MILLIS, BLABLA) values ( ?,?,? )";

    @Override
    public void createMessage(Message message) {
        String username = message.getUsername();
        String blabla = message.getBlabla();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MESSAGE_SQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setLong(2, System.currentTimeMillis());
            preparedStatement.setString(3, blabla);
            int i = preparedStatement.executeUpdate();
            System.out.println("MessageDaoJdbcImpl createMessage insert result = " + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String DELETE_MESSAGE_SQL = "delete from T_MESSAGE where USERNAME = ? and MILLIS = ?";

    @Override
    public void deleteMessage(String username, Long millis) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MESSAGE_SQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setLong(2, millis);
            int i = preparedStatement.executeUpdate();
            System.out.println("MessageDaoJdbcImpl deleteMessage delete result = " + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
