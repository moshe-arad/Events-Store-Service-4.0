package org.moshe.arad.kafka.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.GameRoom;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class LoggedOutOpenByLeftBeforeGameStartedEvent extends BackgammonEvent {

	private String loggedOutUserName;
	private GameRoom gameRoom;

	public LoggedOutOpenByLeftBeforeGameStartedEvent() {
	}

	public LoggedOutOpenByLeftBeforeGameStartedEvent(UUID uuid, int serviceId, int eventId, Date arrived, String clazz,
			String loggedOutUserName, GameRoom gameRoom) {
		super(uuid, serviceId, eventId, arrived, clazz);
		this.loggedOutUserName = loggedOutUserName;
		this.gameRoom = gameRoom;
	}
	
	@Override
	public String toString() {
		return "LoggedOutOpenByLeftBeforeGameStartedEvent [loggedOutUserName=" + loggedOutUserName + ", gameRoom="
				+ gameRoom + "]";
	}

	public String getLoggedOutUserName() {
		return loggedOutUserName;
	}

	public void setLoggedOutUserName(String loggedOutUserName) {
		this.loggedOutUserName = loggedOutUserName;
	}

	public GameRoom getGameRoom() {
		return gameRoom;
	}

	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}
}
