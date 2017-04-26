package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class PullEventsCommandConfig extends SimpleConsumerConfig{

	public PullEventsCommandConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.PULL_EVENTS_COMMAND_GROUP);
	}
}
