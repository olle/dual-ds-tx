package com.example.dual_ds_tx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Logging {
	default Logger logger() {
		return LoggerFactory.getLogger(getClass());
	}
}
