package dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import categories.IntegrationTest;


import entities.Person;

/**
 * Tests the PersonDao Hibernate's implementation
 * @author Jerome
 */
@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:datasource-context.xml", "classpath:config/dao-context.xml"})
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
    	Assert.assertNotNull(person);
    	Assert.assertEquals(Integer.valueOf(1), person.getId());
    	Assert.assertEquals("fn1", person.getFirstName());
    	Assert.assertEquals("ln1", person.getLastName());
    }
    
    @Test
    public void getShouldReturnNullIfPersonDoesNotExist() {
    	Person person = this.personDao.get(100);
    	Assert.assertNull(person);
    }
    
    @Test
    public void listShouldReturn2Persons() {
    	List<Person> persons = this.personDao.list();
    	Assert.assertEquals(2, persons.size());
    }
    
    @Test
    public void createShouldCreateANewPerson() {
    	
    	// New person
    	Person person = new Person();
    	person.setFirstName("new");
    	person.setLastName("new");
    	
    	// Creates the person
    	Integer id = this.personDao.save(person);
    	Assert.assertNotNull(id);
    	
    	// Checks the person is created correctly
    	Person newPerson = this.personDao.get(id);
    	Assert.assertEquals(id, newPerson.getId());
    	Assert.assertEquals("new", newPerson.getFirstName());
    	Assert.assertEquals("new", newPerson.getLastName());
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
    	Assert.assertEquals(Integer.valueOf(1), updatedPerson.getId());
    	Assert.assertEquals("updated", updatedPerson.getFirstName());
    	Assert.assertEquals("updated", updatedPerson.getLastName());
    }
    
    @Test(expected=DataAccessException.class)
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
