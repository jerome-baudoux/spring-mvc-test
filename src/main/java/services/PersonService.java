package services;

import java.util.List;

import entities.Person;

public interface PersonService {

	Person get(int id);
	
	List<Person> list();
	
	Integer save(String firstName, String lastName);
	
	void update(int id, String firstName, String lastName);
}
