package com.example;

import java.util.TimerTask;

public class ScheduledTask extends TimerTask {
	
	GreetingController gc = new GreetingController();
	
	public void run() {
		gc.loadData();
	}
	
}
