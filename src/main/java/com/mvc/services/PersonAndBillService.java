package com.mvc.services;

import java.util.List;

import com.mvc.entities.Bill;
import com.mvc.entities.Person;

public interface PersonAndBillService {
	
	/*
	 * Persons
	 */

	Person getPerson(int id);

	Person getPerson(int id, boolean full);
	
	List<Person> listPersons();
	
	List<Person> listPersons(boolean full);
	
	Integer savePerson(String firstName, String lastName);
	
	void updatePerson(int id, String firstName, String lastName);
	
	/*
	 * Bills
	 */
	
	Bill getBill(int id);

	List<Bill> listBills();
	
	Integer saveBill(int personId, int price);
	
	void updateBill(int billId, int personId, int price);
}
