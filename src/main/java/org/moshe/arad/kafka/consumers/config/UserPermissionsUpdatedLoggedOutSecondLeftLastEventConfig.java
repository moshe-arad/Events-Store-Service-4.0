package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionsUpdatedLoggedOutSecondLeftLastEventConfig extends SimpleConsumerConfig{

	public UserPermissionsUpdatedLoggedOutSecondLeftLastEventConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.USER_PERMISSIONS_UPDATED_LOGGED_OUT_SECOND_LEFT_LAST_EVENT_GROUP);
	}
}
