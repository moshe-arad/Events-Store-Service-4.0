package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionsUpdatedLoggedOutWatcherLeftEventConfig extends SimpleConsumerConfig{

	public UserPermissionsUpdatedLoggedOutWatcherLeftEventConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.USER_PERMISSIONS_UPDATED_LOGGED_OUT_WATCHER_LEFT_EVENT_GROUP);
	}
}
