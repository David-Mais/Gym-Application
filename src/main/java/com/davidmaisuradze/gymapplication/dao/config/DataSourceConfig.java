package com.davidmaisuradze.gymapplication.dao.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String dataUrl;

    @Value("${spring.datasource.username}")
    private String dataUsername;

    @Value("${spring.datasource.password}")
    private String dataPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dataUrl);
        config.setUsername(dataUsername);
        config.setPassword(dataPassword);
        config.setDriverClassName(driver);

        return new HikariDataSource(config);
    }
}
