package com.example.dual_ds_tx.alpha;

import java.time.Instant;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import com.example.dual_ds_tx.Logging;

@Component
public class AlphaScheduler implements Logging {

	private final ApplicationEventPublisher publisher;
	private final TaskScheduler scheduler;

	public AlphaScheduler(ApplicationEventPublisher publisher, TaskScheduler scheduler) {
		this.publisher = publisher;
		this.scheduler = scheduler;
	}

	// @Scheduled(cron = "*/33 * * * * *")
	@EventListener(ApplicationReadyEvent.class)
	public void scheduled() {
		logger().info("TRIGGERED!");
		scheduler.schedule(() -> {
			var event = new AlphaInitiatedEvent(Instant.now());
			logger().info("SCHEDULED -------- @ {}", event);
			publisher.publishEvent(event);
		}, Instant.now());
	}

	record AlphaInitiatedEvent(Instant at) {
	}

}
