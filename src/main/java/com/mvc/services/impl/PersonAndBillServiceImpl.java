package com.mvc.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvc.dao.BillDao;
import com.mvc.dao.PersonDao;
import com.mvc.entities.Bill;
import com.mvc.entities.Person;
import com.mvc.services.PersonAndBillService;

@Service
@Transactional
public class PersonAndBillServiceImpl implements PersonAndBillService {

	@Autowired
	private PersonDao personDao;

	@Autowired
	private BillDao billDao;
	
	/*
	 * Persons
	 */

	@Override
	public Person getPerson(int id) {
		return getPerson(id, false);
	}

	@Override
	public Person getPerson(int id, boolean full) {
		return this.personDao.get(id, full);
	}

	@Override
	public List<Person> listPersons() {
		return listPersons(false);
	}

	@Override
	public List<Person> listPersons(boolean full) {
		return this.personDao.list(full);
	}

	@Override
	public void updatePerson(int id, String firstName, String lastName) {
		
		Person person = new Person();
		person.setId(id);
		person.setFirstName(firstName);
		person.setLastName(lastName);
		
		this.personDao.update(person);
	}

	@Override
	public Integer savePerson(String firstName, String lastName) {
		
		Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		
		return this.personDao.save(person);
	}
	
	/*
	 * Bills
	 */

	@Override
	public Bill getBill(int id) {
		return this.billDao.get(id);
	}

	@Override
	public List<Bill> listBills() {
		return this.billDao.list();
	}

	@Override
	public Integer saveBill(int personId, int price) {
		
		Person person = new Person();
		person.setId(personId);
		
		Bill bill = new Bill();
		bill.setPerson(person);
		bill.setPrice(price);
		
		// TODO Auto-generated method stub
		return this.billDao.save(bill);
	}

	@Override
	public void updateBill(int billId, int personId, int price) {
		
		Person person = new Person();
		person.setId(personId);
		
		Bill bill = new Bill();
		bill.setId(billId);
		bill.setPerson(person);
		bill.setPrice(price);
		
		this.billDao.update(bill);
	}
	
	
	/*
	 * Internal
	 */

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}


	public void setBillDaoo(BillDao billDao) {
		this.billDao = billDao;
	}
	
}
