package org.moshe.arad.mongo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.UUID;

import org.moshe.arad.kafka.Services;
import org.moshe.arad.kafka.events.BackgammonEvent;
import org.moshe.arad.kafka.events.BlackAteWhitePawnEvent;
import org.moshe.arad.kafka.events.BlackPawnCameBackAndAteWhitePawnEvent;
import org.moshe.arad.kafka.events.BlackPawnCameBackEvent;
import org.moshe.arad.kafka.events.BlackPawnTakenOutEvent;
import org.moshe.arad.kafka.events.DiceRolledCanNotPlayEvent;
import org.moshe.arad.kafka.events.DiceRolledEvent;
import org.moshe.arad.kafka.events.EndReadEventsFromMongoEvent;
import org.moshe.arad.kafka.events.ExistingUserJoinedLobbyEvent;
import org.moshe.arad.kafka.events.GameRoomClosedEvent;
import org.moshe.arad.kafka.events.GameStartedEvent;
import org.moshe.arad.kafka.events.GameStoppedEvent;
import org.moshe.arad.kafka.events.InitDiceCompletedEvent;
import org.moshe.arad.kafka.events.InitGameRoomCompletedEvent;
import org.moshe.arad.kafka.events.LastMoveBlackAteWhitePawnEvent;
import org.moshe.arad.kafka.events.LastMoveBlackPawnCameBackAndAteWhitePawnEvent;
import org.moshe.arad.kafka.events.LastMoveBlackPawnCameBackEvent;
import org.moshe.arad.kafka.events.LastMoveBlackPawnTakenOutEvent;
import org.moshe.arad.kafka.events.LastMoveWhiteAteBlackPawnEvent;
import org.moshe.arad.kafka.events.LastMoveWhitePawnCameBackAndAteBlackPawnEvent;
import org.moshe.arad.kafka.events.LastMoveWhitePawnCameBackEvent;
import org.moshe.arad.kafka.events.LastMoveWhitePawnTakenOutEvent;
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
import org.moshe.arad.kafka.events.TurnNotPassedBlackAteWhitePawnEvent;
import org.moshe.arad.kafka.events.TurnNotPassedBlackPawnCameBackAndAteWhitePawnEvent;
import org.moshe.arad.kafka.events.TurnNotPassedBlackPawnCameBackEvent;
import org.moshe.arad.kafka.events.TurnNotPassedBlackPawnTakenOutEvent;
import org.moshe.arad.kafka.events.TurnNotPassedUserMadeMoveEvent;
import org.moshe.arad.kafka.events.TurnNotPassedWhiteAteBlackPawnEvent;
import org.moshe.arad.kafka.events.TurnNotPassedWhitePawnCameBackAndAteBlackPawnEvent;
import org.moshe.arad.kafka.events.TurnNotPassedWhitePawnCameBackEvent;
import org.moshe.arad.kafka.events.TurnNotPassedWhitePawnTakenOutEvent;
import org.moshe.arad.kafka.events.UserAddedAsSecondPlayerEvent;
import org.moshe.arad.kafka.events.UserAddedAsWatcherEvent;
import org.moshe.arad.kafka.events.UserMadeInvalidMoveEvent;
import org.moshe.arad.kafka.events.UserMadeLastMoveEvent;
import org.moshe.arad.kafka.events.UserMadeMoveEvent;
import org.moshe.arad.kafka.events.LoggedOutUserLeftLobbyEvent;
import org.moshe.arad.kafka.events.LoggedOutWatcherLeftEvent;
import org.moshe.arad.kafka.events.LoggedOutWatcherLeftLastEvent;
import org.moshe.arad.kafka.events.UserPermissionsUpdatedEvent;
import org.moshe.arad.kafka.events.WatcherLeftEvent;
import org.moshe.arad.kafka.events.WatcherLeftLastEvent;
import org.moshe.arad.kafka.events.WhiteAteBlackPawnEvent;
import org.moshe.arad.kafka.events.WhitePawnCameBackAndAteBlackPawnEvent;
import org.moshe.arad.kafka.events.WhitePawnCameBackEvent;
import org.moshe.arad.kafka.events.WhitePawnTakenOutEvent;
import org.moshe.arad.kafka.events.WinnerMoveMadeEvent;
import org.moshe.arad.mongo.events.BlackAteWhitePawnMongoEvent;
import org.moshe.arad.mongo.events.BlackPawnCameBackAndAteWhitePawnMongoEvent;
import org.moshe.arad.mongo.events.BlackPawnCameBackMongoEvent;
import org.moshe.arad.mongo.events.BlackPawnTakenOutMongoEvent;
import org.moshe.arad.mongo.events.DiceRolledCanNotPlayMongoEvent;
import org.moshe.arad.mongo.events.DiceRolledMongoEvent;
import org.moshe.arad.mongo.events.ExistingUserJoinedLobbyMongoEvent;
import org.moshe.arad.mongo.events.GameRoomClosedMongoEvent;
import org.moshe.arad.mongo.events.GameStartedMongoEvent;
import org.moshe.arad.mongo.events.GameStoppedMongoEvent;
import org.moshe.arad.mongo.events.IMongoEvent;
import org.moshe.arad.mongo.events.InitDiceCompletedMongoEvent;
import org.moshe.arad.mongo.events.InitGameRoomCompletedMongoEvent;
import org.moshe.arad.mongo.events.LastMoveBlackAteWhitePawnMongoEvent;
import org.moshe.arad.mongo.events.LastMoveBlackPawnCameBackAndAteWhitePawnMongoEvent;
import org.moshe.arad.mongo.events.LastMoveBlackPawnCameBackMongoEvent;
import org.moshe.arad.mongo.events.LastMoveBlackPawnTakenOutMongoEvent;
import org.moshe.arad.mongo.events.LastMoveWhiteAteBlackPawnMongoEvent;
import org.moshe.arad.mongo.events.LastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent;
import org.moshe.arad.mongo.events.LastMoveWhitePawnCameBackMongoEvent;
import org.moshe.arad.mongo.events.LastMoveWhitePawnTakenOutMongoEvent;
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
import org.moshe.arad.mongo.events.TurnNotPassedBlackAteWhitePawnMongoEvent;
import org.moshe.arad.mongo.events.TurnNotPassedBlackPawnCameBackAndAteWhitePawnMongoEvent;
import org.moshe.arad.mongo.events.TurnNotPassedBlackPawnCameBackMongoEvent;
import org.moshe.arad.mongo.events.TurnNotPassedBlackPawnTakenOutMongoEvent;
import org.moshe.arad.mongo.events.TurnNotPassedUserMadeMoveMongoEvent;
import org.moshe.arad.mongo.events.TurnNotPassedWhiteAteBlackPawnMongoEvent;
import org.moshe.arad.mongo.events.TurnNotPassedWhitePawnCameBackAndAteBlackPawnMongoEvent;
import org.moshe.arad.mongo.events.TurnNotPassedWhitePawnCameBackMongoEvent;
import org.moshe.arad.mongo.events.TurnNotPassedWhitePawnTakenOutMongoEvent;
import org.moshe.arad.mongo.events.UserAddedAsSecondPlayerMongoEvent;
import org.moshe.arad.mongo.events.UserAddedAsWatcherMongoEvent;
import org.moshe.arad.mongo.events.UserMadeInvalidMoveMongoEvent;
import org.moshe.arad.mongo.events.UserMadeLastMoveMongoEvent;
import org.moshe.arad.mongo.events.UserMadeMoveMongoEvent;
import org.moshe.arad.mongo.events.LoggedOutUserLeftLobbyMongoEvent;
import org.moshe.arad.mongo.events.LoggedOutWatcherLeftLastMongoEvent;
import org.moshe.arad.mongo.events.LoggedOutWatcherLeftMongoEvent;
import org.moshe.arad.mongo.events.UserPermissionsUpdatedMongoEvent;
import org.moshe.arad.mongo.events.WatcherLeftLastMongoEvent;
import org.moshe.arad.mongo.events.WatcherLeftMongoEvent;
import org.moshe.arad.mongo.events.WhiteAteBlackPawnMongoEvent;
import org.moshe.arad.mongo.events.WhitePawnCameBackAndAteBlackPawnMongoEvent;
import org.moshe.arad.mongo.events.WhitePawnCameBackMongoEvent;
import org.moshe.arad.mongo.events.WhitePawnTakenOutMongoEvent;
import org.moshe.arad.mongo.events.WinnerMoveMadeMongoEvent;
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
	public void addDiceRolledCanNotPlayEvent(DiceRolledCanNotPlayEvent diceRolledCanNotPlayEvent) {
		try{
			DiceRolledCanNotPlayMongoEvent mongoEvent = DiceRolledCanNotPlayMongoEvent.convertIntoMongoEvent(diceRolledCanNotPlayEvent);
			
			mongoTemplate.insert(mongoEvent, "DiceRolledCanNotPlayEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save DiceRolledCanNotPlayMongoEvent into mongo events store");
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
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
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
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addBlackPawnTakenOutEvent(BlackPawnTakenOutEvent blackPawnTakenOutEvent) {
		try{
			BlackPawnTakenOutMongoEvent mongoEvent = BlackPawnTakenOutMongoEvent.convertIntoMongoEvent(blackPawnTakenOutEvent);
			
			mongoTemplate.insert(mongoEvent, "BlackPawnTakenOutEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save BlackPawnTakenOutMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addBlackAteWhitePawnEvent(BlackAteWhitePawnEvent blackAteWhitePawnEvent) {
		try{
			BlackAteWhitePawnMongoEvent mongoEvent = BlackAteWhitePawnMongoEvent.convertIntoMongoEvent(blackAteWhitePawnEvent);
			
			mongoTemplate.insert(mongoEvent, "BlackAteWhitePawnEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save BlackAteWhitePawnMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addWhiteAteBlackPawnEvent(WhiteAteBlackPawnEvent whiteAteBlackPawnEvent) {
		try{
			WhiteAteBlackPawnMongoEvent mongoEvent = WhiteAteBlackPawnMongoEvent.convertIntoMongoEvent(whiteAteBlackPawnEvent);
			
			mongoTemplate.insert(mongoEvent, "WhiteAteBlackPawnEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save WhiteAteBlackPawnMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addUserMadeMoveEvent(UserMadeMoveEvent userMadeMoveEvent) {
		try{
			UserMadeMoveMongoEvent mongoEvent = UserMadeMoveMongoEvent.convertIntoMongoEvent(userMadeMoveEvent);
			
			mongoTemplate.insert(mongoEvent, "UserMadeMoveEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save UserMadeMoveMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addLastMoveWhitePawnCameBackEvent(LastMoveWhitePawnCameBackEvent lastMoveWhitePawnCameBackEvent) {
		try{
			LastMoveWhitePawnCameBackMongoEvent mongoEvent = LastMoveWhitePawnCameBackMongoEvent.convertIntoMongoEvent(lastMoveWhitePawnCameBackEvent);
			
			mongoTemplate.insert(mongoEvent, "LastMoveWhitePawnCameBackEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save LastMoveWhitePawnCameBackMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addTurnNotPassedWhitePawnCameBackEvent(
			TurnNotPassedWhitePawnCameBackEvent turnNotPassedWhitePawnCameBackEvent) {
		try{
			TurnNotPassedWhitePawnCameBackMongoEvent mongoEvent = TurnNotPassedWhitePawnCameBackMongoEvent.convertIntoMongoEvent(turnNotPassedWhitePawnCameBackEvent);
			
			mongoTemplate.insert(mongoEvent, "TurnNotPassedWhitePawnCameBackEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save TurnNotPassedWhitePawnCameBackMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addLastMoveBlackPawnCameBackEvent(LastMoveBlackPawnCameBackEvent lastMoveBlackPawnCameBackEvent) {
		try{
			LastMoveBlackPawnCameBackMongoEvent mongoEvent = LastMoveBlackPawnCameBackMongoEvent.convertIntoMongoEvent(lastMoveBlackPawnCameBackEvent);
			
			mongoTemplate.insert(mongoEvent, "LastMoveBlackPawnCameBackEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save LastMoveBlackPawnCameBackMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addTurnNotPassedBlackPawnCameBackEvent(
			TurnNotPassedBlackPawnCameBackEvent turnNotPassedBlackPawnCameBackEvent) {
		try{
			TurnNotPassedBlackPawnCameBackMongoEvent mongoEvent = TurnNotPassedBlackPawnCameBackMongoEvent.convertIntoMongoEvent(turnNotPassedBlackPawnCameBackEvent);
			
			mongoTemplate.insert(mongoEvent, "TurnNotPassedBlackPawnCameBackEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save TurnNotPassedBlackPawnCameBackMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addLastMoveWhitePawnTakenOutEvent(LastMoveWhitePawnTakenOutEvent lastMoveWhitePawnTakenOutEvent) {
		try{
			LastMoveWhitePawnTakenOutMongoEvent mongoEvent = LastMoveWhitePawnTakenOutMongoEvent.convertIntoMongoEvent(lastMoveWhitePawnTakenOutEvent);
			
			mongoTemplate.insert(mongoEvent, "LastMoveWhitePawnTakenOutEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save LastMoveWhitePawnTakenOutMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addTurnNotPassedWhitePawnTakenOutEvent(
			TurnNotPassedWhitePawnTakenOutEvent turnNotPassedWhitePawnTakenOutEvent) {
		try{
			TurnNotPassedWhitePawnTakenOutMongoEvent mongoEvent = TurnNotPassedWhitePawnTakenOutMongoEvent.convertIntoMongoEvent(turnNotPassedWhitePawnTakenOutEvent);
			
			mongoTemplate.insert(mongoEvent, "TurnNotPassedWhitePawnTakenOutEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save TurnNotPassedWhitePawnTakenOutMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addLastMoveBlackPawnTakenOutEvent(LastMoveBlackPawnTakenOutEvent lastMoveBlackPawnTakenOutEvent) {
		try{
			LastMoveBlackPawnTakenOutMongoEvent mongoEvent = LastMoveBlackPawnTakenOutMongoEvent.convertIntoMongoEvent(lastMoveBlackPawnTakenOutEvent);
			
			mongoTemplate.insert(mongoEvent, "LastMoveBlackPawnTakenOutEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save LastMoveBlackPawnTakenOutMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addTurnNotPassedBlackPawnTakenOutEvent(
			TurnNotPassedBlackPawnTakenOutEvent turnNotPassedBlackPawnTakenOutEvent) {
		try{
			TurnNotPassedBlackPawnTakenOutMongoEvent mongoEvent = TurnNotPassedBlackPawnTakenOutMongoEvent.convertIntoMongoEvent(turnNotPassedBlackPawnTakenOutEvent);
			
			mongoTemplate.insert(mongoEvent, "TurnNotPassedBlackPawnTakenOutEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save TurnNotPassedBlackPawnTakenOutMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addLastMoveBlackAteWhitePawnEvent(LastMoveBlackAteWhitePawnEvent lastMoveBlackAteWhitePawnEvent) {
		try{
			LastMoveBlackAteWhitePawnMongoEvent mongoEvent = LastMoveBlackAteWhitePawnMongoEvent.convertIntoMongoEvent(lastMoveBlackAteWhitePawnEvent);
			
			mongoTemplate.insert(mongoEvent, "LastMoveBlackAteWhitePawnEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save LastMoveBlackAteWhitePawnMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addTurnNotPassedBlackAteWhitePawnEvent(
			TurnNotPassedBlackAteWhitePawnEvent turnNotPassedBlackAteWhitePawnEvent) {
		try{
			TurnNotPassedBlackAteWhitePawnMongoEvent mongoEvent = TurnNotPassedBlackAteWhitePawnMongoEvent.convertIntoMongoEvent(turnNotPassedBlackAteWhitePawnEvent);
			
			mongoTemplate.insert(mongoEvent, "TurnNotPassedBlackAteWhitePawnEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save TurnNotPassedBlackAteWhitePawnMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addLastMoveWhiteAteBlackPawnEvent(LastMoveWhiteAteBlackPawnEvent lastMoveWhiteAteBlackPawnEvent) {
		try{
			LastMoveWhiteAteBlackPawnMongoEvent mongoEvent = LastMoveWhiteAteBlackPawnMongoEvent.convertIntoMongoEvent(lastMoveWhiteAteBlackPawnEvent);
			
			mongoTemplate.insert(mongoEvent, "LastMoveWhiteAteBlackPawnEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save LastMoveWhiteAteBlackPawnMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addTurnNotPassedWhiteAteBlackPawnEvent(
			TurnNotPassedWhiteAteBlackPawnEvent turnNotPassedWhiteAteBlackPawnEvent) {
		try{
			TurnNotPassedWhiteAteBlackPawnMongoEvent mongoEvent = TurnNotPassedWhiteAteBlackPawnMongoEvent.convertIntoMongoEvent(turnNotPassedWhiteAteBlackPawnEvent);
			
			mongoTemplate.insert(mongoEvent, "TurnNotPassedWhiteAteBlackPawnEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save TurnNotPassedWhiteAteBlackPawnMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addUserMadeLastMoveEvent(UserMadeLastMoveEvent userMadeLastMoveEvent) {
		try{
			UserMadeLastMoveMongoEvent mongoEvent = UserMadeLastMoveMongoEvent.convertIntoMongoEvent(userMadeLastMoveEvent);
			
			mongoTemplate.insert(mongoEvent, "UserMadeLastMoveEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save UserMadeLastMoveMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addTurnNotPassedUserMadeMoveEvent(TurnNotPassedUserMadeMoveEvent turnNotPassedUserMadeMoveEvent) {
		try{
			TurnNotPassedUserMadeMoveMongoEvent mongoEvent = TurnNotPassedUserMadeMoveMongoEvent.convertIntoMongoEvent(turnNotPassedUserMadeMoveEvent);
			
			mongoTemplate.insert(mongoEvent, "TurnNotPassedUserMadeMoveEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save TurnNotPassedUserMadeMoveMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addWhitePawnCameBackAndAteBlackPawnEvent(
			WhitePawnCameBackAndAteBlackPawnEvent whitePawnCameBackAndAteBlackPawnEvent) {
		try{
			WhitePawnCameBackAndAteBlackPawnMongoEvent mongoEvent = WhitePawnCameBackAndAteBlackPawnMongoEvent.convertIntoMongoEvent(whitePawnCameBackAndAteBlackPawnEvent);
			
			mongoTemplate.insert(mongoEvent, "WhitePawnCameBackAndAteBlackPawnEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save WhitePawnCameBackAndAteBlackPawnMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addLastMoveWhitePawnCameBackAndAteBlackPawnEvent(
			LastMoveWhitePawnCameBackAndAteBlackPawnEvent lastMoveWhitePawnCameBackAndAteBlackPawnEvent) {
		try{
			LastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent mongoEvent = LastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent.convertIntoMongoEvent(lastMoveWhitePawnCameBackAndAteBlackPawnEvent);
			
			mongoTemplate.insert(mongoEvent, "LastMoveWhitePawnCameBackAndAteBlackPawnEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save LastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addTurnNotPassedWhitePawnCameBackAndAteBlackPawnEvent(
			TurnNotPassedWhitePawnCameBackAndAteBlackPawnEvent turnNotPassedWhitePawnCameBackAndAteBlackPawnEvent) {
		try{
			TurnNotPassedWhitePawnCameBackAndAteBlackPawnMongoEvent mongoEvent = TurnNotPassedWhitePawnCameBackAndAteBlackPawnMongoEvent.convertIntoMongoEvent(turnNotPassedWhitePawnCameBackAndAteBlackPawnEvent);
			
			mongoTemplate.insert(mongoEvent, "TurnNotPassedWhitePawnCameBackAndAteBlackPawnEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save TurnNotPassedWhitePawnCameBackAndAteBlackPawnMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}	
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addBlackPawnCameBackAndAteWhitePawnEvent(
			BlackPawnCameBackAndAteWhitePawnEvent blackPawnCameBackAndAteWhitePawnEvent) {
		try{
			BlackPawnCameBackAndAteWhitePawnMongoEvent mongoEvent = BlackPawnCameBackAndAteWhitePawnMongoEvent.convertIntoMongoEvent(blackPawnCameBackAndAteWhitePawnEvent);
			
			mongoTemplate.insert(mongoEvent, "BlackPawnCameBackAndAteWhitePawnEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save BlackPawnCameBackAndAteWhitePawnMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addLastMoveBlackPawnCameBackAndAteWhitePawnEvent(
			LastMoveBlackPawnCameBackAndAteWhitePawnEvent lastMoveBlackPawnCameBackAndAteWhitePawnEvent) {
		try{
			LastMoveBlackPawnCameBackAndAteWhitePawnMongoEvent mongoEvent = LastMoveBlackPawnCameBackAndAteWhitePawnMongoEvent.convertIntoMongoEvent(lastMoveBlackPawnCameBackAndAteWhitePawnEvent);
			
			mongoTemplate.insert(mongoEvent, "LastMoveBlackPawnCameBackAndAteWhitePawnEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save LastMoveBlackPawnCameBackAndAteWhitePawnMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addTurnNotPassedBlackPawnCameBackAndAteWhitePawnEvent(
			TurnNotPassedBlackPawnCameBackAndAteWhitePawnEvent turnNotPassedBlackPawnCameBackAndAteWhitePawnEvent) {
		try{
			TurnNotPassedBlackPawnCameBackAndAteWhitePawnMongoEvent mongoEvent = TurnNotPassedBlackPawnCameBackAndAteWhitePawnMongoEvent.convertIntoMongoEvent(turnNotPassedBlackPawnCameBackAndAteWhitePawnEvent);
			
			mongoTemplate.insert(mongoEvent, "TurnNotPassedBlackPawnCameBackAndAteWhitePawnEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save TurnNotPassedBlackPawnCameBackAndAteWhitePawnMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addWinnerMoveMadeEvent(WinnerMoveMadeEvent winnerMoveMadeEvent) {
		try{
			WinnerMoveMadeMongoEvent mongoEvent = WinnerMoveMadeMongoEvent.convertIntoMongoEvent(winnerMoveMadeEvent);
			
			mongoTemplate.insert(mongoEvent, "WinnerMoveMadeEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save WinnerMoveMadeMongoEvent into mongo events store");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	//TODO to add this event to code below, a.k.a, handle pull without saving in game service
	public void addGameStoppedEvent(GameStoppedEvent gameStoppedEvent) {
		try{
			GameStoppedMongoEvent mongoEvent = GameStoppedMongoEvent.convertIntoMongoEvent(gameStoppedEvent);
			
			mongoTemplate.insert(mongoEvent, "GameStoppedEvents");		
		}
		catch (Exception ex) {
			logger.error("Failed to save GameStoppedMongoEvent into mongo events store");
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

























