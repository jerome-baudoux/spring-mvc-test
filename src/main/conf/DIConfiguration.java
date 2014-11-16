package main.conf;

import main.services.VersionService;
import main.services.impl.VersionServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
 
/**
 * Dependency injection configuration
 * @author Jerome
 */
@Configuration
@ComponentScan(value={"main.services"})
public class DIConfiguration {
 
	/**
	 * @return an implementation of a VersionService
	 */
    @Bean
    public VersionService getMessageService(){
        return new VersionServiceImpl();
    }
}
