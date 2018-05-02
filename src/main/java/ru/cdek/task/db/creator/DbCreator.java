package ru.cdek.task.db.creator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Класс служит для инициализации БД для приложения. После создания бина идет
 * единовременное обращение к бд с ddl-скриптом.
 * При усложнении схемы, следует заменить его тулзой для миграции (flyway, liquibase и т.д.).
 * Чтобы отказаться от этого класса, его следует просто удалить(снять аннотацию с класса),
 * никаких других действий не требуется.
 */
@Component
public class DbCreator implements InitializingBean {

    private JdbcTemplate jdbcTemplate;

    private String initSql;

    private static final Logger LOGGER = LoggerFactory.getLogger(DbCreator.class);

    @Autowired
    public DbCreator(JdbcTemplate jdbcTemplate, @Value("${db.init}") String initSql) {
        this.jdbcTemplate = jdbcTemplate;
        this.initSql = initSql;
    }


    @Override
    public void afterPropertiesSet() {
        LOGGER.debug("Near database creating...");
        jdbcTemplate.execute(initSql);
    }
}
