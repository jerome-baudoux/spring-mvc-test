package services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import services.impl.PersonServiceImpl;
import categories.UnitTest;
import dao.PersonDao;
import entities.Person;

/**
 * Unit test for the person service
 * @author Jerome
 */
@Category(UnitTest.class)
@RunWith(MockitoJUnitRunner.class)
public class PersonServiceImplTest {
	
	@Mock
	private Person person;

	@Mock
	private PersonDao personDao;
	
	@InjectMocks 
	private PersonService personServiceImpl = new PersonServiceImpl();

	@Test
	public void getShouldCallTheGetMethodOfTheDao() {
		
		// Mock
		Mockito.doReturn(person).when(this.personDao).get(Mockito.anyInt());
		
		// Run
		Person returnedPerson = this.personServiceImpl.get(1);
		
		// Check that correct method was called, and correct result is found
		Assert.assertEquals(this.person, returnedPerson);
		Mockito.verify(this.personDao, Mockito.only()).get(Mockito.anyInt());
	}
}
