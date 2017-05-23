package org.moshe.arad.kafka.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.GameRoom;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class GameRoomClosedEvent extends BackgammonEvent{

	private String closedByUserName;
	private GameRoom gameRoom;
	
	public GameRoomClosedEvent() {
	
	}

	public GameRoomClosedEvent(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}

	public GameRoomClosedEvent(String closedByUserName, GameRoom gameRoom) {
		super();
		this.closedByUserName = closedByUserName;
		this.gameRoom = gameRoom;
	}

	public GameRoomClosedEvent(UUID uuid, int serviceId, int eventId, Date arrived, String clazz,
			String closedByUserName, GameRoom gameRoom) {
		super(uuid, serviceId, eventId, arrived, clazz);
		this.closedByUserName = closedByUserName;
		this.gameRoom = gameRoom;
	}

	@Override
	public String toString() {
		return "GameRoomClosedEvent [closedByUserName=" + closedByUserName + ", gameRoom=" + gameRoom + "]";
	}

	public String getClosedByUserName() {
		return closedByUserName;
	}

	public void setClosedByUserName(String closedByUserName) {
		this.closedByUserName = closedByUserName;
	}

	public GameRoom getGameRoom() {
		return gameRoom;
	}

	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}
}
