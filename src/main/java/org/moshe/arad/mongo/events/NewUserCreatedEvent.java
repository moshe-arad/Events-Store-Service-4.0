package org.moshe.arad.mongo.events;

import java.util.UUID;

import org.moshe.arad.entities.Location;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="events")
public class NewUserCreatedEvent extends MongoEvent {

	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private Location location;
	
	public NewUserCreatedEvent(String userName, String password, String firstName, String lastName, String email,
			Location location) {
		super();
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.location = location;
	}

	@Override
	public String toString() {
		return "NewUserCreatedEvent [userName=" + userName + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", location=" + location + "]";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
