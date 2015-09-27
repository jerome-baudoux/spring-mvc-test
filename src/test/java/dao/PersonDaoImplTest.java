package dao;

import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import categories.IntegrationTest;

import entities.Person;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the PersonDao Hibernate's implementation
 * 
 * @author Jerome
 */
@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:datasource-context.xml", "classpath:config/dao-context.xml" })
public class PersonDaoImplTest extends AbstractDaoTest {

	@Autowired
	private PersonDao personDao;

	@Override
	protected String getDataset() {
		return "dao/person-dao-dataset.xml";
	}

	@Test
	public void getShouldGetAPerson() {
		Person person = this.personDao.get(1);
		assertThat(person).isNotNull();
		assertThat(person.getId()).isEqualTo(Integer.valueOf(1));
		assertThat(person.getFirstName()).isEqualTo("fn1");
		assertThat(person.getLastName()).isEqualTo("ln1");
	}

	@Test
	public void getShouldReturnNullIfPersonDoesNotExist() {
		Person person = this.personDao.get(100);
		assertThat(person).isNull();
	}

	@Test
	public void listShouldReturn2Persons() {
		List<Person> persons = this.personDao.list();
		assertThat(persons).hasSize(2);
	}

	@Test
	public void createShouldCreateANewPerson() {

		// New person
		Person person = new Person();
		person.setFirstName("new");
		person.setLastName("new");

		// Creates the person
		Integer id = this.personDao.save(person);
		assertThat(id).isNotNull();

		// Checks the person is created correctly
		Person newPerson = this.personDao.get(id);
		assertThat(newPerson.getId()).isEqualTo(id);
		assertThat(newPerson.getFirstName()).isEqualTo("new");
		assertThat(newPerson.getLastName()).isEqualTo("new");
	}

	@Test
	public void updateShouldUpdateAnExistingPerson() {

		// New person
		Person person = new Person();
		person.setId(1);
		person.setFirstName("updated");
		person.setLastName("updated");

		// Updates the person
		this.personDao.update(person);

		// Checks the person is updated correctly
		Person updatedPerson = this.personDao.get(1);
		assertThat(updatedPerson.getId()).isEqualTo(1);
		assertThat(updatedPerson.getFirstName()).isEqualTo("updated");
		assertThat(updatedPerson.getLastName()).isEqualTo("updated");
	}

	@Test(expected = DataAccessException.class)
	public void updateShouldFailWhenPersonDoesNotExist() {

		// New person
		Person person = new Person();
		person.setId(100);
		person.setFirstName("updated");
		person.setLastName("updated");

		// Fails
		this.personDao.update(person);
	}
}
