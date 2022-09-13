package cc.openhome.gossip.dao.impl;

import cc.openhome.gossip.dao.MessageDao;
import cc.openhome.gossip.model.Message;
import cc.openhome.gossip.template.JdbcTemplate;

import java.util.List;

public class MessageDaoImpl implements MessageDao {

    private final JdbcTemplate jdbcTemplate;

    public MessageDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_MESSAGE_LIST = "select username, millis, blabla from T_MESSAGE where USERNAME = ? order by millis desc ";

    @Override
    public List<Message> getMessageByUsername(String paramUsername) {
        return jdbcTemplate.selectForObjectList(SELECT_MESSAGE_LIST, resultSet -> {
            String username = resultSet.getString("username");
            long millis = resultSet.getLong("millis");
            String blabla = resultSet.getString("blabla");
            return new Message(username, millis, blabla);
        }, paramUsername);
    }

    private static final String INSERT_MESSAGE_SQL = "insert into T_MESSAGE (USERNAME, MILLIS, BLABLA) values ( ?,?,? )";

    @Override
    public void createMessage(Message message) {
        String username = message.getUsername();
        String blabla = message.getBlabla();
        jdbcTemplate.update(INSERT_MESSAGE_SQL, username, System.currentTimeMillis(), blabla);
    }

    private static final String DELETE_MESSAGE_SQL = "delete from T_MESSAGE where USERNAME = ? and MILLIS = ?";

    @Override
    public void deleteMessage(String username, Long millis) {
        jdbcTemplate.update(DELETE_MESSAGE_SQL, username, millis);
    }

    private static final String SELECT_NEW_MESSAGE_LIST = "select username, millis, blabla from T_MESSAGE order by millis desc limit 5 ";

    @Override
    public List<Message> newMessageList() {
        return jdbcTemplate.selectForObjectList(SELECT_NEW_MESSAGE_LIST, resultSet -> {
            String username = resultSet.getString("username");
            long millis = resultSet.getLong("millis");
            String blabla = resultSet.getString("blabla");
            return new Message(username, millis, blabla);
        });
    }
}
