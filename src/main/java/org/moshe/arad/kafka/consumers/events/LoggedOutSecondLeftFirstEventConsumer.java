package org.moshe.arad.kafka.consumers.events;

import java.io.IOException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.moshe.arad.kafka.ConsumerToProducerQueue;
import org.moshe.arad.kafka.events.LoggedInEvent;
import org.moshe.arad.kafka.events.LoggedOutEvent;
import org.moshe.arad.kafka.events.LoggedOutOpenByLeftEvent;
import org.moshe.arad.kafka.events.LoggedOutOpenByLeftFirstEvent;
import org.moshe.arad.kafka.events.LoggedOutSecondLeftFirstEvent;
import org.moshe.arad.kafka.events.NewUserCreatedEvent;
import org.moshe.arad.mongo.MongoEventsStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Scope("prototype")
public class LoggedOutSecondLeftFirstEventConsumer extends SimpleEventsConsumer {

	@Autowired
	private MongoEventsStore mongoEventsStore;
	
	Logger logger = LoggerFactory.getLogger(LoggedOutSecondLeftFirstEventConsumer.class);
	
	public LoggedOutSecondLeftFirstEventConsumer() {
	}

	@Override
	public void consumerOperations(ConsumerRecord<String,String> record) {
    	try{
    		LoggedOutSecondLeftFirstEvent loggedOutSecondLeftFirstEvent = convertJsonBlobIntoEvent(record.value());    		
        	logger.info("Event recieved, try to put it in events store...");	                
        	mongoEventsStore.addLoggedOutSecondLeftFirstEvent(loggedOutSecondLeftFirstEvent);
        	logger.info("Event saved into events store successfully...");
    	}
		catch (Exception ex) {
			logger.error("Error occured while trying to save event in mongo events store...");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	private LoggedOutSecondLeftFirstEvent convertJsonBlobIntoEvent(String JsonBlob){
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(JsonBlob, LoggedOutSecondLeftFirstEvent.class);
		} catch (IOException e) {
			logger.error("Falied to convert Json blob into Event...");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setConsumerToProducerQueue(ConsumerToProducerQueue consumerToProducerQueue) {
		// TODO Auto-generated method stub
		
	}

}
