package org.moshe.arad.mongo.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.BackgammonDice;
import org.moshe.arad.entities.BackgammonUser;
import org.moshe.arad.entities.GameRoom;
import org.moshe.arad.kafka.events.DiceRolledEvent;
import org.moshe.arad.kafka.events.ExistingUserJoinedLobbyEvent;
import org.moshe.arad.kafka.events.InitDiceCompletedEvent;
import org.moshe.arad.kafka.events.InitGameRoomCompletedEvent;
import org.moshe.arad.kafka.events.LoggedInEvent;
import org.moshe.arad.kafka.events.LoggedOutEvent;
import org.moshe.arad.kafka.events.LoggedOutOpenByLeftEvent;
import org.moshe.arad.kafka.events.LoggedOutOpenByLeftLastEvent;
import org.moshe.arad.kafka.events.NewUserCreatedEvent;
import org.moshe.arad.kafka.events.NewUserJoinedLobbyEvent;
import org.moshe.arad.kafka.events.OpenByLeftLastEvent;
import org.moshe.arad.kafka.events.RollDiceGameRoomFoundEvent;
import org.moshe.arad.kafka.events.UserPermissionsUpdatedEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="DiceRolledEvents")
public class DiceRolledMongoEvent implements IMongoEvent {

	@Id
	private String mongoEventId;
	private UUID uuid;
	private int serviceId;
	private int eventId;
	private Date arrived;
	private String clazz;
	private String username;
	private GameRoom gameRoom;
	private BackgammonDice firstDice;
	private BackgammonDice secondDice;

	public DiceRolledMongoEvent() {
		
	}
	
	public DiceRolledMongoEvent(String mongoEventId, UUID uuid, int serviceId, int eventId, Date arrived, String clazz,
			String username, GameRoom gameRoom, BackgammonDice firstDice, BackgammonDice secondDice) {
		super();
		this.mongoEventId = mongoEventId;
		this.uuid = uuid;
		this.serviceId = serviceId;
		this.eventId = eventId;
		this.arrived = arrived;
		this.clazz = clazz;
		this.username = username;
		this.gameRoom = gameRoom;
		this.firstDice = firstDice;
		this.secondDice = secondDice;
	}

	@Override
	public String toString() {
		return "DiceRolledMongoEvent [mongoEventId=" + mongoEventId + ", uuid=" + uuid + ", serviceId=" + serviceId
				+ ", eventId=" + eventId + ", arrived=" + arrived + ", clazz=" + clazz + ", username=" + username
				+ ", gameRoom=" + gameRoom + ", firstDice=" + firstDice + ", secondDice=" + secondDice + "]";
	}

	public static DiceRolledMongoEvent convertIntoMongoEvent(DiceRolledEvent event) {
		DiceRolledMongoEvent diceRolledMongoEvent = new DiceRolledMongoEvent();
		
		diceRolledMongoEvent.setUuid(event.getUuid());
		diceRolledMongoEvent.setArrived(event.getArrived());
		diceRolledMongoEvent.setClazz(event.getClazz());
		diceRolledMongoEvent.setGameRoom(event.getGameRoom());
		diceRolledMongoEvent.setUsername(event.getUsername());
		diceRolledMongoEvent.setFirstDice(event.getFirstDice());
		diceRolledMongoEvent.setSecondDice(event.getSecondDice());
		
		return diceRolledMongoEvent;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public GameRoom getGameRoom() {
		return gameRoom;
	}

	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
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
