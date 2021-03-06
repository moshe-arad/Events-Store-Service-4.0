package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class GameRoomClosedLoggedOutOpenByLeftLastEventConfig extends SimpleConsumerConfig{

	public GameRoomClosedLoggedOutOpenByLeftLastEventConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.GAME_ROOM_CLOSED_LOGGED_OUT_OPENBY_LEFT_LAST_EVENT_GROUP);
	}
}
