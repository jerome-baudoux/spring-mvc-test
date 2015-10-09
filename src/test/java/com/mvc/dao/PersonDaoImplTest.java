package com.mvc.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.StaleStateException;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mvc.categories.IntegrationTest;
import com.mvc.conf.DaoConfiguration;
import com.mvc.conftest.DataSourceConfiguration;
import com.mvc.entities.Person;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the PersonDao Hibernate's implementation
 * 
 * @author Jerome
 */
@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DataSourceConfiguration.class, DaoConfiguration.class })
public class PersonDaoImplTest extends AbstractDaoTest {

	@Autowired
	private PersonDaoImpl personDao;

	@Override
	protected String getDataset() {
		return "dao/person-dao-dataset.xml";
	}

	@Test
    @Transactional
	public void getShouldGetAPerson() {
		Person person = this.personDao.get(1, false);
		assertThat(person).isNotNull();
		assertThat(person.getId()).isEqualTo(Integer.valueOf(1));
		assertThat(person.getFirstName()).isEqualTo("fn1");
		assertThat(person.getLastName()).isEqualTo("ln1");
	}

	@Test
    @Transactional
	public void getShouldReturnNullIfPersonDoesNotExist() {
		Person person = this.personDao.get(100, false);
		assertThat(person).isNull();
	}

	@Test
    @Transactional
	public void listShouldReturn2Persons() {
		List<Person> persons = this.personDao.list(false);
		assertThat(persons).hasSize(2);
	}

	@Test
    @Transactional
	public void createShouldCreateANewPerson() {

		// New person
		Person person = new Person();
		person.setFirstName("new");
		person.setLastName("new");

		// Creates the person
		Integer id = this.personDao.save(person);
		assertThat(id).isNotNull();
		
		// force the flush before the end of the method
		this.personDao.getSessionFactory().getCurrentSession().flush();

		// Checks the person is created correctly
		Person newPerson = this.personDao.get(id, false);
		assertThat(newPerson.getId()).isEqualTo(id);
		assertThat(newPerson.getFirstName()).isEqualTo("new");
		assertThat(newPerson.getLastName()).isEqualTo("new");
	}

	@Test
    @Transactional
	public void updateShouldUpdateAnExistingPerson() {

		// New person
		Person person = new Person();
		person.setId(1);
		person.setFirstName("updated");
		person.setLastName("updated");

		// Updates the person
		this.personDao.update(person);
		
		// force the flush before the end of the method
		this.personDao.getSessionFactory().getCurrentSession().flush();

		// Checks the person is updated correctly
		Person updatedPerson = this.personDao.get(1, false);
		assertThat(updatedPerson.getId()).isEqualTo(1);
		assertThat(updatedPerson.getFirstName()).isEqualTo("updated");
		assertThat(updatedPerson.getLastName()).isEqualTo("updated");
	}

	@Test(expected = StaleStateException.class)
    @Transactional
	public void updateShouldFailWhenPersonDoesNotExist() {

		// New person
		Person person = new Person();
		person.setId(200);
		person.setFirstName("updated");
		person.setLastName("updated");

		// supposed to Fails
		this.personDao.update(person);
		
		// force the flush before the end of the method
		this.personDao.getSessionFactory().getCurrentSession().flush();
	}
}
