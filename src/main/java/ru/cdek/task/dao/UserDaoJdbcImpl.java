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
        return rows
                .stream()
                .map(row -> new User((String) row.get("name"), (Long) row.get("id")))
                .collect(Collectors.toList());
    }

    @Override
    public User getUserById(long id) {
        return jdbcTemplate.queryForObject(SELECT_SQL, new Object[]{id}, (rs, n) ->
                new User(rs.getString("name"), rs.getLong("id")));
    }
}
