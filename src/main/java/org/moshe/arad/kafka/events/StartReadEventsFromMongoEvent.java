package org.moshe.arad.kafka.events;

import java.util.Date;
import java.util.UUID;

public class StartReadEventsFromMongoEvent extends BackgammonEvent {

	private int totalNumOfEvents;
	private boolean isToSaveEvents;

	public StartReadEventsFromMongoEvent(UUID uuid, int serviceId, int eventId, Date arrived, String clazz,
			int totalNumOfEvents, boolean isToSaveEvents) {
		super(uuid, serviceId, eventId, arrived, clazz);
		this.totalNumOfEvents = totalNumOfEvents;
		this.isToSaveEvents = isToSaveEvents;
	}

	@Override
	public String toString() {
		return "StartReadEventsFromMongoEvent [totalNumOfEvents=" + totalNumOfEvents + ", isToSaveEvents="
				+ isToSaveEvents + "]";
	}
	
	public int getTotalNumOfEvents() {
		return totalNumOfEvents;
	}

	public void setTotalNumOfEvents(int totalNumOfEvents) {
		this.totalNumOfEvents = totalNumOfEvents;
	}

	public boolean isToSaveEvents() {
		return isToSaveEvents;
	}

	public void setToSaveEvents(boolean isToSaveEvents) {
		this.isToSaveEvents = isToSaveEvents;
	}
}
