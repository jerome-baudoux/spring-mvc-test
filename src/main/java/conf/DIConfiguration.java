package conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
 
/**
 * Dependency injection configuration
 * @author Jerome
 */
@Configuration
@ComponentScan(value={"hello", "services"})
@ImportResource(value={"config/datasource-context.xml", "config/dao-context.xml"})
public class DIConfiguration {
	
}
