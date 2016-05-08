package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.connection.DBConnect;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.resources.User;

@RestController
public class DetectionController {


	//Multiple destinations targeting
	// a single machines

	@RequestMapping("/firewall/multipleDestination")
	public HashMap<String,Integer> sourceIP(@RequestParam(value="ip", defaultValue="10.0.0.1") String ip) throws UnknownHostException {

		DBCursor cursor = new DBConnect().connectToFirewall();

		HashMap<String,Integer> map = new HashMap<String,Integer>();
		while(cursor.hasNext()){
			DBObject obj = cursor.next();
			if((obj.get("sourceIP").equals(ip)) && obj.get("outcome").equals("Failure")){

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
	public HashMap<String,Integer> destinationIP(@RequestParam(value="ip", defaultValue="10.0.0.1") String ip) throws UnknownHostException {

		DBCursor cursor = new DBConnect().connectToFirewall();

		HashMap<String,Integer> map = new HashMap<String,Integer>();

		while(cursor.hasNext()){
			DBObject obj = cursor.next();
			System.out.println((obj.get("destIP").equals(ip)) && obj.get("outcome").equals("Failure"));
			if((obj.get("destIP").equals(ip)) && obj.get("outcome").equals("Failure")){

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

	//Number of ports connected to a single source

	@RequestMapping("/firewall/topDestPorts")
	public HashMap<String,Integer> ports(@RequestParam(value="ip", defaultValue="10.0.0.1") String ip) throws UnknownHostException {

		DBCursor cursor = new DBConnect().connectToFirewall();
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		while(cursor.hasNext()){
			DBObject obj = cursor.next();
			if((obj.get("sourceIP").equals(ip))){
				if(!map.containsKey(obj.get("destPort").toString())){
					map.put(obj.get("destPort").toString(), 1);
				}
				else{
					map.put(obj.get("destPort").toString(), map.get(obj.get("destPort"))+1);
				}
			}
		}
		return map;
	}

	//all the active ports on a machine
	@RequestMapping("/firewall/topSourcePorts")
	public HashMap<String,Integer> sourcePorts(@RequestParam(value="ip", defaultValue="10.0.0.1") String ip) throws UnknownHostException {

		DBCursor cursor = new DBConnect().connectToFirewall();
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		while(cursor.hasNext()){
			DBObject obj = cursor.next();
			if((obj.get("sourceIP").equals(ip))){
				if(!map.containsKey(obj.get("sourcePort").toString())){
					map.put(obj.get("sourcePort").toString(), 1);
				}
				else{
					map.put(obj.get("sourcePort").toString(), map.get(obj.get("sourcePort"))+1);
				}
			}
		}
		return map;
	}

	//Top destinations connected to a particular source

	@RequestMapping("/firewall/topDestinations")
	public HashMap<String,Integer> sources(@RequestParam(value="ip", defaultValue="10.0.0.1") String ip) throws UnknownHostException {

		DBCursor cursor = new DBConnect().connectToFirewall();
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		while(cursor.hasNext()){
			DBObject obj = cursor.next();
			if((obj.get("sourceIP").equals(ip)) && obj.get("outcome").equals("Success")){
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

	//Accepted Events API returns a set of accepted events for a particular IP

	@RequestMapping("/firewall/acceptedEvents")
	public HashSet<HashMap<String,String>> acceptedEvents(@RequestParam(value="ip", defaultValue="10.0.0.1") String ip) throws UnknownHostException{
		
		DBCursor cursor = new DBConnect().connectToFirewall();
		HashSet<HashMap<String,String>> set = new HashSet<HashMap<String,String>>();
		HashMap<String,String> map = new HashMap<String,String>();
		while(cursor.hasNext()){
			DBObject obj = cursor.next();
			if((obj.get("sourceIP").equals(ip)) && obj.get("outcome").equals("Success")){
				map.put("destIP",obj.get("destIP").toString());
				map.put("sourcePort",obj.get("sourcePort").toString());
				map.put("destPort",obj.get("destPort").toString());
				map.put("time",obj.get("time").toString());
				map.put("priorty",obj.get("priorty").toString());
				set.add(map);
			}
		}
		
		return set;
	}

	//Rejected Events API returns a set of rejected events for a particular IP

	@RequestMapping("/firewall/rejectedEvents")
	public HashSet<HashMap<String,String>> rejectedEvents(@RequestParam(value="ip", defaultValue="10.0.0.1") String ip) throws UnknownHostException{

		DBCursor cursor = new DBConnect().connectToFirewall();
		HashSet<HashMap<String,String>> set = new HashSet<HashMap<String,String>>();
		HashMap<String,String> map = new HashMap<String,String>();
		while(cursor.hasNext()){
			DBObject obj = cursor.next();
			if((obj.get("sourceIP").equals(ip)) && obj.get("outcome").equals("Failure")){
				map.put("destIP",obj.get("destIP").toString());
				map.put("sourcePort",obj.get("sourcePort").toString());
				map.put("destPort",obj.get("destPort").toString());
				map.put("time",obj.get("time").toString());
				map.put("priorty",obj.get("priorty").toString());
				set.add(map);
			}
		}
		return set;
	}


	//AUTH LOGINS

	@RequestMapping("/auth/failedLogin")
	public List<HashMap<String,Integer>> failedLogin(@RequestParam(value="ip", defaultValue="10.0.0.1") String ip) throws UnknownHostException{

		DBCursor cursor = new DBConnect().connectToAuth();
		String msg;
		HashMap<String,Integer> local = new HashMap<String,Integer>();
		HashMap<String,Integer> remote = new HashMap<String,Integer>();
		List<HashMap<String,Integer>> list = new ArrayList<HashMap<String,Integer>>();
		DBObject obj;

		while(cursor.hasNext()){
			obj = cursor.next();
			if((obj.get("hostIP").equals(ip))){



				msg = obj.get("Message").toString().toLowerCase();

				String pattern = "(\\d{1,3}).(\\d{1,3}).(\\d{1,3}).(\\d{1,3})";
				Pattern p = Pattern.compile(pattern);
				Matcher m = p.matcher(msg);



				if(msg.matches("(.*)failed login(.*)")){
					String ip1 = obj.get("destIP").toString();
					if(!local.containsKey(ip1)){
						local.put(obj.get("destIP").toString(), 1);
					}
					else{
						local.put(ip1, local.get(ip1)+1);
					}
				}
				else if(msg.matches("(.*)failed password(.*)")){
					if(m.find( )){
						String ip1 = m.group()+"";
						if(!remote.containsKey(ip1)){
							remote.put(ip1, 1);
						}
						else{
							remote.put(ip1, remote.get(ip1)+1);
						}
					}
				}
			}
		}
		list.add(0,local);
		list.add(1,remote);
		return list;
	}
	
	
	//Auth Logs

	@RequestMapping("/auth/remoteLogin")
	public HashMap<String, Integer> remoteLogins(@RequestParam(value="ip", defaultValue="10.0.0.1") String ip) throws UnknownHostException{

		DBCursor cursor = new DBConnect().connectToAuth();
		String msg;
		HashMap<String,Integer> remote = new HashMap<String,Integer>();
		DBObject obj;

		while(cursor.hasNext()){
			obj = cursor.next();
			if((obj.get("hostIP").equals(ip))){
				msg = obj.get("Message").toString().toLowerCase();

				String pattern = "(\\d{1,3}).(\\d{1,3}).(\\d{1,3}).(\\d{1,3})";
				Pattern p = Pattern.compile(pattern);
				Matcher m = p.matcher(msg);

				System.out.println(msg);
				if(msg.matches("(.*)accepted password(.*)")){
					if(m.find( )){
						String ip1 = m.group()+"";
						if(!remote.containsKey(ip1)){
							remote.put(ip1, 1);
						}
						else{
							remote.put(ip1, remote.get(ip1)+1);
						}
					}
				}
			}
		}
		return remote;
	}

	@RequestMapping("/auth/userActivity")
	public List<DBObject> userActivity(@RequestParam(value="user", defaultValue="workstation") String user) throws UnknownHostException{

		DBCursor cursor = new DBConnect().connectToAuth();
		List<DBObject> list = new ArrayList<DBObject>();
		DBObject obj;

		String pattern = "(.*)"+user+"(.*)";

		while(cursor.hasNext()){
			obj = cursor.next();
			if((obj.get("sourceName").toString().matches(pattern))){
				list.add(obj);
			}
		}
		return list;
	}

	@RequestMapping("/auth/sourceIPActivity")
	public List<DBObject> sourceIPActivity(@RequestParam(value="ip", defaultValue="10.0.0.1") String ip) throws UnknownHostException{

		DBCursor cursor = new DBConnect().connectToAuth();
		List<DBObject> list = new ArrayList<DBObject>();
		DBObject obj;

		while(cursor.hasNext()){
			obj = cursor.next();
			if((obj.get("hostIP").equals(ip))){
				list.add(obj);
			}
		}
		return list;
	}

	@RequestMapping("/auth/destIPActivity")
	public List<DBObject> destIPActivity(@RequestParam(value="ip", defaultValue="127.0.0.1") String ip) throws UnknownHostException{

		DBCursor cursor = new DBConnect().connectToAuth();
		String msg;
		List<DBObject> list = new ArrayList<DBObject>();
		DBObject obj;

		while(cursor.hasNext()){
			obj = cursor.next();
			if((obj.get("destIP").equals(ip))){
				list.add(obj);
			}
		}
		return list;
	}

	@RequestMapping("/auth/destUserActivity")
	public List<DBObject> destNameActivity(@RequestParam(value="user", defaultValue="10.0.0.1") String destName) throws UnknownHostException{

		DBCursor cursor = new DBConnect().connectToAuth();
		List<DBObject> list = new ArrayList<DBObject>();
		DBObject obj;

		String pattern = "(.*)"+destName+"(.*)";

		while(cursor.hasNext()){
			obj = cursor.next();
			if((obj.get("destName").toString().matches(pattern))){
				list.add(obj);
			}
		}
		return list;
	}

	@RequestMapping("/auth/bruteForce")
	public String bruteForce() throws UnknownHostException{

		DBCursor cursor = new DBConnect().connectToAuth();
		DBObject obj;
		String msg;
		String time;
		HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
		while(cursor.hasNext()){
			obj = cursor.next();
			msg = obj.get("Message").toString().toLowerCase();

			String pattern = "(.*)authentication failure(.*)";

			if(msg.matches(pattern)){
				time = obj.get("startTime").toString();
				String timePattern = "(\\d{1,2}):(\\d{1,2}):(\\d{1,2})";
				Pattern p = Pattern.compile(timePattern);
				Matcher m = p.matcher(time);				
				if(m.find()){
					
					String hostIP=obj.get("hostIP").toString();
					if(!map.containsKey(hostIP)){
						map.put(hostIP,new ArrayList<Integer>());
						map.get(hostIP).add(0,Integer.parseInt(m.group(2)));
					}
					else{
						if(map.get(hostIP).size()<10){
							map.get(hostIP).add(0,Integer.parseInt(m.group(2)));
						}
						else{
							if(Integer.parseInt(m.group(2))-map.get(hostIP).get(9)<1){
								map.get(hostIP).add(0,Integer.parseInt(m.group(2)));
								map.get(hostIP).remove(10);
								return hostIP+" is having a suspicious activity";
							}
							else{
								map.get(hostIP).remove(10);
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	@RequestMapping("/blockIP")
	public String blockIP(@RequestParam(value="ip", defaultValue="10.0.0.1") String ip) throws UnknownHostException{
		new DBConnect().connectToBlockedIP(ip);
		String data = "{\"deny\":\""+ip+"\"}";
		try {
			URL url = new URL("http://localhost:8080/changeIPTable");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = data;			
			
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		 }
		
		return ip+" blocked";
	}
	
	@RequestMapping(value="/signup", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String,String> registerUser(@RequestBody User user) throws UnknownHostException{
    	
		System.out.println(user.toString());
    	HashMap<String,String> map = new HashMap<String,String>();
    	map.put("name", user.getName());
    	map.put("email", user.getEmail().toString());
    	map.put("phoneNumber", user.getPhoneNumber());
    	map.put("organizationName", user.getOrganizationName());
    	map.put("password", user.getPassword());
    	
    	DBConnect dbc = new DBConnect();
    	
    	dbc.connectToUser(map);
    	
    	System.out.println(map);
    	
    	return map;
    }
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public HashMap<String, String> Login(@RequestBody User user) throws UnknownHostException {
		
		String userName=user.getEmail();
		String password=user.getPassword();
		
		System.out.println(userName);


		DBCursor cursor = new DBConnect().connectToUser();
		HashMap<String,String> map = new HashMap<String,String>();
		while(cursor.hasNext()){
			DBObject obj = cursor.next();
			if((obj.get("email").equals(userName)) && obj.get("password").equals(password)){
				map.put("name", obj.get("name").toString());
				map.put("email", obj.get("email").toString());
				map.put("phoneNumber",obj.get("phoneNumber").toString());
				map.put("organizationName",obj.get("organizationName").toString());
				return map;
			}
		}
		return map;
	}
}
