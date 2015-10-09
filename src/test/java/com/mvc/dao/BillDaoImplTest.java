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
import com.mvc.entities.Bill;
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
public class BillDaoImplTest extends AbstractDaoTest {

	@Autowired
	private BillDaoImpl billDao;

	@Override
	protected String getDataset() {
		return "dao/bill-dao-dataset.xml";
	}

	@Test
    @Transactional
	public void getShouldGetABill() {
		Bill bill = this.billDao.get(1);
		assertThat(bill).isNotNull();
		assertThat(bill.getId()).isEqualTo(Integer.valueOf(1));
		assertThat(bill.getPrice()).isEqualTo(10);
		assertThat(bill.getPerson().getId()).isEqualTo(1);
	}

	@Test
    @Transactional
	public void getShouldReturnNullIfBillDoesNotExist() {
		Bill bill = this.billDao.get(100);
		assertThat(bill).isNull();
	}

	@Test
    @Transactional
	public void listShouldReturn3Bills() {
		List<Bill> bills = this.billDao.list();
		assertThat(bills).hasSize(3);
	}

	@Test
    @Transactional
	public void createShouldCreateANewPerson() {
		
		Person person = new Person();
		person.setId(1);

		// New person
		Bill bill = new Bill();
		bill.setPerson(person);
		bill.setPrice(50);

		// Creates the person
		Integer id = this.billDao.save(bill);
		assertThat(id).isNotNull();
		
		// force the flush before the end of the method
		this.billDao.getSessionFactory().getCurrentSession().flush();

		// Checks the person is created correctly
		Bill newBill = this.billDao.get(id);
		assertThat(newBill.getId()).isEqualTo(id);
		assertThat(newBill.getPerson().getId()).isEqualTo(1);
		assertThat(newBill.getPrice()).isEqualTo(50);
	}

	@Test
    @Transactional
	public void updateShouldUpdateAnExistingBill() {

		// New person
		Person person = new Person();
		person.setId(1);

		// New person
		Bill bill = new Bill();
		bill.setId(3);
		bill.setPerson(person);
		bill.setPrice(50);

		// Updates the person
		this.billDao.update(bill);
		
		// force the flush before the end of the method
		this.billDao.getSessionFactory().getCurrentSession().flush();

		// Checks the person is updated correctly
		Bill newBill = this.billDao.get(3);
		assertThat(newBill.getId()).isEqualTo(3);
		assertThat(newBill.getPerson().getId()).isEqualTo(1);
		assertThat(newBill.getPrice()).isEqualTo(50);
	}

	@Test(expected = StaleStateException.class)
    @Transactional
	public void updateShouldFailWhenBillDoesNotExist() {

		// New person
		Person person = new Person();
		person.setId(1);

		// New person
		Bill bill = new Bill();
		bill.setId(500);
		bill.setPerson(person);
		bill.setPrice(50);

		// supposed to Fails
		this.billDao.update(bill);
		
		// force the flush before the end of the method
		this.billDao.getSessionFactory().getCurrentSession().flush();
	}
}
