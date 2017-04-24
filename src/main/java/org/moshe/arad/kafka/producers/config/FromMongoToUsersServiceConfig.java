package org.moshe.arad.kafka.producers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class FromMongoToUsersServiceConfig extends SimpleProducerConfig {

	public FromMongoToUsersServiceConfig() {
		super();
		super.getProperties().put("value.serializer", KafkaUtils.PULL_EVENTS_COMMAND_SERIALIZER);
	}
}
