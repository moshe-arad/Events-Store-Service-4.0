package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class PullEventsWithSavingCommandConfig extends SimpleConsumerConfig{

	public PullEventsWithSavingCommandConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.PULL_EVENTS_WITH_SAVING_COMMAND_GROUP);
	}
}
