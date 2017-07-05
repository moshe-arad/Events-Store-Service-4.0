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
import org.moshe.arad.kafka.consumers.config.*;
import org.moshe.arad.kafka.consumers.events.BlackAteWhitePawnEventConsumer;
import org.moshe.arad.kafka.consumers.events.BlackPawnCameBackAndAteWhitePawnEventConsumer;
import org.moshe.arad.kafka.consumers.events.BlackPawnCameBackEventConsumer;
import org.moshe.arad.kafka.consumers.events.BlackPawnTakenOutEventConsumer;
import org.moshe.arad.kafka.consumers.events.DiceRolledCanNotPlayEventConsumer;
import org.moshe.arad.kafka.consumers.events.DiceRolledEventConsumer;
import org.moshe.arad.kafka.consumers.events.ExistingUserJoinedLobbyEventConsumer;
import org.moshe.arad.kafka.consumers.events.GameRoomClosedLoggedOutOpenByLeftBeforeGameStartedEventConsumer;
import org.moshe.arad.kafka.consumers.events.GameRoomClosedLoggedOutOpenByLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.GameRoomClosedLoggedOutSecondLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.GameRoomClosedLoggedOutWatcherLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.GameRoomClosedOpenByLeftBeforeGameStartedEventConsumer;
import org.moshe.arad.kafka.consumers.events.GameRoomClosedOpenByLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.GameRoomClosedSecondLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.GameRoomClosedWatcherLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.GameStartedEventConsumer;
import org.moshe.arad.kafka.consumers.events.InitDiceCompletedEventConsumer;
import org.moshe.arad.kafka.consumers.events.InitGameRoomCompletedEventConsumer;
import org.moshe.arad.kafka.consumers.events.LastMoveBlackAteWhitePawnEventConsumer;
import org.moshe.arad.kafka.consumers.events.LastMoveBlackPawnCameBackAndAteWhitePawnEventConsumer;
import org.moshe.arad.kafka.consumers.events.LastMoveBlackPawnCameBackEventConsumer;
import org.moshe.arad.kafka.consumers.events.LastMoveBlackPawnTakenOutEventConsumer;
import org.moshe.arad.kafka.consumers.events.LastMoveWhiteAteBlackPawnEventConsumer;
import org.moshe.arad.kafka.consumers.events.LastMoveWhitePawnCameBackAndAteBlackPawnEventConsumer;
import org.moshe.arad.kafka.consumers.events.LastMoveWhitePawnCameBackEventConsumer;
import org.moshe.arad.kafka.consumers.events.LastMoveWhitePawnTakenOutEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedInEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutOpenByLeftBeforeGameStartedEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutOpenByLeftEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutOpenByLeftFirstEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutOpenByLeftFirstGameStoppedEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutOpenByLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutSecondLeftEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutSecondLeftFirstEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutSecondLeftFirstGameStoppedEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutSecondLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutUserLeftLobbyEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutWatcherLeftEventConsumer;
import org.moshe.arad.kafka.consumers.events.LoggedOutWatcherLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.NewGameRoomOpenedEventConsumer;
import org.moshe.arad.kafka.consumers.events.NewUserCreatedEventConsumer;
import org.moshe.arad.kafka.consumers.events.NewUserJoinedLobbyEventsConsumer;
import org.moshe.arad.kafka.consumers.events.OpenByLeftBeforeGameStartedEventConsumer;
import org.moshe.arad.kafka.consumers.events.OpenByLeftEventConsumer;
import org.moshe.arad.kafka.consumers.events.OpenByLeftFirstEventConsumer;
import org.moshe.arad.kafka.consumers.events.OpenByLeftFirstGameStoppedEventConsumer;
import org.moshe.arad.kafka.consumers.events.OpenByLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.RollDiceGameRoomFoundEventConsumer;
import org.moshe.arad.kafka.consumers.events.SecondLeftEventConsumer;
import org.moshe.arad.kafka.consumers.events.SecondLeftFirstEventConsumer;
import org.moshe.arad.kafka.consumers.events.SecondLeftFirstGameStoppedEventConsumer;
import org.moshe.arad.kafka.consumers.events.SecondLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.TurnNotPassedBlackAteWhitePawnEventConsumer;
import org.moshe.arad.kafka.consumers.events.TurnNotPassedBlackPawnCameBackAndAteWhitePawnEventConsumer;
import org.moshe.arad.kafka.consumers.events.TurnNotPassedBlackPawnCameBackEventConsumer;
import org.moshe.arad.kafka.consumers.events.TurnNotPassedBlackPawnTakenOutEventConsumer;
import org.moshe.arad.kafka.consumers.events.TurnNotPassedUserMadeMoveEventConsumer;
import org.moshe.arad.kafka.consumers.events.TurnNotPassedWhiteAteBlackPawnEventConsumer;
import org.moshe.arad.kafka.consumers.events.TurnNotPassedWhitePawnCameBackAndAteBlackPawnEventConsumer;
import org.moshe.arad.kafka.consumers.events.TurnNotPassedWhitePawnCameBackEventConsumer;
import org.moshe.arad.kafka.consumers.events.TurnNotPassedWhitePawnTakenOutEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserAddedAsSecondPlayerEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserAddedAsWatcherEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserMadeInvalidMoveEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserMadeLastMoveEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserMadeMoveEventConsumer;
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
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedOpenByLeftFirstEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedOpenByLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedSecondLeftEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedSecondLeftFirstEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedSecondLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedWatcherLeftEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserPermissionsUpdatedWatcherLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.WatcherLeftEventConsumer;
import org.moshe.arad.kafka.consumers.events.WatcherLeftLastEventConsumer;
import org.moshe.arad.kafka.consumers.events.WhiteAteBlackPawnEventConsumer;
import org.moshe.arad.kafka.consumers.events.WhitePawnCameBackAndAteBlackPawnEventConsumer;
import org.moshe.arad.kafka.consumers.events.WhitePawnCameBackEventConsumer;
import org.moshe.arad.kafka.consumers.events.WhitePawnTakenOutEventConsumer;
import org.moshe.arad.kafka.consumers.events.WinnerMoveMadeEventConsumer;
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
	
	//*** begin to users with saving ****
	private PullEventsWithSavingCommandsConsumer pullEventsWithSavingCommandsConsumer;	
	
	@Autowired
	private PullEventsWithSavingCommandConfig pullEventsWithSavingCommandConfig;
	
	@Autowired
	private SimpleEventsProducer<BackgammonEvent> fromMongoEventsWithSavingProducer;
	//*** end to users without saving ****
	
	//*** begin to lobby with saving ****
	private PullEventsWithSavingCommandsConsumer toLobbypullEventsWithSavingCommandsConsumer;	
	
	@Autowired
	private PullEventsWithSavingCommandConfig toLobbypullEventsWithSavingCommandConfig;
	
	@Autowired
	private SimpleEventsProducer<BackgammonEvent> toLobbyfromMongoEventsWithSavingProducer;
	//*** end to users with saving ****
	
	private LoggedInEventConsumer loggedInEventConsumer;
	
	@Autowired
	private LoggedInEventConfig loggedInEventConfig;
	
	private ExistingUserJoinedLobbyEventConsumer existingUserJoinedLobbyEventConsumer;
	
	@Autowired
	private ExistingUserJoinedLobbyEventConfig existingUserJoinedLobbyEventConfig;
	
	//*** begin to users without saving ****
	private PullEventsWithoutSavingCommandsConsumer pullEventsWithoutSavingCommandsConsumer;
	
	@Autowired
	private PullEventsWithoutSavingCommandConfig pullEventsWithoutSavingCommandConfig;
	
	@Autowired
	private SimpleEventsProducer<BackgammonEvent> fromMongoEventsWithoutSavingProducer;
	//*** end to users without saving ****
	
	//*** begin to lobby without saving ****
	private PullEventsWithoutSavingCommandsConsumer toLobbyPullEventsWithoutSavingCommandsConsumer;
	
	@Autowired
	private ToLobbyPullEventsWithoutSavingCommandConfig toLobbyPullEventsWithoutSavingCommandConfig;
	
	@Autowired
	private SimpleEventsProducer<BackgammonEvent> toLobbyfromMongoEventsWithoutSavingProducer;
	
	//*** end to lobby without saving ****
	
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
	
	private OpenByLeftFirstEventConsumer openByLeftFirstEventConsumer;
	
	@Autowired
	private OpenByLeftFirstEventConfig openByLeftFirstEventConfig;
	
	private UserPermissionsUpdatedOpenByLeftFirstEventConsumer userPermissionsUpdatedOpenByLeftFirstEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedOpenByLeftFirstEventConfig userPermissionsUpdatedOpenByLeftFirstEventConfig;
	
	private SecondLeftFirstEventConsumer secondLeftFirstEventConsumer;
	
	@Autowired
	private SecondLeftFirstEventConfig secondLeftFirstEventConfig;
	
	private UserPermissionsUpdatedSecondLeftFirstEventConsumer userPermissionsUpdatedSecondLeftFirstEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedSecondLeftFirstEventConfig userPermissionsUpdatedSecondLeftFirstEventConfig;
	
	private SecondLeftEventConsumer secondLeftEventConsumer;
	
	@Autowired
	private SecondLeftEventConfig secondLeftEventConfig;
	
	private UserPermissionsUpdatedSecondLeftEventConsumer userPermissionsUpdatedSecondLeftEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedSecondLeftEventConfig userPermissionsUpdatedSecondLeftEventConfig;
	
	private OpenByLeftLastEventConsumer openByLeftLastEventConsumer;
	
	@Autowired
	private OpenByLeftLastEventConfig openByLeftLastEventConfig;
	
	private UserPermissionsUpdatedOpenByLeftLastEventConsumer userPermissionsUpdatedOpenByLeftLastEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedOpenByLeftLastEventConfig userPermissionsUpdatedOpenByLeftLastEventConfig;
	
	private GameRoomClosedOpenByLeftLastEventConsumer gameRoomClosedOpenByLeftLastEventConsumer;
	
	@Autowired
	private GameRoomClosedOpenByLeftLastEventConfig gameRoomClosedOpenByLeftLastEventConfig;
	
	private SecondLeftLastEventConsumer secondLeftLastEventConsumer;
	
	@Autowired
	private SecondLeftLastEventConfig secondLeftLastEventConfig;
	
	private UserPermissionsUpdatedSecondLeftLastEventConsumer userPermissionsUpdatedSecondLeftLastEventConsumer;
	
	@Autowired
	private UserPermissionsUpdatedSecondLeftLastEventConfig userPermissionsUpdatedSecondLeftLastEventConfig;
	
	private GameRoomClosedSecondLeftLastEventConsumer gameRoomClosedSecondLeftLastEventConsumer;
	
	@Autowired
	private GameRoomClosedSecondLeftLastEventConfig gameRoomClosedSecondLeftLastEventConfig;
	
	private InitGameRoomCompletedEventConsumer initGameRoomCompletedEventConsumer;
	
	@Autowired
	private InitGameRoomCompletedEventConfig initGameRoomCompletedEventConfig;
	
	private GameStartedEventConsumer gameStartedEventConsumer;
	
	@Autowired
	private GameStartedEventConfig gameStartedEventConfig;
	
	private InitDiceCompletedEventConsumer initDiceCompletedEventConsumer;
	
	@Autowired
	private InitDiceCompletedEventConfig initDiceCompletedEventConfig;
	
	private RollDiceGameRoomFoundEventConsumer rollDiceGameRoomFoundEventConsumer;
	
	@Autowired
	private RollDiceGameRoomFoundEventConfig rollDiceGameRoomFoundEventConfig;
	
	private DiceRolledEventConsumer diceRolledEventConsumer;
	
	@Autowired
	private DiceRolledEventConfig diceRolledEventConfig;
	
	private UserMadeInvalidMoveEventConsumer userMadeInvalidMoveEventConsumer;
	
	@Autowired
	private UserMadeInvalidMoveConfig userMadeInvalidMoveConfig;
	
	private WhitePawnCameBackEventConsumer whitePawnCameBackEventConsumer;
	
	@Autowired
	private WhitePawnCameBackEventConfig whitePawnCameBackEventConfig;
	
	private BlackPawnCameBackEventConsumer blackPawnCameBackEventConsumer;
	
	@Autowired
	private BlackPawnCameBackEventConfig blackPawnCameBackEventConfig;
	
	private WhitePawnTakenOutEventConsumer whitePawnTakenOutEventConsumer;
	
	@Autowired
	private WhitePawnTakenOutEventConfig whitePawnTakenOutEventConfig;
	
	private BlackPawnTakenOutEventConsumer blackPawnTakenOutEventConsumer;
	
	@Autowired
	private BlackPawnTakenOutEventConfig blackPawnTakenOutEventConfig;
	
	private BlackAteWhitePawnEventConsumer blackAteWhitePawnEventConsumer;
	
	@Autowired
	private BlackAteWhitePawnEventConfig blackAteWhitePawnEventConfig;
	
	private WhiteAteBlackPawnEventConsumer whiteAteBlackPawnEventConsumer;
	
	@Autowired
	private WhiteAteBlackPawnEventConfig whiteAteBlackPawnEventConfig;
	
	private UserMadeMoveEventConsumer userMadeMoveEventConsumer;
	
	@Autowired
	private UserMadeMoveConfig userMadeMoveConfig;
	
	private LastMoveWhitePawnCameBackEventConsumer lastMoveWhitePawnCameBackEventConsumer;
	
	@Autowired
	private LastMoveWhitePawnCameBackEventConfig lastMoveWhitePawnCameBackEventConfig;
	
	private TurnNotPassedWhitePawnCameBackEventConsumer turnNotPassedWhitePawnCameBackEventConsumer;
	
	@Autowired
	private TurnNotPassedWhitePawnCameBackEventConfig turnNotPassedWhitePawnCameBackEventConfig;
	
	private LastMoveBlackPawnCameBackEventConsumer lastMoveBlackPawnCameBackEventConsumer;
	
	@Autowired
	private LastMoveBlackPawnCameBackEventConfig lastMoveBlackPawnCameBackEventConfig;
	
	private TurnNotPassedBlackPawnCameBackEventConsumer turnNotPassedBlackPawnCameBackEventConsumer;
	
	@Autowired
	private TurnNotPassedBlackPawnCameBackEventConfig turnNotPassedBlackPawnCameBackEventConfig;
	
	private LastMoveWhitePawnTakenOutEventConsumer lastMoveWhitePawnTakenOutEventConsumer;
	
	@Autowired
	private LastMoveWhitePawnTakenOutEventConfig lastMoveWhitePawnTakenOutEventConfig;
	
	private TurnNotPassedWhitePawnTakenOutEventConsumer turnNotPassedWhitePawnTakenOutEventConsumer;
	
	@Autowired
	private TurnNotPassedWhitePawnTakenOutEventConfig turnNotPassedWhitePawnTakenOutEventConfig;
	
	private LastMoveBlackPawnTakenOutEventConsumer lastMoveBlackPawnTakenOutEventConsumer;
	
	@Autowired
	private LastMoveBlackPawnTakenOutEventConfig lastMoveBlackPawnTakenOutEventConfig;
	
	private TurnNotPassedBlackPawnTakenOutEventConsumer turnNotPassedBlackPawnTakenOutEventConsumer;
	
	@Autowired
	private TurnNotPassedBlackPawnTakenOutEventConfig turnNotPassedBlackPawnTakenOutEventConfig;
	
	private LastMoveBlackAteWhitePawnEventConsumer lastMoveBlackAteWhitePawnEventConsumer;
	
	@Autowired
	private LastMoveBlackAteWhitePawnEventConfig lastMoveBlackAteWhitePawnEventConfig;
	
	private TurnNotPassedBlackAteWhitePawnEventConsumer turnNotPassedBlackAteWhitePawnEventConsumer;
	
	@Autowired
	private TurnNotPassedBlackAteWhitePawnEventConfig turnNotPassedBlackAteWhitePawnEventConfig;
	
	private LastMoveWhiteAteBlackPawnEventConsumer lastMoveWhiteAteBlackPawnEventConsumer;
	
	@Autowired
	private LastMoveWhiteAteBlackPawnEventConfig lastMoveWhiteAteBlackPawnEventConfig;
	
	private TurnNotPassedWhiteAteBlackPawnEventConsumer turnNotPassedWhiteAteBlackPawnEventConsumer;
	
	@Autowired
	private TurnNotPassedWhiteAteBlackPawnEventConfig turnNotPassedWhiteAteBlackPawnEventConfig;
	
	private UserMadeLastMoveEventConsumer userMadeLastMoveEventConsumer;
	
	@Autowired
	private UserMadeLastMoveConfig userMadeLastMoveConfig;
	
	private TurnNotPassedUserMadeMoveEventConsumer turnNotPassedUserMadeMoveEventConsumer;
	
	private WhitePawnCameBackAndAteBlackPawnEventConsumer whitePawnCameBackAndAteBlackPawnEventConsumer;
	
	@Autowired
	private WhitePawnCameBackAndAteBlackPawnEventConfig whitePawnCameBackAndAteBlackPawnEventConfig;
	
	@Autowired
	private TurnNotPassedUserMadeMoveConfig turnNotPassedUserMadeMoveConfig;
	
	private LastMoveWhitePawnCameBackAndAteBlackPawnEventConsumer lastMoveWhitePawnCameBackAndAteBlackPawnEventConsumer;
	
	@Autowired
	private LastMoveWhitePawnCameBackAndAteBlackPawnEventConfig lastMoveWhitePawnCameBackAndAteBlackPawnEventConfig;
	
	private TurnNotPassedWhitePawnCameBackAndAteBlackPawnEventConsumer turnNotPassedWhitePawnCameBackAndAteBlackPawnEventConsumer;
	
	@Autowired
	private TurnNotPassedWhitePawnCameBackAndAteBlackPawnEventConfig turnNotPassedWhitePawnCameBackAndAteBlackPawnEventConfig;
	
	private BlackPawnCameBackAndAteWhitePawnEventConsumer blackPawnCameBackAndAteWhitePawnEventConsumer;
	
	@Autowired
	private BlackPawnCameBackAndAteWhitePawnEventConfig blackPawnCameBackAndAteWhitePawnEventConfig;
	
	private LastMoveBlackPawnCameBackAndAteWhitePawnEventConsumer lastMoveBlackPawnCameBackAndAteWhitePawnEventConsumer;
	
	@Autowired
	private LastMoveBlackPawnCameBackAndAteWhitePawnEventConfig lastMoveBlackPawnCameBackAndAteWhitePawnEventConfig;
	
	private TurnNotPassedBlackPawnCameBackAndAteWhitePawnEventConsumer turnNotPassedBlackPawnCameBackAndAteWhitePawnEventConsumer;
	
	@Autowired
	private TurnNotPassedBlackPawnCameBackAndAteWhitePawnEventConfig turnNotPassedBlackPawnCameBackAndAteWhitePawnEventConfig;
	
	private DiceRolledCanNotPlayEventConsumer diceRolledCanNotPlayEventConsumer;
	
	@Autowired
	private DiceRolledCanNotPlayEventConfig diceRolledCanNotPlayEventConfig;
	
	private WinnerMoveMadeEventConsumer winnerMoveMadeEventConsumer;
	
	@Autowired
	private WinnerMoveMadeEventConfig winnerMoveMadeEventConfig;
	
	private LoggedOutOpenByLeftFirstGameStoppedEventConsumer loggedOutOpenByLeftFirstGameStoppedEventConsumer;
	
	@Autowired
	private LoggedOutOpenByLeftFirstGameStoppedEventConfig loggedOutOpenByLeftFirstGameStoppedEventConfig;
	
	private LoggedOutSecondLeftFirstGameStoppedEventConsumer loggedOutSecondLeftFirstGameStoppedEventConsumer;
	
	@Autowired
	private LoggedOutSecondLeftFirstGameStoppedEventConfig loggedOutSecondLeftFirstGameStoppedEventConfig;
	
	private OpenByLeftFirstGameStoppedEventConsumer openByLeftFirstGameStoppedEventConsumer;
	
	@Autowired
	private OpenByLeftFirstGameStoppedEventConfig openByLeftFirstGameStoppedEventConfig;
	
	private SecondLeftFirstGameStoppedEventConsumer secondLeftFirstGameStoppedEventConsumer;
	
	@Autowired
	private SecondLeftFirstGameStoppedEventConfig secondLeftFirstGameStoppedEventConfig;
	
	private ExecutorService executor = Executors.newFixedThreadPool(6);
	
	private Logger logger = LoggerFactory.getLogger(AppInit.class);
	
	private ConsumerToProducerQueue pullEventsWithSavingQueue;
	
	private ConsumerToProducerQueue pullEventsWithoutSavingQueue;
	
	private ConsumerToProducerQueue toLobbypullEventsWithoutSavingQueue;
	
	private ConsumerToProducerQueue toLobbypullEventsWithSavingQueue;
	
	private ApplicationContext context;
	
	public static final int NUM_CONSUMERS = 3;
	
	public AppInit() {		
	}
	
	@Override
	public void initKafkaCommandsConsumers() {
		pullEventsWithSavingQueue = context.getBean(ConsumerToProducerQueue.class);
		pullEventsWithoutSavingQueue = context.getBean(ConsumerToProducerQueue.class);
		toLobbypullEventsWithoutSavingQueue = context.getBean(ConsumerToProducerQueue.class);
		toLobbypullEventsWithSavingQueue = context.getBean(ConsumerToProducerQueue.class);
		
		for(int i=0; i<NUM_CONSUMERS; i++){
			pullEventsWithSavingCommandsConsumer = context.getBean(PullEventsWithSavingCommandsConsumer.class);
			pullEventsWithoutSavingCommandsConsumer = context.getBean(PullEventsWithoutSavingCommandsConsumer.class);
			toLobbyPullEventsWithoutSavingCommandsConsumer = context.getBean(PullEventsWithoutSavingCommandsConsumer.class);
			toLobbypullEventsWithSavingCommandsConsumer = context.getBean(PullEventsWithSavingCommandsConsumer.class);
			
			logger.info("Initializing pull events commands consumer...");
			initSingleConsumer(pullEventsWithSavingCommandsConsumer, KafkaUtils.PULL_EVENTS_WITH_SAVING_COMMAND_TOPIC, pullEventsWithSavingCommandConfig, pullEventsWithSavingQueue);
			
			initSingleConsumer(pullEventsWithoutSavingCommandsConsumer, KafkaUtils.PULL_EVENTS_WITHOUT_SAVING_COMMAND_TOPIC, pullEventsWithoutSavingCommandConfig, pullEventsWithoutSavingQueue);
			
			initSingleConsumer(toLobbyPullEventsWithoutSavingCommandsConsumer, KafkaUtils.LOBBY_SERVICE_PULL_EVENTS_WITHOUT_SAVING_COMMAND_TOPIC, toLobbyPullEventsWithoutSavingCommandConfig, toLobbypullEventsWithoutSavingQueue);
			
			initSingleConsumer(toLobbypullEventsWithSavingCommandsConsumer, KafkaUtils.LOBBY_SERVICE_PULL_EVENTS_WITH_SAVING_COMMAND_TOPIC, toLobbypullEventsWithSavingCommandConfig, toLobbypullEventsWithSavingQueue);
			logger.info("Initializing pull events commands consumer...");
			
			executeProducersAndConsumers(Arrays.asList(pullEventsWithSavingCommandsConsumer, 
					pullEventsWithoutSavingCommandsConsumer,
					toLobbyPullEventsWithoutSavingCommandsConsumer,
					toLobbypullEventsWithSavingCommandsConsumer));
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
			
			openByLeftFirstEventConsumer = context.getBean(OpenByLeftFirstEventConsumer.class);
			initSingleConsumer(openByLeftFirstEventConsumer, KafkaUtils.OPENBY_LEFT_FIRST_EVENT_TOPIC, openByLeftFirstEventConfig, null);
			
			userPermissionsUpdatedOpenByLeftFirstEventConsumer = context.getBean(UserPermissionsUpdatedOpenByLeftFirstEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedOpenByLeftFirstEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_OPENBY_LEFT_FIRST_EVENT_TOPIC, userPermissionsUpdatedOpenByLeftFirstEventConfig, null);
			
			secondLeftFirstEventConsumer = context.getBean(SecondLeftFirstEventConsumer.class);
			initSingleConsumer(secondLeftFirstEventConsumer, KafkaUtils.SECOND_LEFT_FIRST_EVENT_TOPIC, secondLeftFirstEventConfig, null);
			
			userPermissionsUpdatedSecondLeftFirstEventConsumer = context.getBean(UserPermissionsUpdatedSecondLeftFirstEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedSecondLeftFirstEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_SECOND_LEFT_FIRST_EVENT_TOPIC, userPermissionsUpdatedSecondLeftFirstEventConfig, null);
			
			secondLeftEventConsumer = context.getBean(SecondLeftEventConsumer.class);
			initSingleConsumer(secondLeftEventConsumer, KafkaUtils.SECOND_LEFT_EVENT_TOPIC, secondLeftEventConfig, null);
			
			userPermissionsUpdatedSecondLeftEventConsumer = context.getBean(UserPermissionsUpdatedSecondLeftEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedSecondLeftEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_SECOND_LEFT_EVENT_TOPIC, userPermissionsUpdatedSecondLeftEventConfig, null);
			
			openByLeftLastEventConsumer = context.getBean(OpenByLeftLastEventConsumer.class);
			initSingleConsumer(openByLeftLastEventConsumer, KafkaUtils.OPENBY_LEFT_LAST_EVENT_TOPIC, openByLeftLastEventConfig, null);
			
			userPermissionsUpdatedOpenByLeftLastEventConsumer = context.getBean(UserPermissionsUpdatedOpenByLeftLastEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedOpenByLeftLastEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_OPENBY_LEFT_LAST_EVENT_TOPIC, userPermissionsUpdatedOpenByLeftLastEventConfig, null);
			
			gameRoomClosedOpenByLeftLastEventConsumer = context.getBean(GameRoomClosedOpenByLeftLastEventConsumer.class);
			initSingleConsumer(gameRoomClosedOpenByLeftLastEventConsumer, KafkaUtils.GAME_ROOM_CLOSED_OPENBY_LEFT_LAST_EVENT_TOPIC, gameRoomClosedOpenByLeftLastEventConfig, null);
			
			secondLeftLastEventConsumer = context.getBean(SecondLeftLastEventConsumer.class);
			initSingleConsumer(secondLeftLastEventConsumer, KafkaUtils.SECOND_LEFT_LAST_EVENT_TOPIC, secondLeftLastEventConfig, null);
			
			userPermissionsUpdatedSecondLeftLastEventConsumer = context.getBean(UserPermissionsUpdatedSecondLeftLastEventConsumer.class);
			initSingleConsumer(userPermissionsUpdatedSecondLeftLastEventConsumer, KafkaUtils.USER_PERMISSIONS_UPDATED_SECOND_LEFT_LAST_EVENT_TOPIC, userPermissionsUpdatedSecondLeftLastEventConfig, null);
			
			gameRoomClosedSecondLeftLastEventConsumer = context.getBean(GameRoomClosedSecondLeftLastEventConsumer.class);
			initSingleConsumer(gameRoomClosedSecondLeftLastEventConsumer, KafkaUtils.GAME_ROOM_CLOSED_SECOND_LEFT_LAST_EVENT_TOPIC, gameRoomClosedSecondLeftLastEventConfig, null);
			
			initGameRoomCompletedEventConsumer = context.getBean(InitGameRoomCompletedEventConsumer.class);
			initSingleConsumer(initGameRoomCompletedEventConsumer, KafkaUtils.INIT_GAME_ROOM_COMPLETED_EVENT_TOPIC, initGameRoomCompletedEventConfig, null);
			
			gameStartedEventConsumer = context.getBean(GameStartedEventConsumer.class);
			initSingleConsumer(gameStartedEventConsumer, KafkaUtils.GAME_STARTED_EVENT_TOPIC, gameStartedEventConfig, null);
			
			initDiceCompletedEventConsumer = context.getBean(InitDiceCompletedEventConsumer.class);
			initSingleConsumer(initDiceCompletedEventConsumer, KafkaUtils.INIT_DICE_COMPLETED_EVENT_TOPIC, initDiceCompletedEventConfig, null);
			
			rollDiceGameRoomFoundEventConsumer = context.getBean(RollDiceGameRoomFoundEventConsumer.class);
			initSingleConsumer(rollDiceGameRoomFoundEventConsumer, KafkaUtils.ROLL_DICE_GAME_ROOM_FOUND_EVENT_TOPIC, rollDiceGameRoomFoundEventConfig, null);
			
			diceRolledEventConsumer = context.getBean(DiceRolledEventConsumer.class);
			initSingleConsumer(diceRolledEventConsumer, KafkaUtils.DICE_ROLLED_EVENT_TOPIC, diceRolledEventConfig, null);
			
			userMadeInvalidMoveEventConsumer = context.getBean(UserMadeInvalidMoveEventConsumer.class);
			initSingleConsumer(userMadeInvalidMoveEventConsumer, KafkaUtils.USER_MADE_INVALID_MOVE_EVENT_TOPIC, userMadeInvalidMoveConfig, null);
			
			whitePawnCameBackEventConsumer = context.getBean(WhitePawnCameBackEventConsumer.class);
			initSingleConsumer(whitePawnCameBackEventConsumer, KafkaUtils.WHITE_PAWN_CAME_BACK_EVENT_TOPIC, whitePawnCameBackEventConfig, null);
			
			blackPawnCameBackEventConsumer = context.getBean(BlackPawnCameBackEventConsumer.class);
			initSingleConsumer(blackPawnCameBackEventConsumer, KafkaUtils.BLACK_PAWN_CAME_BACK_EVENT_TOPIC, blackPawnCameBackEventConfig, null);
			
			whitePawnTakenOutEventConsumer = context.getBean(WhitePawnTakenOutEventConsumer.class);
			initSingleConsumer(whitePawnTakenOutEventConsumer, KafkaUtils.WHITE_PAWN_TAKEN_OUT_EVENT_TOPIC, whitePawnTakenOutEventConfig, null);
			
			blackPawnTakenOutEventConsumer = context.getBean(BlackPawnTakenOutEventConsumer.class);
			initSingleConsumer(blackPawnTakenOutEventConsumer, KafkaUtils.BLACK_PAWN_TAKEN_OUT_EVENT_TOPIC, blackPawnTakenOutEventConfig, null);
			
			blackAteWhitePawnEventConsumer = context.getBean(BlackAteWhitePawnEventConsumer.class);
			initSingleConsumer(blackAteWhitePawnEventConsumer, KafkaUtils.BLACK_ATE_WHITE_PAWN_EVENT_TOPIC, blackAteWhitePawnEventConfig, null);
			
			whiteAteBlackPawnEventConsumer = context.getBean(WhiteAteBlackPawnEventConsumer.class);
			initSingleConsumer(whiteAteBlackPawnEventConsumer, KafkaUtils.WHITE_ATE_BLACK_PAWN_EVENT_TOPIC, whiteAteBlackPawnEventConfig, null);
			
			userMadeMoveEventConsumer = context.getBean(UserMadeMoveEventConsumer.class);
			initSingleConsumer(userMadeMoveEventConsumer, KafkaUtils.USER_MADE_MOVE_EVENT_TOPIC, userMadeMoveConfig, null);
			
			lastMoveWhitePawnCameBackEventConsumer = context.getBean(LastMoveWhitePawnCameBackEventConsumer.class);
			initSingleConsumer(lastMoveWhitePawnCameBackEventConsumer, KafkaUtils.LAST_MOVE_WHITE_PAWN_CAME_BACK_EVENT_TOPIC, lastMoveWhitePawnCameBackEventConfig, null);
			
			turnNotPassedWhitePawnCameBackEventConsumer = context.getBean(TurnNotPassedWhitePawnCameBackEventConsumer.class);
			initSingleConsumer(turnNotPassedWhitePawnCameBackEventConsumer, KafkaUtils.TURN_NOT_PASSED_WHITE_PAWN_CAME_BACK_EVENT_TOPIC, turnNotPassedWhitePawnCameBackEventConfig, null);
			
			lastMoveBlackPawnCameBackEventConsumer = context.getBean(LastMoveBlackPawnCameBackEventConsumer.class);
			initSingleConsumer(lastMoveBlackPawnCameBackEventConsumer, KafkaUtils.LAST_MOVE_BLACK_PAWN_CAME_BACK_EVENT_TOPIC, lastMoveBlackPawnCameBackEventConfig, null);
			
			turnNotPassedBlackPawnCameBackEventConsumer = context.getBean(TurnNotPassedBlackPawnCameBackEventConsumer.class);
			initSingleConsumer(turnNotPassedBlackPawnCameBackEventConsumer, KafkaUtils.TURN_NOT_PASSED_BLACK_PAWN_CAME_BACK_EVENT_TOPIC, turnNotPassedBlackPawnCameBackEventConfig, null);
			
			lastMoveWhitePawnTakenOutEventConsumer = context.getBean(LastMoveWhitePawnTakenOutEventConsumer.class);
			initSingleConsumer(lastMoveWhitePawnTakenOutEventConsumer, KafkaUtils.LAST_MOVE_WHITE_PAWN_TAKEN_OUT_EVENT_TOPIC, lastMoveWhitePawnTakenOutEventConfig, null);
			
			turnNotPassedWhitePawnTakenOutEventConsumer = context.getBean(TurnNotPassedWhitePawnTakenOutEventConsumer.class);
			initSingleConsumer(turnNotPassedWhitePawnTakenOutEventConsumer, KafkaUtils.TURN_NOT_PASSED_WHITE_PAWN_TAKEN_OUT_EVENT_TOPIC, turnNotPassedWhitePawnTakenOutEventConfig, null);
			
			lastMoveBlackPawnTakenOutEventConsumer = context.getBean(LastMoveBlackPawnTakenOutEventConsumer.class);
			initSingleConsumer(lastMoveBlackPawnTakenOutEventConsumer, KafkaUtils.LAST_MOVE_BLACK_PAWN_TAKEN_OUT_EVENT_TOPIC, lastMoveBlackPawnTakenOutEventConfig, null);
			
			turnNotPassedBlackPawnTakenOutEventConsumer = context.getBean(TurnNotPassedBlackPawnTakenOutEventConsumer.class);
			initSingleConsumer(turnNotPassedBlackPawnTakenOutEventConsumer, KafkaUtils.TURN_NOT_PASSED_BLACK_PAWN_TAKEN_OUT_EVENT_TOPIC, turnNotPassedBlackPawnTakenOutEventConfig, null);
			
			lastMoveBlackAteWhitePawnEventConsumer = context.getBean(LastMoveBlackAteWhitePawnEventConsumer.class);
			initSingleConsumer(lastMoveBlackAteWhitePawnEventConsumer, KafkaUtils.LAST_MOVE_BLACK_ATE_WHITE_PAWN_EVENT_TOPIC, lastMoveBlackAteWhitePawnEventConfig, null);
			
			turnNotPassedBlackAteWhitePawnEventConsumer = context.getBean(TurnNotPassedBlackAteWhitePawnEventConsumer.class);
			initSingleConsumer(turnNotPassedBlackAteWhitePawnEventConsumer, KafkaUtils.TURN_NOT_PASSED_BLACK_ATE_WHITE_PAWN_EVENT_TOPIC, turnNotPassedBlackAteWhitePawnEventConfig, null);
			
			lastMoveWhiteAteBlackPawnEventConsumer = context.getBean(LastMoveWhiteAteBlackPawnEventConsumer.class);
			initSingleConsumer(lastMoveWhiteAteBlackPawnEventConsumer, KafkaUtils.LAST_MOVE_WHITE_ATE_BLACK_PAWN_EVENT_TOPIC, lastMoveWhiteAteBlackPawnEventConfig, null);
			
			turnNotPassedWhiteAteBlackPawnEventConsumer = context.getBean(TurnNotPassedWhiteAteBlackPawnEventConsumer.class);
			initSingleConsumer(turnNotPassedWhiteAteBlackPawnEventConsumer, KafkaUtils.TURN_NOT_PASSED_WHITE_ATE_BLACK_PAWN_EVENT_TOPIC, turnNotPassedWhiteAteBlackPawnEventConfig, null);
			
			userMadeLastMoveEventConsumer = context.getBean(UserMadeLastMoveEventConsumer.class);
			initSingleConsumer(userMadeLastMoveEventConsumer, KafkaUtils.USER_MADE_LAST_MOVE_EVENT_TOPIC, userMadeLastMoveConfig, null);
			
			turnNotPassedUserMadeMoveEventConsumer = context.getBean(TurnNotPassedUserMadeMoveEventConsumer.class);
			initSingleConsumer(turnNotPassedUserMadeMoveEventConsumer, KafkaUtils.TURN_NOT_PASSED_USER_MADE_MOVE_EVENT_TOPIC, turnNotPassedUserMadeMoveConfig, null);
			
			whitePawnCameBackAndAteBlackPawnEventConsumer = context.getBean(WhitePawnCameBackAndAteBlackPawnEventConsumer.class);
			initSingleConsumer(whitePawnCameBackAndAteBlackPawnEventConsumer, KafkaUtils.WHITE_PAWN_CAME_BACK_AND_ATE_BLACK_PAWN_EVENT_TOPIC, whitePawnCameBackAndAteBlackPawnEventConfig, null);
			
			lastMoveWhitePawnCameBackAndAteBlackPawnEventConsumer = context.getBean(LastMoveWhitePawnCameBackAndAteBlackPawnEventConsumer.class);
			initSingleConsumer(lastMoveWhitePawnCameBackAndAteBlackPawnEventConsumer, KafkaUtils.LAST_MOVE_WHITE_PAWN_CAME_BACK_AND_ATE_BLACK_PAWN_EVENT_TOPIC, lastMoveWhitePawnCameBackAndAteBlackPawnEventConfig, null);
			
			turnNotPassedWhitePawnCameBackAndAteBlackPawnEventConsumer = context.getBean(TurnNotPassedWhitePawnCameBackAndAteBlackPawnEventConsumer.class);
			initSingleConsumer(turnNotPassedWhitePawnCameBackAndAteBlackPawnEventConsumer, KafkaUtils.TURN_NOT_PASSED_WHITE_PAWN_CAME_BACK_AND_ATE_BLACK_PAWN_EVENT_TOPIC, turnNotPassedWhitePawnCameBackAndAteBlackPawnEventConfig, null);
			
			blackPawnCameBackAndAteWhitePawnEventConsumer = context.getBean(BlackPawnCameBackAndAteWhitePawnEventConsumer.class);
			initSingleConsumer(blackPawnCameBackAndAteWhitePawnEventConsumer, KafkaUtils.BLACK_PAWN_CAME_BACK_AND_ATE_WHITE_PAWN_EVENT_TOPIC, blackPawnCameBackAndAteWhitePawnEventConfig, null);
			
			lastMoveBlackPawnCameBackAndAteWhitePawnEventConsumer = context.getBean(LastMoveBlackPawnCameBackAndAteWhitePawnEventConsumer.class);
			initSingleConsumer(lastMoveBlackPawnCameBackAndAteWhitePawnEventConsumer, KafkaUtils.LAST_MOVE_BLACK_PAWN_CAME_BACK_AND_ATE_WHITE_PAWN_EVENT_TOPIC, lastMoveBlackPawnCameBackAndAteWhitePawnEventConfig, null);
			
			turnNotPassedBlackPawnCameBackAndAteWhitePawnEventConsumer = context.getBean(TurnNotPassedBlackPawnCameBackAndAteWhitePawnEventConsumer.class);
			initSingleConsumer(turnNotPassedBlackPawnCameBackAndAteWhitePawnEventConsumer, KafkaUtils.TURN_NOT_PASSED_BLACK_PAWN_CAME_BACK_AND_ATE_WHITE_PAWN_EVENT_TOPIC, turnNotPassedBlackPawnCameBackAndAteWhitePawnEventConfig, null);
			
			diceRolledCanNotPlayEventConsumer = context.getBean(DiceRolledCanNotPlayEventConsumer.class);
			initSingleConsumer(diceRolledCanNotPlayEventConsumer, KafkaUtils.DICE_ROLLED_CAN_NOT_PLAY_EVENT_TOPIC, diceRolledCanNotPlayEventConfig, null);
			
			winnerMoveMadeEventConsumer = context.getBean(WinnerMoveMadeEventConsumer.class);
			initSingleConsumer(winnerMoveMadeEventConsumer, KafkaUtils.WINNER_MOVE_MADE_EVENT_TOPIC, winnerMoveMadeEventConfig, null);
			
			loggedOutOpenByLeftFirstGameStoppedEventConsumer = context.getBean(LoggedOutOpenByLeftFirstGameStoppedEventConsumer.class);
			initSingleConsumer(loggedOutOpenByLeftFirstGameStoppedEventConsumer, KafkaUtils.LOGGED_OUT_OPENBY_LEFT_FIRST_GAME_STOPPED_EVENT_TOPIC, loggedOutOpenByLeftFirstGameStoppedEventConfig, null);
			
			loggedOutSecondLeftFirstGameStoppedEventConsumer = context.getBean(LoggedOutSecondLeftFirstGameStoppedEventConsumer.class);
			initSingleConsumer(loggedOutSecondLeftFirstGameStoppedEventConsumer, KafkaUtils.LOGGED_OUT_SECOND_LEFT_FIRST_GAME_STOPPED_EVENT_TOPIC, loggedOutSecondLeftFirstGameStoppedEventConfig, null);
			
			openByLeftFirstGameStoppedEventConsumer = context.getBean(OpenByLeftFirstGameStoppedEventConsumer.class);
			initSingleConsumer(openByLeftFirstGameStoppedEventConsumer, KafkaUtils.OPENBY_LEFT_FIRST_GAME_STOPPED_EVENT_TOPIC, openByLeftFirstGameStoppedEventConfig, null);
			
			secondLeftFirstGameStoppedEventConsumer = context.getBean(SecondLeftFirstGameStoppedEventConsumer.class);
			initSingleConsumer(secondLeftFirstGameStoppedEventConsumer, KafkaUtils.SECOND_LEFT_FIRST_GAME_STOPPED_EVENT_TOPIC, secondLeftFirstGameStoppedEventConfig, null);
			
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
					userPermissionsUpdatedWatcherLeftEventConsumer,
					openByLeftFirstEventConsumer,
					userPermissionsUpdatedOpenByLeftFirstEventConsumer,
					secondLeftFirstEventConsumer,
					userPermissionsUpdatedSecondLeftFirstEventConsumer,
					secondLeftEventConsumer,
					userPermissionsUpdatedSecondLeftEventConsumer,
					openByLeftLastEventConsumer,
					userPermissionsUpdatedOpenByLeftLastEventConsumer,
					gameRoomClosedOpenByLeftLastEventConsumer,
					secondLeftLastEventConsumer,
					userPermissionsUpdatedSecondLeftLastEventConsumer,
					gameRoomClosedSecondLeftLastEventConsumer,
					initGameRoomCompletedEventConsumer,
					gameStartedEventConsumer,
					initDiceCompletedEventConsumer,
					rollDiceGameRoomFoundEventConsumer,
					diceRolledEventConsumer,
					userMadeInvalidMoveEventConsumer,
					whitePawnCameBackEventConsumer,
					blackPawnCameBackEventConsumer,
					whitePawnTakenOutEventConsumer,
					blackPawnTakenOutEventConsumer,
					blackAteWhitePawnEventConsumer,
					whiteAteBlackPawnEventConsumer,
					userMadeMoveEventConsumer,
					lastMoveWhitePawnCameBackEventConsumer,
					turnNotPassedWhitePawnCameBackEventConsumer,
					lastMoveBlackPawnCameBackEventConsumer,
					turnNotPassedBlackPawnCameBackEventConsumer,
					lastMoveWhitePawnTakenOutEventConsumer,
					turnNotPassedWhitePawnTakenOutEventConsumer,
					lastMoveBlackPawnTakenOutEventConsumer,
					turnNotPassedBlackPawnTakenOutEventConsumer,
					lastMoveBlackAteWhitePawnEventConsumer,
					turnNotPassedBlackAteWhitePawnEventConsumer,
					lastMoveWhiteAteBlackPawnEventConsumer,
					turnNotPassedWhiteAteBlackPawnEventConsumer,
					userMadeLastMoveEventConsumer,
					turnNotPassedUserMadeMoveEventConsumer,
					whitePawnCameBackAndAteBlackPawnEventConsumer,
					lastMoveWhitePawnCameBackAndAteBlackPawnEventConsumer,
					turnNotPassedWhitePawnCameBackAndAteBlackPawnEventConsumer,
					blackPawnCameBackAndAteWhitePawnEventConsumer,
					lastMoveBlackPawnCameBackAndAteWhitePawnEventConsumer,
					turnNotPassedBlackPawnCameBackAndAteWhitePawnEventConsumer,
					diceRolledCanNotPlayEventConsumer,
					winnerMoveMadeEventConsumer,
					loggedOutOpenByLeftFirstGameStoppedEventConsumer,
					loggedOutSecondLeftFirstGameStoppedEventConsumer,
					openByLeftFirstGameStoppedEventConsumer,
					secondLeftFirstGameStoppedEventConsumer));
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
		
		initSingleProducer(toLobbyfromMongoEventsWithSavingProducer, KafkaUtils.TO_LOBBY_FROM_MONGO_EVENTS_WITH_SAVING_TOPIC, toLobbypullEventsWithSavingQueue);
		
		logger.info("Initialize from mongo to users service events producer, completed...");
		
		executeProducersAndConsumers(Arrays.asList(fromMongoEventsWithSavingProducer, 
				fromMongoEventsWithoutSavingProducer, 
				toLobbyfromMongoEventsWithoutSavingProducer,
				toLobbyfromMongoEventsWithSavingProducer));
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
