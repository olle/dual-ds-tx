package com.example.dual_ds_tx;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@EnableAsync(proxyTargetClass = true)
@EnableTransactionManagement
public class DualDsTxApplication {

	static final String ALPHA_DS = "alphaDataSource";
	public static final String ALPHA_TX = "alphaTransactionManager";
	public static final String ALPHA_CLIENT = "alphaJdbcClient";

	static final String OMEGA_DS = "omegaDataSource";
	public static final String OMEGA_TX = "omegaTransactionManager";
	public static final String OMEGA_CLIENT = "omegaJdbcClient";

	public static void main(String[] args) {
		SpringApplication.run(DualDsTxApplication.class, args);
	}

	@Configuration
	static class DataSourceConfig {

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource.alpha")
		public DataSourceProperties alphaProps() {
			return new DataSourceProperties();
		}

		@Primary
		@Bean(ALPHA_DS)
		@ConfigurationProperties(prefix = "spring.datasource.alpha.hikari")
		public DataSource alpha() {
			return alphaProps().initializeDataSourceBuilder().build();
		}

		@Bean(ALPHA_CLIENT)
		public JdbcClient alphaJdbcClient() {
			return JdbcClient.create(alpha());
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource.omega")
		public DataSourceProperties omegaProps() {
			return new DataSourceProperties();
		}

		@Primary
		@Bean(OMEGA_DS)
		@ConfigurationProperties(prefix = "spring.datasource.omega.hikari")
		public DataSource omega() {
			return omegaProps().initializeDataSourceBuilder().build();
		}

		@Bean(OMEGA_CLIENT)
		public JdbcClient omegaJdbcClient() {
			return JdbcClient.create(omega());
		}

	}

	@Configuration
	static class TransactionManagerConfig {

		@Primary
		@Bean(name = ALPHA_TX)
		public PlatformTransactionManager alphaTransactionManager(@Qualifier(ALPHA_DS) DataSource alphaDataSource) {
			return new DataSourceTransactionManager(alphaDataSource);
		}

		@Bean(name = OMEGA_TX)
		public PlatformTransactionManager omegaTransactionManager(@Qualifier(OMEGA_DS) DataSource omegaDataSource) {
			return new DataSourceTransactionManager(omegaDataSource);
		}
	}

}
