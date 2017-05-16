package org.moshe.arad.mongo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.UUID;
import java.util.stream.Collectors;

import org.moshe.arad.kafka.Services;
import org.moshe.arad.kafka.events.BackgammonEvent;
import org.moshe.arad.kafka.events.EndReadEventsFromMongoEvent;
import org.moshe.arad.kafka.events.ExistingUserJoinedLobbyEvent;
import org.moshe.arad.kafka.events.LoggedInEvent;
import org.moshe.arad.kafka.events.LoggedOutEvent;
import org.moshe.arad.kafka.events.NewGameRoomOpenedEvent;
import org.moshe.arad.kafka.events.NewUserCreatedEvent;
import org.moshe.arad.kafka.events.NewUserJoinedLobbyEvent;
import org.moshe.arad.kafka.events.StartReadEventsFromMongoEvent;
import org.moshe.arad.mongo.events.ExistingUserJoinedLobbyMongoEvent;
import org.moshe.arad.mongo.events.IMongoEvent;
import org.moshe.arad.mongo.events.LoggedInMongoEvent;
import org.moshe.arad.mongo.events.LoggedOutMongoEvent;
import org.moshe.arad.mongo.events.NewGameRoomOpenedMongoEvent;
import org.moshe.arad.mongo.events.NewUserCreatedMongoEvent;
import org.moshe.arad.mongo.events.NewUserJoinedLobbyMongoEvent;
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
	
	public void addNewUserCreatedEvent(NewUserCreatedEvent newUserCreatedEvent){
		try{
			NewUserCreatedMongoEvent newUserCreatedMongoEvent = NewUserCreatedMongoEvent.convertIntoMongoEvent(newUserCreatedEvent);
			
			mongoTemplate.insert(newUserCreatedMongoEvent, "newUserCreatedEvents");
		}
		catch (Exception ex) {
			logger.error("Failed to save newUserCreatedEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		
	}
	
	public void addNewUserJoinedLobbyEvent(NewUserJoinedLobbyEvent newUserJoinedLobbyEvent){
		try{
			NewUserJoinedLobbyMongoEvent newUserJoinedLobbyMongoEvent = NewUserJoinedLobbyMongoEvent.convertIntoMongoEvent(newUserJoinedLobbyEvent);
			
			mongoTemplate.insert(newUserJoinedLobbyMongoEvent, "newUserJoinedLobbyEvents");
		}
		catch (Exception ex) {
			logger.error("Failed to save newUserJoinedLobbyEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		
	}
	
	public void addLoggedInEvent(LoggedInEvent loggedInEvent) {
		try{
			
			LoggedInMongoEvent loggedInMongoEvent = LoggedInMongoEvent.convertIntoMongoEvent(loggedInEvent);
			
			mongoTemplate.insert(loggedInMongoEvent, "loggedInEvents");
		}
		catch (Exception ex) {
			logger.error("Failed to save newUserJoinedLobbyEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addExistingUserJoinedLobbyEvent(ExistingUserJoinedLobbyEvent existingUserJoinedLobbyEvent) {
		try{
			ExistingUserJoinedLobbyMongoEvent existingUserJoinedLobbyMongoEvent = ExistingUserJoinedLobbyMongoEvent.convertIntoMongoEvent(existingUserJoinedLobbyEvent);
			
			mongoTemplate.insert(existingUserJoinedLobbyMongoEvent, "existingUserJoinedLobbyEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save existingUserJoinedLobbyEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}		
	}
	
	public void addLoggedOutEvent(LoggedOutEvent loggedOutEvent) {
		try{
			LoggedOutMongoEvent loggedOutMongoEvent = LoggedOutMongoEvent.convertIntoMongoEvent(loggedOutEvent);
			
			mongoTemplate.insert(loggedOutMongoEvent, "loggedOutEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save existingUserJoinedLobbyEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}	
		
	}
	
	public void addNewGameRoomEvent(NewGameRoomOpenedEvent newGameRoomOpenedEvent) {
		try{
			NewGameRoomOpenedMongoEvent newGameRoomOpenedMongoEvent = NewGameRoomOpenedMongoEvent.convertIntoMongoEvent(newGameRoomOpenedEvent);
			
			mongoTemplate.insert(newGameRoomOpenedMongoEvent, "newGameRoomOpenedEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save existingUserJoinedLobbyEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}		
	}
	
	public synchronized LinkedList<BackgammonEvent> getEventsOccuredFrom(UUID uuid, Date fromDate, String serviceName){
		LinkedList<NewUserCreatedMongoEvent> mongoEventsNewUserCreatedEvent = null;
		LinkedList<NewUserJoinedLobbyMongoEvent> mongoEventsNewUserJoinedLobbyEvent = null;
		LinkedList<LoggedInMongoEvent> mongoEventsLoggedInEvent = null;
		LinkedList<ExistingUserJoinedLobbyMongoEvent> mongoEventsExistingUserJoinedLobbyEvent = null;
		LinkedList<LoggedOutMongoEvent> mongoEventsLoggedOutEvent = null;		
		LinkedList<NewGameRoomOpenedMongoEvent> mongoEventsNewGameRoomOpenedEvent = null;
		
		ArrayList<IMongoEvent> mongoEvents = new ArrayList<>(100000);
		LinkedList<BackgammonEvent> result = new LinkedList<>();
		
		if(fromDate == null){
			if(serviceName.equals(Services.Users.name())){
				mongoEventsNewUserCreatedEvent = new LinkedList<>(mongoTemplate.findAll(NewUserCreatedMongoEvent.class));
				mongoEventsNewUserJoinedLobbyEvent = new LinkedList<>(mongoTemplate.findAll(NewUserJoinedLobbyMongoEvent.class));
				mongoEventsLoggedInEvent = new LinkedList<>(mongoTemplate.findAll(LoggedInMongoEvent.class));
				mongoEventsExistingUserJoinedLobbyEvent = new LinkedList<>(mongoTemplate.findAll(ExistingUserJoinedLobbyMongoEvent.class));
				mongoEventsLoggedOutEvent = new LinkedList<>(mongoTemplate.findAll(LoggedOutMongoEvent.class));
			}
			else if(serviceName.equals(Services.Lobby.name())){
				mongoEventsNewGameRoomOpenedEvent = new LinkedList<>(mongoTemplate.findAll(NewGameRoomOpenedMongoEvent.class));
			}			
		}
		else{
			Criteria criteria = Criteria.where("arrived").gte(fromDate);
			Query query = new Query(criteria);
			
			if(serviceName.equals(Services.Users.name())){
				mongoEventsNewUserCreatedEvent = new LinkedList<>(mongoTemplate.find(query, NewUserCreatedMongoEvent.class));
				mongoEventsNewUserJoinedLobbyEvent = new LinkedList<>(mongoTemplate.find(query, NewUserJoinedLobbyMongoEvent.class));
				mongoEventsLoggedInEvent = new LinkedList<>(mongoTemplate.find(query,LoggedInMongoEvent.class));
				mongoEventsExistingUserJoinedLobbyEvent = new LinkedList<>(mongoTemplate.find(query, ExistingUserJoinedLobbyMongoEvent.class));
				mongoEventsLoggedOutEvent = new LinkedList<>(mongoTemplate.find(query, LoggedOutMongoEvent.class));								
			}
			else if(serviceName.equals(Services.Lobby.name())){
				mongoEventsNewGameRoomOpenedEvent = new LinkedList<>(mongoTemplate.find(query, NewGameRoomOpenedMongoEvent.class));
			}			
		}
		
		if(serviceName.equals(Services.Users.name())){
			mongoEvents.addAll(mongoEventsNewUserCreatedEvent);
			mongoEvents.addAll(mongoEventsNewUserJoinedLobbyEvent);
			mongoEvents.addAll(mongoEventsLoggedInEvent);
			mongoEvents.addAll(mongoEventsExistingUserJoinedLobbyEvent);
			mongoEvents.addAll(mongoEventsLoggedOutEvent);
		}
		else if(serviceName.equals(Services.Lobby.name())){
			mongoEvents.addAll(mongoEventsNewGameRoomOpenedEvent);
		}
				
//		mongoEvents = (ArrayList<IMongoEvent>) mongoEvents.stream().sorted((IMongoEvent e1, IMongoEvent e2) -> {return e2.getArrived().compareTo(e1.getArrived());}).collect(Collectors.toList());
		
		ListIterator<IMongoEvent> it = mongoEvents.listIterator();
		
		while(it.hasNext()){
			result.push(this.convertTo(it.next(), uuid));
		}
		
		int totalNumOfEvents = result.size();
		result.addFirst(new StartReadEventsFromMongoEvent(uuid, 3, 5, new Date(), "StartReadEventsFromMongoEvent", totalNumOfEvents)); 
		result.addLast(new EndReadEventsFromMongoEvent(uuid, 3, 6, new Date(), "EndReadEventsFromMongoEvent", totalNumOfEvents));
		
		return result;
	}
	
	private BackgammonEvent convertTo(IMongoEvent mongoEvent, UUID uuid){
		if(mongoEvent.getClass().equals(NewUserCreatedMongoEvent.class)){
			NewUserCreatedMongoEvent newUserCreatedEventMongo = (NewUserCreatedMongoEvent)mongoEvent; 
			NewUserCreatedEvent newUserCreatedEvent = new NewUserCreatedEvent(uuid, newUserCreatedEventMongo.getServiceId(), newUserCreatedEventMongo.getEventId(), newUserCreatedEventMongo.getArrived(), "NewUserCreatedEvent",newUserCreatedEventMongo.getBackgammonUser());
			return newUserCreatedEvent;
		}
		else if(mongoEvent.getClass().equals(NewUserJoinedLobbyMongoEvent.class)){
			NewUserJoinedLobbyMongoEvent newUserJoinedLobbyEventMongo = (NewUserJoinedLobbyMongoEvent)mongoEvent;
			NewUserJoinedLobbyEvent newUserJoinedLobbyEvent = new NewUserJoinedLobbyEvent(uuid, newUserJoinedLobbyEventMongo.getServiceId(), newUserJoinedLobbyEventMongo.getEventId(), newUserJoinedLobbyEventMongo.getArrived(), "NewUserJoinedLobbyEvent", newUserJoinedLobbyEventMongo.getBackgammonUser());
			return newUserJoinedLobbyEvent;
		}
		else if(mongoEvent.getClass().equals(LoggedInMongoEvent.class)){
			LoggedInMongoEvent loggedInMongoEvent = (LoggedInMongoEvent)mongoEvent; 
			LoggedInEvent loggedInEvent = new LoggedInEvent(uuid, loggedInMongoEvent.getServiceId(), loggedInMongoEvent.getEventId(), loggedInMongoEvent.getArrived(), "LoggedInEvent",loggedInMongoEvent.getBackgammonUser());
			return loggedInEvent;
		}
		else if(mongoEvent.getClass().equals(ExistingUserJoinedLobbyMongoEvent.class)){
			ExistingUserJoinedLobbyMongoEvent existingUserJoinedLobbyMongoEvent = (ExistingUserJoinedLobbyMongoEvent)mongoEvent;
			ExistingUserJoinedLobbyEvent existingUserJoinedLobbyEvent = new ExistingUserJoinedLobbyEvent(uuid, existingUserJoinedLobbyMongoEvent.getServiceId(), existingUserJoinedLobbyMongoEvent.getEventId(), existingUserJoinedLobbyMongoEvent.getArrived(), "ExistingUserJoinedLobbyEvent", existingUserJoinedLobbyMongoEvent.getBackgammonUser());
			return existingUserJoinedLobbyEvent;
		}
		else if(mongoEvent.getClass().equals(LoggedOutMongoEvent.class)){
			LoggedOutMongoEvent loggedOutMongoEvent = (LoggedOutMongoEvent)mongoEvent;
			LoggedOutEvent loggedOutEvent = new LoggedOutEvent(uuid, loggedOutMongoEvent.getServiceId(), loggedOutMongoEvent.getEventId(), loggedOutMongoEvent.getArrived(), "LoggedOutEvent", loggedOutMongoEvent.getBackgammonUser());
			return loggedOutEvent;
		}
		else if(mongoEvent.getClass().equals(NewGameRoomOpenedMongoEvent.class)){
			NewGameRoomOpenedMongoEvent newGameRoomOpenedMongoEvent = (NewGameRoomOpenedMongoEvent)mongoEvent;
			NewGameRoomOpenedEvent newGameRoomOpenedEvent = new NewGameRoomOpenedEvent(uuid, newGameRoomOpenedMongoEvent.getServiceId(), newGameRoomOpenedMongoEvent.getEventId(), newGameRoomOpenedMongoEvent.getArrived(), "NewGameRoomOpenedEvent", newGameRoomOpenedMongoEvent.getGameRoom());
			return newGameRoomOpenedEvent;
		}
		else{
			throw new RuntimeException("Failed to convert mongo event....");
		}
	}
}

























