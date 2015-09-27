package com.mvc.services;

import java.util.List;

import com.mvc.entities.Person;

public interface PersonService {

	Person get(int id);

	Person get(int id, boolean full);
	
	List<Person> list();
	
	List<Person> list(boolean full);
	
	Integer save(String firstName, String lastName);
	
	void update(int id, String firstName, String lastName);
}
