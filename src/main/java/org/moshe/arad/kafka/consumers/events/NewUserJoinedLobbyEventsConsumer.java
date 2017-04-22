package org.moshe.arad.kafka.consumers.events;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.moshe.arad.kafka.consumers.config.SimpleConsumerConfig;
import org.moshe.arad.kafka.events.NewUserJoinedLobbyEvent;
import org.moshe.arad.mongo.MongoEventsStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewUserJoinedLobbyEventsConsumer extends SimpleBackgammonEventsConsumer<NewUserJoinedLobbyEvent> {

	@Autowired
	private MongoEventsStore mongoEventsStore;
	
	Logger logger = LoggerFactory.getLogger(NewUserCreatedEventConsumer.class);
	
	public NewUserJoinedLobbyEventsConsumer() {
	}
	
	public NewUserJoinedLobbyEventsConsumer(SimpleConsumerConfig simpleConsumerConfig, String topic) {
		super(simpleConsumerConfig, topic);
	}

	@Override
	public void consumerOperations(ConsumerRecord<String,NewUserJoinedLobbyEvent> record) {
    	try{
    		logger.info("New User Joined Lobby Event record recieved, " + record.value());	             
        	logger.info("Event recieved, try to put it in events store...");	                
        	NewUserJoinedLobbyEvent newUserJoinedLobbyEvent = (NewUserJoinedLobbyEvent)record.value();
        	mongoEventsStore.addNewEvent(newUserJoinedLobbyEvent);
        	logger.info("Event saved into events store successfully...");
    	}
		catch (Exception ex) {
			logger.error("Error occured while trying to save event in mongo events store...");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}	
}
