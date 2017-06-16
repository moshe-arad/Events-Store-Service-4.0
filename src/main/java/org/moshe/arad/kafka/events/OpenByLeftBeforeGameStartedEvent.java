package org.moshe.arad.kafka.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.GameRoom;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class OpenByLeftBeforeGameStartedEvent extends BackgammonEvent {

	private String leavingUserName;
	private GameRoom gameRoom;

	public OpenByLeftBeforeGameStartedEvent() {
	}

	public OpenByLeftBeforeGameStartedEvent(String leavingUserName, GameRoom gameRoom) {
		super();
		this.leavingUserName = leavingUserName;
		this.gameRoom = gameRoom;
	}

	public OpenByLeftBeforeGameStartedEvent(UUID uuid, int serviceId, int eventId, Date arrived, String clazz,
			String leavingUserName, GameRoom gameRoom) {
		super(uuid, serviceId, eventId, arrived, clazz);
		this.leavingUserName = leavingUserName;
		this.gameRoom = gameRoom;
	}

	@Override
	public String toString() {
		return "OpenByLeftBeforeGameStartedEvent [leavingUserName=" + leavingUserName + ", gameRoom=" + gameRoom + "]";
	}

	public String getLeavingUserName() {
		return leavingUserName;
	}

	public void setLeavingUserName(String leavingUserName) {
		this.leavingUserName = leavingUserName;
	}

	public GameRoom getGameRoom() {
		return gameRoom;
	}

	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}
}
