package org.moshe.arad.mongo.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.GameRoom;
import org.moshe.arad.entities.backgammon.instrument.BackgammonBoard;
import org.moshe.arad.entities.backgammon.instrument.BackgammonDice;
import org.moshe.arad.kafka.events.BlackPawnCameBackEvent;
import org.moshe.arad.kafka.events.DiceRolledEvent;
import org.moshe.arad.kafka.events.LastMoveBlackPawnCameBackEvent;
import org.moshe.arad.kafka.events.TurnNotPassedBlackPawnCameBackEvent;
import org.moshe.arad.kafka.events.UserMadeInvalidMoveEvent;
import org.moshe.arad.kafka.events.WhitePawnCameBackEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="TurnNotPassedBlackPawnCameBackEvents")
public class TurnNotPassedBlackPawnCameBackMongoEvent implements IMongoEvent {

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
	private BackgammonBoard board;
	private BackgammonDice firstDice;
	private BackgammonDice secondDice;
	private boolean isWhite;

	public TurnNotPassedBlackPawnCameBackMongoEvent() {
		
	}

	public TurnNotPassedBlackPawnCameBackMongoEvent(String mongoEventId, UUID uuid, int serviceId, int eventId, Date arrived,
			String clazz, String userName, String gameRoomName, int from, int to, BackgammonBoard board,
			BackgammonDice firstDice, BackgammonDice secondDice, boolean isWhite) {
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
		this.board = board;
		this.firstDice = firstDice;
		this.secondDice = secondDice;
		this.isWhite = isWhite;
	}

	@Override
	public String toString() {
		return "TurnNotPassedBlackPawnCameBackMongoEvent [mongoEventId=" + mongoEventId + ", uuid=" + uuid
				+ ", serviceId=" + serviceId + ", eventId=" + eventId + ", arrived=" + arrived + ", clazz=" + clazz
				+ ", userName=" + userName + ", gameRoomName=" + gameRoomName + ", from=" + from + ", to=" + to
				+ ", board=" + board + ", firstDice=" + firstDice + ", secondDice=" + secondDice + ", isWhite="
				+ isWhite + "]";
	}

	public static TurnNotPassedBlackPawnCameBackMongoEvent convertIntoMongoEvent(TurnNotPassedBlackPawnCameBackEvent event) {
		TurnNotPassedBlackPawnCameBackMongoEvent turnNotPassedBlackPawnCameBackMongoEvent = new TurnNotPassedBlackPawnCameBackMongoEvent();
		
		turnNotPassedBlackPawnCameBackMongoEvent.setUuid(event.getUuid());
		turnNotPassedBlackPawnCameBackMongoEvent.setArrived(event.getArrived());
		turnNotPassedBlackPawnCameBackMongoEvent.setClazz(event.getClazz());
		turnNotPassedBlackPawnCameBackMongoEvent.setUserName(event.getUserName());
		turnNotPassedBlackPawnCameBackMongoEvent.setGameRoomName(event.getGameRoomName());
		turnNotPassedBlackPawnCameBackMongoEvent.setFrom(event.getFrom());
		turnNotPassedBlackPawnCameBackMongoEvent.setTo(event.getTo());
		turnNotPassedBlackPawnCameBackMongoEvent.setBoard(event.getBoard());
		turnNotPassedBlackPawnCameBackMongoEvent.setFirstDice(event.getFirstDice());
		turnNotPassedBlackPawnCameBackMongoEvent.setSecondDice(event.getSecondDice());
		turnNotPassedBlackPawnCameBackMongoEvent.setWhite(event.isWhite());
		
		return turnNotPassedBlackPawnCameBackMongoEvent;
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

	public BackgammonBoard getBoard() {
		return board;
	}

	public void setBoard(BackgammonBoard board) {
		this.board = board;
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
