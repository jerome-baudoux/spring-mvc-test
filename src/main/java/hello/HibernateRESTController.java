package hello;

import entities.Person;
import services.PersonService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * A controller that allows to test hibernate
 * @author Jerome
 */
@RestController
public class HibernateRESTController {

	@Autowired
	private PersonService personService;

	@RequestMapping("/hibernate/persons")
	public List<Person> persons() {

		// Should convert to a dto
		return this.personService.list();
	}

	@RequestMapping(value = "/hibernate/person", method = RequestMethod.PUT)
	public Person person(
			@RequestParam(value="id", required=false) Integer id,
			@RequestParam(value="firstName", required=true) String firstName,
			@RequestParam(value="lastName", required=true) String lastName) {

		if (id==null) {
			id = this.personService.save(firstName, lastName);
		} else {
			this.personService.update(id, firstName, lastName);
		}
		
		// Should convert to a dto
		return this.personService.get(id);
	}

	@RequestMapping(value = "/hibernate/person/exception/outside/tx", method = RequestMethod.PUT)
	public Person personExceptionOutsideTx(
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

	@RequestMapping(value = "/hibernate/person/exception/inside/tx", method = RequestMethod.PUT)
	public Person personExceptionInsideTx(
			@RequestParam(value="id", required=false) Integer id,
			@RequestParam(value="firstName", required=true) String firstName,
			@RequestParam(value="lastName", required=true) String lastName) {

		if (id==null) {
			this.personService.save(firstName, lastName);
		} else {
			this.personService.update(id, firstName, lastName);
		}
		
		// should never happen
		return this.personService.get(id);
	}
}
