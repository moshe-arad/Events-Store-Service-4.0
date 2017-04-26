package org.moshe.arad.initializer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.moshe.arad.kafka.ConsumerToProducerQueue;
import org.moshe.arad.kafka.KafkaUtils;
import org.moshe.arad.kafka.consumers.ISimpleConsumer;
import org.moshe.arad.kafka.consumers.commands.PullEventsCommandsConsumer;
import org.moshe.arad.kafka.consumers.config.PullEventsCommandConfig;
import org.moshe.arad.kafka.consumers.config.SimpleConsumerConfig;
import org.moshe.arad.kafka.consumers.events.NewUserCreatedEventConsumer;
import org.moshe.arad.kafka.consumers.events.NewUserJoinedLobbyEventsConsumer;
import org.moshe.arad.kafka.consumers.events.config.NewUserCreatedEventConfig;
import org.moshe.arad.kafka.consumers.events.config.NewUserJoinedLobbyEventConfig;
import org.moshe.arad.kafka.events.BackgammonEvent;
import org.moshe.arad.kafka.producers.ISimpleProducer;
import org.moshe.arad.kafka.producers.config.SimpleProducerConfig;
import org.moshe.arad.kafka.producers.events.SimpleEventsProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements ApplicationContextAware, IAppInitializer {

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
	private SimpleEventsProducer<BackgammonEvent> fromMongoToUsersServiceEventsProducer;
	
	@Autowired
	private SimpleProducerConfig fromMongoToUsersServiceConfig;
	
	private ExecutorService executor = Executors.newFixedThreadPool(6);
	
	private Logger logger = LoggerFactory.getLogger(AppInit.class);
	
	private ConsumerToProducerQueue pullEventsCommandsConsumerToProducerQueue;
	
	private ApplicationContext context;
	
	public AppInit() {		
	}
	
	@Override
	public void initKafkaCommandsConsumers() {
		pullEventsCommandsConsumerToProducerQueue = context.getBean(ConsumerToProducerQueue.class);
				
		logger.info("Initializing pull events commands consumer...");
		initSingleConsumer(pullEventsCommandsConsumer, KafkaUtils.PULL_EVENTS_COMMAND_TOPIC, pullEventsCommandConfig, pullEventsCommandsConsumerToProducerQueue);
		logger.info("Initializing pull events commands consumer...");
		
		executeProducersAndConsumers(Arrays.asList(pullEventsCommandsConsumer));
	}

	@Override
	public void initKafkaEventsConsumers() {		
		logger.info("Initializing new user created events consumer...");
		initSingleConsumer(newUserCreatedEventConsumer, KafkaUtils.NEW_USER_CREATED_EVENT_TOPIC, newUserCreatedEventConfig, null);
		logger.info("Initialize new user created events consumer, completed...");
		
		logger.info("Initializing new user joined lobby events consumer...");
		initSingleConsumer(newUserJoinedLobbyEventConsumer, KafkaUtils.NEW_USER_JOINED_LOBBY_EVENT_TOPIC, newUserJoinedLobbyEventConfig, null);
		logger.info("Initialize new user joined lobby events, completed...");
		
		executeProducersAndConsumers(Arrays.asList(newUserCreatedEventConsumer, newUserJoinedLobbyEventConsumer));
	}

	@Override
	public void initKafkaCommandsProducers() {
	
	}

	@Override
	public void initKafkaEventsProducers() {
		logger.info("Initializing from mongo to users service events producer...");
		initSingleProducer(fromMongoToUsersServiceEventsProducer, KafkaUtils.FROM_MONGO_TO_USERS_SERVICES, fromMongoToUsersServiceConfig, pullEventsCommandsConsumerToProducerQueue);
		logger.info("Initialize from mongo to users service events producer, completed...");
		
		executeProducersAndConsumers(Arrays.asList(fromMongoToUsersServiceEventsProducer));
	}

	@Override
	public void engineShutdown() {
		logger.info("about to do shutdown.");		
		shutdownSingleConsumer(pullEventsCommandsConsumer);
		shutdownSingleConsumer(newUserCreatedEventConsumer);		
		shutdownSingleConsumer(newUserJoinedLobbyEventConsumer);
		shutdownSingleProducer(fromMongoToUsersServiceEventsProducer);
		selfShutdown();
		logger.info("shutdown compeleted.");
	}
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}
	
	private void initSingleConsumer(ISimpleConsumer consumer, String topic, SimpleConsumerConfig consumerConfig, ConsumerToProducerQueue queue) {
		consumer.setTopic(topic);
		consumer.setSimpleConsumerConfig(consumerConfig);
		consumer.initConsumer();	
		consumer.setConsumerToProducerQueue(queue);
	}
	
	private void initSingleProducer(ISimpleProducer producer, String topic, SimpleProducerConfig consumerConfig, ConsumerToProducerQueue queue) {
		producer.setTopic(topic);
		producer.setSimpleProducerConfig(consumerConfig);	
		producer.setConsumerToProducerQueue(queue);
	}
	
	private void shutdownSingleConsumer(ISimpleConsumer consumer) {
		consumer.setRunning(false);
		consumer.getScheduledExecutor().shutdown();	
	}
	
	private void shutdownSingleProducer(ISimpleProducer producer) {
		producer.setRunning(false);
		producer.getScheduledExecutor().shutdown();	
	}
	
	private void selfShutdown(){
		this.executor.shutdown();
	}
	
	private void executeProducersAndConsumers(List<Runnable> jobs){
		for(Runnable job:jobs)
			executor.execute(job);
	}
}
