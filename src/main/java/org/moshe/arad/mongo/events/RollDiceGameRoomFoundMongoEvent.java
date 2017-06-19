package org.moshe.arad.mongo.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.BackgammonUser;
import org.moshe.arad.entities.GameRoom;
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

@Document(collection="RollDiceGameRoomFoundEvents")
public class RollDiceGameRoomFoundMongoEvent implements IMongoEvent {

	@Id
	private String mongoEventId;
	private UUID uuid;
	private int serviceId;
	private int eventId;
	private Date arrived;
	private String clazz;
	private String username;
	private GameRoom gameRoom;

	public RollDiceGameRoomFoundMongoEvent() {
		
	}

	public RollDiceGameRoomFoundMongoEvent(String mongoEventId, UUID uuid, int serviceId, int eventId, Date arrived,
			String clazz, String username, GameRoom gameRoom) {
		super();
		this.mongoEventId = mongoEventId;
		this.uuid = uuid;
		this.serviceId = serviceId;
		this.eventId = eventId;
		this.arrived = arrived;
		this.clazz = clazz;
		this.username = username;
		this.gameRoom = gameRoom;
	}

	@Override
	public String toString() {
		return "RollDiceGameRoomFoundMongoEvent [mongoEventId=" + mongoEventId + ", uuid=" + uuid + ", serviceId="
				+ serviceId + ", eventId=" + eventId + ", arrived=" + arrived + ", clazz=" + clazz + ", username="
				+ username + ", gameRoom=" + gameRoom + "]";
	}

	public static RollDiceGameRoomFoundMongoEvent convertIntoMongoEvent(RollDiceGameRoomFoundEvent event) {
		RollDiceGameRoomFoundMongoEvent rollDiceGameRoomFoundMongoEvent = new RollDiceGameRoomFoundMongoEvent();
		
		rollDiceGameRoomFoundMongoEvent.setUuid(event.getUuid());
		rollDiceGameRoomFoundMongoEvent.setArrived(event.getArrived());
		rollDiceGameRoomFoundMongoEvent.setClazz(event.getClazz());
		rollDiceGameRoomFoundMongoEvent.setGameRoom(event.getGameRoom());
		rollDiceGameRoomFoundMongoEvent.setUsername(event.getUsername());
		
		return rollDiceGameRoomFoundMongoEvent;
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
}
