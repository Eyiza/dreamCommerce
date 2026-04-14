package org.dreamcommerce.dreamcommerce.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

// The dataSource is not needed as Spring can do it automatically from the properties defined in applications.properties

@Configuration
public class DatasourceConfig {
//    @Bean
//    public DataSource dataSource() {
//        try (HikariDataSource dataSource = new HikariDataSource()) {
//            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//            dataSource.setJdbcUrl("jdbc:mysql://localhost:3308/dreamcommerce?createDatabaseIfNotExist=true");
//            dataSource.setUsername("root");
//            dataSource.setPassword("password");
//
//            return dataSource;
//        }
//    }
}
