package org.moshe.arad.initializer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.moshe.arad.kafka.ConsumerToProducerQueue;
import org.moshe.arad.kafka.KafkaUtils;
import org.moshe.arad.kafka.consumers.commands.PullEventsCommandsConsumer;
import org.moshe.arad.kafka.consumers.commands.config.PullEventsCommandConfig;
import org.moshe.arad.kafka.consumers.events.NewUserCreatedEventConsumer;
import org.moshe.arad.kafka.consumers.events.NewUserJoinedLobbyEventsConsumer;
import org.moshe.arad.kafka.consumers.events.config.NewUserCreatedEventConfig;
import org.moshe.arad.kafka.consumers.events.config.NewUserJoinedLobbyEventConfig;
import org.moshe.arad.kafka.events.BackgammonEvent;
import org.moshe.arad.kafka.producers.SimpleBackgammonEventsProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements ApplicationContextAware, AppInitializer {

	@Autowired
	private NewUserCreatedEventConsumer newUserCreatedEventConsumer;
	
	@Autowired
	private NewUserCreatedEventConfig newUserCreatedEventConfig;
	
	@Autowired
	private NewUserJoinedLobbyEventsConsumer newUserJoinedLobbyEventConsumer;
	
	@Autowired
	private NewUserJoinedLobbyEventConfig newUserJoinedLobbyEventConfig;
	
	@Autowired
	private PullEventsCommandsConsumer pullEventsCommandsConsumer;	
	
	@Autowired
	private PullEventsCommandConfig pullEventsCommandConfig;
	
	@Autowired
	private SimpleBackgammonEventsProducer<BackgammonEvent> fromMongoToUsersServiceEventsProducer;
	
	private ExecutorService executor = Executors.newFixedThreadPool(6);
	
	private Logger logger = LoggerFactory.getLogger(AppInit.class);
	
	private ConsumerToProducerQueue pullEventsCommandsConsumerToProducerQueue;
	
	private ApplicationContext context;
	
	public AppInit() {
		pullEventsCommandsConsumerToProducerQueue = context.getBean(ConsumerToProducerQueue.class);
	}
	
	private void initKafkaCommandsConsumers() {
		logger.info("Initializing pull events commands consumer...");
		pullEventsCommandsConsumer.setTopic(KafkaUtils.PULL_EVENTS_COMMAND_TOPIC);
		pullEventsCommandsConsumer.setSimpleConsumerConfig(pullEventsCommandConfig);
		pullEventsCommandsConsumer.initConsumer();
		pullEventsCommandsConsumer.setConsumerToProducerQueue(pullEventsCommandsConsumerToProducerQueue);
		logger.info("Initializing pull events commands consumer...");
	}

	private void initKafkaEventsConsumers() {
		logger.info("Initializing new user created events consumer...");
		newUserCreatedEventConsumer.setTopic(KafkaUtils.NEW_USER_CREATED_EVENT_TOPIC);
		newUserCreatedEventConsumer.setSimpleConsumerConfig(newUserCreatedEventConfig);
		newUserCreatedEventConsumer.initConsumer();
		logger.info("Initialize new user created events consumer, completed...");
		
		logger.info("Initializing new user joined lobby events consumer...");
		newUserJoinedLobbyEventConsumer.setTopic(KafkaUtils.NEW_USER_JOINED_LOBBY_EVENT_TOPIC);
		newUserJoinedLobbyEventConsumer.setSimpleConsumerConfig(newUserJoinedLobbyEventConfig);
		newUserJoinedLobbyEventConsumer.initConsumer();
		logger.info("Initialize new user joined lobby events, completed...");
	}

	private void initKafkaCommandsProducers() {
	
	}

	private void initKafkaEventsProducers() {
		fromMongoToUsersServiceEventsProducer.setTopic(KafkaUtils.FROM_MONGO_TO_USERS_SERVICES);
		fromMongoToUsersServiceEventsProducer.setSimpleProducerConfig();
	}

	@Override
	public void startEngine() {
		logger.info("Events store Service, Engine is about to start...");
		
		initKafkaCommandsConsumers();
		initKafkaEventsConsumers();
		initKafkaCommandsProducers();
		initKafkaEventsProducers();
		
		executor.execute(newUserCreatedEventConsumer);
		executor.execute(newUserJoinedLobbyEventConsumer);
		executor.execute(pullEventsCommandsConsumer);
		logger.info("Events store, Engine started successfuly...");
	}

	@Override
	public void engineShutdown() {
		logger.info("about to do shutdown.");
		newUserCreatedEventConsumer.setRunning(false);
		newUserCreatedEventConsumer.getScheduledExecutor().shutdown();
		
		newUserJoinedLobbyEventConsumer.setRunning(false);
		newUserJoinedLobbyEventConsumer.getScheduledExecutor().shutdown();
		
		pullEventsCommandsConsumer.setRunning(false);
		pullEventsCommandsConsumer.getScheduledExecutor().shutdown();
		
		this.executor.shutdown();
		logger.info("shutdown compeleted.");
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}	
}
