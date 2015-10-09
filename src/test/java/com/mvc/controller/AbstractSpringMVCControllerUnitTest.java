package com.mvc.controller;

import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public abstract class AbstractSpringMVCControllerUnitTest<T> {

    protected MockMvc mockMvc;
    
    protected abstract T getController();

    @Before
    public void setUp() throws Exception {

    	// So that redirects are taken into account
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("classpath:templates/");
		viewResolver.setSuffix(".html");
        
        // create mvc context
        this.mockMvc = MockMvcBuilders.standaloneSetup(getController())
                .setViewResolvers(viewResolver)
                .build();
    }
}
