package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionsUpdatedSecondLeftEventConfig extends SimpleConsumerConfig{

	public UserPermissionsUpdatedSecondLeftEventConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.USER_PERMISSIONS_UPDATED_SECOND_LEFT_EVENT_GROUP);
	}
}
