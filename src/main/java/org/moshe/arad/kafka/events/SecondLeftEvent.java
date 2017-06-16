package org.moshe.arad.kafka.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.GameRoom;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SecondLeftEvent extends BackgammonEvent {

	private String second;
	private GameRoom gameRoom;
	
	public SecondLeftEvent() {
	
	}

	public SecondLeftEvent(String second, GameRoom gameRoom) {
		super();
		this.second = second;
		this.gameRoom = gameRoom;
	}

	public SecondLeftEvent(UUID uuid, int serviceId, int eventId, Date arrived, String clazz, String second,
			GameRoom gameRoom) {
		super(uuid, serviceId, eventId, arrived, clazz);
		this.second = second;
		this.gameRoom = gameRoom;
	}

	@Override
	public String toString() {
		return "SecondLeftEvent [second=" + second + ", gameRoom=" + gameRoom + "]";
	}

	public GameRoom getGameRoom() {
		return gameRoom;
	}

	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}
}
