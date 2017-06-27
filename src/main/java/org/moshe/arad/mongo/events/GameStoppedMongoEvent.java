package org.moshe.arad.mongo.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.GameRoom;
import org.moshe.arad.entities.backgammon.instrument.BackgammonDice;
import org.moshe.arad.entities.backgammon.json.BackgammonBoardJson;
import org.moshe.arad.kafka.events.BlackAteWhitePawnEvent;
import org.moshe.arad.kafka.events.GameStoppedEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="GameStoppedEvents")
public class GameStoppedMongoEvent implements IMongoEvent {

	@Id
	private String mongoEventId;
	private UUID uuid;
	private int serviceId;
	private int eventId;
	private Date arrived;
	private String clazz;
	private String userName;
	private GameRoom gameRoom;
	private BackgammonBoardJson backgammonBoardJson;
	private BackgammonDice firstDice;
	private BackgammonDice secondDice;
	private boolean isWhite;

	public GameStoppedMongoEvent() {
		
	}

	public GameStoppedMongoEvent(String mongoEventId, UUID uuid, int serviceId, int eventId, Date arrived, String clazz,
			String userName, GameRoom gameRoom, BackgammonBoardJson backgammonBoardJson, BackgammonDice firstDice,
			BackgammonDice secondDice, boolean isWhite) {
		super();
		this.mongoEventId = mongoEventId;
		this.uuid = uuid;
		this.serviceId = serviceId;
		this.eventId = eventId;
		this.arrived = arrived;
		this.clazz = clazz;
		this.userName = userName;
		this.gameRoom = gameRoom;
		this.backgammonBoardJson = backgammonBoardJson;
		this.firstDice = firstDice;
		this.secondDice = secondDice;
		this.isWhite = isWhite;
	}

	@Override
	public String toString() {
		return "GameStoppedMongoEvent [mongoEventId=" + mongoEventId + ", uuid=" + uuid + ", serviceId=" + serviceId
				+ ", eventId=" + eventId + ", arrived=" + arrived + ", clazz=" + clazz + ", userName=" + userName
				+ ", backgammonBoardJson=" + backgammonBoardJson + ", firstDice=" + firstDice + ", secondDice="
				+ secondDice + ", isWhite=" + isWhite + "]";
	}

	public static GameStoppedMongoEvent convertIntoMongoEvent(GameStoppedEvent event) {
		GameStoppedMongoEvent gameStoppedMongoEvent = new GameStoppedMongoEvent();
		
		gameStoppedMongoEvent.setUuid(event.getUuid());
		gameStoppedMongoEvent.setArrived(event.getArrived());
		gameStoppedMongoEvent.setClazz(event.getClazz());
		gameStoppedMongoEvent.setUserName(event.getUserName());
		gameStoppedMongoEvent.setGameRoom(event.getGameRoom());
		gameStoppedMongoEvent.setBackgammonBoardJson(event.getBackgammonBoardJson());
		gameStoppedMongoEvent.setFirstDice(event.getFirstDice());
		gameStoppedMongoEvent.setSecondDice(event.getSecondDice());
		gameStoppedMongoEvent.setWhite(event.isWhite());
		
		return gameStoppedMongoEvent;
	}
	
	public String getMongoEventId() {
		return mongoEventId;
	}

	public void setMongoEventId(String mongoEventId) {
		this.mongoEventId = mongoEventId;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	@Override
	public Date getArrived() {
		return arrived;
	}

	@Override
	public void setArrived(Date arrived) {
		this.arrived = arrived;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public GameRoom getGameRoom() {
		return gameRoom;
	}

	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}

	public BackgammonBoardJson getBackgammonBoardJson() {
		return backgammonBoardJson;
	}

	public void setBackgammonBoardJson(BackgammonBoardJson backgammonBoardJson) {
		this.backgammonBoardJson = backgammonBoardJson;
	}

	public boolean isWhite() {
		return isWhite;
	}

	public void setWhite(boolean isWhite) {
		this.isWhite = isWhite;
	}

	@Override
	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public BackgammonDice getFirstDice() {
		return firstDice;
	}

	public void setFirstDice(BackgammonDice firstDice) {
		this.firstDice = firstDice;
	}

	public BackgammonDice getSecondDice() {
		return secondDice;
	}

	public void setSecondDice(BackgammonDice secondDice) {
		this.secondDice = secondDice;
	}
}
