package com.example.dual_ds_tx.alpha;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AlphaService {
	
	private final ApplicationEventPublisher publisher;
	
	public AlphaService(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	@Scheduled(cron = "*/10 * * * * *")
	public void scheduled() {
		System.out.println("NOW NOW NOW....");
	}
}
