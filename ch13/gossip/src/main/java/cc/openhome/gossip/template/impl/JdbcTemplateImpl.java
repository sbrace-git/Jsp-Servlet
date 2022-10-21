package cc.openhome.gossip.template.impl;

import cc.openhome.gossip.template.JdbcTemplate;
import cc.openhome.gossip.template.ResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTemplateImpl implements JdbcTemplate {

    @Autowired
    private DataSource dataSource;

    @Override
    public int update(String sql, Object... params) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int[] updateBatchTransaction(String[] sqlArray, Object[][] params) {
        try {
            Connection connection = dataSource.getConnection();
            boolean autoCommit = connection.getAutoCommit();
            int[] resultArray = new int[sqlArray.length];
            try {
                connection.setAutoCommit(false);
                for (int sqlIndex = 0; sqlIndex < sqlArray.length; sqlIndex++) {
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sqlArray[sqlIndex])) {
                        Object[] param = params[sqlIndex];
                        for (int paramIndex = 0; paramIndex < param.length; paramIndex++) {
                            preparedStatement.setObject(paramIndex + 1, param[paramIndex]);
                        }
                        int result = preparedStatement.executeUpdate();
                        resultArray[sqlIndex] = result;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw new RuntimeException(e);
            } finally {
                connection.setAutoCommit(autoCommit);
                connection.close();
            }
            return resultArray;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T selectForObject(String sql, ResultSetExtractor<T> resultSetExtractor, Object... params) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetExtractor.extractor(resultSet);
                }
                return null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> selectForObjectList(String sql, ResultSetExtractor<T> resultSetExtractor, Object... params) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ArrayList<T> resultList = new ArrayList<>();
                while (resultSet.next()) {
                    T resultItem = resultSetExtractor.extractor(resultSet);
                    resultList.add(resultItem);
                }
                return resultList;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> selectForObjectList(String sql, ResultSetExtractor<T> resultSetExtractor) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                ArrayList<T> resultList = new ArrayList<>();
                while (resultSet.next()) {
                    T resultItem = resultSetExtractor.extractor(resultSet);
                    resultList.add(resultItem);
                }
                return resultList;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
