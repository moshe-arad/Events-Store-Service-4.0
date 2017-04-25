package org.moshe.arad.kafka.consumers.commands;

import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.moshe.arad.kafka.ConsumerToProducerQueue;
import org.moshe.arad.kafka.commands.PullEventsCommand;
import org.moshe.arad.kafka.consumers.SimpleConsumerConfig;
import org.moshe.arad.kafka.events.BackgammonEvent;
import org.moshe.arad.mongo.MongoEventsStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PullEventsCommandsConsumerNonJson extends SimpleBackgammonCommandsConsumer<PullEventsCommand> {
	
	@Autowired
	private MongoEventsStore mongoEventsStore;
	
	private ConsumerToProducerQueue consumerToProducerQueue;
	
	Logger logger = LoggerFactory.getLogger(PullEventsCommandsConsumerNonJson.class);
	
	public PullEventsCommandsConsumerNonJson() {
	}
	
	public PullEventsCommandsConsumerNonJson(SimpleConsumerConfig simpleConsumerConfig, String topic) {
		super(simpleConsumerConfig, topic);
	}

	@Override
	public void consumerOperations(ConsumerRecord<String,PullEventsCommand> record) {
//    	try{
//    		logger.info("Pull Events Command record recieved, " + record.value());	             
//        	logger.info("Trying to extract from date field, and query mongo DB about events occured after that date...");
//        	Date fromDate = record.value().getFromDate();
//        	logger.info("Date extracted successfully, Date = " + fromDate.toString());
//        	
//        	//TODO need to move logic from consumers, on consumers we'll have only simple code related to recieved record.
//        	
//        	logger.info("Trying to extract UUID, from recieved record...");
//        	UUID uuid = record.value().getUuid();
//        	logger.info("UUID extracted successfully, UUID = " + uuid.toString());
//        	
//        	logger.info("Initiating request to mongo events store in order to get existing events in events store which occured from date = " + fromDate.toString());
//        	//TODO consumer always be responsible to pass data from himself to producer
//        	LinkedList<BackgammonEvent> eventsFromMongoToProducer = new LinkedList<>(mongoEventsStore.getEventsOccuredFrom(fromDate));
//        	logger.info("Events were extracted from mongo DB successfuly...");
//        	
//        	logger.info("Start passing events to producer...");
//        	ListIterator<BackgammonEvent> it = eventsFromMongoToProducer.listIterator();
//        	while(it.hasNext()){
//        		BackgammonEvent event = it.next();
//        		consumerToProducerQueue.getEventsQueue().put(event);
//        		logger.info("Event passed to producer, event = " + event);
//        	}
//        	logger.info("All events passed to producer...");
//    	}
//		catch (Exception ex) {
//			logger.error("Error occured while trying to save event in mongo events store...");
//			logger.error(ex.getMessage());
//			ex.printStackTrace();
//		}
	}

	public ConsumerToProducerQueue getConsumerToProducerQueue() {
		return consumerToProducerQueue;
	}

	public void setConsumerToProducerQueue(ConsumerToProducerQueue consumerToProducerQueue) {
		this.consumerToProducerQueue = consumerToProducerQueue;
	}
}




	