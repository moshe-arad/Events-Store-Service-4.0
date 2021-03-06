package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class GameRoomClosedOpenByLeftLastEventConfig extends SimpleConsumerConfig{

	public GameRoomClosedOpenByLeftLastEventConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.GAME_ROOM_CLOSED_OPENBY_LEFT_LAST_EVENT_GROUP);
	}
}
