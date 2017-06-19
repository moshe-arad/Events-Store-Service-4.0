package org.moshe.arad.kafka.events;

import java.util.Date;
import java.util.UUID;

public class EndReadEventsFromMongoEvent extends BackgammonEvent {

	private int totalNumOfEvents;
	
	public EndReadEventsFromMongoEvent(UUID uuid, int serviceId, int eventId, Date arrived, String clazz,
			int totalNumOfEvents) {
		super(uuid, serviceId, eventId, arrived, clazz);
		this.totalNumOfEvents = totalNumOfEvents;
	}

	public EndReadEventsFromMongoEvent(UUID uuid, int serviceId, int eventId, Date arrived, String clazz) {
		super(uuid, serviceId, eventId, arrived, clazz);
	}

	@Override
	public String toString() {
		return "EndReadEventsFromMongoEvent [totalNumOfEvents=" + totalNumOfEvents + "]";
	}

	public int getTotalNumOfEvents() {
		return totalNumOfEvents;
	}

	public void setTotalNumOfEvents(int totalNumOfEvents) {
		this.totalNumOfEvents = totalNumOfEvents;
	}
}
