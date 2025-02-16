package com.example.dual_ds_tx.omega;

import java.time.Duration;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.dual_ds_tx.DualDsTxApplication;
import com.example.dual_ds_tx.Logging;
import com.example.dual_ds_tx.SuccessEvent;

@Service
@Transactional(DualDsTxApplication.OMEGA_TX)
public class OmegaService implements Logging {

	private final OmegaRepository repo;

	public OmegaService(OmegaRepository repo) {
		this.repo = repo;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handle(SuccessEvent event) {
		try {
			logger().info("OMEGA HANDLING -- {}", event);
			repo.handle(event);
			Thread.sleep(Duration.ofSeconds(2));
			logger().info("OMEGA DONE!");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
