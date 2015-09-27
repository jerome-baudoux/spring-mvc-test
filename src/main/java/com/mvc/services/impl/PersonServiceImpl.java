package com.mvc.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvc.dao.PersonDao;
import com.mvc.entities.Person;
import com.mvc.services.PersonService;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonDao personDao;

	@Override
	public Person get(int id) {
		return get(id, false);
	}

	@Override
	public Person get(int id, boolean full) {
		return this.personDao.get(id, full);
	}

	@Override
	public List<Person> list() {
		return list(false);
	}

	@Override
	public List<Person> list(boolean full) {
		return this.personDao.list(full);
	}

	@Override
	public void update(int id, String firstName, String lastName) {
		
		Person person = new Person();
		person.setId(id);
		person.setFirstName(firstName);
		person.setLastName(lastName);
		
		this.personDao.update(person);
	}

	@Override
	public Integer save(String firstName, String lastName) {
		
		Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		
		return this.personDao.save(person);
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}
}
