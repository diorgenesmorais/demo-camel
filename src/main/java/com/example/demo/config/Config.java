package com.example.demo.config;

import javax.sql.DataSource;

import org.apache.camel.component.sql.SqlComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

	@Autowired
	DataSource dataSource;

	@Bean
	SqlComponent sql(DataSource dataSource) {
		SqlComponent sql = new SqlComponent();
		sql.setDataSource(dataSource);
		return sql;
	}
}
