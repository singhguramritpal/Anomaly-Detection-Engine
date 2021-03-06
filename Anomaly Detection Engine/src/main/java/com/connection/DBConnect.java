package com.connection;

import java.net.UnknownHostException;
import java.util.HashMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class DBConnect {
	
	public DBCursor connectToFirewall() throws UnknownHostException{
		MongoClient mongoClient = new MongoClient( "52.39.5.137" , 27017 );
    	
    	DB db1 = mongoClient.getDB("log_inventory_db");
		DBCollection coll = db1.getCollection("firewall_events");
		
		DBCursor cursor = coll.find();
		
		return cursor;
	}
	
	public DBCursor connectToAuth() throws UnknownHostException{
		MongoClient mongoClient = new MongoClient( "52.39.5.137" , 27017 );
    	
    	DB db1 = mongoClient.getDB("log_inventory_db");
		DBCollection coll = db1.getCollection("auth_events"); 
		
		DBCursor cursor = coll.find();
		
		return cursor;
	}
	public DBCollection connectToBlockedIP() throws UnknownHostException{
		MongoClient mongoClient = new MongoClient( "52.39.5.137" , 27017 );
    	
    	DB db1 = mongoClient.getDB("log_inventory_db");
		DBCollection coll = db1.getCollection("blockedIP");
		
		return coll;
	}
	
	public void connectToUser(HashMap<String,String> map) throws UnknownHostException{
		MongoClient mongoClient = new MongoClient( "52.39.5.137" , 27017 );
    	
    	DB db1 = mongoClient.getDB("log_inventory_db");
		DBCollection coll = db1.getCollection("user");
		
		BasicDBObject document = new BasicDBObject(map);
		//document.put(map);
		
		System.out.println(document);
		
		coll.insert(document);
	}
	
	public DBCursor connectToUser() throws UnknownHostException{
		MongoClient mongoClient = new MongoClient( "52.39.5.137" , 27017 );
    	
    	DB db1 = mongoClient.getDB("log_inventory_db");
		DBCollection coll = db1.getCollection("user");
		
		DBCursor cursor = coll.find();
		
		return cursor;
	}

}
