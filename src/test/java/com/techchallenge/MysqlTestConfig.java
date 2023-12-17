package com.techchallenge;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(value = {"com.techchallenge"})
@EnableJpaRepositories
public class MysqlTestConfig {
	
	

}
