package com.phonebook.dao;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class AbstractDao {
    private static JdbcTemplate jdbcTemplate = null;
    private static NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;

    private static DataSource getDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:src/main/resources/phonebook.db");
        return dataSource;
    }

    protected JdbcTemplate getJdbcTemplate(){
        if(jdbcTemplate == null){
            instanciateJdbcTemplate();
        }
        return jdbcTemplate;
    }

    protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(){
        if(namedParameterJdbcTemplate == null){
            instanciateNamedParameterJdbcTemplate();
        }
        return namedParameterJdbcTemplate;
    }

    private void instanciateJdbcTemplate(){
        jdbcTemplate = new JdbcTemplate(getDataSource());
    }

    private void instanciateNamedParameterJdbcTemplate(){
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate());
    }
}
