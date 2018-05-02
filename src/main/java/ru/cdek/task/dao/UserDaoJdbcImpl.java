package ru.cdek.task.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.cdek.task.entity.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class UserDaoJdbcImpl implements UserDao {

    private static final String INSERT_SQL = "insert into user (name) values(?)";
    private static final String SELECT_ALL_SQL = "select * from user";
    private static final String SELECT_BY_ID_RANGE_SQL = "select * from user where id >= ? and id <= ?";
    private static final String SELECT_BY_ID_MORE_THAN_SQL = "select * from user where id >= ?";
    private static final String SELECT_BY_ID_LESS_THAN_SQL = "select * from user where id <= ?";
    private static final String SELECT_SQL = "select * from user where id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public boolean createUser(User user) {
        int affectedRowsNumber = jdbcTemplate.update(INSERT_SQL, user.getName());
        return affectedRowsNumber > 0;
    }

    @Override
    public List<User> getAll() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_ALL_SQL);
        return mapToList(rows);
    }

    @Override
    public List<User> findUsersByIdRange(long minId, long maxId) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_BY_ID_RANGE_SQL, minId, maxId);
        return mapToList(rows);
    }

    @Override
    public List<User> findUsersByIdMoreThan(long minId) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_BY_ID_MORE_THAN_SQL, minId);
        return mapToList(rows);
    }

    @Override
    public List<User> findUsersByIdLessThan(long maxId) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_BY_ID_LESS_THAN_SQL, maxId);
        return mapToList(rows);
    }

    private List<User> mapToList(List<Map<String, Object>> rows) {
        return rows
                .stream()
                .map(row -> new User((String) row.get("name"), (Long) row.get("id")))
                .collect(Collectors.toList());
    }
}
