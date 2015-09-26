package services.impl;

import java.util.List;

import dao.PersonDao;
import entities.Person;
import services.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonDao personDao;

	@Override
	public Person get(int id) {
		return this.personDao.get(id);
	}

	@Override
	public List<Person> list() {
		return this.personDao.list();
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
