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
import org.moshe.arad.kafka.events.NewGameRoomOpenedEvent;
import org.moshe.arad.kafka.events.NewUserCreatedEvent;
import org.moshe.arad.kafka.events.NewUserJoinedLobbyEvent;
import org.moshe.arad.kafka.events.StartReadEventsFromMongoEvent;
import org.moshe.arad.kafka.events.UserAddedAsSecondPlayerEvent;
import org.moshe.arad.kafka.events.UserAddedAsWatcherEvent;
import org.moshe.arad.kafka.events.UserPermissionsUpdatedEvent;
import org.moshe.arad.kafka.events.WatcherRemovedEvent;
import org.moshe.arad.mongo.events.AddSecondPlayerMongoEvent;
import org.moshe.arad.mongo.events.GameRoomClosedMongoEvent;
import org.moshe.arad.mongo.events.GameRoomMongoEvent;
import org.moshe.arad.mongo.events.GameRoomWatcherMongoEvent;
import org.moshe.arad.mongo.events.IMongoEvent;
import org.moshe.arad.mongo.events.UserMongoEvent;
import org.moshe.arad.mongo.events.WatcherRemovedMongoEvent;
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
			GameRoomMongoEvent gameRoomMongoEvent = GameRoomMongoEvent.convertIntoMongoEvent(newGameRoomOpenedEvent);
			
			mongoTemplate.insert(gameRoomMongoEvent, "gameRoomsEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save existingUserJoinedLobbyEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}		
	}
	
	public void addGameRoomClosedEvent(GameRoomClosedEvent gameRoomClosedEvent) {
		try{
			GameRoomClosedMongoEvent gameRoomClosedMongoEvent = GameRoomClosedMongoEvent.convertIntoMongoEvent(gameRoomClosedEvent);
			
			mongoTemplate.insert(gameRoomClosedMongoEvent, "gameRoomsEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save existingUserJoinedLobbyEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}			
	}
	
	public void addUserAddedAsWatcherEvent(UserAddedAsWatcherEvent userAddedAsWatcherEvent) {
		try{
			GameRoomWatcherMongoEvent gameRoomMongoEvent = (GameRoomWatcherMongoEvent) GameRoomWatcherMongoEvent.convertIntoMongoEvent(userAddedAsWatcherEvent);
			
			mongoTemplate.insert(gameRoomMongoEvent, "gameRoomsEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save existingUserJoinedLobbyEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}		
	}
	
	public void addWatcherRemovedEvent(WatcherRemovedEvent watcherRemovedEvent) {
		try{
			WatcherRemovedMongoEvent gameRoomMongoEvent = (WatcherRemovedMongoEvent) WatcherRemovedMongoEvent.convertIntoMongoEvent(watcherRemovedEvent);
			
			mongoTemplate.insert(gameRoomMongoEvent, "gameRoomsEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save existingUserJoinedLobbyEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addUserAddedAsSecondPlayerEvent(UserAddedAsSecondPlayerEvent userAddedAsSecondPlayerEvent) {
		try{
			AddSecondPlayerMongoEvent addSecondPlayerMongoEvent = (AddSecondPlayerMongoEvent) AddSecondPlayerMongoEvent.convertIntoMongoEvent(userAddedAsSecondPlayerEvent);
			
			mongoTemplate.insert(addSecondPlayerMongoEvent, "gameRoomsEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save existingUserJoinedLobbyEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}		
	}
	
	public void addUserPermissionsUpdatedEvent(UserPermissionsUpdatedEvent userPermissionsUpdatedEvent) {
		try{
			UserMongoEvent userPermissionsUpdatedMongoEvent = UserMongoEvent.convertIntoMongoEvent(userPermissionsUpdatedEvent);
			
			mongoTemplate.insert(userPermissionsUpdatedMongoEvent, "userEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save existingUserJoinedLobbyEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public synchronized LinkedList<BackgammonEvent> getEventsOccuredFrom(UUID uuid, Date fromDate, String serviceName){
		LinkedList<UserMongoEvent> userMongoEvents = null;		
		LinkedList<GameRoomMongoEvent> gameRoomMongoEvent = null;
		
		ArrayList<IMongoEvent> mongoEvents = new ArrayList<>(100000);
		LinkedList<BackgammonEvent> result = new LinkedList<>();
		
		if(fromDate == null){
			if(serviceName.equals(Services.Users.name())){
				userMongoEvents = new LinkedList<>(mongoTemplate.findAll(UserMongoEvent.class));
			}
			else if(serviceName.equals(Services.Lobby.name())){
				gameRoomMongoEvent = new LinkedList<>(mongoTemplate.findAll(GameRoomMongoEvent.class));
			}			
		}
		else{
			Criteria criteria = Criteria.where("arrived").gte(fromDate);
			Query query = new Query(criteria);
			
			if(serviceName.equals(Services.Users.name())){
				userMongoEvents = new LinkedList<>(mongoTemplate.find(query, UserMongoEvent.class));								
			}
			else if(serviceName.equals(Services.Lobby.name())){
				gameRoomMongoEvent = new LinkedList<>(mongoTemplate.find(query, GameRoomMongoEvent.class));
			}			
		}
		
		if(serviceName.equals(Services.Users.name())){
			mongoEvents.addAll(userMongoEvents);
		}
		else if(serviceName.equals(Services.Lobby.name())){
			mongoEvents.addAll(gameRoomMongoEvent);
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
			GameRoomMongoEvent newGameRoomOpenedMongoEvent = (GameRoomMongoEvent)mongoEvent;
			NewGameRoomOpenedEvent newGameRoomOpenedEvent = new NewGameRoomOpenedEvent(uuid, newGameRoomOpenedMongoEvent.getServiceId(), newGameRoomOpenedMongoEvent.getEventId(), newGameRoomOpenedMongoEvent.getArrived(), "NewGameRoomOpenedEvent", newGameRoomOpenedMongoEvent.getGameRoom());
			return newGameRoomOpenedEvent;
		}
//		else if(clazz.equals("GameRoomClosedEvent")){
//			GameRoomMongoEvent newGameRoomOpenedMongoEvent = (GameRoomMongoEvent)mongoEvent;
//			NewGameRoomOpenedEvent newGameRoomOpenedEvent = new NewGameRoomOpenedEvent(uuid, newGameRoomOpenedMongoEvent.getServiceId(), newGameRoomOpenedMongoEvent.getEventId(), newGameRoomOpenedMongoEvent.getArrived(), "GameRoomClosedEvent", newGameRoomOpenedMongoEvent.getGameRoom());
//			return newGameRoomOpenedEvent;
//		}
		else if(clazz.equals("UserAddedAsWatcherEvent")){
			GameRoomWatcherMongoEvent gameRoomWatcherMongoEvent = (GameRoomWatcherMongoEvent)mongoEvent;
			UserAddedAsWatcherEvent userAddedAsWatcherEvent = new UserAddedAsWatcherEvent(uuid, gameRoomWatcherMongoEvent.getServiceId(), gameRoomWatcherMongoEvent.getEventId(), gameRoomWatcherMongoEvent.getArrived(), "UserAddedAsWatcherEvent", gameRoomWatcherMongoEvent.getNewWatcher(), gameRoomWatcherMongoEvent.getGameRoom());
			return userAddedAsWatcherEvent;
		}	
		else if(clazz.equals("GameRoomClosedEvent")){
			GameRoomClosedMongoEvent gameRoomClosedMongoEvent = (GameRoomClosedMongoEvent)mongoEvent;
			GameRoomClosedEvent gameRoomClosedEvent = new GameRoomClosedEvent(uuid, gameRoomClosedMongoEvent.getServiceId(), gameRoomClosedMongoEvent.getEventId(), gameRoomClosedMongoEvent.getArrived(), "GameRoomClosedEvent", gameRoomClosedMongoEvent.getClosedByUserName(), gameRoomClosedMongoEvent.getGameRoom());
			return gameRoomClosedEvent;
		}
		else if(clazz.equals("WatcherRemovedEvent")){
			WatcherRemovedMongoEvent watcherRemovedMongoEvent = (WatcherRemovedMongoEvent)mongoEvent;
			WatcherRemovedEvent watcherRemovedEvent = new WatcherRemovedEvent(uuid, watcherRemovedMongoEvent.getServiceId(), watcherRemovedMongoEvent.getEventId(), watcherRemovedMongoEvent.getArrived(), "WatcherRemovedEvent", watcherRemovedMongoEvent.getRemovedWatcher(), watcherRemovedMongoEvent.getGameRoom());
			return watcherRemovedEvent;
		}
		else if(clazz.equals("UserAddedAsSecondPlayerEvent")){
			AddSecondPlayerMongoEvent addSecondPlayerMongoEvent = (AddSecondPlayerMongoEvent)mongoEvent;
			UserAddedAsSecondPlayerEvent userAddedAsSecondPlayerEvent = new UserAddedAsSecondPlayerEvent(uuid, addSecondPlayerMongoEvent.getServiceId(), addSecondPlayerMongoEvent.getEventId(), addSecondPlayerMongoEvent.getArrived(), "UserAddedAsSecondPlayerEvent", addSecondPlayerMongoEvent.getSecondPlayer(), addSecondPlayerMongoEvent.getGameRoom());
			return userAddedAsSecondPlayerEvent;
		}
		else if(clazz.equals("UserPermissionsUpdatedEvent")){
			UserMongoEvent userPermissionsUpdatedMongoEvent = (UserMongoEvent)mongoEvent;
			UserPermissionsUpdatedEvent userPermissionsUpdatedEvent = new UserPermissionsUpdatedEvent(uuid, userPermissionsUpdatedMongoEvent.getServiceId(), userPermissionsUpdatedMongoEvent.getEventId(), userPermissionsUpdatedMongoEvent.getArrived(), "UserPermissionsUpdatedEvent", userPermissionsUpdatedMongoEvent.getBackgammonUser());
			return userPermissionsUpdatedEvent;
		}
		else{
			throw new RuntimeException("Failed to convert mongo event....");
		}
	}

	
}

























