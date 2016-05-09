package connection;



import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class DBConnect {
		
		MongoClient mongoClient = new MongoClient( "52.36.17.56" , 27017 );
		DB db = mongoClient.getDB("db");
		DBCollection coll = db.getCollection("auth_events"); 
		DBCursor cursor = coll.find();
		DBCursor oldCursor = coll.find();
		int count =0;
		
		
		DBCollection coll1 = db.getCollection("firewall_events"); 
		DBCursor cursor1 = coll1.find();
		DBCursor oldCursor1 = coll1.find();
		
		int count1 = 0;
		
		
		List<DBObject> auth_list;
		List<DBObject> firewall_list;
		
		
		public List<DBObject> connectToAuthEvents(){
			
			cursor = coll.find().skip(count);
			
			auth_list  = new ArrayList<DBObject>();
			while(cursor.hasNext()){
				auth_list.add(cursor.next());
			}
			count = cursor.count()-1;
			return auth_list;
			
		}
		
		public List<DBObject> connectToFirewallEvents(){
			
			cursor1 = coll1.find().skip(count1);
			firewall_list = new ArrayList<DBObject>();
			while(cursor1.hasNext()){
				firewall_list.add(cursor1.next());
			}
			count1 = cursor1.count()-1;
			return firewall_list;
		}
		
		
		// to add the blocked IP's to the database
		public void connectToBlockedIP(String ip){
			
			DBCollection coll1 = db.getCollection("blockIP"); 
			
			BasicDBObject document = new BasicDBObject();
			ip=ip.replace('.', ' ');
			document.put(ip, "deny");
			
			coll1.insert(document);
			
		}
		
}
