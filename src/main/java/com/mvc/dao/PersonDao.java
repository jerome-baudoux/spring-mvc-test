package com.mvc.dao;

import java.util.List;

import com.mvc.entities.Person;

public interface PersonDao {

	Person get(int id, boolean full);
	
	List<Person> list(boolean full);
	
	Integer save(Person person);
	
	void update(Person person);
}
