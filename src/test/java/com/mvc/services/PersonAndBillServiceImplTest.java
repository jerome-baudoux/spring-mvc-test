package com.mvc.services;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mvc.categories.UnitTest;
import com.mvc.dao.BillDao;
import com.mvc.dao.PersonDao;
import com.mvc.entities.Bill;
import com.mvc.entities.Person;
import com.mvc.services.PersonAndBillService;
import com.mvc.services.impl.PersonAndBillServiceImpl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for the person service
 * 
 * @author Jerome
 */
@Category(UnitTest.class)
@RunWith(MockitoJUnitRunner.class)
public class PersonAndBillServiceImplTest {

	@Mock
	private PersonDao personDao;

	@Mock
	private BillDao billDao;

	@InjectMocks
	private PersonAndBillService personAndBillServiceImpl = new PersonAndBillServiceImpl();

	@Mock
	private Person person;

	@Mock
	private List<Person> persons;
	
	@Mock
	private Bill bill;
	
	@Mock
	private List<Bill> bills;

	@Before
	public void setup() {
		// reset mocks
		reset(this.personDao, this.billDao);
	}

	@Test
	public void getPersonShouldCallTheGetMethodOfTheDao() {

		// Mock
		doReturn(this.person).when(this.personDao).get(anyInt(), anyBoolean());

		// Run
		Person returnedPerson = this.personAndBillServiceImpl.getPerson(1, false);

		// Check that correct method was called, and correct result is found
		assertThat(returnedPerson).isEqualTo(this.person);
		verify(this.personDao, only()).get(eq(1), eq(false));
	}

	@Test
	public void listPersonsShouldCallTheListMethodOfTheDao() {

		// Mock
		doReturn(this.persons).when(this.personDao).list(anyBoolean());

		// Run
		List<Person> returnedPersons = this.personAndBillServiceImpl.listPersons(false);

		// Check that correct method was called, and correct result is found
		assertThat(returnedPersons).isEqualTo(this.persons);
		verify(this.personDao, only()).list(eq(false));
	}

	@Test
	public void savePersonShouldCallTheSavetMethodOfTheDao() {

		// Mock
		doReturn(Integer.valueOf(1)).when(this.personDao).save(any(Person.class));

		// Run
		Integer id = this.personAndBillServiceImpl.savePerson("mock", "mock");

		// Check that correct method was called, and correct result is found
		assertThat(id).isEqualTo(1);
		verify(this.personDao, only()).save(any(Person.class));
	}

	@Test
	public void updatePersonShouldCallTheUpdateMethodOfTheDao() {

		// Run
		this.personAndBillServiceImpl.updatePerson(1, "mock", "mock");

		// Check that correct method was called, and correct result is found
		verify(this.personDao, only()).update(any(Person.class));
	}

	@Test
	public void getBillShouldCallTheGetMethodOfTheDao() {

		// Mock
		doReturn(this.bill).when(this.billDao).get(anyInt());

		// Run
		Bill returnedBill = this.personAndBillServiceImpl.getBill(1);

		// Check that correct method was called, and correct result is found
		assertThat(returnedBill).isEqualTo(this.bill);
		verify(this.billDao, only()).get(eq(1));
	}

	@Test
	public void listBillsShouldCallTheListMethodOfTheDao() {

		// Mock
		doReturn(this.bills).when(this.billDao).list();

		// Run
		List<Bill> returnedBills = this.personAndBillServiceImpl.listBills();

		// Check that correct method was called, and correct result is found
		assertThat(returnedBills).isEqualTo(this.bills);
		verify(this.billDao, only()).list();
	}

	@Test
	public void saveBillShouldCallTheSavetMethodOfTheDao() {

		// Mock
		doReturn(Integer.valueOf(1)).when(this.billDao).save(any(Bill.class));

		// Run
		Integer id = this.personAndBillServiceImpl.saveBill(1, 10);

		// Check that correct method was called, and correct result is found
		assertThat(id).isEqualTo(1);
		verify(this.billDao, only()).save(any(Bill.class));
	}

	@Test
	public void updateBillShouldCallTheUpdateMethodOfTheDao() {

		// Run
		this.personAndBillServiceImpl.updateBill(1, 1, 10);

		// Check that correct method was called, and correct result is found
		verify(this.billDao, only()).update(any(Bill.class));
	}
}
