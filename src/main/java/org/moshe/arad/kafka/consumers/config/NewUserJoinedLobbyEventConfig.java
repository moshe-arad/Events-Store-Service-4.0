package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class NewUserJoinedLobbyEventConfig extends SimpleConsumerConfig {

	public NewUserJoinedLobbyEventConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.NEW_USER_JOINED_LOBBY_EVENT_GROUP);
	}
}
