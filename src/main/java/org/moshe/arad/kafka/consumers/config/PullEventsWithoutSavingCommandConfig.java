package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class PullEventsWithoutSavingCommandConfig extends SimpleConsumerConfig{

	public PullEventsWithoutSavingCommandConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.PULL_EVENTS_WITHOUT_SAVING_COMMAND_GROUP);
	}
}
