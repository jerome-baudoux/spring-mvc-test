package com.mvc.controller;

import java.util.Arrays;
import java.util.List;

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
import com.mvc.entities.Person;
import com.mvc.services.PersonService;

import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyBoolean;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
	HibernateRESTController controller;

    @Mock
    PersonService mockPersonService;

    @Mock
    View mockView;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
    	
    	// reset count
        Mockito.reset(this.mockView, this.mockPersonService);
        
        // create mvc context
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setSingleView(mockView)
                .build();
    }

    @Test
    public void testFetchPersons() throws Exception {
        List<Person> expectedPeople = Arrays.asList(new Person(-1, "fn", "ln"));
        when(mockPersonService.list(anyBoolean())).thenReturn(expectedPeople);

        this.mockMvc.perform(get("/hibernate/persons"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":-1,\"firstName\":\"fn\",\"lastName\":\"ln\"}]"));
    }
}
