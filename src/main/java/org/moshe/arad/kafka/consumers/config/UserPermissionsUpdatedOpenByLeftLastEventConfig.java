package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionsUpdatedOpenByLeftLastEventConfig extends SimpleConsumerConfig{

	public UserPermissionsUpdatedOpenByLeftLastEventConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.USER_PERMISSIONS_UPDATED_OPENBY_LEFT_LAST_EVENT_GROUP);
	}
}
