package com.example.cityfun.config;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @author xiaohan
 * @since 2019-08-20 20:37
 */
@Configuration
public class DataSourceConfig {

    @Value("${datasource.driverClass}")
    private String jdbcDriver;

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.password}")
    private String password;

    @Value("${datasource.jdbcUrl}")
    private String jdbcUrl;

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(this.jdbcDriver);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setUrl(this.jdbcUrl);
        //dataSource.setMaxActive(this.maxActive);
        //dataSource.setValidationQuery(this.validationQuery);
        //dataSource.setTestOnBorrow(this.testOnBorrow);
        //dataSource.setTestOnReturn(this.testOnReturn);
        //dataSource.setTestWhileIdle(this.testWhileIdle);
        //dataSource.setTimeBetweenConnectErrorMillis(this.timeBetweenEvictionRunsMills);
        //dataSource.setMinEvictableIdleTimeMillis(minEvictableTimeMills);
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}
