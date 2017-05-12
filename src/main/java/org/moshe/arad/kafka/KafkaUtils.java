package org.moshe.arad.kafka;

public class KafkaUtils {

	public static final String SERVERS = "192.168.1.4:9092,192.168.1.4:9093,192.168.1.4:9094";
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
	public static final String LOGGED_OUT_EVENT_GROUP = "LoggedOutEventGroup";
}
