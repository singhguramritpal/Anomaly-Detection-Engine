package com.example;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import connection.DBConnect;

@RestController
@CrossOrigin(origins="*")
public class GreetingController {
	
	DBConnect dbc = new DBConnect();
	
    public void loadData() {
    	List<DBObject> auth_data;
    	List<DBObject> firewall_data;
    	
    	MongoClient mongoClient = new MongoClient( "52.39.5.137" , 27017 );
    	
    	DB db1 = mongoClient.getDB("log_inventory_db");
		DBCollection coll1 = db1.getCollection("auth_events"); 
		DBCollection coll2 = db1.getCollection("firewall_events");
		
    	BasicDBObject query = new BasicDBObject();
    	    	
		auth_data = dbc.connectToAuthEvents();
		List<String> list = new ArrayList<String>();
		for(DBObject temp : auth_data){
			
			BasicDBObject ip = new BasicDBObject();
			
			ip.append("startTime", temp.get("startTime"));
			ip.append("endTime", temp.get("endTime"));
			ip.append("sourceName", temp.get("sourceName"));
			ip.append("hostIP", temp.get("hostIP"));
			ip.append("destName", temp.get("destName"));
			ip.append("destIP", temp.get("destIP"));
			ip.append("Message", temp.get("Message"));
			ip.append("facilityText", temp.get("facilityText"));
			ip.append("severityText", temp.get("severityText"));
			
			coll1.insert(ip);
		
		}
		
		firewall_data = dbc.connectToFirewallEvents();
		for(DBObject temp : firewall_data){
			
			BasicDBObject ip = new BasicDBObject();

			ip.append("sourceIP", temp.get("sourceIP"));
			ip.append("sourceName", temp.get("sourceName"));
			ip.append("destIP", temp.get("destIP"));
			ip.append("destName", temp.get("destName"));
			ip.append("sourcePort", temp.get("sourcePort"));
			ip.append("destPort", temp.get("destPort"));
			ip.append("status", temp.get("status"));
			ip.append("time", temp.get("time"));
			ip.append("outcome", temp.get("outcome"));
			ip.append("priorty", temp.get("priorty"));				

			coll2.insert(ip);
			
		}
    }
    
    @RequestMapping(value="/changeIPTable", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus changeIPTable(@RequestBody String input){
    	
    	JSONObject jsonObj = new JSONObject(input);
    	
    	String result = "" + jsonObj.getString("deny");
		
    	AddToFile atf = new AddToFile();
    	atf.openFile(result);
    	
		return HttpStatus.ACCEPTED;
		
    }
    
}