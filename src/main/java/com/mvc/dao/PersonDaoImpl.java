package com.mvc.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.mvc.entities.Person;

public class PersonDaoImpl extends HibernateDaoSupport implements PersonDao {

	@Override
	public Person get(int id, boolean full) {
		Person person = getHibernateTemplate().get(Person.class, id);
		if (full) {
			Hibernate.initialize(person.getBills());
		}
		return person;
	}

	@Override
	public List<Person> list(boolean full) {
		return getHibernateTemplate().execute(new HibernateCallback<List<Person>>() {

			@Override
			@SuppressWarnings("unchecked")
			public List<Person> doInHibernate(Session session) throws HibernateException {
				
				// Get the list
				List<Person> persons = session.createCriteria(Person.class).list();
				
				// If we want the full content, fetch bills
				if(full) {
					persons.forEach((person)->{
						Hibernate.initialize(person.getBills());
					});
				}
					
				return persons;
			}
		});
	}

	@Override
	public Integer save(Person person) {
		return (Integer) getHibernateTemplate().save(person);
	}
	
	@Override
	public void update(Person person) {
		getHibernateTemplate().update(person);
	}
}
