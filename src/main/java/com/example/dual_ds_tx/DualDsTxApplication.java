package com.example.dual_ds_tx;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
@EnableScheduling
public class DualDsTxApplication {

	static final String ALPHA_DATA_SOURCE = "alphaDataSource";
	static final String OMEGA_DATA_SOURCE = "omegaDataSource";

	public static void main(String[] args) {
		SpringApplication.run(DualDsTxApplication.class, args);
	}

	@Configuration
	static class DataSourceConfig {

		@Primary
		@Bean(ALPHA_DATA_SOURCE)
		@ConfigurationProperties(prefix = "spring.datasource.alpha")
		public DataSource alpha() {
			return DataSourceBuilder.create().build();
		}

		@Primary
		@Bean(OMEGA_DATA_SOURCE)
		@ConfigurationProperties(prefix = "spring.datasource.omega")
		public DataSource omega() {
			return DataSourceBuilder.create().build();
		}
	}

	@Configuration
	static class TransactionManagerConfig {

		@Primary
		@Bean(name = "alphaTransactionManager")
		public PlatformTransactionManager alphaTransactionManager(
				@Qualifier(ALPHA_DATA_SOURCE) DataSource alphaDataSource) {
			return new DataSourceTransactionManager(alphaDataSource);
		}

		@Bean(name = "omegaTransactionManager")
		public PlatformTransactionManager omegaTransactionManager(
				@Qualifier(OMEGA_DATA_SOURCE) DataSource omegaDataSource) {
			return new DataSourceTransactionManager(omegaDataSource);
		}
	}

}
