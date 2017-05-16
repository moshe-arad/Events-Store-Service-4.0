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
import org.moshe.arad.mongo.events.UserMongoEvent;
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
			UserMongoEvent newUserCreatedMongoEvent = UserMongoEvent.convertIntoMongoEvent(newUserCreatedEvent);
			
			mongoTemplate.insert(newUserCreatedMongoEvent, "userEvents");
		}
		catch (Exception ex) {
			logger.error("Failed to save newUserCreatedEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		
	}
	
	public void addNewUserJoinedLobbyEvent(NewUserJoinedLobbyEvent newUserJoinedLobbyEvent){
		try{
			UserMongoEvent newUserJoinedLobbyMongoEvent = UserMongoEvent.convertIntoMongoEvent(newUserJoinedLobbyEvent);
			
			mongoTemplate.insert(newUserJoinedLobbyMongoEvent, "userEvents");
		}
		catch (Exception ex) {
			logger.error("Failed to save newUserJoinedLobbyEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		
	}
	
	public void addLoggedInEvent(LoggedInEvent loggedInEvent) {
		try{
			
			UserMongoEvent loggedInMongoEvent = UserMongoEvent.convertIntoMongoEvent(loggedInEvent);
			
			mongoTemplate.insert(loggedInMongoEvent, "userEvents");
		}
		catch (Exception ex) {
			logger.error("Failed to save newUserJoinedLobbyEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addExistingUserJoinedLobbyEvent(ExistingUserJoinedLobbyEvent existingUserJoinedLobbyEvent) {
		try{
			UserMongoEvent existingUserJoinedLobbyMongoEvent = UserMongoEvent.convertIntoMongoEvent(existingUserJoinedLobbyEvent);
			
			mongoTemplate.insert(existingUserJoinedLobbyMongoEvent, "userEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save existingUserJoinedLobbyEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}		
	}
	
	public void addLoggedOutEvent(LoggedOutEvent loggedOutEvent) {
		try{
			UserMongoEvent loggedOutMongoEvent = UserMongoEvent.convertIntoMongoEvent(loggedOutEvent);
			
			mongoTemplate.insert(loggedOutMongoEvent, "userEvents");		
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
		LinkedList<UserMongoEvent> userMongoEvents = null;		
		LinkedList<NewGameRoomOpenedMongoEvent> mongoEventsNewGameRoomOpenedEvent = null;
		
		ArrayList<IMongoEvent> mongoEvents = new ArrayList<>(100000);
		LinkedList<BackgammonEvent> result = new LinkedList<>();
		
		if(fromDate == null){
			if(serviceName.equals(Services.Users.name())){
				userMongoEvents = new LinkedList<>(mongoTemplate.findAll(UserMongoEvent.class));
			}
			else if(serviceName.equals(Services.Lobby.name())){
				mongoEventsNewGameRoomOpenedEvent = new LinkedList<>(mongoTemplate.findAll(NewGameRoomOpenedMongoEvent.class));
			}			
		}
		else{
			Criteria criteria = Criteria.where("arrived").gte(fromDate);
			Query query = new Query(criteria);
			
			if(serviceName.equals(Services.Users.name())){
				userMongoEvents = new LinkedList<>(mongoTemplate.find(query, UserMongoEvent.class));								
			}
			else if(serviceName.equals(Services.Lobby.name())){
				mongoEventsNewGameRoomOpenedEvent = new LinkedList<>(mongoTemplate.find(query, NewGameRoomOpenedMongoEvent.class));
			}			
		}
		
		if(serviceName.equals(Services.Users.name())){
			mongoEvents.addAll(userMongoEvents);
		}
		else if(serviceName.equals(Services.Lobby.name())){
			mongoEvents.addAll(mongoEventsNewGameRoomOpenedEvent);
		}
		
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
		String clazz = mongoEvent.getClazz();
		
		if(clazz.equals("NewUserCreatedEvent")){
			UserMongoEvent newUserCreatedEventMongo = (UserMongoEvent)mongoEvent; 
			NewUserCreatedEvent newUserCreatedEvent = new NewUserCreatedEvent(uuid, newUserCreatedEventMongo.getServiceId(), newUserCreatedEventMongo.getEventId(), newUserCreatedEventMongo.getArrived(), "NewUserCreatedEvent",newUserCreatedEventMongo.getBackgammonUser());
			return newUserCreatedEvent;
		}
		else if(clazz.equals("NewUserJoinedLobbyEvent")){
			UserMongoEvent newUserJoinedLobbyEventMongo = (UserMongoEvent)mongoEvent;
			NewUserJoinedLobbyEvent newUserJoinedLobbyEvent = new NewUserJoinedLobbyEvent(uuid, newUserJoinedLobbyEventMongo.getServiceId(), newUserJoinedLobbyEventMongo.getEventId(), newUserJoinedLobbyEventMongo.getArrived(), "NewUserJoinedLobbyEvent", newUserJoinedLobbyEventMongo.getBackgammonUser());
			return newUserJoinedLobbyEvent;
		}
		else if(clazz.equals("LoggedInEvent")){
			UserMongoEvent loggedInMongoEvent = (UserMongoEvent)mongoEvent; 
			LoggedInEvent loggedInEvent = new LoggedInEvent(uuid, loggedInMongoEvent.getServiceId(), loggedInMongoEvent.getEventId(), loggedInMongoEvent.getArrived(), "LoggedInEvent",loggedInMongoEvent.getBackgammonUser());
			return loggedInEvent;
		}
		else if(clazz.equals("ExistingUserJoinedLobbyEvent")){
			UserMongoEvent existingUserJoinedLobbyMongoEvent = (UserMongoEvent)mongoEvent;
			ExistingUserJoinedLobbyEvent existingUserJoinedLobbyEvent = new ExistingUserJoinedLobbyEvent(uuid, existingUserJoinedLobbyMongoEvent.getServiceId(), existingUserJoinedLobbyMongoEvent.getEventId(), existingUserJoinedLobbyMongoEvent.getArrived(), "ExistingUserJoinedLobbyEvent", existingUserJoinedLobbyMongoEvent.getBackgammonUser());
			return existingUserJoinedLobbyEvent;
		}
		else if(clazz.equals("LoggedOutEvent")){
			UserMongoEvent loggedOutMongoEvent = (UserMongoEvent)mongoEvent;
			LoggedOutEvent loggedOutEvent = new LoggedOutEvent(uuid, loggedOutMongoEvent.getServiceId(), loggedOutMongoEvent.getEventId(), loggedOutMongoEvent.getArrived(), "LoggedOutEvent", loggedOutMongoEvent.getBackgammonUser());
			return loggedOutEvent;
		}
		else if(clazz.equals("NewGameRoomOpenedEvent")){
			NewGameRoomOpenedMongoEvent newGameRoomOpenedMongoEvent = (NewGameRoomOpenedMongoEvent)mongoEvent;
			NewGameRoomOpenedEvent newGameRoomOpenedEvent = new NewGameRoomOpenedEvent(uuid, newGameRoomOpenedMongoEvent.getServiceId(), newGameRoomOpenedMongoEvent.getEventId(), newGameRoomOpenedMongoEvent.getArrived(), "NewGameRoomOpenedEvent", newGameRoomOpenedMongoEvent.getGameRoom());
			return newGameRoomOpenedEvent;
		}
		else{
			throw new RuntimeException("Failed to convert mongo event....");
		}
	}
}

























