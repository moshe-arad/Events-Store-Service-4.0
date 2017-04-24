package org.moshe.arad.kafka.deserializers;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.moshe.arad.entities.BackgammonUser;
import org.moshe.arad.entities.Location;
import org.moshe.arad.kafka.commands.PullEventsCommand;
import org.moshe.arad.kafka.events.NewUserCreatedEvent;

public class PullEventsCommandDeserializer implements Deserializer<PullEventsCommand>{

private String encoding = "UTF8";
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configure(Map<String, ?> paramMap, boolean paramBoolean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PullEventsCommand deserialize(String topic, byte[] data) {
		try {
            if (data == null){
                System.out.println("Null recieved at deserialize");
                return null;
            }
            
            ByteBuffer buf = ByteBuffer.wrap(data);         
      
            UUID uuid = new UUID(buf.getLong(), buf.getLong());
            Date date = new Date(buf.getLong());
                  
            PullEventsCommand pullEventsCommand = new PullEventsCommand(uuid, date);         
            return pullEventsCommand;	            		           
            
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to PullEventsCommand");
        }
	}
}
