package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionsUpdatedLoggedOutWatcherLeftLastEventConfig extends SimpleConsumerConfig{

	public UserPermissionsUpdatedLoggedOutWatcherLeftLastEventConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.USER_PERMISSIONS_UPDATED_LOGGED_OUT_WATCHER_LEFT_LAST_EVENT_GROUP);
	}
}
