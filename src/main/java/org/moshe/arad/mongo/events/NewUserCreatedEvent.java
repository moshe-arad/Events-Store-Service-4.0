package org.moshe.arad.mongo.events;

import java.util.Date;
import java.util.UUID;

import org.moshe.arad.entities.BackgammonUser;
import org.moshe.arad.entities.Location;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="newUserCreatedEvents")
public class NewUserCreatedEvent extends MongoEvent {

	BackgammonUser backgammonUser;
	
//	private String userName;
//	private String password;
//	private String firstName;
//	private String lastName;
//	private String email;
//	private Location location;
//
//	public NewUserCreatedEvent(String mongoEventId, UUID uuid, int serviceId, int eventId, Date arrived,
//			String userName, String password, String firstName, String lastName, String email, Location location) {
//		super(mongoEventId, uuid, serviceId, eventId, arrived);
//		this.userName = userName;
//		this.password = password;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.location = location;
//	}

	public NewUserCreatedEvent(String mongoEventId, UUID uuid, int serviceId, int eventId, Date arrived,
		BackgammonUser backgammonUser) {
		super(mongoEventId, uuid, serviceId, eventId, arrived);
		this.backgammonUser = backgammonUser;
	}

	public BackgammonUser getBackgammonUser() {
		return backgammonUser;
	}

	public void setBackgammonUser(BackgammonUser backgammonUser) {
		this.backgammonUser = backgammonUser;
	}	
}
