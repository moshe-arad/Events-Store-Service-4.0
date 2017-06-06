package org.moshe.arad.initializer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.moshe.arad.kafka.ConsumerToProducerQueue;
import org.moshe.arad.kafka.KafkaUtils;
import org.moshe.arad.kafka.consumers.ISimpleConsumer;
import org.moshe.arad.kafka.consumers.commands.PullEventsWithSavingCommandsConsumer;
import org.moshe.arad.kafka.consumers.commands.PullEventsWithoutSavingCommandsConsumer;
import org.moshe.arad.kafka.consumers.config.ExistingUserJoinedLobbyEventConfig;
import org.moshe.arad.kafka.consumers.config.GameRoomClosedLoggedOutOpenByLeftBeforeGameStartedEventConfig;
import org.moshe.arad.kafka.consumers.config.LoggedInEventConfig;
import org.moshe.arad.kafka.consumers.config.LoggedOutEventConfig;
import org.moshe.arad.kafka.consumers.config.LoggedOutOpenByLeftBeforeGameStartedEventConfig;
import org.moshe.arad.kafka.consumers.config.NewGameRoomOpenedEventConfig;
import org.moshe.arad.kafka.consumers.config.NewUserCreatedEventConfig;
import org.moshe.arad.kafka.consumers.config.NewUserJoinedLobbyEventConfig;
import org.moshe.arad.kafka.consumers.config.PullEventsWithSavingCommandConfig;
import org.moshe.arad.kafka.consumers.config.PullEventsWithoutSavingCommandConfig;
import org.moshe.arad.kafka.consumers.config.SimpleConsumerConfig;
import org.moshe.arad.kafka.consumers.config.ToLobbyPullEventsWithoutSavingCommandConfig;
import org.moshe.arad.kafka.consumers.config.UserAddedAsSecondPlayerEventConfig;
import org.moshe.arad.kafka.consumers.config.UserAddedAsWatcherEventConfig;
import org.moshe.arad.kafka.consumers.config.LoggedOutUserLeftLobbyEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedAddedSecondPlayerEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedAddedWatcherEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedLeftLobbyEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedLoggedOutOpenByLeftBeforeGameStartedEventConfig;
import org.moshe.arad.kafka.consumers.events.ExistingUserJoinedLobbyEventConsumer;
import org.moshe.arad.kafka.consumers.events.GameRoomClosedLoggedOutOpenByLeftBeforeGameStartedEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedInEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutOpenByLeftBeforeGameStartedEventConsumer;
import org.moshe.arad.kafka.consumers.events.NewGameRoomOpenedEventConsumer;
import org.moshe.arad.kafka.consumers.events.NewUserCreatedEventConsumer;
import org.moshe.arad.kafka.consumers.events.NewUserJoinedLobbyEventsConsumer;
import org.moshe.arad.kafka.consumers.events.UserAddedAsSecondPlayerEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserAddedAsWatcherEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutUserLeftLobbyEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedAddedSecondPlayerEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedAddedWatcherEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedLeftLobbyEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedLoggedOutOpenByLeftBeforeGameStartedEventConsumer;
import org.moshe.arad.kafka.events.BackgammonEvent;
import org.moshe.arad.kafka.producers.ISimpleProducer;
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

	private NewUserCreatedEventConsumer newUserCreatedEventConsumer;
	
	@Autowired
	private NewUserCreatedEventConfig newUserCreatedEventConfig;
	
	private NewUserJoinedLobbyEventsConsumer newUserJoinedLobbyEventConsumer;
	
	@Autowired
	private NewUserJoinedLobbyEventConfig newUserJoinedLobbyEventConfig;
	
	private PullEventsWithSavingCommandsConsumer pullEventsWithSavingCommandsConsumer;	
	
	@Autowired
	private PullEventsWithSavingCommandConfig pullEventsWithSavingCommandConfig;
	
	@Autowired
	private PullEventsWithoutSavingCommandConfig pullEventsWithoutSavingCommandConfig;
	
	@Autowired
	private SimpleEventsProducer<BackgammonEvent> fromMongoEventsWithSavingProducer;
	
	@Autowired
	private SimpleEventsProducer<BackgammonEvent> fromMongoEventsWithoutSavingProducer;
	
	@Autowired
	private SimpleEventsProducer<BackgammonEvent> toLobbyfromMongoEventsWithoutSavingProducer;
	
	private LoggedInEventConsumer loggedInEventConsumer;
	
	@Autowired
	private LoggedInEventConfig loggedInEventConfig;
	
	private ExistingUserJoinedLobbyEventConsumer existingUserJoinedLobbyEventConsumer;
	
	@Autowired
	private ExistingUserJoinedLobbyEventConfig existingUserJoinedLobbyEventConfig;
	
	private PullEventsWithoutSavingCommandsConsumer pullEventsWithoutSavingCommandsConsumer;
	
	private PullEventsWithoutSavingCommandsConsumer toLobbyPullEventsWithoutSavingCommandsConsumer;
	
	@Autowired
	private ToLobbyPullEventsWithoutSavingCommandConfig toLobbyPullEventsWithoutSavingCommandConfig;
	
	private NewGameRoomOpenedEventConsumer newGameRoomOpenedEventConsumer;
	
	@Autowired
	private NewGameRoomOpenedEventConfig newGameRoomOpenedEventConfig;
	
	private UserAddedAsWatcherEventConsumer userAddedAsWatcherEventConsumer;
	
	@Autowired
	private UserAddedAsWatcherEventConfig userAddedAsWatcherEventConfig;
	
	private UserAddedAsSecondPlayerEventConsumer userAddedAsSecondPlayerEventConsumer;
	
	@Autowired
	private UserAddedAsSecondPlayerEventConfig userAddedAsSecondPlayerEventConfig;

	private UserPermissionsUpdatedEventConsumer userPermissionsUpdatedEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedEventConfig userPermissionsUpdatedEventConfig;
	
	private UserPermissionsUpdatedAddedWatcherEventConsumer userPermissionsUpdatedAddedWatcherEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedAddedWatcherEventConfig userPermissionsUpdatedAddedWatcherEventConfig;
	
	private UserPermissionsUpdatedAddedSecondPlayerEventConsumer userPermissionsUpdatedAddedSecondPlayerEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedAddedSecondPlayerEventConfig userPermissionsUpdatedAddedSecondPlayerEventConfig;
	
	private LoggedOutEventConsumer loggedOutEventConsumer;
	
	@Autowired
	private LoggedOutEventConfig loggedOutEventConfig;
	
	private LoggedOutUserLeftLobbyEventConsumer loggedOutUserLeftLobbyEventConsumer;
	
	@Autowired
	private LoggedOutUserLeftLobbyEventConfig loggedOutUserLeftLobbyEventConfig;
	
	private UserPermissionsUpdatedLeftLobbyEventConsumer userPermissionsUpdatedLeftLobbyEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedLeftLobbyEventConfig userPermissionsUpdatedLeftLobbyEventConfig;
	
	private LoggedOutOpenByLeftBeforeGameStartedEventConsumer loggedOutOpenByLeftBeforeGameStartedEventConsumer;
	
	@Autowired
	private LoggedOutOpenByLeftBeforeGameStartedEventConfig loggedOutOpenByLeftBeforeGameStartedEventConfig;
	
	private UserPermissionsUpdatedLoggedOutOpenByLeftBeforeGameStartedEventConsumer userPermissionsUpdatedLoggedOutOpenByLeftBeforeGameStartedEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedLoggedOutOpenByLeftBeforeGameStartedEventConfig userPermissionsUpdatedLoggedOutOpenByLeftBeforeGameStartedEventConfig;
	
	private GameRoomClosedLoggedOutOpenByLeftBeforeGameStartedEventConsumer gameRoomClosedLoggedOutOpenByLeftBeforeGameStartedEventConsumer;
	
	@Autowired
	private GameRoomClosedLoggedOutOpenByLeftBeforeGameStartedEventConfig gameRoomClosedLoggedOutOpenByLeftBeforeGameStartedEventConfig;
	
	private ExecutorService executor = Executors.newFixedThreadPool(6);
	
	private Logger logger = LoggerFactory.getLogger(AppInit.class);
	
	private ConsumerToProducerQueue pullEventsWithSavingQueue;
	
	private ConsumerToProducerQueue pullEventsWithoutSavingQueue;
	
	private ConsumerToProducerQueue toLobbypullEventsWithoutSavingQueue;
	
	private ApplicationContext context;
	
	public static final int NUM_CONSUMERS = 3;
	
	public AppInit() {		
	}
	
	@Override
	public void initKafkaCommandsConsumers() {
		pullEventsWithSavingQueue = context.getBean(ConsumerToProducerQueue.class);
		pullEventsWithoutSavingQueue = context.getBean(ConsumerToProducerQueue.class);
		toLobbypullEventsWithoutSavingQueue = context.getBean(ConsumerToProducerQueue.class);
		
		for(int i=0; i<NUM_CONSUMERS; i++){
			pullEventsWithSavingCommandsConsumer = context.getBean(PullEventsWithSavingCommandsConsumer.class);
			pullEventsWithoutSavingCommandsConsumer = context.getBean(PullEventsWithoutSavingCommandsConsumer.class);
			toLobbyPullEventsWithoutSavingCommandsConsumer = context.getBean(PullEventsWithoutSavingCommandsConsumer.class);
			
			logger.info("Initializing pull events commands consumer...");
			initSingleConsumer(pullEventsWithSavingCommandsConsumer, KafkaUtils.PULL_EVENTS_WITH_SAVING_COMMAND_TOPIC, pullEventsWithSavingCommandConfig, pullEventsWithSavingQueue);
			
			initSingleConsumer(pullEventsWithoutSavingCommandsConsumer, KafkaUtils.PULL_EVENTS_WITHOUT_SAVING_COMMAND_TOPIC, pullEventsWithoutSavingCommandConfig, pullEventsWithoutSavingQueue);
			
			initSingleConsumer(toLobbyPullEventsWithoutSavingCommandsConsumer, KafkaUtils.LOBBY_SERVICE_PULL_EVENTS_WITHOUT_SAVING_COMMAND_TOPIC, toLobbyPullEventsWithoutSavingCommandConfig, toLobbypullEventsWithoutSavingQueue);
			logger.info("Initializing pull events commands consumer...");
			
			executeProducersAndConsumers(Arrays.asList(pullEventsWithSavingCommandsConsumer, 
					pullEventsWithoutSavingCommandsConsumer,
					toLobbyPullEventsWithoutSavingCommandsConsumer));
		}
	}

	@Override
	public void initKafkaEventsConsumers() {
		
		for(int i=0; i<NUM_CONSUMERS; i++){
			newUserCreatedEventConsumer = context.getBean(NewUserCreatedEventConsumer.class);
			logger.info("Initializing new user created events consumer...");
			initSingleConsumer(newUserCreatedEventConsumer, KafkaUtils.NEW_USER_CREATED_EVENT_TOPIC, newUserCreatedEventConfig, null);
			logger.info("Initialize new user created events consumer, completed...");
			
			newUserJoinedLobbyEventConsumer = context.getBean(NewUserJoinedLobbyEventsConsumer.class);
			logger.info("Initializing new user joined lobby events consumer...");
			initSingleConsumer(newUserJoinedLobbyEventConsumer, KafkaUtils.NEW_USER_JOINED_LOBBY_EVENT_TOPIC, newUserJoinedLobbyEventConfig, null);
			logger.info("Initialize new user joined lobby events, completed...");
			
			loggedInEventConsumer = context.getBean(LoggedInEventConsumer.class);
			initSingleConsumer(loggedInEventConsumer, KafkaUtils.LOGGED_IN_EVENT_TOPIC, loggedInEventConfig, null);
			
			existingUserJoinedLobbyEventConsumer = context.getBean(ExistingUserJoinedLobbyEventConsumer.class);
			initSingleConsumer(existingUserJoinedLobbyEventConsumer, KafkaUtils.EXISTING_USER_JOINED_LOBBY_EVENT_TOPIC, existingUserJoinedLobbyEventConfig, null);
			
			newGameRoomOpenedEventConsumer = context.getBean(NewGameRoomOpenedEventConsumer.class);
			initSingleConsumer(newGameRoomOpenedEventConsumer, KafkaUtils.NEW_GAME_ROOM_OPENED_EVENT_TOPIC, newGameRoomOpenedEventConfig, null);
			
			userAddedAsWatcherEventConsumer = context.getBean(UserAddedAsWatcherEventConsumer.class);
			initSingleConsumer(userAddedAsWatcherEventConsumer, KafkaUtils.USER_ADDED_AS_WATCHER_EVENT_TOPIC, userAddedAsWatcherEventConfig, null);
			
			userAddedAsSecondPlayerEventConsumer = context.getBean(UserAddedAsSecondPlayerEventConsumer.class);
			initSingleConsumer(userAddedAsSecondPlayerEventConsumer, KafkaUtils.USER_ADDED_AS_SECOND_PLAYER_EVENT_TOPIC, userAddedAsSecondPlayerEventConfig, null);
			
			userPermissionsUpdatedEventConsumer = context.getBean(UserPermissionsUpdatedEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_EVENT_TOPIC, userPermissionsUpdatedEventConfig, null);
			
			userPermissionsUpdatedAddedWatcherEventConsumer = context.getBean(UserPermissionsUpdatedAddedWatcherEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedAddedWatcherEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_USER_ADDED_WATCHER_EVENT_TOPIC, userPermissionsUpdatedAddedWatcherEventConfig, null);
			
			userPermissionsUpdatedAddedSecondPlayerEventConsumer = context.getBean(UserPermissionsUpdatedAddedSecondPlayerEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedAddedSecondPlayerEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_USER_ADDED_SECOND_PLAYER_EVENT_TOPIC, userPermissionsUpdatedAddedSecondPlayerEventConfig, null);
			
			loggedOutEventConsumer = context.getBean(LoggedOutEventConsumer.class);
			initSingleConsumer(loggedOutEventConsumer, KafkaUtils.LOGGED_OUT_EVENT_TOPIC, loggedOutEventConfig, null);
			
			loggedOutUserLeftLobbyEventConsumer = context.getBean(LoggedOutUserLeftLobbyEventConsumer.class);
			initSingleConsumer(loggedOutUserLeftLobbyEventConsumer, KafkaUtils.LOGGED_OUT_USER_LEFT_LOBBY_EVENT_TOPIC, loggedOutUserLeftLobbyEventConfig, null);
			
			userPermissionsUpdatedLeftLobbyEventConsumer = context.getBean(UserPermissionsUpdatedLeftLobbyEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedLeftLobbyEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_USER_LEFT_LOBBY_EVENT_TOPIC, userPermissionsUpdatedLeftLobbyEventConfig, null);
			
			loggedOutOpenByLeftBeforeGameStartedEventConsumer = context.getBean(LoggedOutOpenByLeftBeforeGameStartedEventConsumer.class);
			initSingleConsumer(loggedOutOpenByLeftBeforeGameStartedEventConsumer, KafkaUtils.LOGGED_OUT_OPENBY_LEFT_BEFORE_GAME_STARTED_EVENT_TOPIC, loggedOutOpenByLeftBeforeGameStartedEventConfig, null);
			
			userPermissionsUpdatedLoggedOutOpenByLeftBeforeGameStartedEventConsumer = context.getBean(UserPermissionsUpdatedLoggedOutOpenByLeftBeforeGameStartedEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedLoggedOutOpenByLeftBeforeGameStartedEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_LOGGED_OUT_OPENBY_LEFT_BEFORE_GAME_STARTED_EVENT_TOPIC, userPermissionsUpdatedLoggedOutOpenByLeftBeforeGameStartedEventConfig, null);
			
			gameRoomClosedLoggedOutOpenByLeftBeforeGameStartedEventConsumer = context.getBean(GameRoomClosedLoggedOutOpenByLeftBeforeGameStartedEventConsumer.class);
			initSingleConsumer(gameRoomClosedLoggedOutOpenByLeftBeforeGameStartedEventConsumer, KafkaUtils.GAME_ROOM_CLOSED_LOGGED_OUT_OPENBY_LEFT_BEFORE_GAME_STARTED_EVENT_TOPIC, gameRoomClosedLoggedOutOpenByLeftBeforeGameStartedEventConfig, null);
			
			executeProducersAndConsumers(Arrays.asList(newUserCreatedEventConsumer, 
					newUserJoinedLobbyEventConsumer, 
					loggedInEventConsumer,
					existingUserJoinedLobbyEventConsumer,
					newGameRoomOpenedEventConsumer,
					userAddedAsWatcherEventConsumer,
					userAddedAsSecondPlayerEventConsumer,
					userPermissionsUpdatedEventConsumer,
					userPermissionsUpdatedAddedWatcherEventConsumer,
					userPermissionsUpdatedAddedSecondPlayerEventConsumer,
					loggedOutEventConsumer,
					loggedOutUserLeftLobbyEventConsumer,
					userPermissionsUpdatedLeftLobbyEventConsumer,
					loggedOutOpenByLeftBeforeGameStartedEventConsumer,
					userPermissionsUpdatedLoggedOutOpenByLeftBeforeGameStartedEventConsumer,
					gameRoomClosedLoggedOutOpenByLeftBeforeGameStartedEventConsumer));
		}
	}

	@Override
	public void initKafkaCommandsProducers() {
	
	}

	@Override
	public void initKafkaEventsProducers() {
		logger.info("Initializing from mongo to users service events producer...");
		initSingleProducer(fromMongoEventsWithSavingProducer, KafkaUtils.FROM_MONGO_EVENTS_WITH_SAVING_TOPIC, pullEventsWithSavingQueue);
		
		initSingleProducer(fromMongoEventsWithoutSavingProducer, KafkaUtils.FROM_MONGO_EVENTS_WITHOUT_SAVING_TOPIC, pullEventsWithoutSavingQueue);
		
		initSingleProducer(toLobbyfromMongoEventsWithoutSavingProducer, KafkaUtils.TO_LOBBY_FROM_MONGO_EVENTS_WITHOUT_SAVING_TOPIC, toLobbypullEventsWithoutSavingQueue);
		logger.info("Initialize from mongo to users service events producer, completed...");
		
		executeProducersAndConsumers(Arrays.asList(fromMongoEventsWithSavingProducer, fromMongoEventsWithoutSavingProducer, toLobbyfromMongoEventsWithoutSavingProducer));
	}

	@Override
	public void engineShutdown() {
		logger.info("about to do shutdown.");		
		shutdownSingleConsumer(pullEventsWithSavingCommandsConsumer);
		shutdownSingleConsumer(newUserCreatedEventConsumer);		
		shutdownSingleConsumer(newUserJoinedLobbyEventConsumer);
		shutdownSingleProducer(fromMongoEventsWithSavingProducer);
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
	
	private void initSingleProducer(ISimpleProducer producer, String topic, ConsumerToProducerQueue queue) {
		producer.setTopic(topic);	
		producer.setConsumerToProducerQueue(queue);
	}
	
	private void shutdownSingleConsumer(ISimpleConsumer consumer) {
		consumer.setRunning(false);
		consumer.getScheduledExecutor().shutdown();	
		consumer.closeConsumer();
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
