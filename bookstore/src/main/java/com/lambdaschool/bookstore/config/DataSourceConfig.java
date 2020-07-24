package com.lambdaschool.bookstore.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig
{
    /**
     * Reads the value from the environment variable spring.datasource.url. If the environment variable does not exists, defaults to a null string
     */
    @Value("${spring.datasource.url:}")
    private String dbUrl;

    /**
     * Reads values from application.properties. If local.run.db does not exist, default to H2
     */
    @Value("${local.run.db:H2}")
    private String dbValue;

    String myUrlString;
    String myDriverClass;
    String myDBUser;
    String myDBPassword;

    @Bean
    public DataSource dataSource()
    {
        if (dbValue.equalsIgnoreCase("POSTGRESQL"))
        {
            // assumes PostgreSQL on Heroku
            HikariConfig config = new HikariConfig();
            config.setDriverClassName("org.postgresql.Driver");
            config.setJdbcUrl(dbUrl);
            return new HikariDataSource(config);
        } else
        {
            // Assumes H2
            myUrlString = "jdbc:h2:mem:testdb";
            myDriverClass = "org.h2.Driver";
            myDBUser = "sa";
            myDBPassword = "";

            return DataSourceBuilder.create()
                .username(myDBUser)
                .password(myDBPassword)
                .url(myUrlString)
                .driverClassName(myDriverClass)
                .build();
        }
    }
}
