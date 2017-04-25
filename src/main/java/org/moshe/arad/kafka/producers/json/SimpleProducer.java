package org.moshe.arad.kafka.producers.json;

import org.moshe.arad.kafka.events.BackgammonEvent;

public interface SimpleProducer <T extends BackgammonEvent>{

	public void sendKafkaMessage(T event);
}
