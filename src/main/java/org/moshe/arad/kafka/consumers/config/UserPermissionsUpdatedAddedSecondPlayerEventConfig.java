package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionsUpdatedAddedSecondPlayerEventConfig extends SimpleConsumerConfig{

	public UserPermissionsUpdatedAddedSecondPlayerEventConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.USER_PERMISSIONS_UPDATED_ADDED_SECOND_PLAYER_EVENT_GROUP);
	}
}
