package org.moshe.arad;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.moshe.arad.kafka.consumers.NewUserCreatedEventConsumer;
import org.moshe.arad.kafka.consumers.NewUserJoinedLobbyEventConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventsStoreService {

	@Autowired
	private NewUserCreatedEventConsumer newUserCreatedEventConsumer;
	
	@Autowired
	private NewUserJoinedLobbyEventConsumer newUserJoinedLobbyEventConsumer;
	
	private ExecutorService executor = Executors.newFixedThreadPool(6);
	
	private Logger logger = LoggerFactory.getLogger(EventsStoreService.class);
	
	public void acceptNewEvents(){
		logger.info("Started to accept new events from services...");
		executor.execute(newUserCreatedEventConsumer);
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
