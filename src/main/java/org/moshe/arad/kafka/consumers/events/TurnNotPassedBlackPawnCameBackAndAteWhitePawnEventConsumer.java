package org.moshe.arad.kafka.consumers.events;

import java.io.IOException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.moshe.arad.kafka.ConsumerToProducerQueue;
import org.moshe.arad.kafka.events.BlackPawnCameBackAndAteWhitePawnEvent;
import org.moshe.arad.kafka.events.LastMoveBlackPawnCameBackAndAteWhitePawnEvent;
import org.moshe.arad.kafka.events.TurnNotPassedBlackPawnCameBackAndAteWhitePawnEvent;
import org.moshe.arad.mongo.MongoEventsStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Scope("prototype")
public class TurnNotPassedBlackPawnCameBackAndAteWhitePawnEventConsumer extends SimpleEventsConsumer {

	@Autowired
	private MongoEventsStore mongoEventsStore;
	
	Logger logger = LoggerFactory.getLogger(TurnNotPassedBlackPawnCameBackAndAteWhitePawnEventConsumer.class);
	
	public TurnNotPassedBlackPawnCameBackAndAteWhitePawnEventConsumer() {
	}

	@Override
	public void consumerOperations(ConsumerRecord<String,String> record) {
    	try{
    		TurnNotPassedBlackPawnCameBackAndAteWhitePawnEvent turnNotPassedBlackPawnCameBackAndAteWhitePawnEvent = convertJsonBlobIntoEvent(record.value());    		
        	logger.info("Event recieved, try to put it in events store...");	                
        	mongoEventsStore.addTurnNotPassedBlackPawnCameBackAndAteWhitePawnEvent(turnNotPassedBlackPawnCameBackAndAteWhitePawnEvent);
        	logger.info("Event saved into events store successfully...");
    	}
		catch (Exception ex) {
			logger.error("Error occured while trying to save event in mongo events store...");
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	private TurnNotPassedBlackPawnCameBackAndAteWhitePawnEvent convertJsonBlobIntoEvent(String JsonBlob){
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(JsonBlob, TurnNotPassedBlackPawnCameBackAndAteWhitePawnEvent.class);
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