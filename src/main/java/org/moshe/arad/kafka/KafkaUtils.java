package org.moshe.arad.kafka;

public class KafkaUtils {

	public static final String SERVERS = "localhost:9092,localhost:9093,localhost:9094";
	public static final String CREATE_NEW_USER_COMMAND_GROUP = "CreateNewUserCommandGroup";
	public static final String STRING_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";
	public static final String STRING_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";
	public static final String CREATE_NEW_USER_COMMAND_DESERIALIZER = "org.moshe.arad.kafka.deserializers.CreateNewUserCommandDeserializer";
	public static final String NEW_USER_CREATED_EVENT_SERIALIZER = "org.moshe.arad.kafka.serializers.NewUserCreatedEventSerializer";
	public static final String COMMANDS_TO_USERS_SERVICE_TOPIC = "Commands-To-Users-Service";
	public static final String NEW_USER_CREATED_EVENT_DESERIALIZER = "org.moshe.arad.kafka.deserializers.NewUserCreatedEventDeserializer";
	public static final String NEW_USER_CREATED_EVENT_TOPIC = "New-User-Created-Event";
	public static final String NEW_USER_JOINED_LOBBY_EVENT_TOPIC = "New-User-Joined-Lobby-Event";
	public static final String NEW_USER_CREATED_EVENT_GROUP = "NewUserCreatedEventGroup1";
	public static final String NEW_USER_JOINED_LOBBY_EVENT_DESERIALIZER = "org.moshe.arad.kafka.deserializers.NewUserJoinedLobbyEventDeserializer";
	public static final String NEW_USER_JOINED_LOBBY_EVENT_GROUP = "NewUserJoinedLobbyEventGroup";
	public static final String FROM_MONGO_EVENTS_WITH_SAVING_TOPIC = "From-Mongo-Events-With-Saving";
	public static final String FROM_MONGO_EVENTS_WITHOUT_SAVING_TOPIC = "From-Mongo-Events-Without-Saving";
	public static final String PULL_EVENTS_WITH_SAVING_COMMAND_TOPIC = "Pull-Events-With-Saving-Command";	
	public static final String PULL_EVENTS_WITHOUT_SAVING_COMMAND_TOPIC = "Pull-Events-Without-Saving-Command";
	public static final String PULL_EVENTS_WITH_SAVING_COMMAND_GROUP = "PullEventsWithSavingCommandGroup";
	public static final String PULL_EVENTS_WITHOUT_SAVING_COMMAND_GROUP = "PullEventsWithoutSavingCommandGroup";
	public static final String PULL_EVENTS_COMMAND_DESERIALIZER = "org.moshe.arad.kafka.deserializers.PullEventsCommandDeserializer";
	public static final String LOGGED_IN_EVENT_GROUP = "LoggedInEventGroup1";
	public static final String LOGGED_IN_EVENT_TOPIC = "Logged-In-Event";
	public static final String EXISTING_USER_JOINED_LOBBY_EVENT_GROUP = "ExistingUserJoinedLobbyEventGroup1";
	public static final String EXISTING_USER_JOINED_LOBBY_EVENT_TOPIC = "Existing-User-Joined-Lobby-Event";
	public static final String LOGGED_OUT_EVENT_TOPIC = "Logged-Out-Event";
	public static final String LOGGED_OUT_EVENT_GROUP = "LoggedOutEventGroup1";
	public static final String LOBBY_SERVICE_PULL_EVENTS_WITH_SAVING_COMMAND_TOPIC = "Lobby-Service-Pull-Events-With-Saving-Command";
	public static final String LOBBY_SERVICE_PULL_EVENTS_WITHOUT_SAVING_COMMAND_TOPIC = "Lobby-Service-Pull-Events-Without-Saving-Command";
	public static final String TO_LOBBY_PULL_EVENTS_WITHOUT_SAVING_COMMAND_GROUP = "ToLobbyPullEventsWithoutSavingCommandGroup";
	public static final String TO_LOBBY_FROM_MONGO_EVENTS_WITHOUT_SAVING_TOPIC = "To-Lobby-From-Mongo-Events-Without-Saving";
	public static final String NEW_GAME_ROOM_OPENED_EVENT_GROUP = "NewGameRoomOpenedEventGroup2";
	public static final String NEW_GAME_ROOM_OPENED_EVENT_TOPIC = "New-Game-Room-Opened-Event";
	public static final String GAME_ROOM_CLOSED_EVENT_GROUP = "GameRoomClosedEventGroup2";
	public static final String GAME_ROOM_CLOSED_EVENT_TOPIC = "Game-Room-Closed-Event";
	public static final String USER_ADDED_AS_WATCHER_EVENT_GROUP = "UserAddedAsWatcherEventGroup2";
	public static final String USER_ADDED_AS_WATCHER_EVENT_TOPIC = "User-Added-As-Watcher-Event";
	public static final String GAME_ROOM_CLOSED_EVENT_LOGOUT_GROUP = "GameRoomClosedEventLogoutGroup1";
	public static final String GAME_ROOM_CLOSED_EVENT_LOGOUT_TOPIC = "Game-Room-Closed-Event-Logout";
	public static final String WATCHER_REMOVED_EVENT_TOPIC = "Watcher-Removed-Event";
	public static final String WATCHER_REMOVED_EVENT_GROUP = "WatcherRemovedEventGroup1";
	public static final String USER_ADDED_AS_SECOND_PLAYER_EVENT_GROUP = "UserAddedAsSecondPlayerEventGroup1";
	public static final String USER_ADDED_AS_SECOND_PLAYER_EVENT_TOPIC = "User-Added-As-Second-Player-Event";
	public static final Object USER_PERMISSIONS_UPDATED_EVENT_GROUP = "UserPermissionsUpdatedEventGroup1";
	public static final String USER_PERMISSIONS_UPDATED_EVENT_TOPIC = "User-Permissions-Updated-Event";
	public static final String USER_PERMISSIONS_UPDATED_ADDED_WATCHER_EVENT_GROUP = "UserPermissionsUpdatedAddedWatcherEventGroup1";
	public static final String USER_PERMISSIONS_UPDATED_USER_ADDED_WATCHER_EVENT_TOPIC = "User-Permissions-Updated-User-Added-Watcher-Event";
	public static final String USER_PERMISSIONS_UPDATED_ADDED_SECOND_PLAYER_EVENT_GROUP = "UserPermissionsUpdatedUserAdded-SecondPlayerEventGroup1";
	public static final String USER_PERMISSIONS_UPDATED_USER_ADDED_SECOND_PLAYER_EVENT_TOPIC = "User-Permissions-Updated-User-Added-Second-Player-Event";
	public static final String LOGGED_OUT_USER_LEFT_LOBBY_EVENT_GROUP = "LoggedOutUserLeftLobbyEventGroup1";
	public static final String LOGGED_OUT_USER_LEFT_LOBBY_EVENT_TOPIC = "Logged-Out-User-Left-Lobby-Event";
	public static final String USER_PERMISSIONS_UPDATED_LEFT_LOBBY_EVENT_GROUP = "UserPermissionsUpdatedLeftLobbyEventGroup1";
	public static final String USER_PERMISSIONS_UPDATED_USER_LEFT_LOBBY_EVENT_TOPIC = "User-Permissions-Updated-User-Left-Lobby-Event";
	public static final String LOGGED_OUT_OPENBY_LEFT_BEFORE_GAME_STARTED_EVENT_GROUP = "LoggedOutOpenByLeftBeforeGameStartedEventGroup1";
	public static final String LOGGED_OUT_OPENBY_LEFT_BEFORE_GAME_STARTED_EVENT_TOPIC = "Logged-Out-Openby-Left-Before-Game-Started-Event";
	public static final String USER_PERMISSIONS_UPDATED_LOGGED_OUT_OPENBY_LEFT_BEFORE_GAME_STARTED_EVENT_GROUP = "UserPermissionsUpdatedLoggedOutOpenByLeftBeforeGameStartedEventGroup1";
	public static final String USER_PERMISSIONS_UPDATED_LOGGED_OUT_OPENBY_LEFT_BEFORE_GAME_STARTED_EVENT_TOPIC = "User-Permissions-Updated-Logged-Out-Openby-Left-Before-Game-Started-Event";
	public static final String GAME_ROOM_CLOSED_LOGGED_OUT_OPENBY_LEFT_BEFORE_GAME_STARTED_EVENT_GROUP = "GameRoomClosedLoggedOutOpenByLeftBeforeGameStartedEventGroup1";
	public static final String GAME_ROOM_CLOSED_LOGGED_OUT_OPENBY_LEFT_BEFORE_GAME_STARTED_EVENT_TOPIC = "Game-Room-Closed-Logged-Out-Openby-Left-Before-Game-Started-Event";
	public static final String LOGGED_OUT_OPENBY_LEFT_EVENT_GROUP = "LoggedOutOpenbyLeftEventGroup1";
	public static final String LOGGED_OUT_OPENBY_LEFT_EVENT_TOPIC = "Logged-Out-Openby-Left-Event";
	public static final String USER_PERMISSIONS_UPDATED_LOGGED_OUT_OPENBY_LEFT_EVENT_GROUP = "UserPermissionsUpdatedLoggedOutOpenbyLeftEventGroup1";
	public static final String USER_PERMISSIONS_UPDATED_LOGGED_OUT_OPENBY_LEFT_EVENT_TOPIC = "User-Permissions-Updated-Logged-Out-Openby-Left-Event";
	public static final String LOGGED_OUT_WATCHER_LEFT_LAST_EVENT_GROUP = "LoggedOutWatcherLeftLastEventGroup2";
	public static final String LOGGED_OUT_WATCHER_LEFT_LAST_EVENT_TOPIC = "Logged-Out-Watcher-Left-Last-Event";
	public static final String GAME_ROOM_CLOSED_LOGGED_OUT_WATCHER_LEFT_LAST_EVENT_GROUP = "GameRoomClosedLoggedOutWatcherLeftLastEventGroup1";
	public static final String GAME_ROOM_CLOSED_LOGGED_OUT_WATCHER_LEFT_LAST_EVENT_TOPIC = "Game-Room-Closed-Logged-Out-Watcher-Left-Last-Event";
	public static final String USER_PERMISSIONS_UPDATED_LOGGED_OUT_WATCHER_LEFT_LAST_EVENT_GROUP = "UserPermissionsUpdatedLoggedOutWatcherLeftLastEventGroup1";
	public static final String USER_PERMISSIONS_UPDATED_LOGGED_OUT_WATCHER_LEFT_LAST_EVENT_TOPIC = "User-Permissions-Updated-Logged-Out-Watcher-Left-Last-Event";
	public static final String LOGGED_OUT_WATCHER_LEFT_EVENT_GROUP = "LoggedOutWatcherLeftEventGroup1";
	public static final String LOGGED_OUT_WATCHER_LEFT_EVENT_TOPIC = "Logged-Out-Watcher-Left-Event";
	public static final String USER_PERMISSIONS_UPDATED_LOGGED_OUT_WATCHER_LEFT_EVENT_GROUP = "UserPermissionsUpdatedLoggedOutWatcherLeftEventGroup1";
	public static final String USER_PERMISSIONS_UPDATED_LOGGED_OUT_WATCHER_LEFT_EVENT_TOPIC = "User-Permissions-Updated-Logged-Out-Watcher-Left-Event";
	public static final String LOGGED_OUT_OPENBY_LEFT_FIRST_EVENT_GROUP = "LoggedOutOpenByLeftFirstEventGroup1";
	public static final String LOGGED_OUT_OPENBY_LEFT_FIRST_EVENT_TOPIC = "Logged-Out-Openby-Left-First-Event";
	public static final String USER_PERMISSIONS_UPDATED_LOGGED_OUT_OPENBY_LEFT_FIRST_EVENT_GROUP = "UserPermissionsUpdatedLoggedOutOpenByLeftFirstEventGroup1";
	public static final String USER_PERMISSIONS_UPDATED_LOGGED_OUT_OPENBY_LEFT_FIRST_EVENT_TOPIC = "User-Permissions-Updated-Logged-Out-Openby-Left-First-Event";
	public static final String LOGGED_OUT_SECOND_LEFT_FIRST_EVENT_GROUP = "LoggedOutSecondLeftFirstEventGroup1";
	public static final String LOGGED_OUT_SECOND_LEFT_FIRST_EVENT_TOPIC = "Logged-Out-Second-Left-First-Event";
	public static final String USER_PERMISSIONS_UPDATED_LOGGED_OUT_SECOND_LEFT_FIRST_EVENT_GROUP = "UserPermissionsUpdatedLoggedOutSecondLeftFirstEventGroup1";
	public static final String USER_PERMISSIONS_UPDATED_LOGGED_OUT_SECOND_LEFT_FIRST_EVENT_TOPIC = "User-Permissions-Updated-Logged-Out-Second-Left-First-Event";
	public static final String LOGGED_OUT_SECOND_LEFT_EVENT_GROUP = "LoggedOutSecondLeftEventGroup1";
	public static final String LOGGED_OUT_SECOND_LEFT_EVENT_TOPIC = "Logged-Out-Second-Left-Event";
	public static final String USER_PERMISSIONS_UPDATED_LOGGED_OUT_SECOND_LEFT_EVENT_GROUP = "UserPermissionsUpdatedLoggedOutSecondLeftEventGroup1";
	public static final String USER_PERMISSIONS_UPDATED_LOGGED_OUT_SECOND_LEFT_EVENT_TOPIC = "User-Permissions-Updated-Logged-Out-Second-Left-Event";
	public static final String LOGGED_OUT_OPENBY_LEFT_LAST_EVENT_GROUP = "LoggedOutOpenByLeftLastEventGroup1";
	public static final String LOGGED_OUT_OPENBY_LEFT_LAST_EVENT_TOPIC = "Logged-Out-Openby-Left-Last-Event";
	public static final String USER_PERMISSIONS_UPDATED_LOGGED_OUT_OPENBY_LEFT_LAST_EVENT_GROUP = "UserPermissionsUpdatedLoggedOutOpenbyLeftLastEventGroup1";
	public static final String USER_PERMISSIONS_UPDATED_LOGGED_OUT_OPENBY_LEFT_LAST_EVENT_TOPIC = "User-Permissions-Updated-Logged-Out-Openby-Left-Last-Event";
	public static final String GAME_ROOM_CLOSED_LOGGED_OUT_OPENBY_LEFT_LAST_EVENT_GROUP = "GameRoomClosedLoggedOutOpenbyLeftLastEventGroup1";
	public static final String GAME_ROOM_CLOSED_LOGGED_OUT_OPENBY_LEFT_LAST_EVENT_TOPIC = "Game-Room-Closed-Logged-Out-Openby-Left-Last-Event";
	public static final String LOGGED_OUT_SECOND_LEFT_LAST_EVENT_GROUP = "LoggedOutSecondLeftLastEventGroup1";
	public static final String LOGGED_OUT_SECOND_LEFT_LAST_EVENT_TOPIC = "Logged-Out-Second-Left-Last-Event";
	public static final String USER_PERMISSIONS_UPDATED_LOGGED_OUT_SECOND_LEFT_LAST_EVENT_GROUP = "UserPermissionsUpdatedLoggedOutSecondLeftLastEventGroup1";
	public static final String USER_PERMISSIONS_UPDATED_LOGGED_OUT_SECOND_LEFT_LAST_EVENT_TOPIC = "User-Permissions-Updated-Logged-Out-Second-Left-Last-Event";
	public static final String GAME_ROOM_CLOSED_LOGGED_OUT_SECOND_LEFT_LAST_EVENT_GROUP = "GameRoomClosedLoggedOutSecondLeftLastEventGroup1";
	public static final String GAME_ROOM_CLOSED_LOGGED_OUT_SECOND_LEFT_LAST_EVENT_TOPIC = "Game-Room-Closed-Logged-Out-Second-Left-Last-Event";
	public static final String OPENBY_LEFT_BEFORE_GAME_STARTED_EVENT_GROUP = "OpenbyLeftBeforeGameStartedEventGroup1";
	public static final String OPENBY_LEFT_BEFORE_GAME_STARTED_EVENT_TOPIC = "Openby-Left-Before-Game-Started-Event";
	public static final String USER_PERMISSIONS_UPDATED_OPENBY_LEFT_BEFORE_GAME_STARTED_EVENT_TOPIC = "User-Permissions-Updated-Openby-Left-Before-Game-Started-Event";
	public static final String GAME_ROOM_CLOSED_OPENBY_LEFT_BEFORE_GAME_STARTED_EVENT_GROUP = "GameRoomClosedOpenbyLeftBeforeGameStartedEventGroup1";
	public static final String GAME_ROOM_CLOSED_OPENBY_LEFT_BEFORE_GAME_STARTED_EVENT_TOPIC = "Game-Room-Closed-Openby-Left-Before-Game-Started-Event";
	public static final String OPENBY_LEFT_EVENT_GROUP = "OpenbyLeftEventGroup1";
	public static final String OPENBY_LEFT_EVENT_TOPIC = "Openby-Left-Event";
	public static final String USER_PERMISSIONS_UPDATED_OPENBY_LEFT_EVENT_GROUP = "UserPermissionsUpdatedOpenbyLeftEventGroup1";
	public static final String USER_PERMISSIONS_UPDATED_OPENBY_LEFT_EVENT_TOPIC = "User-Permissions-Updated-Openby-Left-Event";
	public static final String WATCHER_LEFT_LAST_EVENT_GROUP = "WatcherLeftLastEventGroup1";
	public static final String WATCHER_LEFT_LAST_EVENT_TOPIC = "Watcher-Left-Last-Event";
	public static final String USER_PERMISSIONS_UPDATED_WATCHER_LEFT_LAST_EVENT_GROUP = "UserPermissionsUpdatedWatcherLeftLastEventGroup1";
	public static final String USER_PERMISSIONS_UPDATED_WATCHER_LEFT_LAST_EVENT_TOPIC = "User-Permissions-Updated-Watcher-Left-Last-Event";
	public static final String GAME_ROOM_CLOSED_WATCHER_LEFT_LAST_EVENT_GROUP = "GameRoomClosedWatcherLeftLastEventGroup1";
	public static final String GAME_ROOM_CLOSED_WATCHER_LEFT_LAST_EVENT_TOPIC = "Game-Room-Closed-Watcher-Left-Last-Event";
}
