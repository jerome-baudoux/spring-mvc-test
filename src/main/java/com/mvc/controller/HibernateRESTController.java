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

import com.mvc.dto.PersonDto;
import com.mvc.entities.Person;
import com.mvc.services.PersonService;

/**
 * A controller that allows to test hibernate
 * @author Jerome
 */
@RestController
public class HibernateRESTController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HibernateRESTController.class);

	@Autowired
	private PersonService personService;

	@RequestMapping("/hibernate/persons")
	public List<PersonDto> persons() {

		List<Person> persons = this.personService.list(true);

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
		
		Integer id = this.personService.save(firstName, lastName);
		
		return toPersonDto(this.personService.get(id));
	}
	
	@RequestMapping(value = "/hibernate/person", method = RequestMethod.PUT)
	public PersonDto person(
			@RequestParam(value="id", required=true) Integer id,
			@RequestParam(value="firstName", required=true) String firstName,
			@RequestParam(value="lastName", required=true) String lastName) {

		this.personService.update(id, firstName, lastName);

		return toPersonDto(this.personService.get(id));
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
			this.personService.save(firstName, lastName);
		} else {
			this.personService.update(id, firstName, lastName);
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
			this.personService.save(firstName, lastName);
		} else {
			this.personService.update(id, firstName, lastName);
		}
		
		// should never happen
		return toPersonDto(this.personService.get(id));
	}
	
	/*
	 * Internal
	 */
	
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
}