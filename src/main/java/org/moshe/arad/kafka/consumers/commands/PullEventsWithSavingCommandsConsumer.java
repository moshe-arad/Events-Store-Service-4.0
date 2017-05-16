package org.moshe.arad.kafka.consumers.commands;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.moshe.arad.kafka.ConsumerToProducerQueue;
import org.moshe.arad.kafka.KafkaUtils;
import org.moshe.arad.kafka.Services;
import org.moshe.arad.kafka.commands.PullEventsCommand;
import org.moshe.arad.kafka.commands.PullEventsWithSavingCommand;
import org.moshe.arad.kafka.commands.PullEventsWithoutSavingCommand;
import org.moshe.arad.kafka.consumers.ISimpleConsumer;
import org.moshe.arad.kafka.events.BackgammonEvent;
import org.moshe.arad.mongo.MongoEventsStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Scope("prototype")
public class PullEventsWithSavingCommandsConsumer extends PullEventsCommandsConsumer {

	@Override
	public PullEventsWithSavingCommand getCommandFromKafkaRecord(ConsumerRecord<String, String> record) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			PullEventsWithSavingCommand pullEventsWithSavingCommand = (PullEventsWithSavingCommand) objectMapper.readValue(record.value(), PullEventsWithSavingCommand.class);
			setServiceName(record, pullEventsWithSavingCommand); 
			return pullEventsWithSavingCommand;
		} catch (IOException e) {
			logger.error("Failed to convert json into PullEventsWithSavingCommand object...");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}


	private void setServiceName(ConsumerRecord<String, String> record, PullEventsWithSavingCommand pullEventsWithSavingCommand) {
		if(record.topic().equals(KafkaUtils.LOBBY_SERVICE_PULL_EVENTS_WITH_SAVING_COMMAND_TOPIC) || record.topic().equals(KafkaUtils.LOBBY_SERVICE_PULL_EVENTS_WITH_SAVING_COMMAND_TOPIC)) pullEventsWithSavingCommand.setServiceName(Services.Lobby.name());
		else if(record.topic().equals(KafkaUtils.PULL_EVENTS_WITH_SAVING_COMMAND_TOPIC) || record.topic().equals(KafkaUtils.PULL_EVENTS_WITHOUT_SAVING_COMMAND_TOPIC)) pullEventsWithSavingCommand.setServiceName(Services.Users.name());
	}
}




	