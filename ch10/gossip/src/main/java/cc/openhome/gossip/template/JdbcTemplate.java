package cc.openhome.gossip.template;

import java.util.List;

public interface JdbcTemplate {
    int update(String sql, Object... params);

    int[] updateBatchTransaction(String[] sqlArray, Object[][] params);

    <T> T selectForObject(String sql, ResultSetExtractor<T> resultSetExtractor, Object... params);

    <T> List<T> selectForObjectList(String sql, ResultSetExtractor<T> resultSetExtractor, Object... params);

    <T> List<T> selectForObjectList(String sql, ResultSetExtractor<T> resultSetExtractor);
}