package org.moshe.arad.mongo;

import org.moshe.arad.kafka.events.BackgammonEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoEventsStore {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private MongoOperations mongoOperations;
	
	private Logger logger = LoggerFactory.getLogger(MongoEventsStore.class);
	
	public void addNewEvent(BackgammonEvent backgammonEvent){
		try{
			mongoTemplate.insert(backgammonEvent, "events");
		}
		catch (Exception ex) {
			logger.error("Failed to save event into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		
	}
	
}
