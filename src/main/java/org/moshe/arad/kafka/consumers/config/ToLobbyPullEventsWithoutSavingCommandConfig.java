package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class ToLobbyPullEventsWithoutSavingCommandConfig extends SimpleConsumerConfig{

	public ToLobbyPullEventsWithoutSavingCommandConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.TO_LOBBY_PULL_EVENTS_WITHOUT_SAVING_COMMAND_GROUP);
	}
}
