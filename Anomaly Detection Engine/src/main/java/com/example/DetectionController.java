
package com.example;

import java.net.UnknownHostException;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.connection.DBConnect;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@RestController
public class DetectionController {


	//Multiple destinations targeting
	// a single machines

	@RequestMapping("/firewall/multipleDestination")
	public HashMap<String,Integer> sourceIP(@RequestParam(value="ip", defaultValue="World") String name) throws UnknownHostException {

		DBCursor cursor = new DBConnect().connectToFirewall();

		HashMap<String,Integer> map = new HashMap<>();
		while(cursor.hasNext()){
			DBObject obj = cursor.next();
			if((obj.get("sourceIP").equals(name)) && obj.get("outcome").equals("Failure")){

				if(!map.containsKey(obj.get("destIP").toString())){
					map.put(obj.get("destIP").toString(), 1);
				}
				else{
					map.put(obj.get("destIP").toString(), map.get(obj.get("destIP"))+1);
				}
			}
		}
		return map;
	}

	//All the source that 
	//are targeted by
	//particular destination

	@RequestMapping("/firewall/multipleSource")
	public HashMap<String,Integer> destinationIP(@RequestParam(value="ip", defaultValue="World") String name) throws UnknownHostException {

		DBCursor cursor = new DBConnect().connectToFirewall();

		HashMap<String,Integer> map = new HashMap<>();

		while(cursor.hasNext()){
			DBObject obj = cursor.next();
			System.out.println((obj.get("destIP").equals(name)) && obj.get("outcome").equals("Failure"));
			if((obj.get("destIP").equals(name)) && obj.get("outcome").equals("Failure")){

				if(!map.containsKey(obj.get("sourceIP").toString())){
					map.put(obj.get("sourceIP").toString(), 1);
				}
				else{
					map.put(obj.get("sourceIP").toString(), map.get(obj.get("sourceIP"))+1);
				}
			}
		}
		return map;
	}

}
