package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionsUpdatedSecondLeftFirstEventConfig extends SimpleConsumerConfig{

	public UserPermissionsUpdatedSecondLeftFirstEventConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.USER_PERMISSIONS_UPDATED_SECOND_LEFT_FIRST_EVENT_GROUP);
	}
}
