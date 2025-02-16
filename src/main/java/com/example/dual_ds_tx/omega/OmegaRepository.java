package com.example.dual_ds_tx.omega;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.example.dual_ds_tx.DualDsTxApplication;
import com.example.dual_ds_tx.Logging;
import com.example.dual_ds_tx.SuccessEvent;

@Repository
public class OmegaRepository implements Logging {

	private final JdbcClient client;

	public OmegaRepository(@Qualifier(DualDsTxApplication.OMEGA_CLIENT) JdbcClient client) {
		this.client = client;
	}

	public void handle(SuccessEvent event) {
		logger().info("QUERY RESULT {}", client.sql("SELECT * FROM values").query().listOfRows());
	}

}
