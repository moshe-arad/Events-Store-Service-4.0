package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class ExistingUserJoinedLobbyEventConfig extends SimpleConsumerConfig {

	public ExistingUserJoinedLobbyEventConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.EXISTING_USER_JOINED_LOBBY_EVENT_GROUP);
	}
}
