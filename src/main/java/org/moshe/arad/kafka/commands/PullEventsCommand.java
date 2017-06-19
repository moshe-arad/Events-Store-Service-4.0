package org.moshe.arad.kafka.commands;

import java.util.Date;
import java.util.UUID;

public abstract class PullEventsCommand implements ICommand {

	private UUID uuid;
	private Date fromDate;
	private boolean isIgnoreDate;
	private String serviceName;
	
	public PullEventsCommand() {
	
	}
	
	public PullEventsCommand(UUID uuid, Date fromDate, boolean isIgnoreDate) {
		super();
		this.uuid = uuid;
		this.fromDate = fromDate;
		this.isIgnoreDate = isIgnoreDate;
	}
	
	public PullEventsCommand(UUID uuid, Date fromDate, boolean isIgnoreDate, String serviceName) {
		super();
		this.uuid = uuid;
		this.fromDate = fromDate;
		this.isIgnoreDate = isIgnoreDate;
		this.serviceName = serviceName;
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

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}	
}
