package com.mvc.controller;

import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;

import com.mvc.categories.UnitTest;
import com.mvc.entities.Bill;
import com.mvc.entities.Person;
import com.mvc.services.PersonAndBillService;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * Test the Hibernate REST controller
 * @author Jerome
 *
 */
@Category(UnitTest.class)
@RunWith(MockitoJUnitRunner.class)
public class HibernateRESTControllerTest {

	@InjectMocks
	private HibernateRESTController controller;

    @Mock
    private PersonAndBillService mockPersonService;

    @Mock
    private View mockView;

    private MockMvc mockMvc;
    
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
    	
    	// reset count
        Mockito.reset(this.mockView, this.mockPersonService);
        
        // create mvc context
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller)
                .setSingleView(this.mockView)
                .build();
        
        // Configure jackson mapper
        this.mapper = new ObjectMapper();
        this.mapper.setSerializationInclusion(Inclusion.NON_NULL);
    }

    @Test
    public void testFetchPersons() throws Exception {
    	
        List<Person> expectedPersons = Arrays.asList(new Person(-1, "fn", "ln"));
        String personsAsString = mapper.writeValueAsString(expectedPersons);
        
        when(mockPersonService.listPersons(anyBoolean())).thenReturn(expectedPersons);

        this.mockMvc.perform(get("/hibernate/persons"))
                .andExpect(status().isOk())
                .andExpect(content().string(personsAsString));
        
        verify(mockPersonService, times(1)).listPersons(eq(true));
    }

    @Test
    public void testCreatePerson() throws Exception {
    	
        Person expectedPerson = new Person(1, "fn", "ln");
        String personAsString = mapper.writeValueAsString(expectedPerson);
        
        when(mockPersonService.savePerson(anyString(), anyString())).thenReturn(Integer.valueOf(1));
        when(mockPersonService.getPerson(anyInt())).thenReturn(expectedPerson);

        this.mockMvc.perform(post("/hibernate/person?firstName=fn&lastName=ln"))
                .andExpect(status().isOk())
                .andExpect(content().string(personAsString));

        verify(mockPersonService, times(1)).savePerson(eq("fn"), eq("ln"));
        verify(mockPersonService, times(1)).getPerson(eq(1));
    }

    @Test
    public void testCreatePersonShouldFailIfFirstNameIsMissing() throws Exception {
    	
        this.mockMvc.perform(post("/hibernate/person?lastName=ln"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testCreatePersonShouldFailIfLastNameIsMissing() throws Exception {
    	
        this.mockMvc.perform(post("/hibernate/person?firstName=fn"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testUpdatePerson() throws Exception {
    	
        Person expectedPerson = new Person(1, "fn", "ln");
        String personAsString = mapper.writeValueAsString(expectedPerson);
        
        when(mockPersonService.savePerson(anyString(), anyString())).thenReturn(Integer.valueOf(1));
        when(mockPersonService.getPerson(anyInt())).thenReturn(expectedPerson);

        this.mockMvc.perform(put("/hibernate/person?id=1&firstName=fn&lastName=ln"))
                .andExpect(status().isOk())
                .andExpect(content().string(personAsString));

        verify(mockPersonService, times(1)).updatePerson(eq(1), eq("fn"), eq("ln"));
        verify(mockPersonService, times(1)).getPerson(eq(1));
    }

    @Test
    public void testUpdatePersonShouldFailIfIdIsMissing() throws Exception {
    	
        this.mockMvc.perform(put("/hibernate/person?firstName=fn&lastName=ln"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testUpdatePersonShouldFailIfFirstNameIsMissing() throws Exception {
    	
        this.mockMvc.perform(put("/hibernate/person?id=1&lastName=ln"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testUpdatePersonShouldFailIfLastNameIsMissing() throws Exception {
    	
        this.mockMvc.perform(put("/hibernate/person?id=1&firstName=fn"))
                .andExpect(status().is4xxClientError());
    }
    
    @Test
    public void testFetchBills() throws Exception {
    	
        List<Bill> expectedBills = Arrays.asList(new Bill(1, new Person(1, "fake", "fake"), 10));
        String billsAsString = mapper.writeValueAsString(expectedBills);
        
        when(mockPersonService.listBills()).thenReturn(expectedBills);

        this.mockMvc.perform(get("/hibernate/bills"))
                .andExpect(status().isOk())
                .andExpect(content().string(billsAsString));
        
        verify(mockPersonService, times(1)).listBills();
    }

    @Test
    public void testCreateBill() throws Exception {

        Person expectedPerson = new Person(1, "fn", "ln");
        Bill expectedBill = new Bill(1, expectedPerson, 10);
        String billAsString = mapper.writeValueAsString(expectedBill);
        
        when(mockPersonService.saveBill(anyInt(), anyInt())).thenReturn(Integer.valueOf(1));
        when(mockPersonService.getBill(anyInt())).thenReturn(expectedBill);

        this.mockMvc.perform(post("/hibernate/bill?personId=1&price=10"))
                .andExpect(status().isOk())
                .andExpect(content().string(billAsString));

        verify(mockPersonService, times(1)).saveBill(eq(1), eq(10));
        verify(mockPersonService, times(1)).getBill(eq(1));
    }

    @Test
    public void testCreateBillShouldFailIfPersonIdIsMissing() throws Exception {
    	
        this.mockMvc.perform(post("/hibernate/person?price=10"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testCreateBillShouldFailIfPriceIsMissing() throws Exception {
    	
        this.mockMvc.perform(post("/hibernate/person?personId=1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testUpdateBill() throws Exception {
    	
        Person expectedPerson = new Person(1, "fn", "ln");
        Bill expectedBill = new Bill(1, expectedPerson, 10);
        String billAsString = mapper.writeValueAsString(expectedBill);
        
        when(mockPersonService.getBill(anyInt())).thenReturn(expectedBill);

        this.mockMvc.perform(put("/hibernate/bill?id=1&personId=1&price=10"))
                .andExpect(status().isOk())
                .andExpect(content().string(billAsString));

        verify(mockPersonService, times(1)).updateBill(eq(1), eq(1), eq(10));
        verify(mockPersonService, times(1)).getBill(eq(1));
    }

    @Test
    public void testUpdateBillShouldFailIfIdIsMissing() throws Exception {
    	
        this.mockMvc.perform(put("/hibernate/bill?personId=1&price=10"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testUpdateBillShouldFailIfPersonIdIsMissing() throws Exception {
    	
        this.mockMvc.perform(put("/hibernate/bill?id=1&price=10"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testUpdateBillShouldFailIfPriceIsMissing() throws Exception {
    	
        this.mockMvc.perform(put("/hibernate/bill?id=1&personId=10"))
                .andExpect(status().is4xxClientError());
    }
    
}
