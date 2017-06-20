package org.moshe.arad.mongo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.UUID;

import org.moshe.arad.kafka.Services;
import org.moshe.arad.kafka.events.BackgammonEvent;
import org.moshe.arad.kafka.events.BlackPawnCameBackEvent;
import org.moshe.arad.kafka.events.DiceRolledEvent;
import org.moshe.arad.kafka.events.EndReadEventsFromMongoEvent;
import org.moshe.arad.kafka.events.ExistingUserJoinedLobbyEvent;
import org.moshe.arad.kafka.events.GameRoomClosedEvent;
import org.moshe.arad.kafka.events.GameStartedEvent;
import org.moshe.arad.kafka.events.InitDiceCompletedEvent;
import org.moshe.arad.kafka.events.InitGameRoomCompletedEvent;
import org.moshe.arad.kafka.events.LoggedInEvent;
import org.moshe.arad.kafka.events.LoggedOutEvent;
import org.moshe.arad.kafka.events.LoggedOutOpenByLeftBeforeGameStartedEvent;
import org.moshe.arad.kafka.events.LoggedOutOpenByLeftEvent;
import org.moshe.arad.kafka.events.LoggedOutOpenByLeftFirstEvent;
import org.moshe.arad.kafka.events.LoggedOutOpenByLeftLastEvent;
import org.moshe.arad.kafka.events.LoggedOutSecondLeftEvent;
import org.moshe.arad.kafka.events.LoggedOutSecondLeftFirstEvent;
import org.moshe.arad.kafka.events.LoggedOutSecondLeftLastEvent;
import org.moshe.arad.kafka.events.NewGameRoomOpenedEvent;
import org.moshe.arad.kafka.events.NewUserCreatedEvent;
import org.moshe.arad.kafka.events.NewUserJoinedLobbyEvent;
import org.moshe.arad.kafka.events.OpenByLeftBeforeGameStartedEvent;
import org.moshe.arad.kafka.events.OpenByLeftEvent;
import org.moshe.arad.kafka.events.OpenByLeftFirstEvent;
import org.moshe.arad.kafka.events.OpenByLeftLastEvent;
import org.moshe.arad.kafka.events.RollDiceGameRoomFoundEvent;
import org.moshe.arad.kafka.events.SecondLeftEvent;
import org.moshe.arad.kafka.events.SecondLeftFirstEvent;
import org.moshe.arad.kafka.events.SecondLeftLastEvent;
import org.moshe.arad.kafka.events.StartReadEventsFromMongoEvent;
import org.moshe.arad.kafka.events.UserAddedAsSecondPlayerEvent;
import org.moshe.arad.kafka.events.UserAddedAsWatcherEvent;
import org.moshe.arad.kafka.events.UserMadeInvalidMoveEvent;
import org.moshe.arad.kafka.events.LoggedOutUserLeftLobbyEvent;
import org.moshe.arad.kafka.events.LoggedOutWatcherLeftEvent;
import org.moshe.arad.kafka.events.LoggedOutWatcherLeftLastEvent;
import org.moshe.arad.kafka.events.UserPermissionsUpdatedEvent;
import org.moshe.arad.kafka.events.WatcherLeftEvent;
import org.moshe.arad.kafka.events.WatcherLeftLastEvent;
import org.moshe.arad.kafka.events.WhitePawnCameBackEvent;
import org.moshe.arad.kafka.events.WhitePawnTakenOutEvent;
import org.moshe.arad.mongo.events.BlackPawnCameBackMongoEvent;
import org.moshe.arad.mongo.events.DiceRolledMongoEvent;
import org.moshe.arad.mongo.events.ExistingUserJoinedLobbyMongoEvent;
import org.moshe.arad.mongo.events.GameRoomClosedMongoEvent;
import org.moshe.arad.mongo.events.GameStartedMongoEvent;
import org.moshe.arad.mongo.events.IMongoEvent;
import org.moshe.arad.mongo.events.InitDiceCompletedMongoEvent;
import org.moshe.arad.mongo.events.InitGameRoomCompletedMongoEvent;
import org.moshe.arad.mongo.events.LoggedInMongoEvent;
import org.moshe.arad.mongo.events.LoggedOutMongoEvent;
import org.moshe.arad.mongo.events.LoggedOutOpenByLeftBeforeGameStartedMongoEvent;
import org.moshe.arad.mongo.events.LoggedOutOpenByLeftFirstMongoEvent;
import org.moshe.arad.mongo.events.LoggedOutOpenByLeftLastMongoEvent;
import org.moshe.arad.mongo.events.LoggedOutOpenByLeftMongoEvent;
import org.moshe.arad.mongo.events.LoggedOutSecondLeftFirstMongoEvent;
import org.moshe.arad.mongo.events.LoggedOutSecondLeftLastMongoEvent;
import org.moshe.arad.mongo.events.LoggedOutSecondLeftMongoEvent;
import org.moshe.arad.mongo.events.NewGameRoomOpenedMongoEvent;
import org.moshe.arad.mongo.events.NewUserCreatedMongoEvent;
import org.moshe.arad.mongo.events.NewUserJoinedLobbyMongoEvent;
import org.moshe.arad.mongo.events.OpenByLeftBeforeGameStartedMongoEvent;
import org.moshe.arad.mongo.events.OpenByLeftFirstMongoEvent;
import org.moshe.arad.mongo.events.OpenByLeftLastMongoEvent;
import org.moshe.arad.mongo.events.OpenByLeftMongoEvent;
import org.moshe.arad.mongo.events.RollDiceGameRoomFoundMongoEvent;
import org.moshe.arad.mongo.events.SecondLeftFirstMongoEvent;
import org.moshe.arad.mongo.events.SecondLeftLastMongoEvent;
import org.moshe.arad.mongo.events.SecondLeftMongoEvent;
import org.moshe.arad.mongo.events.UserAddedAsSecondPlayerMongoEvent;
import org.moshe.arad.mongo.events.UserAddedAsWatcherMongoEvent;
import org.moshe.arad.mongo.events.UserMadeInvalidMoveMongoEvent;
import org.moshe.arad.mongo.events.LoggedOutUserLeftLobbyMongoEvent;
import org.moshe.arad.mongo.events.LoggedOutWatcherLeftLastMongoEvent;
import org.moshe.arad.mongo.events.LoggedOutWatcherLeftMongoEvent;
import org.moshe.arad.mongo.events.UserPermissionsUpdatedMongoEvent;
import org.moshe.arad.mongo.events.WatcherLeftLastMongoEvent;
import org.moshe.arad.mongo.events.WatcherLeftMongoEvent;
import org.moshe.arad.mongo.events.WhitePawnCameBackMongoEvent;
import org.moshe.arad.mongo.events.WhitePawnTakenOutMongoEvent;
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
	
	public void addLoggedOutWatcherLeftLast(LoggedOutWatcherLeftLastEvent loggedOutWatcherLeftLastEvent) {
		try{
			LoggedOutWatcherLeftLastMongoEvent mongoEvent = LoggedOutWatcherLeftLastMongoEvent.convertIntoMongoEvent(loggedOutWatcherLeftLastEvent);
			
			mongoTemplate.insert(mongoEvent, "LoggedOutWatcherLeftLastEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save LoggedOutWatcherLeftLastMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addLoggedOutWatcherLeft(LoggedOutWatcherLeftEvent loggedOutWatcherLeftEvent) {
		try{
			LoggedOutWatcherLeftMongoEvent mongoEvent = LoggedOutWatcherLeftMongoEvent.convertIntoMongoEvent(loggedOutWatcherLeftEvent);
			
			mongoTemplate.insert(mongoEvent, "LoggedOutWatcherLeftEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save LoggedOutWatcherLeftMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addLoggedOutOpenByLeftFirstEvent(LoggedOutOpenByLeftFirstEvent loggedOutOpenByLeftFirstEvent) {
		try{
			LoggedOutOpenByLeftFirstMongoEvent mongoEvent = LoggedOutOpenByLeftFirstMongoEvent.convertIntoMongoEvent(loggedOutOpenByLeftFirstEvent);
			
			mongoTemplate.insert(mongoEvent, "LoggedOutOpenByLeftFirstEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save LoggedOutOpenByLeftFirstMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addLoggedOutSecondLeftFirstEvent(LoggedOutSecondLeftFirstEvent loggedOutSecondLeftFirstEvent) {
		try{
			LoggedOutSecondLeftFirstMongoEvent mongoEvent = LoggedOutSecondLeftFirstMongoEvent.convertIntoMongoEvent(loggedOutSecondLeftFirstEvent);
			
			mongoTemplate.insert(mongoEvent, "LoggedOutSecondLeftFirstEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save LoggedOutSecondLeftFirstMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addLoggedOutSecondLeftEvent(LoggedOutSecondLeftEvent loggedOutSecondLeftEvent) {
		try{
			LoggedOutSecondLeftMongoEvent mongoEvent = LoggedOutSecondLeftMongoEvent.convertIntoMongoEvent(loggedOutSecondLeftEvent);
			
			mongoTemplate.insert(mongoEvent, "LoggedOutSecondLeftEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save LoggedOutSecondLeftMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void addLoggedOutOpenByLeftLastEvent(LoggedOutOpenByLeftLastEvent loggedOutOpenByLeftLastEvent) {
		try{
			LoggedOutOpenByLeftLastMongoEvent mongoEvent = LoggedOutOpenByLeftLastMongoEvent.convertIntoMongoEvent(loggedOutOpenByLeftLastEvent);
			
			mongoTemplate.insert(mongoEvent, "LoggedOutOpenByLeftLastEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save LoggedOutOpenByLeftLastMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addLoggedOutSecondLeftLastEvent(LoggedOutSecondLeftLastEvent loggedOutSecondLeftLastEvent) {
		try{
			LoggedOutSecondLeftLastMongoEvent mongoEvent = LoggedOutSecondLeftLastMongoEvent.convertIntoMongoEvent(loggedOutSecondLeftLastEvent);
			
			mongoTemplate.insert(mongoEvent, "LoggedOutSecondLeftLastEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save LoggedOutSecondLeftLastMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addOpenByLeftBeforeGameStartedEvent(OpenByLeftBeforeGameStartedEvent openByLeftBeforeGameStartedEvent) {
		try{
			OpenByLeftBeforeGameStartedMongoEvent mongoEvent = OpenByLeftBeforeGameStartedMongoEvent.convertIntoMongoEvent(openByLeftBeforeGameStartedEvent);
			
			mongoTemplate.insert(mongoEvent, "OpenByLeftBeforeGameStartedEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save OpenByLeftBeforeGameStartedMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addOpenByLeftEvent(OpenByLeftEvent openByLeftEvent) {
		try{
			OpenByLeftMongoEvent mongoEvent = OpenByLeftMongoEvent.convertIntoMongoEvent(openByLeftEvent);
			
			mongoTemplate.insert(mongoEvent, "OpenByLeftEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save OpenByLeftBeforeGameStartedMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addWatcherLeftLastEvent(WatcherLeftLastEvent watcherLeftLastEvent) {
		try{
			WatcherLeftLastMongoEvent mongoEvent = WatcherLeftLastMongoEvent.convertIntoMongoEvent(watcherLeftLastEvent);
			
			mongoTemplate.insert(mongoEvent, "WatcherLeftLastEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save WatcherLeftLastMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addWatcherLeftEvent(WatcherLeftEvent watcherLeftEvent) {
		try{
			WatcherLeftMongoEvent mongoEvent = WatcherLeftMongoEvent.convertIntoMongoEvent(watcherLeftEvent);
			
			mongoTemplate.insert(mongoEvent, "WatcherLeftEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save WatcherLeftMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addOpenByLeftFirstEvent(OpenByLeftFirstEvent openByLeftFirstEvent) {
		try{
			OpenByLeftFirstMongoEvent mongoEvent = OpenByLeftFirstMongoEvent.convertIntoMongoEvent(openByLeftFirstEvent);
			
			mongoTemplate.insert(mongoEvent, "OpenByLeftFirstEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save OpenByLeftFirstEvents into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addSecondLeftFirstEvent(SecondLeftFirstEvent secondLeftFirstEvent) {
		try{
			SecondLeftFirstMongoEvent mongoEvent = SecondLeftFirstMongoEvent.convertIntoMongoEvent(secondLeftFirstEvent);
			
			mongoTemplate.insert(mongoEvent, "SecondLeftFirstEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save SecondLeftFirstMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}	
	
	public void addSecondLeftEvent(SecondLeftEvent secondLeftEvent) {
		try{
			SecondLeftMongoEvent mongoEvent = SecondLeftMongoEvent.convertIntoMongoEvent(secondLeftEvent);
			
			mongoTemplate.insert(mongoEvent, "SecondLeftEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save SecondLeftMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addOpenByLeftLastEvent(OpenByLeftLastEvent openByLeftLastEvent) {
		try{
			OpenByLeftLastMongoEvent mongoEvent = OpenByLeftLastMongoEvent.convertIntoMongoEvent(openByLeftLastEvent);
			
			mongoTemplate.insert(mongoEvent, "OpenByLeftLastEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save OpenByLeftLastMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addSecondLeftLastEvent(SecondLeftLastEvent secondLeftLastEvent) {
		try{
			SecondLeftLastMongoEvent mongoEvent = SecondLeftLastMongoEvent.convertIntoMongoEvent(secondLeftLastEvent);
			
			mongoTemplate.insert(mongoEvent, "SecondLeftLastEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save SecondLeftLastMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addGameStartedEvent(GameStartedEvent gameStartedEvent) {
		try{
			GameStartedMongoEvent mongoEvent = GameStartedMongoEvent.convertIntoMongoEvent(gameStartedEvent);
			
			mongoTemplate.insert(mongoEvent, "GameStartedEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save GameStartedMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}	
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addInitGameRoomCompletedEvent(InitGameRoomCompletedEvent initGameRoomCompletedEvent) {
		try{
			InitGameRoomCompletedMongoEvent mongoEvent = InitGameRoomCompletedMongoEvent.convertIntoMongoEvent(initGameRoomCompletedEvent);
			
			mongoTemplate.insert(mongoEvent, "InitGameRoomCompletedEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save InitGameRoomCompletedMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addInitDiceCompletedEvent(InitDiceCompletedEvent initDiceCompletedEvent) {
		try{
			InitDiceCompletedMongoEvent mongoEvent = InitDiceCompletedMongoEvent.convertIntoMongoEvent(initDiceCompletedEvent);
			
			mongoTemplate.insert(mongoEvent, "InitDiceCompletedEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save InitGameRoomCompletedMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addRollDiceGameRoomFoundEvent(RollDiceGameRoomFoundEvent rollDiceGameRoomFoundEvent) {
		try{
			RollDiceGameRoomFoundMongoEvent mongoEvent = RollDiceGameRoomFoundMongoEvent.convertIntoMongoEvent(rollDiceGameRoomFoundEvent);
			
			mongoTemplate.insert(mongoEvent, "RollDiceGameRoomFoundEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save RollDiceGameRoomFoundEvents into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addDiceRolledEvent(DiceRolledEvent diceRolledEvent) {
		try{
			DiceRolledMongoEvent mongoEvent = DiceRolledMongoEvent.convertIntoMongoEvent(diceRolledEvent);
			
			mongoTemplate.insert(mongoEvent, "DiceRolledEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save DiceRolledEvents into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}	
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addUserMadeInvalidMoveEvent(UserMadeInvalidMoveEvent userMadeInvalidMoveEvent) {
		try{
			UserMadeInvalidMoveMongoEvent mongoEvent = UserMadeInvalidMoveMongoEvent.convertIntoMongoEvent(userMadeInvalidMoveEvent);
			
			mongoTemplate.insert(mongoEvent, "UserMadeInvalidMoveEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save UserMadeInvalidMoveMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}	
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addWhitePawnCameBackEvent(WhitePawnCameBackEvent whitePawnCameBackEvent) {
		try{
			WhitePawnCameBackMongoEvent mongoEvent = WhitePawnCameBackMongoEvent.convertIntoMongoEvent(whitePawnCameBackEvent);
			
			mongoTemplate.insert(mongoEvent, "WhitePawnCameBackEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save WhitePawnCameBackMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addBlackPawnCameBackEvent(BlackPawnCameBackEvent blackPawnCameBackEvent) {
		try{
			BlackPawnCameBackMongoEvent mongoEvent = BlackPawnCameBackMongoEvent.convertIntoMongoEvent(blackPawnCameBackEvent);
			
			mongoTemplate.insert(mongoEvent, "BlackPawnCameBackEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save BlackPawnCameBackMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void addWhitePawnTakenOutEvent(WhitePawnTakenOutEvent whitePawnTakenOutEvent) {
		try{
			WhitePawnTakenOutMongoEvent mongoEvent = WhitePawnTakenOutMongoEvent.convertIntoMongoEvent(whitePawnTakenOutEvent);
			
			mongoTemplate.insert(mongoEvent, "WhitePawnTakenOutEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save WhitePawnTakenOutMongoEvent into mongo events store");
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
		LinkedList<LoggedOutWatcherLeftLastMongoEvent> loggedOutWatcherLeftLastMongoEvents = null;
		LinkedList<LoggedOutWatcherLeftMongoEvent> loggedOutWatcherLeftMongoEvents = null;
		LinkedList<LoggedOutOpenByLeftFirstMongoEvent> loggedOutOpenByLeftFirstMongoEvents = null;
		LinkedList<LoggedOutSecondLeftFirstMongoEvent> loggedOutSecondLeftFirstMongoEvents = null;
		LinkedList<LoggedOutSecondLeftMongoEvent> loggedOutSecondLeftMongoEvents = null;
		LinkedList<LoggedOutOpenByLeftLastMongoEvent> loggedOutOpenByLeftLastMongoEvents = null;
		LinkedList<LoggedOutSecondLeftLastMongoEvent> loggedOutSecondLeftLastMongoEvents = null;
		LinkedList<OpenByLeftBeforeGameStartedMongoEvent> openByLeftBeforeGameStartedMongoEvents = null;
		LinkedList<OpenByLeftMongoEvent> openByLeftMongoEvents = null;
		LinkedList<WatcherLeftLastMongoEvent> watcherLeftLastMongoEvents = null;
		LinkedList<WatcherLeftMongoEvent> watcherLeftMongoEvents = null;
		LinkedList<OpenByLeftFirstMongoEvent> openByLeftFirstMongoEvents = null;
		LinkedList<SecondLeftFirstMongoEvent> secondLeftFirstMongoEvents = null;
		LinkedList<SecondLeftMongoEvent> secondLeftMongoEvents = null;
		LinkedList<OpenByLeftLastMongoEvent> openByLeftLastMongoEvents = null;
		LinkedList<SecondLeftLastMongoEvent> secondLeftLastMongoEvents = null;
		
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
				loggedOutWatcherLeftLastMongoEvents = new LinkedList<>(mongoTemplate.findAll(LoggedOutWatcherLeftLastMongoEvent.class));
				loggedOutWatcherLeftMongoEvents = new LinkedList<>(mongoTemplate.findAll(LoggedOutWatcherLeftMongoEvent.class));
				loggedOutOpenByLeftFirstMongoEvents = new LinkedList<>(mongoTemplate.findAll(LoggedOutOpenByLeftFirstMongoEvent.class));
				loggedOutSecondLeftFirstMongoEvents = new LinkedList<>(mongoTemplate.findAll(LoggedOutSecondLeftFirstMongoEvent.class));
				loggedOutSecondLeftMongoEvents = new LinkedList<>(mongoTemplate.findAll(LoggedOutSecondLeftMongoEvent.class));
				loggedOutOpenByLeftLastMongoEvents = new LinkedList<>(mongoTemplate.findAll(LoggedOutOpenByLeftLastMongoEvent.class));
				loggedOutSecondLeftLastMongoEvents = new LinkedList<>(mongoTemplate.findAll(LoggedOutSecondLeftLastMongoEvent.class));
				openByLeftBeforeGameStartedMongoEvents = new LinkedList<>(mongoTemplate.findAll(OpenByLeftBeforeGameStartedMongoEvent.class));
				openByLeftMongoEvents = new LinkedList<>(mongoTemplate.findAll(OpenByLeftMongoEvent.class));
				watcherLeftLastMongoEvents = new LinkedList<>(mongoTemplate.findAll(WatcherLeftLastMongoEvent.class));
				watcherLeftMongoEvents = new LinkedList<>(mongoTemplate.findAll(WatcherLeftMongoEvent.class));
				openByLeftFirstMongoEvents = new LinkedList<>(mongoTemplate.findAll(OpenByLeftFirstMongoEvent.class));
				secondLeftFirstMongoEvents = new LinkedList<>(mongoTemplate.findAll(SecondLeftFirstMongoEvent.class));
				secondLeftMongoEvents = new LinkedList<>(mongoTemplate.findAll(SecondLeftMongoEvent.class));
				openByLeftLastMongoEvents = new LinkedList<>(mongoTemplate.findAll(OpenByLeftLastMongoEvent.class));
				secondLeftLastMongoEvents = new LinkedList<>(mongoTemplate.findAll(SecondLeftLastMongoEvent.class));
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
				loggedOutWatcherLeftLastMongoEvents = new LinkedList<>(mongoTemplate.find(query, LoggedOutWatcherLeftLastMongoEvent.class));
				loggedOutWatcherLeftMongoEvents = new LinkedList<>(mongoTemplate.find(query, LoggedOutWatcherLeftMongoEvent.class));
				loggedOutOpenByLeftFirstMongoEvents = new LinkedList<>(mongoTemplate.find(query, LoggedOutOpenByLeftFirstMongoEvent.class));
				loggedOutSecondLeftFirstMongoEvents = new LinkedList<>(mongoTemplate.find(query, LoggedOutSecondLeftFirstMongoEvent.class));
				loggedOutSecondLeftMongoEvents = new LinkedList<>(mongoTemplate.find(query, LoggedOutSecondLeftMongoEvent.class));
				loggedOutOpenByLeftLastMongoEvents = new LinkedList<>(mongoTemplate.find(query, LoggedOutOpenByLeftLastMongoEvent.class));
				loggedOutSecondLeftLastMongoEvents = new LinkedList<>(mongoTemplate.find(query, LoggedOutSecondLeftLastMongoEvent.class));
				openByLeftBeforeGameStartedMongoEvents = new LinkedList<>(mongoTemplate.find(query, OpenByLeftBeforeGameStartedMongoEvent.class));
				openByLeftMongoEvents = new LinkedList<>(mongoTemplate.find(query, OpenByLeftMongoEvent.class));
				watcherLeftLastMongoEvents = new LinkedList<>(mongoTemplate.find(query, WatcherLeftLastMongoEvent.class));
				watcherLeftMongoEvents = new LinkedList<>(mongoTemplate.find(query, WatcherLeftMongoEvent.class));
				openByLeftFirstMongoEvents = new LinkedList<>(mongoTemplate.find(query, OpenByLeftFirstMongoEvent.class));
				secondLeftFirstMongoEvents = new LinkedList<>(mongoTemplate.find(query, SecondLeftFirstMongoEvent.class));
				secondLeftMongoEvents = new LinkedList<>(mongoTemplate.find(query, SecondLeftMongoEvent.class));
				openByLeftLastMongoEvents = new LinkedList<>(mongoTemplate.find(query, OpenByLeftLastMongoEvent.class));
				secondLeftLastMongoEvents = new LinkedList<>(mongoTemplate.find(query, SecondLeftLastMongoEvent.class));
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
			mongoEvents.addAll(loggedOutWatcherLeftLastMongoEvents);
			mongoEvents.addAll(loggedOutWatcherLeftMongoEvents);
			mongoEvents.addAll(loggedOutOpenByLeftFirstMongoEvents);
			mongoEvents.addAll(loggedOutSecondLeftFirstMongoEvents);
			mongoEvents.addAll(loggedOutSecondLeftMongoEvents);
			mongoEvents.addAll(loggedOutOpenByLeftLastMongoEvents);
			mongoEvents.addAll(loggedOutSecondLeftLastMongoEvents);
			mongoEvents.addAll(openByLeftBeforeGameStartedMongoEvents);	
			mongoEvents.addAll(openByLeftMongoEvents);
			mongoEvents.addAll(watcherLeftLastMongoEvents);
			mongoEvents.addAll(watcherLeftMongoEvents);
			mongoEvents.addAll(openByLeftFirstMongoEvents);
			mongoEvents.addAll(secondLeftFirstMongoEvents);
			mongoEvents.addAll(secondLeftMongoEvents);
			mongoEvents.addAll(openByLeftLastMongoEvents);
			mongoEvents.addAll(secondLeftLastMongoEvents);
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
		else if(clazz.equals("LoggedOutWatcherLeftLastEvent")){
			LoggedOutWatcherLeftLastMongoEvent loggedOutWatcherLeftLastMongoEvent = (LoggedOutWatcherLeftLastMongoEvent)mongoEvent;
			LoggedOutWatcherLeftLastEvent loggedOutWatcherLeftLastEvent = new LoggedOutWatcherLeftLastEvent(uuid, loggedOutWatcherLeftLastMongoEvent.getServiceId(), loggedOutWatcherLeftLastMongoEvent.getEventId(), loggedOutWatcherLeftLastMongoEvent.getArrived(), "LoggedOutWatcherLeftLastEvent", loggedOutWatcherLeftLastMongoEvent.getWatcher(), loggedOutWatcherLeftLastMongoEvent.getGameRoom());
			return loggedOutWatcherLeftLastEvent;
		}
		else if(clazz.equals("LoggedOutWatcherLeftEvent")){
			LoggedOutWatcherLeftMongoEvent loggedOutWatcherLeftMongoEvent = (LoggedOutWatcherLeftMongoEvent)mongoEvent;
			LoggedOutWatcherLeftEvent loggedOutWatcherLeftEvent = new LoggedOutWatcherLeftEvent(uuid, loggedOutWatcherLeftMongoEvent.getServiceId(), loggedOutWatcherLeftMongoEvent.getEventId(), loggedOutWatcherLeftMongoEvent.getArrived(), "LoggedOutWatcherLeftEvent", loggedOutWatcherLeftMongoEvent.getWatcher(), loggedOutWatcherLeftMongoEvent.getGameRoom());
			return loggedOutWatcherLeftEvent;
		}
		else if(clazz.equals("LoggedOutOpenByLeftFirstEvent")){
			LoggedOutOpenByLeftFirstMongoEvent loggedOutOpenByLeftFirstMongoEvent = (LoggedOutOpenByLeftFirstMongoEvent)mongoEvent;
			LoggedOutOpenByLeftFirstEvent loggedOutOpenByLeftFirstEvent = new LoggedOutOpenByLeftFirstEvent(uuid, loggedOutOpenByLeftFirstMongoEvent.getServiceId(), loggedOutOpenByLeftFirstMongoEvent.getEventId(), loggedOutOpenByLeftFirstMongoEvent.getArrived(), "LoggedOutOpenByLeftFirstEvent", loggedOutOpenByLeftFirstMongoEvent.getOpenBy(), loggedOutOpenByLeftFirstMongoEvent.getGameRoom());
			return loggedOutOpenByLeftFirstEvent;
		}
		else if(clazz.equals("LoggedOutSecondLeftFirstEvent")){
			LoggedOutSecondLeftFirstMongoEvent loggedOutSecondLeftFirstMongoEvent = (LoggedOutSecondLeftFirstMongoEvent)mongoEvent;
			LoggedOutSecondLeftFirstEvent loggedOutSecondLeftFirstEvent = new LoggedOutSecondLeftFirstEvent(uuid, loggedOutSecondLeftFirstMongoEvent.getServiceId(), loggedOutSecondLeftFirstMongoEvent.getEventId(), loggedOutSecondLeftFirstMongoEvent.getArrived(), "LoggedOutSecondLeftFirstEvent", loggedOutSecondLeftFirstMongoEvent.getSecond(), loggedOutSecondLeftFirstMongoEvent.getGameRoom());
			return loggedOutSecondLeftFirstEvent;
		}
		else if(clazz.equals("LoggedOutSecondLeftEvent")){
			LoggedOutSecondLeftMongoEvent loggedOutSecondLeftMongoEvent = (LoggedOutSecondLeftMongoEvent)mongoEvent;
			LoggedOutSecondLeftEvent loggedOutSecondLeftEvent = new LoggedOutSecondLeftEvent(uuid, loggedOutSecondLeftMongoEvent.getServiceId(), loggedOutSecondLeftMongoEvent.getEventId(), loggedOutSecondLeftMongoEvent.getArrived(), "LoggedOutSecondLeftEvent", loggedOutSecondLeftMongoEvent.getSecond(), loggedOutSecondLeftMongoEvent.getGameRoom());
			return loggedOutSecondLeftEvent;
		}
		else if(clazz.equals("LoggedOutOpenByLeftLastEvent")){
			LoggedOutOpenByLeftLastMongoEvent loggedOutOpenByLeftLastMongoEvent = (LoggedOutOpenByLeftLastMongoEvent)mongoEvent;
			LoggedOutOpenByLeftLastEvent loggedOutOpenByLeftLastEvent = new LoggedOutOpenByLeftLastEvent(uuid, loggedOutOpenByLeftLastMongoEvent.getServiceId(), loggedOutOpenByLeftLastMongoEvent.getEventId(), loggedOutOpenByLeftLastMongoEvent.getArrived(), "LoggedOutOpenByLeftLastEvent", loggedOutOpenByLeftLastMongoEvent.getOpenBy(), loggedOutOpenByLeftLastMongoEvent.getGameRoom());
			return loggedOutOpenByLeftLastEvent;
		}
		else if(clazz.equals("LoggedOutSecondLeftLastEvent")){
			LoggedOutSecondLeftLastMongoEvent loggedOutSecondLeftLastMongoEvent = (LoggedOutSecondLeftLastMongoEvent)mongoEvent;
			LoggedOutSecondLeftLastEvent loggedOutSecondLeftLastEvent = new LoggedOutSecondLeftLastEvent(uuid, loggedOutSecondLeftLastMongoEvent.getServiceId(), loggedOutSecondLeftLastMongoEvent.getEventId(), loggedOutSecondLeftLastMongoEvent.getArrived(), "LoggedOutSecondLeftLastEvent", loggedOutSecondLeftLastMongoEvent.getSecond(), loggedOutSecondLeftLastMongoEvent.getGameRoom());
			return loggedOutSecondLeftLastEvent;
		}
		else if(clazz.equals("OpenByLeftBeforeGameStartedEvent")){
			OpenByLeftBeforeGameStartedMongoEvent openByLeftBeforeGameStartedMongoEvent = (OpenByLeftBeforeGameStartedMongoEvent)mongoEvent;
			OpenByLeftBeforeGameStartedEvent openByLeftBeforeGameStartedEvent = new OpenByLeftBeforeGameStartedEvent(uuid, openByLeftBeforeGameStartedMongoEvent.getServiceId(), openByLeftBeforeGameStartedMongoEvent.getEventId(), openByLeftBeforeGameStartedMongoEvent.getArrived(), "OpenByLeftBeforeGameStartedEvent", openByLeftBeforeGameStartedMongoEvent.getLeavingUserName(), openByLeftBeforeGameStartedMongoEvent.getGameRoom());
			return openByLeftBeforeGameStartedEvent;
		}
		else if(clazz.equals("OpenByLeftEvent")){
			OpenByLeftMongoEvent openByLeftMongoEvent = (OpenByLeftMongoEvent)mongoEvent;
			OpenByLeftEvent openByLeftEvent = new OpenByLeftEvent(uuid, openByLeftMongoEvent.getServiceId(), openByLeftMongoEvent.getEventId(), openByLeftMongoEvent.getArrived(), "OpenByLeftEvent", openByLeftMongoEvent.getOpenBy(), openByLeftMongoEvent.getGameRoom());
			return openByLeftEvent;
		}
		else if(clazz.equals("WatcherLeftLastEvent")){
			WatcherLeftLastMongoEvent watcherLeftLastMongoEvent = (WatcherLeftLastMongoEvent)mongoEvent;
			WatcherLeftLastEvent watcherLeftLastEvent = new WatcherLeftLastEvent(uuid, watcherLeftLastMongoEvent.getServiceId(), watcherLeftLastMongoEvent.getEventId(), watcherLeftLastMongoEvent.getArrived(), "WatcherLeftLastEvent", watcherLeftLastMongoEvent.getWatcher(), watcherLeftLastMongoEvent.getGameRoom());
			return watcherLeftLastEvent;
		}
		else if(clazz.equals("WatcherLeftEvent")){
			WatcherLeftMongoEvent watcherLeftMongoEvent = (WatcherLeftMongoEvent)mongoEvent;
			WatcherLeftEvent watcherLeftEvent = new WatcherLeftEvent(uuid, watcherLeftMongoEvent.getServiceId(), watcherLeftMongoEvent.getEventId(), watcherLeftMongoEvent.getArrived(), "WatcherLeftEvent", watcherLeftMongoEvent.getWatcher(), watcherLeftMongoEvent.getGameRoom());
			return watcherLeftEvent;
		}
		else if(clazz.equals("OpenByLeftFirstEvent")){
			OpenByLeftFirstMongoEvent openByLeftFirstMongoEvent = (OpenByLeftFirstMongoEvent)mongoEvent;
			OpenByLeftFirstEvent openByLeftFirstEvent = new OpenByLeftFirstEvent(uuid, openByLeftFirstMongoEvent.getServiceId(), openByLeftFirstMongoEvent.getEventId(), openByLeftFirstMongoEvent.getArrived(), "OpenByLeftFirstEvent", openByLeftFirstMongoEvent.getOpenBy(), openByLeftFirstMongoEvent.getGameRoom());
			return openByLeftFirstEvent;
		}
		else if(clazz.equals("SecondLeftFirstEvent")){
			SecondLeftFirstMongoEvent secondLeftFirstMongoEvent = (SecondLeftFirstMongoEvent)mongoEvent;
			SecondLeftFirstEvent secondLeftFirstEvent = new SecondLeftFirstEvent(uuid, secondLeftFirstMongoEvent.getServiceId(), secondLeftFirstMongoEvent.getEventId(), secondLeftFirstMongoEvent.getArrived(), "SecondLeftFirstEvent", secondLeftFirstMongoEvent.getSecond(), secondLeftFirstMongoEvent.getGameRoom());
			return secondLeftFirstEvent;
		}
		else if(clazz.equals("SecondLeftEvent")){
			SecondLeftMongoEvent secondLeftMongoEvent = (SecondLeftMongoEvent)mongoEvent;
			SecondLeftEvent secondLeftEvent = new SecondLeftEvent(uuid, secondLeftMongoEvent.getServiceId(), secondLeftMongoEvent.getEventId(), secondLeftMongoEvent.getArrived(), "SecondLeftEvent", secondLeftMongoEvent.getSecond(), secondLeftMongoEvent.getGameRoom());
			return secondLeftEvent;
		}
		else if(clazz.equals("OpenByLeftLastEvent")){
			OpenByLeftLastMongoEvent openByLeftLastMongoEvent = (OpenByLeftLastMongoEvent)mongoEvent;
			OpenByLeftLastEvent openByLeftLastEvent = new OpenByLeftLastEvent(uuid, openByLeftLastMongoEvent.getServiceId(), openByLeftLastMongoEvent.getEventId(), openByLeftLastMongoEvent.getArrived(), "OpenByLeftLastEvent", openByLeftLastMongoEvent.getOpenBy(), openByLeftLastMongoEvent.getGameRoom());
			return openByLeftLastEvent;
		}
		else if(clazz.equals("SecondLeftLastEvent")){
			SecondLeftLastMongoEvent secondLeftLastMongoEvent = (SecondLeftLastMongoEvent)mongoEvent;
			SecondLeftLastEvent secondLeftLastEvent = new SecondLeftLastEvent(uuid, secondLeftLastMongoEvent.getServiceId(), secondLeftLastMongoEvent.getEventId(), secondLeftLastMongoEvent.getArrived(), "SecondLeftLastEvent", secondLeftLastMongoEvent.getSecond(), secondLeftLastMongoEvent.getGameRoom());
			return secondLeftLastEvent;
		}
		else{
			throw new RuntimeException("Failed to convert mongo event....");
		}
	}
}

























