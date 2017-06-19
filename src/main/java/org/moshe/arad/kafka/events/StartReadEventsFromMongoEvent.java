package org.moshe.arad.kafka.events;

import java.util.Date;
import java.util.UUID;

public class StartReadEventsFromMongoEvent extends BackgammonEvent {

	private int totalNumOfEvents;

	public StartReadEventsFromMongoEvent(UUID uuid, int serviceId, int eventId, Date arrived, String clazz,
			int totalNumOfEvents) {
		super(uuid, serviceId, eventId, arrived, clazz);
		this.totalNumOfEvents = totalNumOfEvents;
	}
	
	@Override
	public String toString() {
		return "StartReadEventsFromMongoEvent [totalNumOfEvents=" + totalNumOfEvents + "]";
	}

	public int getTotalNumOfEvents() {
		return totalNumOfEvents;
	}

	public void setTotalNumOfEvents(int totalNumOfEvents) {
		this.totalNumOfEvents = totalNumOfEvents;
	}
}
