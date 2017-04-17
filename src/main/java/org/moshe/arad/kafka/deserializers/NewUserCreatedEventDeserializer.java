package org.moshe.arad.kafka.deserializers;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.moshe.arad.kafka.events.NewUserCreatedEvent;

public class NewUserCreatedEventDeserializer implements Deserializer<NewUserCreatedEvent>{

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configure(Map<String, ?> paramMap, boolean paramBoolean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NewUserCreatedEvent deserialize(String paramString, byte[] paramArrayOfByte) {
		// TODO Auto-generated method stub
		return null;
	}

}
