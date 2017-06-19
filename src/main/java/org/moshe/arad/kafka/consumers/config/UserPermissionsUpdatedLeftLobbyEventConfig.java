package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionsUpdatedLeftLobbyEventConfig extends SimpleConsumerConfig{

	public UserPermissionsUpdatedLeftLobbyEventConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.USER_PERMISSIONS_UPDATED_LEFT_LOBBY_EVENT_GROUP);
	}
}
