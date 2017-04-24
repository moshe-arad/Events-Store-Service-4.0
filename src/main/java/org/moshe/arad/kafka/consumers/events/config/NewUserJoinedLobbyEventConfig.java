package org.moshe.arad.kafka.consumers.events.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.moshe.arad.kafka.consumers.SimpleConsumerConfig;
import org.springframework.stereotype.Component;

@Component
public class NewUserJoinedLobbyEventConfig extends SimpleConsumerConfig {

	public NewUserJoinedLobbyEventConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.NEW_USER_JOINED_LOBBY_EVENT_GROUP);
		super.getProperties().put("value.deserializer", KafkaUtils.NEW_USER_JOINED_LOBBY_EVENT_DESERIALIZER);
	}
}
