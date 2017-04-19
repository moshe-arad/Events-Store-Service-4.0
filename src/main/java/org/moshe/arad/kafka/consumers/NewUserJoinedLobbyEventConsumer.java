package org.moshe.arad.kafka.consumers;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.moshe.arad.entities.BackgammonUser;
import org.moshe.arad.kafka.KafkaUtils;
import org.moshe.arad.kafka.events.NewUserJoinedLobbyEvent;
import org.moshe.arad.mongo.MongoEventsStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewUserJoinedLobbyEventConsumer  implements Runnable {
	
	@Autowired
	private MongoEventsStore mongoEventsStore;
	
	Logger logger = LoggerFactory.getLogger(NewUserCreatedEventConsumer.class);
	private static final int CONSUMERS_NUM = 3;
	private Properties properties;
	
	private Consumer<String, NewUserJoinedLobbyEvent> consumer;
	private boolean isRunning = true;
	private ScheduledThreadPoolExecutor scheduledExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(6);
	
	public NewUserJoinedLobbyEventConsumer() {
		properties = new Properties();
		properties.put("bootstrap.servers", KafkaUtils.SERVERS);
		properties.put("group.id", "NewUserJoinedLobbyEventConsumer");
		properties.put("key.deserializer", KafkaUtils.KEY_STRING_DESERIALIZER);
		properties.put("value.deserializer", KafkaUtils.NEW_USER_JOINED_LOBBY_EVENT_DESERIALIZER);
		consumer = new KafkaConsumer<>(properties);
	}

	private void executeConsumers(int numConsumers, String topicName){
		
		while(scheduledExecutor.getQueue().size() < numConsumers){
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(scheduledExecutor.getActiveCount() == numConsumers) continue;
			
//			logger.info("Threads in pool's queue before schedule = " + scheduledExecutor.getQueue().size());
			scheduledExecutor.scheduleAtFixedRate( () -> {
				consumer.subscribe(Arrays.asList(topicName));
	    		
	    		while (isRunning){
	                ConsumerRecords<String, NewUserJoinedLobbyEvent> records = consumer.poll(100);
	                for (ConsumerRecord<String, NewUserJoinedLobbyEvent> record : records){
	                	logger.info("New User Joined Lobby Event record recieved, " + record.value());	             
	                	logger.info("Event recieved, try to put it in events store...");	                
	                	NewUserJoinedLobbyEvent newUserJoinedLobbyEvent = (NewUserJoinedLobbyEvent)record.value();
	                	mongoEventsStore.addNewEvent(newUserJoinedLobbyEvent);
	                	logger.info("Event saved into events store successfully...");	                	
	                }	              	             
	    		}
		        consumer.close();
		        
			} , 0, 100, TimeUnit.MILLISECONDS);
//			logger.info("Threads in pool's queue after schedule = " + scheduledExecutor.getQueue().size());
		}
	}
	
	@Override
	public void run() {
		this.executeConsumers(CONSUMERS_NUM, KafkaUtils.NEW_USER_JOINED_LOBBY_EVENT_TOPIC);
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public ScheduledThreadPoolExecutor getScheduledExecutor() {
		return scheduledExecutor;
	}	
}
