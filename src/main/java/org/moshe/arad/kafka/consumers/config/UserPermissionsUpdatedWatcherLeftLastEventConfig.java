package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionsUpdatedWatcherLeftLastEventConfig extends SimpleConsumerConfig{

	public UserPermissionsUpdatedWatcherLeftLastEventConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.USER_PERMISSIONS_UPDATED_WATCHER_LEFT_LAST_EVENT_GROUP);
	}
}
