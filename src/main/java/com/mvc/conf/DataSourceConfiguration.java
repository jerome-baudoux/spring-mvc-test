package com.mvc.conf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfiguration {

	@Bean
	public String dataBaseDialect(@Value("${datasource.dialect}") String dialect) {
		return dialect;
	}

	@Bean
	public DataSource dataSource(
			@Value("${datasource.driver}") String driver,
			@Value("${datasource.url}") String url,
			@Value("${datasource.user}") String user,
			@Value("${datasource.password}") String password) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		return dataSource;
	}
}
