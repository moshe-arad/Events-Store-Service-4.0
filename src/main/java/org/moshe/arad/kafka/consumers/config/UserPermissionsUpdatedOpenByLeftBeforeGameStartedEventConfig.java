package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionsUpdatedOpenByLeftBeforeGameStartedEventConfig extends SimpleConsumerConfig{

	public UserPermissionsUpdatedOpenByLeftBeforeGameStartedEventConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.USER_PERMISSIONS_UPDATED_LOGGED_OUT_OPENBY_LEFT_BEFORE_GAME_STARTED_EVENT_GROUP);
	}
}
