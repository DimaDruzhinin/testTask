package ru.cdek.task;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

import static org.springframework.beans.factory.config.PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_NEVER;

@Configuration
@ComponentScan("ru.cdek.task")
@EnableTransactionManagement
@EnableWebMvc
public class AppConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfiguration.class);

    @Bean
    public static PropertyPlaceholderConfigurer placeholderConfigurer(@Value("#{systemProperties['configDir']}") String configDir) {
        Objects.requireNonNull(configDir, "configDir property is not specified");
        String propLocation = configDir + "/application.properties";
        String initDbScriptLocation = configDir + "/init.sql";
        PropertyPlaceholderConfigurer placeholderConfigurer = new PropertyPlaceholderConfigurer();
        placeholderConfigurer.setSystemPropertiesMode(SYSTEM_PROPERTIES_MODE_NEVER);
        placeholderConfigurer.setFileEncoding("UTF-8");
        Properties properties = new Properties();
        try (InputStream is = FileUtils.openInputStream(new File(propLocation))) {
            String initDbScript = FileUtils.readFileToString(new File(initDbScriptLocation), "UTF-8");
            properties.load(is);
            properties.setProperty("db.init", initDbScript);
        } catch (IOException e) {
            LOGGER.error("Problem occur loading application properties from {} or {}", propLocation, initDbScriptLocation);
            throw new IllegalArgumentException(e);
        }
        placeholderConfigurer.setProperties(properties);
        LOGGER.info("Found configuration from location {}", propLocation);
        return placeholderConfigurer;
    }

    @Bean
    public DataSource dataSource() {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        return dataSourceLookup.getDataSource("jdbc/cdekTask");
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
