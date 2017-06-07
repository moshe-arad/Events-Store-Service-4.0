package org.moshe.arad.mongo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.UUID;

import org.moshe.arad.kafka.Services;
import org.moshe.arad.kafka.events.BackgammonEvent;
import org.moshe.arad.kafka.events.EndReadEventsFromMongoEvent;
import org.moshe.arad.kafka.events.ExistingUserJoinedLobbyEvent;
import org.moshe.arad.kafka.events.GameRoomClosedEvent;
import org.moshe.arad.kafka.events.LoggedInEvent;
import org.moshe.arad.kafka.events.LoggedOutEvent;
import org.moshe.arad.kafka.events.LoggedOutOpenByLeftBeforeGameStartedEvent;
import org.moshe.arad.kafka.events.LoggedOutOpenByLeftEvent;
import org.moshe.arad.kafka.events.NewGameRoomOpenedEvent;
import org.moshe.arad.kafka.events.NewUserCreatedEvent;
import org.moshe.arad.kafka.events.NewUserJoinedLobbyEvent;
import org.moshe.arad.kafka.events.StartReadEventsFromMongoEvent;
import org.moshe.arad.kafka.events.UserAddedAsSecondPlayerEvent;
import org.moshe.arad.kafka.events.UserAddedAsWatcherEvent;
import org.moshe.arad.kafka.events.LoggedOutUserLeftLobbyEvent;
import org.moshe.arad.kafka.events.UserPermissionsUpdatedEvent;
import org.moshe.arad.mongo.events.ExistingUserJoinedLobbyMongoEvent;
import org.moshe.arad.mongo.events.GameRoomClosedMongoEvent;
import org.moshe.arad.mongo.events.IMongoEvent;
import org.moshe.arad.mongo.events.LoggedInMongoEvent;
import org.moshe.arad.mongo.events.LoggedOutMongoEvent;
import org.moshe.arad.mongo.events.LoggedOutOpenByLeftBeforeGameStartedMongoEvent;
import org.moshe.arad.mongo.events.LoggedOutOpenByLeftMongoEvent;
import org.moshe.arad.mongo.events.NewGameRoomOpenedMongoEvent;
import org.moshe.arad.mongo.events.NewUserCreatedMongoEvent;
import org.moshe.arad.mongo.events.NewUserJoinedLobbyMongoEvent;
import org.moshe.arad.mongo.events.UserAddedAsSecondPlayerMongoEvent;
import org.moshe.arad.mongo.events.UserAddedAsWatcherMongoEvent;
import org.moshe.arad.mongo.events.LoggedOutUserLeftLobbyMongoEvent;
import org.moshe.arad.mongo.events.UserPermissionsUpdatedMongoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class MongoEventsStore {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	private Logger logger = LoggerFactory.getLogger(MongoEventsStore.class);	
	
	public void addNewUserCreatedEvent(NewUserCreatedEvent newUserCreatedEvent){
		try{
			NewUserCreatedMongoEvent newUserCreatedMongoEvent = NewUserCreatedMongoEvent.convertIntoMongoEvent(newUserCreatedEvent);
			
			mongoTemplate.insert(newUserCreatedMongoEvent, "NewUserCreatedEvents");
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
			
			mongoTemplate.insert(newUserJoinedLobbyMongoEvent, "NewUserJoinedLobbyEvents");
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
			
			mongoTemplate.insert(loggedInMongoEvent, "LoggedInEvents");
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
			
			mongoTemplate.insert(existingUserJoinedLobbyMongoEvent, "ExistingUserJoinedLobbyEvents");		
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
			
			mongoTemplate.insert(newGameRoomOpenedMongoEvent, "NewGameRoomOpenedEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save NewGameRoomOpenedEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}		
	}
	
	public void addUserAddedAsWatcherEvent(UserAddedAsWatcherEvent userAddedAsWatcherEvent) {
		try{
			UserAddedAsWatcherMongoEvent userAddedAsWatcherMongoEvent = UserAddedAsWatcherMongoEvent.convertIntoMongoEvent(userAddedAsWatcherEvent);
			
			mongoTemplate.insert(userAddedAsWatcherMongoEvent, "UserAddedAsWatcherEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save UserAddedAsWatcherEvents into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}		
	}
	
	public void addUserAddedAsSecondPlayerEvent(UserAddedAsSecondPlayerEvent userAddedAsSecondPlayerEvent) {
		try{
			UserAddedAsSecondPlayerMongoEvent userAddedAsSecondPlayerMongoEvent = UserAddedAsSecondPlayerMongoEvent.convertIntoMongoEvent(userAddedAsSecondPlayerEvent);
			
			mongoTemplate.insert(userAddedAsSecondPlayerMongoEvent, "UserAddedAsSecondPlayerEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save UserAddedAsSecondPlayerEvents into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}		
	}
	
	public void addUserPermissionsUpdatedEvent(UserPermissionsUpdatedEvent userPermissionsUpdatedEvent) {
		try{
			UserPermissionsUpdatedMongoEvent userPermissionsUpdatedMongoEvent = UserPermissionsUpdatedMongoEvent.convertIntoMongoEvent(userPermissionsUpdatedEvent);
			
			mongoTemplate.insert(userPermissionsUpdatedMongoEvent, "UserPermissionsUpdatedEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save UserPermissionsUpdatedEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addLoggedOutEvent(LoggedOutEvent loggedOutEvent) {
		try{
			LoggedOutMongoEvent loggedOutMongoEvent = LoggedOutMongoEvent.convertIntoMongoEvent(loggedOutEvent);
			
			mongoTemplate.insert(loggedOutMongoEvent, "LoggedOutEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save LoggedOutEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addLoggedOutUserLeftLobbyEvent(LoggedOutUserLeftLobbyEvent userLeftLobbyEvent) {
		try{
			LoggedOutUserLeftLobbyMongoEvent userLeftLobbyMongoEvent = LoggedOutUserLeftLobbyMongoEvent.convertIntoMongoEvent(userLeftLobbyEvent);
			
			mongoTemplate.insert(userLeftLobbyMongoEvent, "LoggedOutUserLeftLobbyEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save UserLeftLobbyEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addLoggedOutOpenByLeftBeforeGameStartedEvent(
			LoggedOutOpenByLeftBeforeGameStartedEvent loggedOutOpenByLeftBeforeGameStartedEvent) {
		try{
			LoggedOutOpenByLeftBeforeGameStartedMongoEvent mongoEvent = LoggedOutOpenByLeftBeforeGameStartedMongoEvent.convertIntoMongoEvent(loggedOutOpenByLeftBeforeGameStartedEvent);
			
			mongoTemplate.insert(mongoEvent, "LoggedOutOpenByLeftBeforeGameStartedEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save LoggedOutOpenByLeftBeforeGameStartedEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addGameRoomClosedEvent(GameRoomClosedEvent gameRoomClosedEvent) {
		try{
			GameRoomClosedMongoEvent mongoEvent = GameRoomClosedMongoEvent.convertIntoMongoEvent(gameRoomClosedEvent);
			
			mongoTemplate.insert(mongoEvent, "GameRoomClosedEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save GameRoomClosedMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void addLoggedOutOpenByLeftEvent(LoggedOutOpenByLeftEvent loggedOutOpenByLeftEvent) {
		try{
			LoggedOutOpenByLeftMongoEvent mongoEvent = LoggedOutOpenByLeftMongoEvent.convertIntoMongoEvent(loggedOutOpenByLeftEvent);
			
			mongoTemplate.insert(mongoEvent, "LoggedOutOpenByLeftEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save GameRoomClosedMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}	
	
	public synchronized LinkedList<BackgammonEvent> getEventsOccuredFrom(UUID uuid, Date fromDate, String serviceName){
		LinkedList<NewUserCreatedMongoEvent> newUserCreatedMongoEvents = null;
		LinkedList<NewUserJoinedLobbyMongoEvent> newUserJoinedLobbyMongoEvents = null;
		LinkedList<LoggedInMongoEvent> loggedInMongoEvents = null;
		LinkedList<ExistingUserJoinedLobbyMongoEvent> existingUserJoinedLobbyMongoEvents = null;
		LinkedList<NewGameRoomOpenedMongoEvent> newGameRoomOpenedMongoEvents = null;
		LinkedList<UserAddedAsWatcherMongoEvent> userAddedAsWatcherMongoEvents = null;
		LinkedList<UserAddedAsSecondPlayerMongoEvent> userAddedAsSecondPlayerMongoEvents = null;
		LinkedList<UserPermissionsUpdatedMongoEvent> userPermissionsUpdatedMongoEvents = null;
		LinkedList<LoggedOutMongoEvent> loggedOutMongoEvents = null;
		LinkedList<LoggedOutUserLeftLobbyMongoEvent> userLeftLobbyMongoEvents = null;
		LinkedList<LoggedOutOpenByLeftBeforeGameStartedMongoEvent> loggedOutOpenByLeftBeforeGameStartedMongoEvents = null;
		LinkedList<GameRoomClosedMongoEvent> gameRoomClosedMongoEvents = null;
		LinkedList<LoggedOutOpenByLeftMongoEvent> loggedOutOpenByLeftMongoEvents = null;
		
		ArrayList<IMongoEvent> mongoEvents = new ArrayList<>(100000);
		LinkedList<BackgammonEvent> result = new LinkedList<>();
		
		if(fromDate == null){
			if(serviceName.equals(Services.Users.name())){
				newUserCreatedMongoEvents = new LinkedList<>(mongoTemplate.findAll(NewUserCreatedMongoEvent.class));
				newUserJoinedLobbyMongoEvents = new LinkedList<>(mongoTemplate.findAll(NewUserJoinedLobbyMongoEvent.class));
				loggedInMongoEvents = new LinkedList<>(mongoTemplate.findAll(LoggedInMongoEvent.class));
				existingUserJoinedLobbyMongoEvents = new LinkedList<>(mongoTemplate.findAll(ExistingUserJoinedLobbyMongoEvent.class));
				userPermissionsUpdatedMongoEvents = new LinkedList<>(mongoTemplate.findAll(UserPermissionsUpdatedMongoEvent.class));
				loggedOutMongoEvents = new LinkedList<>(mongoTemplate.findAll(LoggedOutMongoEvent.class));
				userLeftLobbyMongoEvents = new LinkedList<>(mongoTemplate.findAll(LoggedOutUserLeftLobbyMongoEvent.class));
			}
			else if(serviceName.equals(Services.Lobby.name())){
				newGameRoomOpenedMongoEvents = new LinkedList<>(mongoTemplate.findAll(NewGameRoomOpenedMongoEvent.class));
				userAddedAsWatcherMongoEvents = new LinkedList<>(mongoTemplate.findAll(UserAddedAsWatcherMongoEvent.class));
				userAddedAsSecondPlayerMongoEvents = new LinkedList<>(mongoTemplate.findAll(UserAddedAsSecondPlayerMongoEvent.class));
				loggedOutOpenByLeftBeforeGameStartedMongoEvents = new LinkedList<>(mongoTemplate.findAll(LoggedOutOpenByLeftBeforeGameStartedMongoEvent.class));
				gameRoomClosedMongoEvents = new LinkedList<>(mongoTemplate.findAll(GameRoomClosedMongoEvent.class));
				loggedOutOpenByLeftMongoEvents = new LinkedList<>(mongoTemplate.findAll(LoggedOutOpenByLeftMongoEvent.class));
			}					
		}
		else{
			Criteria criteria = Criteria.where("arrived").gte(fromDate);
			Query query = new Query(criteria);
			
			if(serviceName.equals(Services.Users.name())){
				newUserCreatedMongoEvents = new LinkedList<>(mongoTemplate.find(query, NewUserCreatedMongoEvent.class));
				newUserJoinedLobbyMongoEvents = new LinkedList<>(mongoTemplate.find(query, NewUserJoinedLobbyMongoEvent.class));
				loggedInMongoEvents = new LinkedList<>(mongoTemplate.find(query, LoggedInMongoEvent.class));
				existingUserJoinedLobbyMongoEvents = new LinkedList<>(mongoTemplate.find(query, ExistingUserJoinedLobbyMongoEvent.class));
				userPermissionsUpdatedMongoEvents = new LinkedList<>(mongoTemplate.find(query, UserPermissionsUpdatedMongoEvent.class));
				loggedOutMongoEvents = new LinkedList<>(mongoTemplate.find(query, LoggedOutMongoEvent.class));
				userLeftLobbyMongoEvents = new LinkedList<>(mongoTemplate.find(query, LoggedOutUserLeftLobbyMongoEvent.class));
			}
			else if(serviceName.equals(Services.Lobby.name())){
				newGameRoomOpenedMongoEvents = new LinkedList<>(mongoTemplate.find(query, NewGameRoomOpenedMongoEvent.class));
				userAddedAsWatcherMongoEvents = new LinkedList<>(mongoTemplate.find(query, UserAddedAsWatcherMongoEvent.class));
				userAddedAsSecondPlayerMongoEvents = new LinkedList<>(mongoTemplate.find(query, UserAddedAsSecondPlayerMongoEvent.class));
				loggedOutOpenByLeftBeforeGameStartedMongoEvents = new LinkedList<>(mongoTemplate.find(query, LoggedOutOpenByLeftBeforeGameStartedMongoEvent.class));
				gameRoomClosedMongoEvents = new LinkedList<>(mongoTemplate.find(query, GameRoomClosedMongoEvent.class));
				loggedOutOpenByLeftMongoEvents = new LinkedList<>(mongoTemplate.find(query, LoggedOutOpenByLeftMongoEvent.class));
			}				
		}
		
		if(serviceName.equals(Services.Users.name())){
			mongoEvents.addAll(newUserCreatedMongoEvents);
			mongoEvents.addAll(newUserJoinedLobbyMongoEvents);
			mongoEvents.addAll(loggedInMongoEvents);
			mongoEvents.addAll(existingUserJoinedLobbyMongoEvents);
			mongoEvents.addAll(userPermissionsUpdatedMongoEvents);
			mongoEvents.addAll(loggedOutMongoEvents);
			mongoEvents.addAll(userLeftLobbyMongoEvents);
		}
		else if(serviceName.equals(Services.Lobby.name())){
			mongoEvents.addAll(newGameRoomOpenedMongoEvents);
			mongoEvents.addAll(userAddedAsWatcherMongoEvents);
			mongoEvents.addAll(userAddedAsSecondPlayerMongoEvents);
			mongoEvents.addAll(loggedOutOpenByLeftBeforeGameStartedMongoEvents);
			mongoEvents.addAll(gameRoomClosedMongoEvents);
			mongoEvents.addAll(loggedOutOpenByLeftMongoEvents);
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
			NewUserCreatedMongoEvent newUserCreatedMongoEvent = (NewUserCreatedMongoEvent)mongoEvent; 
			NewUserCreatedEvent newUserCreatedEvent = new NewUserCreatedEvent(uuid, newUserCreatedMongoEvent.getServiceId(), newUserCreatedMongoEvent.getEventId(), newUserCreatedMongoEvent.getArrived(), "NewUserCreatedEvent",newUserCreatedMongoEvent.getBackgammonUser());
			return newUserCreatedEvent;
		}
		else if(clazz.equals("NewUserJoinedLobbyEvent")){
			NewUserJoinedLobbyMongoEvent newUserJoinedLobbyMongoEvent = (NewUserJoinedLobbyMongoEvent)mongoEvent;
			NewUserJoinedLobbyEvent newUserJoinedLobbyEvent = new NewUserJoinedLobbyEvent(uuid, newUserJoinedLobbyMongoEvent.getServiceId(), newUserJoinedLobbyMongoEvent.getEventId(), newUserJoinedLobbyMongoEvent.getArrived(), "NewUserJoinedLobbyEvent", newUserJoinedLobbyMongoEvent.getBackgammonUser());
			return newUserJoinedLobbyEvent;
		}
		else if(clazz.equals("LoggedInEvent")){
			LoggedInMongoEvent loggedInMongoEvent = (LoggedInMongoEvent)mongoEvent; 
			LoggedInEvent loggedInEvent = new LoggedInEvent(uuid, loggedInMongoEvent.getServiceId(), loggedInMongoEvent.getEventId(), loggedInMongoEvent.getArrived(), "LoggedInEvent",loggedInMongoEvent.getBackgammonUser());
			return loggedInEvent;
		}
		else if(clazz.equals("ExistingUserJoinedLobbyEvent")){
			ExistingUserJoinedLobbyMongoEvent existingUserJoinedLobbyMongoEvent = (ExistingUserJoinedLobbyMongoEvent)mongoEvent;
			ExistingUserJoinedLobbyEvent existingUserJoinedLobbyEvent = new ExistingUserJoinedLobbyEvent(uuid, existingUserJoinedLobbyMongoEvent.getServiceId(), existingUserJoinedLobbyMongoEvent.getEventId(), existingUserJoinedLobbyMongoEvent.getArrived(), "ExistingUserJoinedLobbyEvent", existingUserJoinedLobbyMongoEvent.getBackgammonUser());
			return existingUserJoinedLobbyEvent;
		}
		else if(clazz.equals("NewGameRoomOpenedEvent")){
			NewGameRoomOpenedMongoEvent newGameRoomOpenedMongoEvent = (NewGameRoomOpenedMongoEvent)mongoEvent;
			NewGameRoomOpenedEvent newGameRoomOpenedEvent = new NewGameRoomOpenedEvent(uuid, newGameRoomOpenedMongoEvent.getServiceId(), newGameRoomOpenedMongoEvent.getEventId(), newGameRoomOpenedMongoEvent.getArrived(), "NewGameRoomOpenedEvent", newGameRoomOpenedMongoEvent.getGameRoom());
			return newGameRoomOpenedEvent;
		}
		else if(clazz.equals("UserAddedAsWatcherEvent")){
			UserAddedAsWatcherMongoEvent userAddedAsWatcherMongoEvent = (UserAddedAsWatcherMongoEvent)mongoEvent;
			UserAddedAsWatcherEvent userAddedAsWatcherEvent = new UserAddedAsWatcherEvent(uuid, userAddedAsWatcherMongoEvent.getServiceId(), userAddedAsWatcherMongoEvent.getEventId(), userAddedAsWatcherMongoEvent.getArrived(), "UserAddedAsWatcherEvent", userAddedAsWatcherMongoEvent.getNewWatcher(), userAddedAsWatcherMongoEvent.getGameRoom());
			return userAddedAsWatcherEvent;
		}	
		else if(clazz.equals("UserAddedAsSecondPlayerEvent")){
			UserAddedAsSecondPlayerMongoEvent userAddedAsSecondPlayerMongoEvent = (UserAddedAsSecondPlayerMongoEvent)mongoEvent;
			UserAddedAsSecondPlayerEvent userAddedAsSecondPlayerEvent = new UserAddedAsSecondPlayerEvent(uuid, userAddedAsSecondPlayerMongoEvent.getServiceId(), userAddedAsSecondPlayerMongoEvent.getEventId(), userAddedAsSecondPlayerMongoEvent.getArrived(), "UserAddedAsSecondPlayerEvent", userAddedAsSecondPlayerMongoEvent.getSecondPlayer(), userAddedAsSecondPlayerMongoEvent.getGameRoom());
			return userAddedAsSecondPlayerEvent;
		}
		else if(clazz.equals("UserPermissionsUpdatedEvent")){
			UserPermissionsUpdatedMongoEvent userPermissionsUpdatedMongoEvent = (UserPermissionsUpdatedMongoEvent)mongoEvent;
			UserPermissionsUpdatedEvent userPermissionsUpdatedEvent = new UserPermissionsUpdatedEvent(uuid, userPermissionsUpdatedMongoEvent.getServiceId(), userPermissionsUpdatedMongoEvent.getEventId(), userPermissionsUpdatedMongoEvent.getArrived(), "UserPermissionsUpdatedEvent", userPermissionsUpdatedMongoEvent.getBackgammonUser());
			return userPermissionsUpdatedEvent;
		}
		else if(clazz.equals("LoggedOutEvent")){
			LoggedOutMongoEvent loggedOutMongoEvent = (LoggedOutMongoEvent)mongoEvent;
			LoggedOutEvent loggedOutEvent = new LoggedOutEvent(uuid, loggedOutMongoEvent.getServiceId(), loggedOutMongoEvent.getEventId(), loggedOutMongoEvent.getArrived(), "LoggedOutEvent", loggedOutMongoEvent.getBackgammonUser());
			return loggedOutEvent;
		}
		else if(clazz.equals("LoggedOutUserLeftLobbyEvent")){
			LoggedOutUserLeftLobbyMongoEvent userLeftLobbyMongoEvent = (LoggedOutUserLeftLobbyMongoEvent)mongoEvent;
			LoggedOutUserLeftLobbyEvent userLeftLobbyEvent = new LoggedOutUserLeftLobbyEvent(uuid, userLeftLobbyMongoEvent.getServiceId(), userLeftLobbyMongoEvent.getEventId(), userLeftLobbyMongoEvent.getArrived(), "LoggedOutUserLeftLobbyEvent", userLeftLobbyMongoEvent.getBackgammonUser());
			return userLeftLobbyEvent;
		}
		else if(clazz.equals("LoggedOutOpenByLeftBeforeGameStartedEvent")){
			LoggedOutOpenByLeftBeforeGameStartedMongoEvent loggedOutOpenByLeftBeforeGameStartedMongoEvent = (LoggedOutOpenByLeftBeforeGameStartedMongoEvent)mongoEvent;
			LoggedOutOpenByLeftBeforeGameStartedEvent loggedOutOpenByLeftBeforeGameStartedEvent = new LoggedOutOpenByLeftBeforeGameStartedEvent(uuid, loggedOutOpenByLeftBeforeGameStartedMongoEvent.getServiceId(), loggedOutOpenByLeftBeforeGameStartedMongoEvent.getEventId(), loggedOutOpenByLeftBeforeGameStartedMongoEvent.getArrived(), "LoggedOutOpenByLeftBeforeGameStartedEvent", loggedOutOpenByLeftBeforeGameStartedMongoEvent.getLoggedOutUserName(), loggedOutOpenByLeftBeforeGameStartedMongoEvent.getGameRoom());
			return loggedOutOpenByLeftBeforeGameStartedEvent;
		}
		else if(clazz.equals("GameRoomClosedEvent")){
			GameRoomClosedMongoEvent gameRoomClosedMongoEvent = (GameRoomClosedMongoEvent)mongoEvent;
			GameRoomClosedEvent gameRoomClosedEvent = new GameRoomClosedEvent(uuid, gameRoomClosedMongoEvent.getServiceId(), gameRoomClosedMongoEvent.getEventId(), gameRoomClosedMongoEvent.getArrived(), "GameRoomClosedEvent", gameRoomClosedMongoEvent.getClosedByUserName(), gameRoomClosedMongoEvent.getGameRoom());
			return gameRoomClosedEvent;
		}
		else if(clazz.equals("LoggedOutOpenByLeftEvent")){
			LoggedOutOpenByLeftMongoEvent loggedOutOpenByLeftMongoEvent = (LoggedOutOpenByLeftMongoEvent)mongoEvent;
			LoggedOutOpenByLeftEvent loggedOutOpenByLeftEvent = new LoggedOutOpenByLeftEvent(uuid, loggedOutOpenByLeftMongoEvent.getServiceId(), loggedOutOpenByLeftMongoEvent.getEventId(), loggedOutOpenByLeftMongoEvent.getArrived(), "LoggedOutOpenByLeftEvent", loggedOutOpenByLeftMongoEvent.getOpenBy(), loggedOutOpenByLeftMongoEvent.getGameRoom());
			return loggedOutOpenByLeftEvent;
		}
		else{
			throw new RuntimeException("Failed to convert mongo event....");
		}
	}	
}

























