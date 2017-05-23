package org.moshe.arad.kafka.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.GameRoom;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class WatcherRemovedEvent extends BackgammonEvent{

	private String removedWatcher;
	private GameRoom gameRoom;
	
	public WatcherRemovedEvent() {
	
	}

	public WatcherRemovedEvent(String removedWatcher, GameRoom gameRoom) {
		super();
		this.removedWatcher = removedWatcher;
		this.gameRoom = gameRoom;
	}
	
	public WatcherRemovedEvent(UUID uuid, int serviceId, int eventId, Date arrived, String clazz, String removedWatcher,
			GameRoom gameRoom) {
		super(uuid, serviceId, eventId, arrived, clazz);
		this.removedWatcher = removedWatcher;
		this.gameRoom = gameRoom;
	}

	@Override
	public String toString() {
		return "WatcherRemovedEvent [removedWatcher=" + removedWatcher + ", gameRoom=" + gameRoom + "]";
	}

	public String getRemovedWatcher() {
		return removedWatcher;
	}

	public void setRemovedWatcher(String removedWatcher) {
		this.removedWatcher = removedWatcher;
	}

	public GameRoom getGameRoom() {
		return gameRoom;
	}

	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}
}
