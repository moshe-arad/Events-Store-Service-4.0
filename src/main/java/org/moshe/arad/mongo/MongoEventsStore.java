package org.moshe.arad.mongo;

import java.util.Date;
import java.util.LinkedList;

import org.moshe.arad.kafka.events.BackgammonEvent;
import org.moshe.arad.kafka.events.NewUserCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
	
	public LinkedList<BackgammonEvent> getEventsOccuredFrom(Date fromDate){
		Criteria criteria = Criteria.where("arrived").gte(fromDate);
		Query query = new Query(criteria).with(new Sort(new Order(Direction.ASC, "arrived")));
		LinkedList<BackgammonEvent> result = new LinkedList<>(mongoTemplate.find(query, BackgammonEvent.class));
		result.addFirst(new NewUserCreatedEvent(null, -1, "", -1, "", -1, "", null));
		result.addLast(new NewUserCreatedEvent(null, -1, "", -1, "", -1, "", null));
		
		return result;
	}
}

























