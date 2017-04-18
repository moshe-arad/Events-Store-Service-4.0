package org.moshe.arad.kafka.events;

import java.util.Date;

import org.moshe.arad.entities.BackgammonUser;

public class NewUserJoinedLobbyEvent extends BackgammonEvent {

	private BackgammonUser backgammonUser;
	
	public NewUserJoinedLobbyEvent() {
	}

	public NewUserJoinedLobbyEvent(int serviceId, String serviceName, int entityId, String entityType, int eventId,
			String eventType, BackgammonUser backgammonUser) {
		super(serviceId, serviceName, entityId, entityType, eventId, eventType);
		this.backgammonUser = backgammonUser;
	}

	public NewUserJoinedLobbyEvent(int serviceId, String serviceName, int entityId, String entityType, int eventId,
			String eventType, BackgammonUser backgammonUser, Date arrived) {
		super(serviceId, serviceName, entityId, entityType, eventId, eventType, arrived);
		this.backgammonUser = backgammonUser;
	}

	public BackgammonUser getBackgammonUser() {
		return backgammonUser;
	}

	public void setBackgammonUser(BackgammonUser backgammonUser) {
		this.backgammonUser = backgammonUser;
	}	
}
