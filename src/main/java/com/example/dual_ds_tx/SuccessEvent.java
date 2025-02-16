package com.example.dual_ds_tx;

import org.jmolecules.event.annotation.DomainEvent;

@DomainEvent
public record SuccessEvent(Object source) {
}
