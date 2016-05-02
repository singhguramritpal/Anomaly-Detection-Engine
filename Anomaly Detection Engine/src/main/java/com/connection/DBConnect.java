package com.connection;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class DBConnect {
	
	public DBCursor connectToFirewall() throws UnknownHostException{
		MongoClient mongoClient = new MongoClient( "52.25.60.89" , 27017 );
    	
    	DB db1 = mongoClient.getDB("log_inventory_db");
		DBCollection coll = db1.getCollection("firewall_events");
		
		DBCursor cursor = coll.find();
		
		return cursor;
	}
}
