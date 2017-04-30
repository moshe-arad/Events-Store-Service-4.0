package org.moshe.arad.kafka.commands;

import java.util.Date;
import java.util.UUID;

public class PullEventsWithSavingCommand extends PullEventsCommand {

	public PullEventsWithSavingCommand() {
	
	}

	public PullEventsWithSavingCommand(UUID uuid, Date fromDate, boolean isIgnoreDate) {
		super(uuid, fromDate, isIgnoreDate);
	}

	@Override
	public String toString() {
		return "PullEventsWithSavingCommand []";
	}
}
