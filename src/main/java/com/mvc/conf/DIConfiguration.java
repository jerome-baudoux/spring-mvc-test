package com.mvc.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
 
/**
 * Dependency injection configuration
 * @author Jerome
 */
@Configuration
@ComponentScan(value={"com.mvc.hello", "com.mvc.services"})
@Import(value={DataSourceConfiguration.class, DaoConfiguration.class})
public class DIConfiguration {
	
}
