package com.example.dual_ds_tx.alpha;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.example.dual_ds_tx.DualDsTxApplication;
import com.example.dual_ds_tx.Logging;
import com.example.dual_ds_tx.alpha.AlphaScheduler.AlphaInitiatedEvent;

@Repository
public class AlphaRepository implements Logging {

	private final JdbcClient client;

	public AlphaRepository(@Qualifier(DualDsTxApplication.ALPHA_CLIENT) JdbcClient client) {
		this.client = client;
	}

	public void handle(AlphaInitiatedEvent event) {
		logger().info("QUERY RESULT {}", client.sql("SELECT * FROM values").query().listOfRows());
	}

}
