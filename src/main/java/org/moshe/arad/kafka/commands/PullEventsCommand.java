package org.moshe.arad.kafka.commands;

import java.util.Date;
import java.util.UUID;

public abstract class PullEventsCommand implements ICommand {

	private UUID uuid;
	private Date fromDate;
	private boolean isIgnoreDate;
	
	public PullEventsCommand() {
	
	}
	
	public PullEventsCommand(UUID uuid, Date fromDate, boolean isIgnoreDate) {
		super();
		this.uuid = uuid;
		this.fromDate = fromDate;
		this.isIgnoreDate = isIgnoreDate;
	}
	
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public boolean isIgnoreDate() {
		return isIgnoreDate;
	}

	public void setIgnoreDate(boolean isIgnoreDate) {
		this.isIgnoreDate = isIgnoreDate;
	}

}
