package com.mvc.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MvcResult;

import com.mvc.categories.UnitTest;
import com.mvc.services.VersionService;
import com.mvc.services.VersionService.Version;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Test the SyncAsync REST controller
 * @author Jerome
 *
 */
@Category(UnitTest.class)
@RunWith(MockitoJUnitRunner.class)
public class SyncAsyncRESTControllerUnitTest extends AbstractSpringMVCControllerUnitTest<SyncAsyncRESTController> {
	
	private static final long MAX_WAIT = 10000; // 10s

	@InjectMocks
	private SyncAsyncRESTController controller;
	
	@Mock
	private VersionService versionService;

	@Override
	protected SyncAsyncRESTController getController() {
		return controller;
	}

    @Before
    public void setUp() throws Exception {
    	
    	super.setUp();
    	
    	// reset count
        Mockito.reset(this.versionService);
    }

    @Test
    public void testSyncVersion() throws Exception {
    	
        Version expectedVersion = new Version(1, 2);
        
        when(this.versionService.getVersion()).thenReturn(expectedVersion);

        this.mockMvc.perform(get("/version"))
        		.andDo(print())
		        .andExpect(status().isOk())
                .andExpect(request().asyncNotStarted())
                .andExpect(jsonPath("minor").value(1))
        		.andExpect(jsonPath("major").value(2));
        
        verify(this.versionService, times(1)).getVersion();
    }

    @Test
    public void testAsyncVersion() throws Exception {
    	
        Version expectedVersion = new Version(1, 2);
        
        when(this.versionService.getVersion()).thenReturn(expectedVersion);

        MvcResult result = this.mockMvc.perform(get("/async-version"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();
        
        // Wait for the response
        // TODO -- is there a better way ?
        result.getAsyncResult(MAX_WAIT);
        
        this.mockMvc.perform(asyncDispatch(result))
	        .andExpect(status().isOk())
            .andExpect(jsonPath("minor").value(1))
    		.andExpect(jsonPath("major").value(2));
        
        verify(this.versionService, times(1)).getVersion();
    }

  
}
