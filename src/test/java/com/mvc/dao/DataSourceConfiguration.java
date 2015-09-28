package com.mvc.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfiguration {

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:mem:testdb");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}

	@Bean
	public IDatabaseConnection databaseConnection(DataSource dataSource) throws SQLException {
		DatabaseDataSourceConnection DatabaseDataSourceConnection = new DatabaseDataSourceConnection(dataSource);
		return DatabaseDataSourceConnection;
	}
}
