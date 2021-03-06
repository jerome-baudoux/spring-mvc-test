package com.mvc.conf;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;

import com.mvc.dao.BillDao;
import com.mvc.dao.BillDaoImpl;
import com.mvc.dao.PersonDao;
import com.mvc.dao.PersonDaoImpl;
import com.mvc.entities.Bill;
import com.mvc.entities.Person;

@Configuration
public class DaoConfiguration {
	
	@Bean
	public SessionFactory sessionFactory(DataSource dataSource, String dataBaseDialect) {
		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
		sessionBuilder.addAnnotatedClasses(Person.class, Bill.class);
		sessionBuilder.setProperty("hibernate.dialect", dataBaseDialect);
		sessionBuilder.setProperty("hibernate.show_sql", "true");
		return sessionBuilder.buildSessionFactory();
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
	
	@Bean
	public BillDao billDao(SessionFactory sessionFactory) {
		BillDaoImpl billDao = new BillDaoImpl();
		billDao.setSessionFactory(sessionFactory);
		return billDao;
	}
}
