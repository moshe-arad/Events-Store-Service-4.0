package org.moshe.arad.kafka.commands;

import java.util.Date;
import java.util.UUID;

public class PullEventsWithoutSavingCommand extends PullEventsCommand {

	public PullEventsWithoutSavingCommand() {
	}
	
	public PullEventsWithoutSavingCommand(UUID uuid, Date fromDate, boolean isIgnoreDate) {
		super(uuid, fromDate, isIgnoreDate);
	}

	@Override
	public String toString() {
		return "PullEventsWithoutSavingCommand []";
	}

}
