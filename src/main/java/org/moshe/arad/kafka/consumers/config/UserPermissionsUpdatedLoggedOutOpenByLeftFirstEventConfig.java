package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionsUpdatedLoggedOutOpenByLeftFirstEventConfig extends SimpleConsumerConfig{

	public UserPermissionsUpdatedLoggedOutOpenByLeftFirstEventConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.USER_PERMISSIONS_UPDATED_LOGGED_OUT_OPENBY_LEFT_FIRST_EVENT_GROUP);
	}
}
