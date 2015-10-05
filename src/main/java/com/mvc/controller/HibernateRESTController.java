package com.mvc.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mvc.dto.BillDto;
import com.mvc.dto.PersonDto;
import com.mvc.entities.Bill;
import com.mvc.entities.Person;
import com.mvc.services.PersonAndBillService;

/**
 * A controller that allows to test hibernate
 * @author Jerome
 */
@RestController
public class HibernateRESTController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HibernateRESTController.class);

	@Autowired
	private PersonAndBillService personService;
	
	/*
	 * Persons
	 */

	@RequestMapping("/hibernate/persons")
	public List<PersonDto> persons() {

		List<Person> persons = this.personService.listPersons(true);

		// The persons contains the bill too, but we don't need it
		// We fetch them just to log them
		persons.forEach((person)->{
			LOGGER.debug("Person: {}", person);
			LOGGER.debug("  Bills: {}", person.getBills());
		});
		
		return toPersonDto(persons);
	}

	@RequestMapping(value = "/hibernate/person", method = RequestMethod.POST)
	public PersonDto person(
			@RequestParam(value="firstName", required=true) String firstName,
			@RequestParam(value="lastName", required=true) String lastName) {
		
		Integer id = this.personService.savePerson(firstName, lastName);
		
		return toPersonDto(this.personService.getPerson(id));
	}
	
	@RequestMapping(value = "/hibernate/person", method = RequestMethod.PUT)
	public PersonDto person(
			@RequestParam(value="id", required=true) Integer id,
			@RequestParam(value="firstName", required=true) String firstName,
			@RequestParam(value="lastName", required=true) String lastName) {

		this.personService.updatePerson(id, firstName, lastName);

		return toPersonDto(this.personService.getPerson(id));
	}
	
	/*
	 * Bills
	 */

	@RequestMapping("/hibernate/bills")
	public List<BillDto> bills() {
		List<Bill> bills = this.personService.listBills();
		return toBillDto(bills);
	}

	@RequestMapping(value = "/hibernate/bill", method = RequestMethod.POST)
	public BillDto bill(
			@RequestParam(value="personId", required=true) int personId,
			@RequestParam(value="price", required=true) int price) {
		
		Integer id = this.personService.saveBill(personId, price);
		
		return toBillDto(this.personService.getBill(id));
	}

	@RequestMapping(value = "/hibernate/bill", method = RequestMethod.PUT)
	public BillDto bill(
			@RequestParam(value="id", required=true) int id,
			@RequestParam(value="personId", required=true) int personId,
			@RequestParam(value="price", required=true) int price) {
		
		this.personService.updateBill(id, personId, price);
		
		return toBillDto(this.personService.getBill(id));
	}
	
	/*
	 * Internal
	 */
	
	private List<BillDto> toBillDto(List<Bill> bills) {
		return bills.stream().map((bill)-> {
			return toBillDto(bill);
		}).collect(Collectors.toList());
	}
	
	private BillDto toBillDto(Bill bill) {
		return new BillDto(
				bill.getId(), 
				toPersonDto(bill.getPerson()), 
				bill.getPrice());
	}
	
	private List<PersonDto> toPersonDto(List<Person> persons) {
		return persons.stream().map((Person person)->{
			return toPersonDto(person);
		}).collect(Collectors.toList());
	}
	
	private PersonDto toPersonDto(Person person) {
		return new PersonDto(
				person.getId(), 
				person.getFirstName(), 
				person.getLastName());
	}

	/*
	 * Debug, just to test the transactions
	 */

	@RequestMapping(value = "/hibernate/person/exception/outside/tx", method = RequestMethod.GET)
	public PersonDto personExceptionOutsideTx(
			@RequestParam(value="id", required=false) Integer id,
			@RequestParam(value="firstName", required=true) String firstName,
			@RequestParam(value="lastName", required=true) String lastName) {

		if (id==null) {
			this.personService.savePerson(firstName, lastName);
		} else {
			this.personService.updatePerson(id, firstName, lastName);
		}
		
		// outside the transaction, so it should be ok
		throw new RuntimeException("Error outside the transaction");
	}

	@RequestMapping(value = "/hibernate/person/exception/inside/tx", method = RequestMethod.GET)
	public PersonDto personExceptionInsideTx(
			@RequestParam(value="id", required=false) Integer id,
			@RequestParam(value="firstName", required=true) String firstName,
			@RequestParam(value="lastName", required=true) String lastName) {

		if (id==null) {
			this.personService.savePerson(firstName, lastName);
		} else {
			this.personService.updatePerson(id, firstName, lastName);
		}
		
		// should never happen
		return toPersonDto(this.personService.getPerson(id));
	}
}
