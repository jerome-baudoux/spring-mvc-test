package dao;

import java.util.List;

import entities.Person;

public interface PersonDao {

	Person get(int id);
	
	List<Person> list();
	
	Integer save(Person person);
	
	void update(Person person);
}
