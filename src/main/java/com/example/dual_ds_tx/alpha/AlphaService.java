package com.example.dual_ds_tx.alpha;

import java.time.Duration;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dual_ds_tx.DualDsTxApplication;
import com.example.dual_ds_tx.Logging;
import com.example.dual_ds_tx.SuccessEvent;
import com.example.dual_ds_tx.alpha.AlphaScheduler.AlphaInitiatedEvent;

@Service
@Transactional(DualDsTxApplication.ALPHA_TX)
public class AlphaService implements Logging {
	
	private final ApplicationEventPublisher publisher;
	private final AlphaRepository repo;
	
	public AlphaService(ApplicationEventPublisher publisher, AlphaRepository repo) {
		this.publisher = publisher;
		this.repo = repo;
	}
	
	@EventListener
	public void on(AlphaInitiatedEvent event) {
		try {
			logger().info("ALPHA HANDLING ---- {}", event);
			repo.handle(event);
			Thread.sleep(Duration.ofSeconds(3));
			publisher.publishEvent(new SuccessEvent(event));
			logger().info("ALPHA DONE!");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
