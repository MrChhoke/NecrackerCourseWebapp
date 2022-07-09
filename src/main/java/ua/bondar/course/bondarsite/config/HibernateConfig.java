package ua.bondar.course.bondarsite.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@Slf4j
public class HibernateConfig {

    @Value("${db.datasource.driver-class-name}")
    public String driverClassName;

    @Value("${db.datasource.url}")
    public String url;

    @Value("${db.datasource.username}")
    public String username;

    @Value("${db.datasource.password}")
    public String passwordDB;

    @Bean(name="entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory(){
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("ua.bondar.course.bondarsite");
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }

    @Bean
    public DataSource dataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(passwordDB);
        dataSourceBuilder.url(url);
        return dataSourceBuilder.build();
    }

    private final Properties hibernateProperties(){
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("properties.hibernate.format_sql", "true");
        hibernateProperties.setProperty("hibernate.show_sql", "false");
        hibernateProperties.setProperty("org.hibernate.dialect", "PostgreSQLDialect");
        hibernateProperties.setProperty("hibernate.default_schema", "myschema");
        return hibernateProperties;
    }

}
