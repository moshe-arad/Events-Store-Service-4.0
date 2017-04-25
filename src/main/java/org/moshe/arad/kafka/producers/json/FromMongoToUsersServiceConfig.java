package org.moshe.arad.kafka.producers.json;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class FromMongoToUsersServiceConfig extends SimpleProducerConfig {

	public FromMongoToUsersServiceConfig() {
		super();
		super.getProperties().put("value.serializer", KafkaUtils.STRING_SERIALIZER);
	}
}
