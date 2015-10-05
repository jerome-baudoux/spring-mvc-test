package com.mvc.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
 
/**
 * Dependency injection configuration
 * @author Jerome
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan(value={"com.mvc.controller", "com.mvc.services", "com.mvc.aop"})
@Import(value={DataSourceConfiguration.class, DaoConfiguration.class, SpringMVCConfig.class})
public class DIConfiguration {
	
}
