package org.moshe.arad.mongo.events;

import java.util.Date;

public interface IMongoEvent {

	public Date getArrived();

	public void setArrived(Date arrived);

	public String getClazz();
}
