package com.mvc.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.mvc.aop.LoggerAspect.Log;
import com.mvc.entities.Person;

public class PersonDaoImpl extends HibernateDaoSupport implements PersonDao {

	@Log
	@Override
	public Person get(int id, boolean full) {
		Person person = getHibernateTemplate().get(Person.class, id);
		if (full) {
			Hibernate.initialize(person.getBills());
		}
		return person;
	}

	@Log
	@Override
	@SuppressWarnings("unchecked")
	public List<Person> list(boolean full) {
		return getHibernateTemplate().execute((Session session) -> {
				
			// Get the list
			List<Person> persons =  session.createCriteria(Person.class).list();

			// If we want the full content, fetch bills
			if(full) {
				persons.forEach((person)->{
					Hibernate.initialize(person.getBills());
				});
			}

			return persons;
		});
	}

	@Log(logCall=false)
	@Override
	public Integer save(Person person) {
		return (Integer) getHibernateTemplate().save(person);
	}

	@Log(logReturn=false)
	@Override
	public void update(Person person) {
		getHibernateTemplate().update(person);
	}
}
