package com.example.cityfun.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ClassUtils;

/**
 * @author xiaohan
 * @since 2019-08-20 20:34
 */
@Configuration
@MapperScan(basePackages = "com.alipay.ipay.gndata.dal",
    sqlSessionTemplateRef = "sqlSessionTemplate")
public class MybatisConfig {
    private static Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        logger.debug("start sqlSessionFactory");
        final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        //  设置datasource
        sqlSessionFactory.setDataSource(dataSource);
        //  设置mybatis configuration 扫描路径
        sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        sqlSessionFactory.setFailFast(true);
        //自动扫描entity目录
        sqlSessionFactory.setMapperLocations(getResource("mappers", "**/*.xml"));
        SqlMonitorManager sqlMonitorManager = new SqlMonitorManager();
        Properties properties = new Properties();
        properties.setProperty("show_sql", "true");
        sqlMonitorManager.setProperties(properties);

        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties property = new Properties();
        properties.setProperty("databaseType", "mysql");
        pageInterceptor.setProperties(property);
        sqlSessionFactory.setPlugins(new Interceptor[] {sqlMonitorManager, pageInterceptor});
        return sqlSessionFactory.getObject();
    }


    private Resource[] getResource(String basePackage, String pattern) throws IOException {
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
            + ClassUtils.convertClassNameToResourcePath(new StandardEnvironment()
            .resolveRequiredPlaceholders(basePackage)) + "/" + pattern;
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
        return resources;
    }


}
