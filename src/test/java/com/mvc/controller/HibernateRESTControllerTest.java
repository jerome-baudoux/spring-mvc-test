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
import com.mvc.entities.Person;
import com.mvc.services.PersonService;

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
	HibernateRESTController controller;

    @Mock
    PersonService mockPersonService;

    @Mock
    View mockView;

    MockMvc mockMvc;
    
    ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
    	
    	// reset count
        Mockito.reset(this.mockView, this.mockPersonService);
        
        // create mvc context
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setSingleView(mockView)
                .build();
        
        // Configure jackson mapper
        this.mapper = new ObjectMapper();
        this.mapper.setSerializationInclusion(Inclusion.NON_NULL);
    }

    @Test
    public void testFetchPersons() throws Exception {
    	
        List<Person> expectedPersons = Arrays.asList(new Person(-1, "fn", "ln"));
        String personsAsString = mapper.writeValueAsString(expectedPersons);
        
        when(mockPersonService.list(anyBoolean())).thenReturn(expectedPersons);

        this.mockMvc.perform(get("/hibernate/persons"))
                .andExpect(status().isOk())
                .andExpect(content().string(personsAsString));
        
        verify(mockPersonService, times(1)).list(eq(true));
    }

    @Test
    public void testCreatePerson() throws Exception {
    	
        Person expectedPerson = new Person(1, "fn", "ln");
        String personAsString = mapper.writeValueAsString(expectedPerson);
        
        when(mockPersonService.save(anyString(), anyString())).thenReturn(Integer.valueOf(1));
        when(mockPersonService.get(anyInt())).thenReturn(expectedPerson);

        this.mockMvc.perform(post("/hibernate/person?firstName=fn&lastName=ln"))
                .andExpect(status().isOk())
                .andExpect(content().string(personAsString));

        verify(mockPersonService, times(1)).save(eq("fn"), eq("ln"));
        verify(mockPersonService, times(1)).get(eq(1));
    }

    @Test
    public void testUpdatePerson() throws Exception {
    	
        Person expectedPerson = new Person(1, "fn", "ln");
        String personAsString = mapper.writeValueAsString(expectedPerson);
        
        when(mockPersonService.save(anyString(), anyString())).thenReturn(Integer.valueOf(1));
        when(mockPersonService.get(anyInt())).thenReturn(expectedPerson);

        this.mockMvc.perform(put("/hibernate/person?id=1&firstName=fn&lastName=ln"))
                .andExpect(status().isOk())
                .andExpect(content().string(personAsString));

        verify(mockPersonService, times(1)).update(eq(1), eq("fn"), eq("ln"));
        verify(mockPersonService, times(1)).get(eq(1));
    }
}
