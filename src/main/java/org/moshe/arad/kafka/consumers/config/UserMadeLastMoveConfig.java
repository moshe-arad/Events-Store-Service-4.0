package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMadeLastMoveConfig extends SimpleConsumerConfig{

	public UserMadeLastMoveConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.USER_MADE_LAST_MOVE_EVENT_GROUP);
	}
}
