package org.moshe.arad.kafka.consumers.config;

import org.moshe.arad.kafka.KafkaUtils;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionsUpdatedAddedWatcherEventConfig extends SimpleConsumerConfig{

	public UserPermissionsUpdatedAddedWatcherEventConfig() {
		super();
		super.getProperties().put("group.id", KafkaUtils.USER_PERMISSIONS_UPDATED_ADDED_WATCHER_EVENT_GROUP);
	}
}
