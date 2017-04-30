package org.moshe.arad.kafka.consumers.commands;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.moshe.arad.kafka.ConsumerToProducerQueue;
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
public class PullEventsWithoutSavingCommandsConsumer extends PullEventsCommandsConsumer {
	
	@Override
	public PullEventsWithoutSavingCommand getCommandFromKafkaRecord(ConsumerRecord<String, String> record) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return (PullEventsWithoutSavingCommand) objectMapper.readValue(record.value(), PullEventsWithoutSavingCommand.class);
		} catch (IOException e) {
			logger.error("Failed to convert json into PullEventsWithoutSavingCommand object...");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
}




	