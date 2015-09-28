package com.mvc.conf;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.mvc.dao.PersonDao;
import com.mvc.dao.PersonDaoImpl;
import com.mvc.entities.Bill;
import com.mvc.entities.Person;

@Configuration
public class DaoConfiguration {
	
	@Bean
	public AnnotationSessionFactoryBean sessionFactory(DataSource dataSource) {
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
	
		AnnotationSessionFactoryBean sessionFactoryBean = new AnnotationSessionFactoryBean();
		sessionFactoryBean.setAnnotatedClasses(Person.class, Bill.class);
		sessionFactoryBean.setHibernateProperties(hibernateProperties);
		sessionFactoryBean.setDataSource(dataSource);
		
		return sessionFactoryBean;
	}
	
	@Bean 
	public PlatformTransactionManager transactionManager(DataSource dataSource, SessionFactory sessionFactory) {
		HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
		hibernateTransactionManager.setDataSource(dataSource);
		hibernateTransactionManager.setSessionFactory(sessionFactory);
		return hibernateTransactionManager;
	}
	
	@Bean
	public PersonDao personDao(SessionFactory sessionFactory) {
		PersonDaoImpl personDao = new PersonDaoImpl();
		personDao.setSessionFactory(sessionFactory);
		return personDao;
	}
}
