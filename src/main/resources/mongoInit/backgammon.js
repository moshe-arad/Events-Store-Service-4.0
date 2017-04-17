use backgammon;
db.auth("arad","arad123");
db.services.drop();
db.services.insert(
[{
	"serviceId": 1,
	"serviceName": "Users Service",
	"incomingCommands":[{
		"commandId": 1,
		"commandName": "createNewUserCommand",
		"commandDescription": "This command is being sent beacuse an end-user tried to create a new user for himself, eventually a new user will be created, the proper events will be stored in the event store, and proper views will be updated.",
		"entityId": 1,
		"entityName": "User",
		"issuedByEndUser": true,
		"issuedByCommand": false,
		"issuedByEvent": false,
		"issuedByCommandId": null,
		"issuedByEvents": []
	}],
	"incomingEvents":"",
	"outgoingEvents":[{
		"eventId": 1,
		"eventName": "newUserCreatedEvent",
		"eventDescription": "This event is being created after recieving a Create New User Command. When recieving a Create New User Command, a new user is created in users service, and afterwards a New User Created Event will be triggered and sent.",
		"entityId": 1,
		"entityName": "User",
		"issuedByEndUser": false,
		"issuedByCommand": true,
		"issuedByEvent": false,
		"issuedByCommandId": 1,
		"issuedByEvents": []
	}]
}, {
	"serviceId": 2,
	"serviceName": "Lobby Service",
	"incomingCommands":"",
	"incomingEvents":[{
		"eventId": 1,
		"eventName": "newUserCreatedEvent",
		"eventDescription": "This event is being created after users service recieved a Create New User Command. Lobby service will add the user to its lobby, and will initiate a new event newUserJoinedLobbyEvent.",
		"entityId": 1,
		"entityName": "User",
		"issuedByEndUser": false,
		"issuedByCommand": true,
		"issuedByEvent": false,
		"issuedByCommandId": 1,
		"issuedByEvents": []
	}],
	"outgoingEvents":[{
		"eventId": 2,
		"eventName": "newUserJoinedLobbyEvent",
		"eventDescription": "This event is being created after recieving a New User Created Event. When recieving a New User Created Event, a new user is being added to the lobby which contains all game rooms.",
		"entityId": 1,
		"entityName": "User",
		"issuedByEndUser": false,
		"issuedByCommand": false,
		"issuedByEvent": true,
		"issuedByCommandId": null,
		"issuedByEvents": [1]
	}]
}, {
	"serviceId": 3,
	"serviceName": "Events Store Service",
	"incomingCommands":"",
	"incomingEvents":[{
		"eventId": 1,
		"eventName": "newUserCreatedEvent",
		"eventDescription": "This event is being created after users service recieved a Create New User Command. The Event Store recieves this event for storing purposes.",
		"entityId": 1,
		"entityName": "User",
		"issuedByEndUser": false,
		"issuedByCommand": true,
		"issuedByEvent": false,
		"issuedByCommandId": 1,
		"issuedByEvents": []
	},
	{
		"eventId": 2,
		"eventName": "newUserJoinedLobbyEvent",
		"eventDescription": "This event is being created after recieving a New User Created Event. When recieving a New User Created Event, a new user is being added to the lobby which contains all game rooms.",
		"entityId": 1,
		"entityName": "User",
		"issuedByEndUser": false,
		"issuedByCommand": false,
		"issuedByEvent": true,
		"issuedByCommandId": null,
		"issuedByEvents": [1]
	}],
	"outgoingEvents":""
},
{
	"serviceId": 4,
	"serviceName": "Users View Service",
	"incomingCommands":"",
	"incomingEvents":[{
		"eventId": 1,
		"eventName": "newUserCreatedEvent",
		"eventDescription": "This event is being created after users service recieved a Create New User Command. The Users View Service gets this event in order to update its materialized view in the cqrs pattern.",
		"entityId": 1,
		"entityName": "User",
		"issuedByEndUser": false,
		"issuedByCommand": true,
		"issuedByEvent": false,
		"issuedByCommandId": 1,
		"issuedByEvents": []
	}],
	"outgoingEvents":""
}]);
