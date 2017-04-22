package org.moshe.arad;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.moshe.arad.kafka.KafkaUtils;
import org.moshe.arad.kafka.consumers.config.NewUserCreatedEventConfig;
import org.moshe.arad.kafka.consumers.config.NewUserJoinedLobbyEventConfig;
import org.moshe.arad.kafka.consumers.events.NewUserCreatedEventConsumer;
import org.moshe.arad.kafka.consumers.events.NewUserJoinedLobbyEventsConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppInit {

	@Autowired
	private NewUserCreatedEventConsumer newUserCreatedEventConsumer;
	
	@Autowired
	private NewUserCreatedEventConfig newUserCreatedEventConfig;
	
	@Autowired
	private NewUserJoinedLobbyEventsConsumer newUserJoinedLobbyEventConsumer;
	
	@Autowired
	private NewUserJoinedLobbyEventConfig newUserJoinedLobbyEventConfig;
	
	private ExecutorService executor = Executors.newFixedThreadPool(6);
	
	private Logger logger = LoggerFactory.getLogger(AppInit.class);
	
	public void acceptNewEvents(){
		logger.info("Started to accept new events from services...");
		
		newUserCreatedEventConsumer.setTopic(KafkaUtils.NEW_USER_CREATED_EVENT_TOPIC);
		newUserCreatedEventConsumer.setSimpleConsumerConfig(newUserCreatedEventConfig);
		newUserCreatedEventConsumer.initConsumer();
		executor.execute(newUserCreatedEventConsumer);
		
		newUserJoinedLobbyEventConsumer.setTopic(KafkaUtils.NEW_USER_JOINED_LOBBY_EVENT_TOPIC);
		newUserJoinedLobbyEventConsumer.setSimpleConsumerConfig(newUserJoinedLobbyEventConfig);
		newUserJoinedLobbyEventConsumer.initConsumer();
		executor.execute(newUserJoinedLobbyEventConsumer);
		
		logger.info("Stopped to accept new events from services...");
	}
	
	public void shutdown(){
		newUserCreatedEventConsumer.setRunning(false);
		newUserCreatedEventConsumer.getScheduledExecutor().shutdown();
		
		newUserJoinedLobbyEventConsumer.setRunning(false);
		newUserJoinedLobbyEventConsumer.getScheduledExecutor().shutdown();
		
		this.executor.shutdown();
	}
}
