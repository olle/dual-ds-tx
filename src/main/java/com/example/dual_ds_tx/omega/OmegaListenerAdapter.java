package com.example.dual_ds_tx.omega;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.dual_ds_tx.Logging;
import com.example.dual_ds_tx.SuccessEvent;

@Component
public class OmegaListenerAdapter implements Logging {

	private final OmegaService service;

	public OmegaListenerAdapter(OmegaService service) {
		this.service = service;
	}

	@Async
	@TransactionalEventListener
	public void on(SuccessEvent event) {
		logger().info("OMEGA LISTENER RECEVIED -- {}", event);
		service.handle(event);
	}

}
