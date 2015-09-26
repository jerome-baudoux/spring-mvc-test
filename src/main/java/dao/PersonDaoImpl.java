package dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import entities.Person;

public class PersonDaoImpl extends HibernateDaoSupport implements PersonDao {

	@Override
	public Person get(int id) {
		return getHibernateTemplate().get(Person.class, id);
	}

	@Override
	public List<Person> list() {
		return getHibernateTemplate().execute(new HibernateCallback<List<Person>>() {

			@Override
			@SuppressWarnings("unchecked")
			public List<Person> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createCriteria(Person.class).list();
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
