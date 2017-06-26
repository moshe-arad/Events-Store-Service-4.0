package org.moshe.arad.mongo.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.backgammon.instrument.BackgammonBoard;
import org.moshe.arad.entities.backgammon.instrument.BackgammonDice;
import org.moshe.arad.entities.backgammon.json.BackgammonBoardJson;
import org.moshe.arad.kafka.events.LastMoveWhitePawnCameBackAndAteBlackPawnEvent;
import org.moshe.arad.kafka.events.WhitePawnCameBackAndAteBlackPawnEvent;
import org.moshe.arad.kafka.events.WhitePawnCameBackEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="LastMoveWhitePawnCameBackAndAteBlackPawnEvents")
public class LastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent implements IMongoEvent {

	@Id
	private String mongoEventId;
	private UUID uuid;
	private int serviceId;
	private int eventId;
	private Date arrived;
	private String clazz;
	private String userName;
	private String gameRoomName;
	private int from;
	private int to;
	private BackgammonBoardJson backgammonBoardJson;
	private BackgammonDice firstDice;
	private BackgammonDice secondDice;
	private boolean isWhite;

	public LastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent() {
		
	}

	public LastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent(String mongoEventId, UUID uuid, int serviceId, int eventId, Date arrived,
			String clazz, String userName, String gameRoomName, int from, int to,
			BackgammonBoardJson backgammonBoardJson, BackgammonDice firstDice, BackgammonDice secondDice,
			boolean isWhite) {
		super();
		this.mongoEventId = mongoEventId;
		this.uuid = uuid;
		this.serviceId = serviceId;
		this.eventId = eventId;
		this.arrived = arrived;
		this.clazz = clazz;
		this.userName = userName;
		this.gameRoomName = gameRoomName;
		this.from = from;
		this.to = to;
		this.backgammonBoardJson = backgammonBoardJson;
		this.firstDice = firstDice;
		this.secondDice = secondDice;
		this.isWhite = isWhite;
	}

	@Override
	public String toString() {
		return "LastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent [mongoEventId=" + mongoEventId + ", uuid=" + uuid
				+ ", serviceId=" + serviceId + ", eventId=" + eventId + ", arrived=" + arrived + ", clazz=" + clazz
				+ ", userName=" + userName + ", gameRoomName=" + gameRoomName + ", from=" + from + ", to=" + to
				+ ", backgammonBoardJson=" + backgammonBoardJson + ", firstDice=" + firstDice + ", secondDice="
				+ secondDice + ", isWhite=" + isWhite + "]";
	}

	public static LastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent convertIntoMongoEvent(LastMoveWhitePawnCameBackAndAteBlackPawnEvent event) {
		LastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent lastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent = new LastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent();
		
		lastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent.setUuid(event.getUuid());
		lastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent.setArrived(event.getArrived());
		lastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent.setClazz(event.getClazz());
		lastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent.setUserName(event.getUserName());
		lastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent.setGameRoomName(event.getGameRoomName());
		lastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent.setFrom(event.getFrom());
		lastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent.setTo(event.getTo());
		lastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent.setBackgammonBoardJson(event.getBackgammonBoardJson());
		lastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent.setFirstDice(event.getFirstDice());
		lastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent.setSecondDice(event.getSecondDice());
		lastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent.setWhite(event.isWhite());
		
		return lastMoveWhitePawnCameBackAndAteBlackPawnMongoEvent;
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

	public String getGameRoomName() {
		return gameRoomName;
	}

	public void setGameRoomName(String gameRoomName) {
		this.gameRoomName = gameRoomName;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
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
