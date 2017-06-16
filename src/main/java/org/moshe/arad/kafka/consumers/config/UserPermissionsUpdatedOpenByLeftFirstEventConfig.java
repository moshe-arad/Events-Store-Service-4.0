package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionsUpdatedOpenByLeftFirstEventConfig extends SimpleConsumerConfig{

	public UserPermissionsUpdatedOpenByLeftFirstEventConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.USER_PERMISSIONS_UPDATED_OPENBY_LEFT_FIRST_EVENT_GROUP);
	}
}
