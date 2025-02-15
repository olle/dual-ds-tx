package com.example.dual_ds_tx;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class DualDsTxApplication {

	public static void main(String[] args) {
		SpringApplication.run(DualDsTxApplication.class, args);
	}
	
	@Configuration
	static class DataSourceConfig {
		
		@Primary
		@Bean("alpha")
		@ConfigurationProperties(prefix = "spring.datasource.alpha")
		public DataSource alpha() {
			return DataSourceBuilder.create().build();
		}
		
		@Primary
		@Bean("omega")
		@ConfigurationProperties(prefix = "spring.datasource.omega")
		public DataSource omega() {
			return DataSourceBuilder.create().build();
		}

		
	}

}
