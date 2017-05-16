package org.moshe.arad.kafka.consumers.commands;

import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.moshe.arad.kafka.ConsumerToProducerQueue;
import org.moshe.arad.kafka.commands.PullEventsCommand;
import org.moshe.arad.kafka.commands.PullEventsWithoutSavingCommand;
import org.moshe.arad.kafka.consumers.ISimpleConsumer;
import org.moshe.arad.kafka.events.BackgammonEvent;
import org.moshe.arad.mongo.MongoEventsStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class PullEventsCommandsConsumer extends SimpleCommandsConsumer implements ISimpleConsumer {

	@Autowired
	private MongoEventsStore mongoEventsStore;
	
	private ConsumerToProducerQueue consumerToProducerQueue;
	
	Logger logger = LoggerFactory.getLogger(PullEventsWithoutSavingCommandsConsumer.class);
	
	public PullEventsCommandsConsumer() {
	}

	public abstract PullEventsCommand getCommandFromKafkaRecord(ConsumerRecord<String,String> record);
	
	@Override
	public void consumerOperations(ConsumerRecord<String,String> record) {
    	try{
    		logger.info("Trying to convert from String JSON to Pull Events Command object, JSON blob = " + record.value());
    		ObjectMapper objectMapper = new ObjectMapper();
    		PullEventsCommand pullEventsCommand = getCommandFromKafkaRecord(record);
    		logger.info("Convert from String JSON to Pull Events Command object, Successfuly completed = " + pullEventsCommand);
    			             
        	logger.info("Trying to extract from date field, and query mongo DB about events occured after that date...");
        	Date fromDate = pullEventsCommand.getFromDate();
        	boolean isIgnoreDate = pullEventsCommand.isIgnoreDate();
        	logger.info("Date extracted successfully, Date = " + fromDate.toString());
        	
        	logger.info("Trying to extract UUID, from recieved command...");
        	UUID uuid = pullEventsCommand.getUuid();
        	logger.info("UUID extracted successfully, UUID = " + uuid.toString());
        	
        	logger.info("Initiating request to mongo events store in order to get existing events in events store which occured from date = " + fromDate.toString());
    		
        	LinkedList<BackgammonEvent> eventsFromMongoToProducer;
        	if(isIgnoreDate == true) eventsFromMongoToProducer = new LinkedList<>(mongoEventsStore.getEventsOccuredFrom(uuid, null, pullEventsCommand.getServiceName()));
        	else eventsFromMongoToProducer = new LinkedList<>(mongoEventsStore.getEventsOccuredFrom(uuid, fromDate, pullEventsCommand.getServiceName()));
        	logger.info("Events were extracted from mongo DB successfuly...");
        	
        	logger.info("Start passing events to producer...");
        	ListIterator<BackgammonEvent> it = eventsFromMongoToProducer.listIterator();
        	while(it.hasNext()){
        		BackgammonEvent event = it.next();
        		consumerToProducerQueue.getEventsQueue().put(event);
        		logger.info("Event passed to producer, event = " + event);
        	}
        	logger.info("All events passed to producer...");        	
    	}
		catch (Exception ex) {
			logger.error("Error occured while trying to save event in mongo events store...");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}

	public ConsumerToProducerQueue getConsumerToProducerQueue() {
		return consumerToProducerQueue;
	}

	public void setConsumerToProducerQueue(ConsumerToProducerQueue consumerToProducerQueue) {
		this.consumerToProducerQueue = consumerToProducerQueue;
	}
}
