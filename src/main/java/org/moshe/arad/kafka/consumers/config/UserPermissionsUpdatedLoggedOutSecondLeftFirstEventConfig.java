package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionsUpdatedLoggedOutSecondLeftFirstEventConfig extends SimpleConsumerConfig{

	public UserPermissionsUpdatedLoggedOutSecondLeftFirstEventConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.USER_PERMISSIONS_UPDATED_LOGGED_OUT_SECOND_LEFT_FIRST_EVENT_GROUP);
	}
}
