package org.moshe.arad.mongo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.UUID;
import java.util.stream.Collectors;

import org.moshe.arad.kafka.events.BackgammonEvent;
import org.moshe.arad.kafka.events.EndReadEventsFromMongoEvent;
import org.moshe.arad.kafka.events.NewUserCreatedEvent;
import org.moshe.arad.kafka.events.NewUserJoinedLobbyEvent;
import org.moshe.arad.kafka.events.StartReadEventsFromMongoEvent;
import org.moshe.arad.mongo.events.MongoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	public void addNewEvent(NewUserCreatedEvent newUserCreatedEvent){
		try{
			mongoTemplate.insert(newUserCreatedEvent, "newUserCreatedEvents");
		}
		catch (Exception ex) {
			logger.error("Failed to save newUserCreatedEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		
	}
	
	public void addNewEvent(NewUserJoinedLobbyEvent newUserJoinedLobbyEvent){
		try{
			mongoTemplate.insert(newUserJoinedLobbyEvent, "newUserJoinedLobbyEvents");
		}
		catch (Exception ex) {
			logger.error("Failed to save newUserJoinedLobbyEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		
	}
	
	public LinkedList<BackgammonEvent> getEventsOccuredFrom(UUID uuid, Date fromDate, boolean isToSaveEvents){
		LinkedList<org.moshe.arad.mongo.events.NewUserCreatedEvent> mongoEventsNewUserCreatedEvent = null;
		LinkedList<org.moshe.arad.mongo.events.NewUserJoinedLobbyEvent> mongoEventsNewUserJoinedLobbyEvent = null;
		ArrayList<org.moshe.arad.mongo.events.MongoEvent> mongoEvents = new ArrayList<>();
		LinkedList<BackgammonEvent> result = new LinkedList<>();
		
		if(fromDate == null){
			mongoEventsNewUserCreatedEvent = new LinkedList<>(mongoTemplate.findAll(org.moshe.arad.mongo.events.NewUserCreatedEvent.class));
			mongoEventsNewUserJoinedLobbyEvent = new LinkedList<>(mongoTemplate.findAll(org.moshe.arad.mongo.events.NewUserJoinedLobbyEvent.class));			
		}
		else{
			Criteria criteria = Criteria.where("arrived").gte(fromDate);
			Query query = new Query(criteria);
			mongoEventsNewUserCreatedEvent = new LinkedList<>(mongoTemplate.find(query, org.moshe.arad.mongo.events.NewUserCreatedEvent.class));
			mongoEventsNewUserJoinedLobbyEvent = new LinkedList<>(mongoTemplate.find(query, org.moshe.arad.mongo.events.NewUserJoinedLobbyEvent.class));
		}
		
		mongoEvents.addAll(mongoEventsNewUserCreatedEvent);
		mongoEvents.addAll(mongoEventsNewUserJoinedLobbyEvent);
		mongoEvents = (ArrayList<MongoEvent>) mongoEvents.stream().sorted((MongoEvent e1, MongoEvent e2) -> {return e2.getArrived().compareTo(e1.getArrived());}).collect(Collectors.toList());
		
		ListIterator<org.moshe.arad.mongo.events.MongoEvent> it = mongoEvents.listIterator();
		
		while(it.hasNext()){
			result.push(this.convertTo(it.next(), uuid));
		}
		
		int totalNumOfEvents = result.size();
		result.addFirst(new StartReadEventsFromMongoEvent(uuid, 3, 5, new Date(), "StartReadEventsFromMongoEvent", totalNumOfEvents, isToSaveEvents)); 
		result.addLast(new EndReadEventsFromMongoEvent(uuid, 3, 6, new Date(), "EndReadEventsFromMongoEvent", totalNumOfEvents));
		
		return result;
	}
	
	private BackgammonEvent convertTo(MongoEvent mongoEvent, UUID uuid){
		if(mongoEvent.getClass().equals(org.moshe.arad.mongo.events.NewUserCreatedEvent.class)){
			org.moshe.arad.mongo.events.NewUserCreatedEvent newUserCreatedEventMongo = (org.moshe.arad.mongo.events.NewUserCreatedEvent)mongoEvent; 
			NewUserCreatedEvent newUserCreatedEvent = new NewUserCreatedEvent(uuid, newUserCreatedEventMongo.getServiceId(), newUserCreatedEventMongo.getEventId(), newUserCreatedEventMongo.getArrived(), "NewUserCreatedEvent",newUserCreatedEventMongo.getBackgammonUser());
			return newUserCreatedEvent;
		}
		else if(mongoEvent.getClass().equals(org.moshe.arad.mongo.events.NewUserJoinedLobbyEvent.class)){
			org.moshe.arad.mongo.events.NewUserJoinedLobbyEvent newUserJoinedLobbyEventMongo = (org.moshe.arad.mongo.events.NewUserJoinedLobbyEvent)mongoEvent;
			NewUserJoinedLobbyEvent newUserJoinedLobbyEvent = new NewUserJoinedLobbyEvent(uuid, newUserJoinedLobbyEventMongo.getServiceId(), newUserJoinedLobbyEventMongo.getEventId(), newUserJoinedLobbyEventMongo.getArrived(), "NewUserJoinedLobbyEvent", newUserJoinedLobbyEventMongo.getBackgammonUser());
			return newUserJoinedLobbyEvent;
		}
		return null;
	}
}

























