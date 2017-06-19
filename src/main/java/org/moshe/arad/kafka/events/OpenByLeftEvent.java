package org.moshe.arad.kafka.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.GameRoom;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class OpenByLeftEvent extends BackgammonEvent {

	private String openBy;
	private GameRoom gameRoom;
	
	public OpenByLeftEvent() {
	
	}

	public OpenByLeftEvent(String openBy, GameRoom gameRoom) {
		super();
		this.openBy = openBy;
		this.gameRoom = gameRoom;
	}

	public OpenByLeftEvent(UUID uuid, int serviceId, int eventId, Date arrived, String clazz, String openBy,
			GameRoom gameRoom) {
		super(uuid, serviceId, eventId, arrived, clazz);
		this.openBy = openBy;
		this.gameRoom = gameRoom;
	}

	@Override
	public String toString() {
		return "OpenByLeftEvent [openBy=" + openBy + ", gameRoom=" + gameRoom + "]";
	}

	public String getOpenBy() {
		return openBy;
	}

	public void setOpenBy(String openBy) {
		this.openBy = openBy;
	}

	public GameRoom getGameRoom() {
		return gameRoom;
	}

	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}
}
