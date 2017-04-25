package org.moshe.arad.mongo;

import java.util.Date;
import java.util.LinkedList;

import org.moshe.arad.kafka.events.BackgammonEvent;
import org.moshe.arad.kafka.events.NewUserCreatedEvent;
import org.moshe.arad.mongo.events.MongoEvent;
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
	
	public LinkedList<MongoEvent> getEventsOccuredFrom(Date fromDate){
		LinkedList<MongoEvent> result;
		
		if(fromDate == null){
			result = new LinkedList<>(mongoTemplate.findAll(MongoEvent.class));
//			result.add(mongoTemplate.findOne(new Query().addCriteria(new Criteria().where("eventType").is("NewUserCreatedEvent")), NewUserCreatedEvent.class));
		}
		else{
			Criteria criteria = Criteria.where("arrived").gte(fromDate);
			Query query = new Query(criteria).with(new Sort(new Order(Direction.ASC, "arrived")));
			result = new LinkedList<>(mongoTemplate.find(query, MongoEvent.class));
		}
				
		result.addFirst(new MongoEvent("begin", null, null, -1));
		result.addLast(new MongoEvent("end", null, null, -1));
		
		return result;
	}
	
	private BackgammonEvent convertTo(MongoEvent mongoEvent){
		if(mongoEvent.getClass().equals(org.moshe.arad.mongo.events.NewUserCreatedEvent.class)){
			org.moshe.arad.mongo.events.NewUserCreatedEvent newUserCreatedEventMongo = (org.moshe.arad.mongo.events.NewUserCreatedEvent)mongoEvent; 
			NewUserCreatedEvent newUserCreatedEvent = new NewUserCreatedEvent(newUserCreatedEventMongo.getUuid(), 
					newUserCreatedEventMongo.get, serviceName, entityId, entityType, eventId, eventType, arrived, backgammonUser)
		}
	}
}

























