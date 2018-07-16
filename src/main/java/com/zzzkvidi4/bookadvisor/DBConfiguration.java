package com.zzzkvidi4.bookadvisor;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.logging.Logger;

@Configuration
@EnableTransactionManagement
public class DBConfiguration {
    private Logger logger = Logger.getLogger(getClass().getName());
    @Bean
    public DataSource dataSource(){
        logger.info("Configured data source");
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public SessionFactory sessionFactory(){
        logger.info("Configured session factory");
        return new LocalSessionFactoryBuilder(dataSource())
                .scanPackages("com.zzzkvidi4.bookadvisor.model.db")
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        logger.info("Configured transaction manager");
        HibernateTransactionManager manager = new HibernateTransactionManager();
        manager.setSessionFactory(sessionFactory());
        manager.setDataSource(dataSource());
        return manager;
    }
}
