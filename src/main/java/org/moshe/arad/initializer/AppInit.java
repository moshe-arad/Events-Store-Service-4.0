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
import org.moshe.arad.kafka.consumers.config.GameRoomClosedLoggedOutOpenByLeftLastEventConfig;
import org.moshe.arad.kafka.consumers.config.GameRoomClosedLoggedOutSecondLeftLastEventConfig;
import org.moshe.arad.kafka.consumers.config.GameRoomClosedLoggedOutWatcherLeftLastEventConfig;
import org.moshe.arad.kafka.consumers.config.GameRoomClosedOpenByLeftBeforeGameStartedEventConfig;
import org.moshe.arad.kafka.consumers.config.GameRoomClosedWatcherLeftLastEventConfig;
import org.moshe.arad.kafka.consumers.config.LoggedInEventConfig;
import org.moshe.arad.kafka.consumers.config.LoggedOutEventConfig;
import org.moshe.arad.kafka.consumers.config.LoggedOutOpenByLeftBeforeGameStartedEventConfig;
import org.moshe.arad.kafka.consumers.config.LoggedOutOpenByLeftEventConfig;
import org.moshe.arad.kafka.consumers.config.LoggedOutOpenByLeftFirstEventConfig;
import org.moshe.arad.kafka.consumers.config.LoggedOutOpenByLeftLastEventConfig;
import org.moshe.arad.kafka.consumers.config.LoggedOutSecondLeftEventConfig;
import org.moshe.arad.kafka.consumers.config.LoggedOutSecondLeftFirstEventConfig;
import org.moshe.arad.kafka.consumers.config.LoggedOutSecondLeftLastEventConfig;
import org.moshe.arad.kafka.consumers.config.NewGameRoomOpenedEventConfig;
import org.moshe.arad.kafka.consumers.config.NewUserCreatedEventConfig;
import org.moshe.arad.kafka.consumers.config.NewUserJoinedLobbyEventConfig;
import org.moshe.arad.kafka.consumers.config.OpenByLeftBeforeGameStartedEventConfig;
import org.moshe.arad.kafka.consumers.config.OpenByLeftEventConfig;
import org.moshe.arad.kafka.consumers.config.PullEventsWithSavingCommandConfig;
import org.moshe.arad.kafka.consumers.config.PullEventsWithoutSavingCommandConfig;
import org.moshe.arad.kafka.consumers.config.SimpleConsumerConfig;
import org.moshe.arad.kafka.consumers.config.ToLobbyPullEventsWithoutSavingCommandConfig;
import org.moshe.arad.kafka.consumers.config.UserAddedAsSecondPlayerEventConfig;
import org.moshe.arad.kafka.consumers.config.UserAddedAsWatcherEventConfig;
import org.moshe.arad.kafka.consumers.config.LoggedOutUserLeftLobbyEventConfig;
import org.moshe.arad.kafka.consumers.config.LoggedOutWatcherLeftEventConfig;
import org.moshe.arad.kafka.consumers.config.LoggedOutWatcherLeftLastEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedAddedSecondPlayerEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedAddedWatcherEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedLeftLobbyEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedLoggedOutOpenByLeftBeforeGameStartedEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedLoggedOutOpenByLeftEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedLoggedOutOpenByLeftFirstEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedLoggedOutOpenByLeftLastEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedLoggedOutSecondLeftEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedLoggedOutSecondLeftFirstEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedLoggedOutSecondLeftLastEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedLoggedOutWatcherLeftEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedLoggedOutWatcherLeftLastEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedOpenByLeftBeforeGameStartedEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedOpenByLeftEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedWatcherLeftEventConfig;
import org.moshe.arad.kafka.consumers.config.UserPermissionsUpdatedWatcherLeftLastEventConfig;
import org.moshe.arad.kafka.consumers.config.WatcherLeftEventConfig;
import org.moshe.arad.kafka.consumers.config.WatcherLeftLastEventConfig;
import org.moshe.arad.kafka.consumers.events.ExistingUserJoinedLobbyEventConsumer;
import org.moshe.arad.kafka.consumers.events.GameRoomClosedLoggedOutOpenByLeftBeforeGameStartedEventConsumer;
import org.moshe.arad.kafka.consumers.events.GameRoomClosedLoggedOutOpenByLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.GameRoomClosedLoggedOutSecondLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.GameRoomClosedLoggedOutWatcherLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.GameRoomClosedOpenByLeftBeforeGameStartedEventConsumer;
import org.moshe.arad.kafka.consumers.events.GameRoomClosedWatcherLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedInEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutOpenByLeftBeforeGameStartedEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutOpenByLeftEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutOpenByLeftFirstEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutOpenByLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutSecondLeftEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutSecondLeftFirstEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutSecondLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.NewGameRoomOpenedEventConsumer;
import org.moshe.arad.kafka.consumers.events.NewUserCreatedEventConsumer;
import org.moshe.arad.kafka.consumers.events.NewUserJoinedLobbyEventsConsumer;
import org.moshe.arad.kafka.consumers.events.OpenByLeftBeforeGameStartedEventConsumer;
import org.moshe.arad.kafka.consumers.events.OpenByLeftEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserAddedAsSecondPlayerEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserAddedAsWatcherEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutUserLeftLobbyEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutWatcherLeftEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutWatcherLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedAddedSecondPlayerEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedAddedWatcherEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedLeftLobbyEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedLoggedOutOpenByLeftBeforeGameStartedEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedLoggedOutOpenByLeftEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedLoggedOutOpenByLeftFirstEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedLoggedOutOpenByLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedLoggedOutSecondLeftEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedLoggedOutSecondLeftFirstEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedLoggedOutSecondLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedLoggedOutWatcherLeftEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedLoggedOutWatcherLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedOpenByLeftBeforeGameStartedEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedOpenByLeftEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedWatcherLeftEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedWatcherLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.WatcherLeftEventConsumer;
import org.moshe.arad.kafka.consumers.events.WatcherLeftLastEventConsumer;
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
	
	private LoggedOutOpenByLeftEventConsumer loggedOutOpenByLeftEventConsumer;
	
	@Autowired
	private LoggedOutOpenByLeftEventConfig loggedOutOpenByLeftEventConfig;
	
	private UserPermissionsUpdatedLoggedOutOpenByLeftEventConsumer userPermissionsUpdatedLoggedOutOpenByLeftEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedLoggedOutOpenByLeftEventConfig userPermissionsUpdatedLoggedOutOpenByLeftEventConfig;
	
	private LoggedOutWatcherLeftLastEventConsumer loggedOutWatcherLeftLastEventConsumer;
	
	@Autowired
	private LoggedOutWatcherLeftLastEventConfig loggedOutWatcherLeftLastEventConfig;
	
	private GameRoomClosedLoggedOutWatcherLeftLastEventConsumer gameRoomClosedLoggedOutWatcherLeftLastEventConsumer;
	
	@Autowired
	private GameRoomClosedLoggedOutWatcherLeftLastEventConfig gameRoomClosedLoggedOutWatcherLeftLastEventConfig;
	
	private UserPermissionsUpdatedLoggedOutWatcherLeftLastEventConsumer userPermissionsUpdatedLoggedOutWatcherLeftLastEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedLoggedOutWatcherLeftLastEventConfig userPermissionsUpdatedLoggedOutWatcherLeftLastEventConfig;
	
	private LoggedOutWatcherLeftEventConsumer loggedOutWatcherLeftEventConsumer;
	
	@Autowired
	private LoggedOutWatcherLeftEventConfig loggedOutWatcherLeftEventConfig;
	
	private UserPermissionsUpdatedLoggedOutWatcherLeftEventConsumer userPermissionsUpdatedLoggedOutWatcherLeftEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedLoggedOutWatcherLeftEventConfig userPermissionsUpdatedLoggedOutWatcherLeftEventConfig;
	
	private LoggedOutOpenByLeftFirstEventConsumer loggedOutOpenByLeftFirstEventConsumer;
	
	@Autowired
	private LoggedOutOpenByLeftFirstEventConfig loggedOutOpenByLeftFirstEventConfig;
	
	private UserPermissionsUpdatedLoggedOutOpenByLeftFirstEventConsumer userPermissionsUpdatedLoggedOutOpenByLeftFirstEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedLoggedOutOpenByLeftFirstEventConfig userPermissionsUpdatedLoggedOutOpenByLeftFirstEventConfig;
	
	private LoggedOutSecondLeftFirstEventConsumer loggedOutSecondLeftFirstEventConsumer;
	
	@Autowired
	private LoggedOutSecondLeftFirstEventConfig loggedOutSecondLeftFirstEventConfig;
	
	private UserPermissionsUpdatedLoggedOutSecondLeftFirstEventConsumer userPermissionsUpdatedLoggedOutSecondLeftFirstEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedLoggedOutSecondLeftFirstEventConfig userPermissionsUpdatedLoggedOutSecondLeftFirstEventConfig;
	
	private LoggedOutSecondLeftEventConsumer loggedOutSecondLeftEventConsumer;
	
	@Autowired
	private LoggedOutSecondLeftEventConfig loggedOutSecondLeftEventConfig;
	
	private UserPermissionsUpdatedLoggedOutSecondLeftEventConsumer userPermissionsUpdatedLoggedOutSecondLeftEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedLoggedOutSecondLeftEventConfig userPermissionsUpdatedLoggedOutSecondLeftEventConfig;
	
	private LoggedOutOpenByLeftLastEventConsumer loggedOutOpenByLeftLastEventConsumer;
	
	@Autowired
	private LoggedOutOpenByLeftLastEventConfig loggedOutOpenByLeftLastEventConfig;
	
	private UserPermissionsUpdatedLoggedOutOpenByLeftLastEventConsumer userPermissionsUpdatedLoggedOutOpenByLeftLastEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedLoggedOutOpenByLeftLastEventConfig userPermissionsUpdatedLoggedOutOpenByLeftLastEventConfig;
	
	private GameRoomClosedLoggedOutOpenByLeftLastEventConsumer gameRoomClosedLoggedOutOpenByLeftLastEventConsumer;
	
	@Autowired
	private GameRoomClosedLoggedOutOpenByLeftLastEventConfig gameRoomClosedLoggedOutOpenByLeftLastEventConfig;
	
	private LoggedOutSecondLeftLastEventConsumer loggedOutSecondLeftLastEventConsumer;
	
	@Autowired
	private LoggedOutSecondLeftLastEventConfig loggedOutSecondLeftLastEventConfig;
	
	private UserPermissionsUpdatedLoggedOutSecondLeftLastEventConsumer userPermissionsUpdatedLoggedOutSecondLeftLastEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedLoggedOutSecondLeftLastEventConfig userPermissionsUpdatedLoggedOutSecondLeftLastEventConfig;
	
	private GameRoomClosedLoggedOutSecondLeftLastEventConsumer gameRoomClosedLoggedOutSecondLeftLastEventConsumer;
	
	@Autowired
	private GameRoomClosedLoggedOutSecondLeftLastEventConfig gameRoomClosedLoggedOutSecondLeftLastEventConfig;
	
	private OpenByLeftBeforeGameStartedEventConsumer openByLeftBeforeGameStartedEventConsumer;
	
	@Autowired
	private OpenByLeftBeforeGameStartedEventConfig openByLeftBeforeGameStartedEventConfig;
	
	private UserPermissionsUpdatedOpenByLeftBeforeGameStartedEventConsumer userPermissionsUpdatedOpenByLeftBeforeGameStartedEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedOpenByLeftBeforeGameStartedEventConfig userPermissionsUpdatedOpenByLeftBeforeGameStartedEventConfig;
	
	private GameRoomClosedOpenByLeftBeforeGameStartedEventConsumer gameRoomClosedOpenByLeftBeforeGameStartedEventConsumer;
	
	@Autowired
	private GameRoomClosedOpenByLeftBeforeGameStartedEventConfig gameRoomClosedOpenByLeftBeforeGameStartedEventConfig;
	
	private OpenByLeftEventConsumer openByLeftEventConsumer;
	
	@Autowired
	private OpenByLeftEventConfig openByLeftEventConfig;
	
	private UserPermissionsUpdatedOpenByLeftEventConsumer userPermissionsUpdatedOpenByLeftEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedOpenByLeftEventConfig userPermissionsUpdatedOpenByLeftEventConfig;
	
	private WatcherLeftLastEventConsumer watcherLeftLastEventConsumer;
	
	@Autowired
	private WatcherLeftLastEventConfig watcherLeftLastEventConfig;
	
	private UserPermissionsUpdatedWatcherLeftLastEventConsumer userPermissionsUpdatedWatcherLeftLastEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedWatcherLeftLastEventConfig userPermissionsUpdatedWatcherLeftLastEventConfig;
	
	private GameRoomClosedWatcherLeftLastEventConsumer gameRoomClosedWatcherLeftLastEventConsumer;
	
	@Autowired
	private GameRoomClosedWatcherLeftLastEventConfig gameRoomClosedWatcherLeftLastEventConfig;
	
	private WatcherLeftEventConsumer watcherLeftEventConsumer;
	
	@Autowired
	private WatcherLeftEventConfig watcherLeftEventConfig;
	
	private UserPermissionsUpdatedWatcherLeftEventConsumer userPermissionsUpdatedWatcherLeftEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedWatcherLeftEventConfig userPermissionsUpdatedWatcherLeftEventConfig;
	
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
			
			loggedOutOpenByLeftEventConsumer = context.getBean(LoggedOutOpenByLeftEventConsumer.class);
			initSingleConsumer(loggedOutOpenByLeftEventConsumer, KafkaUtils.LOGGED_OUT_OPENBY_LEFT_EVENT_TOPIC, loggedOutOpenByLeftEventConfig, null);
			
			userPermissionsUpdatedLoggedOutOpenByLeftEventConsumer = context.getBean(UserPermissionsUpdatedLoggedOutOpenByLeftEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedLoggedOutOpenByLeftEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_LOGGED_OUT_OPENBY_LEFT_EVENT_TOPIC, userPermissionsUpdatedLoggedOutOpenByLeftEventConfig, null);
			
			loggedOutWatcherLeftLastEventConsumer = context.getBean(LoggedOutWatcherLeftLastEventConsumer.class);
			initSingleConsumer(loggedOutWatcherLeftLastEventConsumer, KafkaUtils.LOGGED_OUT_WATCHER_LEFT_LAST_EVENT_TOPIC, loggedOutWatcherLeftLastEventConfig, null);
			
			gameRoomClosedLoggedOutWatcherLeftLastEventConsumer = context.getBean(GameRoomClosedLoggedOutWatcherLeftLastEventConsumer.class);
			initSingleConsumer(gameRoomClosedLoggedOutWatcherLeftLastEventConsumer, KafkaUtils.GAME_ROOM_CLOSED_LOGGED_OUT_WATCHER_LEFT_LAST_EVENT_TOPIC, gameRoomClosedLoggedOutWatcherLeftLastEventConfig, null);
			
			userPermissionsUpdatedLoggedOutWatcherLeftLastEventConsumer = context.getBean(UserPermissionsUpdatedLoggedOutWatcherLeftLastEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedLoggedOutWatcherLeftLastEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_LOGGED_OUT_WATCHER_LEFT_LAST_EVENT_TOPIC, userPermissionsUpdatedLoggedOutWatcherLeftLastEventConfig, null);
			
			loggedOutWatcherLeftEventConsumer = context.getBean(LoggedOutWatcherLeftEventConsumer.class);
			initSingleConsumer(loggedOutWatcherLeftEventConsumer, KafkaUtils.LOGGED_OUT_WATCHER_LEFT_EVENT_TOPIC, loggedOutWatcherLeftEventConfig, null);
			
			userPermissionsUpdatedLoggedOutWatcherLeftEventConsumer = context.getBean(UserPermissionsUpdatedLoggedOutWatcherLeftEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedLoggedOutWatcherLeftEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_LOGGED_OUT_WATCHER_LEFT_EVENT_TOPIC, userPermissionsUpdatedLoggedOutWatcherLeftEventConfig, null);
			
			loggedOutOpenByLeftFirstEventConsumer = context.getBean(LoggedOutOpenByLeftFirstEventConsumer.class);
			initSingleConsumer(loggedOutOpenByLeftFirstEventConsumer, KafkaUtils.LOGGED_OUT_OPENBY_LEFT_FIRST_EVENT_TOPIC, loggedOutOpenByLeftFirstEventConfig, null);
			
			userPermissionsUpdatedLoggedOutOpenByLeftFirstEventConsumer = context.getBean(UserPermissionsUpdatedLoggedOutOpenByLeftFirstEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedLoggedOutOpenByLeftFirstEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_LOGGED_OUT_OPENBY_LEFT_FIRST_EVENT_TOPIC, userPermissionsUpdatedLoggedOutOpenByLeftFirstEventConfig, null);
			
			loggedOutSecondLeftFirstEventConsumer = context.getBean(LoggedOutSecondLeftFirstEventConsumer.class);
			initSingleConsumer(loggedOutSecondLeftFirstEventConsumer, KafkaUtils.LOGGED_OUT_SECOND_LEFT_FIRST_EVENT_TOPIC, loggedOutSecondLeftFirstEventConfig, null);
			
			userPermissionsUpdatedLoggedOutSecondLeftFirstEventConsumer = context.getBean(UserPermissionsUpdatedLoggedOutSecondLeftFirstEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedLoggedOutSecondLeftFirstEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_LOGGED_OUT_SECOND_LEFT_FIRST_EVENT_TOPIC, userPermissionsUpdatedLoggedOutSecondLeftFirstEventConfig, null);
			
			loggedOutSecondLeftEventConsumer = context.getBean(LoggedOutSecondLeftEventConsumer.class);
			initSingleConsumer(loggedOutSecondLeftEventConsumer, KafkaUtils.LOGGED_OUT_SECOND_LEFT_EVENT_TOPIC, loggedOutSecondLeftEventConfig, null);
			
			userPermissionsUpdatedLoggedOutSecondLeftEventConsumer = context.getBean(UserPermissionsUpdatedLoggedOutSecondLeftEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedLoggedOutSecondLeftEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_LOGGED_OUT_SECOND_LEFT_EVENT_TOPIC, userPermissionsUpdatedLoggedOutSecondLeftEventConfig, null);
			
			loggedOutOpenByLeftLastEventConsumer = context.getBean(LoggedOutOpenByLeftLastEventConsumer.class);
			initSingleConsumer(loggedOutOpenByLeftLastEventConsumer, KafkaUtils.LOGGED_OUT_OPENBY_LEFT_LAST_EVENT_TOPIC, loggedOutOpenByLeftLastEventConfig, null);
			
			userPermissionsUpdatedLoggedOutOpenByLeftLastEventConsumer = context.getBean(UserPermissionsUpdatedLoggedOutOpenByLeftLastEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedLoggedOutOpenByLeftLastEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_LOGGED_OUT_OPENBY_LEFT_LAST_EVENT_TOPIC, userPermissionsUpdatedLoggedOutOpenByLeftLastEventConfig, null);
			
			gameRoomClosedLoggedOutOpenByLeftLastEventConsumer = context.getBean(GameRoomClosedLoggedOutOpenByLeftLastEventConsumer.class);
			initSingleConsumer(gameRoomClosedLoggedOutOpenByLeftLastEventConsumer, KafkaUtils.GAME_ROOM_CLOSED_LOGGED_OUT_OPENBY_LEFT_LAST_EVENT_TOPIC, gameRoomClosedLoggedOutOpenByLeftLastEventConfig, null);
			
			loggedOutSecondLeftLastEventConsumer = context.getBean(LoggedOutSecondLeftLastEventConsumer.class);
			initSingleConsumer(loggedOutSecondLeftLastEventConsumer, KafkaUtils.LOGGED_OUT_SECOND_LEFT_LAST_EVENT_TOPIC, loggedOutSecondLeftLastEventConfig, null);
			
			userPermissionsUpdatedLoggedOutSecondLeftLastEventConsumer = context.getBean(UserPermissionsUpdatedLoggedOutSecondLeftLastEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedLoggedOutSecondLeftLastEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_LOGGED_OUT_SECOND_LEFT_LAST_EVENT_TOPIC, userPermissionsUpdatedLoggedOutSecondLeftLastEventConfig, null);
			
			gameRoomClosedLoggedOutSecondLeftLastEventConsumer = context.getBean(GameRoomClosedLoggedOutSecondLeftLastEventConsumer.class);
			initSingleConsumer(gameRoomClosedLoggedOutSecondLeftLastEventConsumer, KafkaUtils.GAME_ROOM_CLOSED_LOGGED_OUT_SECOND_LEFT_LAST_EVENT_TOPIC, gameRoomClosedLoggedOutSecondLeftLastEventConfig, null);
			
			openByLeftBeforeGameStartedEventConsumer = context.getBean(OpenByLeftBeforeGameStartedEventConsumer.class);
			initSingleConsumer(openByLeftBeforeGameStartedEventConsumer, KafkaUtils.OPENBY_LEFT_BEFORE_GAME_STARTED_EVENT_TOPIC, openByLeftBeforeGameStartedEventConfig, null);
			
			userPermissionsUpdatedOpenByLeftBeforeGameStartedEventConsumer = context.getBean(UserPermissionsUpdatedOpenByLeftBeforeGameStartedEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedOpenByLeftBeforeGameStartedEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_OPENBY_LEFT_BEFORE_GAME_STARTED_EVENT_TOPIC, userPermissionsUpdatedOpenByLeftBeforeGameStartedEventConfig, null);
			
			gameRoomClosedOpenByLeftBeforeGameStartedEventConsumer = context.getBean(GameRoomClosedOpenByLeftBeforeGameStartedEventConsumer.class);
			initSingleConsumer(gameRoomClosedOpenByLeftBeforeGameStartedEventConsumer, KafkaUtils.GAME_ROOM_CLOSED_OPENBY_LEFT_BEFORE_GAME_STARTED_EVENT_TOPIC, gameRoomClosedOpenByLeftBeforeGameStartedEventConfig, null);
			
			openByLeftEventConsumer = context.getBean(OpenByLeftEventConsumer.class);
			initSingleConsumer(openByLeftEventConsumer, KafkaUtils.OPENBY_LEFT_EVENT_TOPIC, openByLeftEventConfig, null);
			
			userPermissionsUpdatedOpenByLeftEventConsumer = context.getBean(UserPermissionsUpdatedOpenByLeftEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedOpenByLeftEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_OPENBY_LEFT_EVENT_TOPIC, userPermissionsUpdatedOpenByLeftEventConfig, null);
			
			watcherLeftLastEventConsumer =context.getBean(WatcherLeftLastEventConsumer.class);
			initSingleConsumer(watcherLeftLastEventConsumer, KafkaUtils.WATCHER_LEFT_LAST_EVENT_TOPIC, watcherLeftLastEventConfig, null);
			
			userPermissionsUpdatedWatcherLeftLastEventConsumer = context.getBean(UserPermissionsUpdatedWatcherLeftLastEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedWatcherLeftLastEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_WATCHER_LEFT_LAST_EVENT_TOPIC, userPermissionsUpdatedWatcherLeftLastEventConfig, null);
			
			gameRoomClosedWatcherLeftLastEventConsumer = context.getBean(GameRoomClosedWatcherLeftLastEventConsumer.class);
			initSingleConsumer(gameRoomClosedWatcherLeftLastEventConsumer, KafkaUtils.GAME_ROOM_CLOSED_WATCHER_LEFT_LAST_EVENT_TOPIC, gameRoomClosedWatcherLeftLastEventConfig, null);
			
			watcherLeftEventConsumer = context.getBean(WatcherLeftEventConsumer.class);
			initSingleConsumer(watcherLeftEventConsumer, KafkaUtils.WATCHER_LEFT_EVENT_TOPIC, watcherLeftEventConfig, null);
			
			userPermissionsUpdatedWatcherLeftEventConsumer = context.getBean(UserPermissionsUpdatedWatcherLeftEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedWatcherLeftEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_WATCHER_LEFT_EVENT_TOPIC, userPermissionsUpdatedWatcherLeftEventConfig, null);
			
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
					gameRoomClosedLoggedOutOpenByLeftBeforeGameStartedEventConsumer,
					loggedOutOpenByLeftEventConsumer,
					userPermissionsUpdatedLoggedOutOpenByLeftEventConsumer,
					loggedOutWatcherLeftLastEventConsumer,
					gameRoomClosedLoggedOutWatcherLeftLastEventConsumer,
					userPermissionsUpdatedLoggedOutWatcherLeftLastEventConsumer,
					loggedOutWatcherLeftEventConsumer,
					userPermissionsUpdatedLoggedOutWatcherLeftEventConsumer,
					loggedOutOpenByLeftFirstEventConsumer,
					userPermissionsUpdatedLoggedOutOpenByLeftFirstEventConsumer,
					loggedOutSecondLeftFirstEventConsumer,
					userPermissionsUpdatedLoggedOutSecondLeftFirstEventConsumer,
					loggedOutSecondLeftEventConsumer,
					userPermissionsUpdatedLoggedOutSecondLeftEventConsumer,
					loggedOutOpenByLeftLastEventConsumer,
					userPermissionsUpdatedLoggedOutOpenByLeftLastEventConsumer,
					gameRoomClosedLoggedOutOpenByLeftLastEventConsumer,
					loggedOutSecondLeftLastEventConsumer,
					userPermissionsUpdatedLoggedOutSecondLeftLastEventConsumer,
					gameRoomClosedLoggedOutSecondLeftLastEventConsumer,
					openByLeftBeforeGameStartedEventConsumer,
					userPermissionsUpdatedOpenByLeftBeforeGameStartedEventConsumer,
					gameRoomClosedOpenByLeftBeforeGameStartedEventConsumer,
					openByLeftEventConsumer,
					userPermissionsUpdatedOpenByLeftEventConsumer,
					watcherLeftLastEventConsumer,
					userPermissionsUpdatedWatcherLeftLastEventConsumer,
					gameRoomClosedWatcherLeftLastEventConsumer,
					watcherLeftEventConsumer,
					userPermissionsUpdatedWatcherLeftEventConsumer));
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
