package org.moshe.arad.kafka.consumers.commands.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.moshe.arad.kafka.consumers.SimpleConsumerConfig;
import org.springframework.stereotype.Component;

@Component
public class PullEventsCommandConfigNonJson extends SimpleConsumerConfig{

	public PullEventsCommandConfigNonJson() {
		super();
		super.getProperties().put("group.id", KafkaUtils.PULL_EVENTS_COMMAND_GROUP);
		super.getProperties().put("value.deserializer", KafkaUtils.PULL_EVENTS_COMMAND_DESERIALIZER);
	}
}
