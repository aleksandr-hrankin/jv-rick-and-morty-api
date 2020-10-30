package ua.antibyte.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
@ComponentScan(basePackages = {
        "ua.antibyte.service",
        "ua.antibyte.dao"
})
@PropertySource("classpath:application.properties")
public class AppConfig {
    private final Environment environment;

    public AppConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("db.driver"));
        dataSource.setUrl(environment.getProperty("db.url"));
        dataSource.setUsername(environment.getProperty("db.username"));
        dataSource.setPassword(environment.getProperty("db.password"));
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean getLocalSessionFactoryBean() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        properties.setProperty("hibernate.hbm2ddl.auto",
                environment.getProperty("hibernate.hbm2ddl.auto"));
        sessionFactory.setHibernateProperties(properties);
        sessionFactory.setPackagesToScan("ua.antibyte.model");
        return sessionFactory;
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Bean
    public CloseableHttpClient getHttpClient() {
        return HttpClients.createDefault();
    }
}
