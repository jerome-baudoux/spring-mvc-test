package services;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import services.impl.PersonServiceImpl;
import categories.UnitTest;
import dao.PersonDao;
import entities.Person;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for the person service
 * 
 * @author Jerome
 */
@Category(UnitTest.class)
@RunWith(MockitoJUnitRunner.class)
public class PersonServiceImplTest {

	@Mock
	private PersonDao personDao;

	@InjectMocks
	private PersonService personServiceImpl = new PersonServiceImpl();

	@Mock
	private Person person;

	@Mock
	private List<Person> persons;

	@Before
	public void setup() {
		// reset mocks
		reset(this.personDao);
	}

	@Test
	public void getShouldCallTheGetMethodOfTheDao() {

		// Mock
		doReturn(person).when(this.personDao).get(anyInt());

		// Run
		Person returnedPerson = this.personServiceImpl.get(1);

		// Check that correct method was called, and correct result is found
		assertThat(returnedPerson).isEqualTo(this.person);
		verify(this.personDao, only()).get(anyInt());
	}

	@Test
	public void listShouldCallTheListMethodOfTheDao() {

		// Mock
		doReturn(persons).when(this.personDao).list();

		// Run
		List<Person> returnedPersons = this.personServiceImpl.list();

		// Check that correct method was called, and correct result is found
		assertThat(returnedPersons).isEqualTo(this.persons);
		verify(this.personDao, only()).list();
	}

	@Test
	public void saveShouldCallTheSavetMethodOfTheDao() {

		// Mock
		doReturn(Integer.valueOf(1)).when(this.personDao).save(
				any(Person.class));

		// Run
		Integer id = this.personServiceImpl.save("mock", "mock");

		// Check that correct method was called, and correct result is found
		assertThat(id).isEqualTo(1);
		verify(this.personDao, only()).save(any(Person.class));
	}

	@Test
	public void updateShouldCallTheUpdateMethodOfTheDao() {

		// Run
		this.personServiceImpl.update(1, "mock", "mock");

		// Check that correct method was called, and correct result is found
		verify(this.personDao, only()).update(any(Person.class));
	}
}
