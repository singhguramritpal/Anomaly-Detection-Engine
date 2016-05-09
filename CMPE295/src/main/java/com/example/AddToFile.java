package com.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AddToFile {
	
	File file;

	public void openFile(String ip){
		
		file = new File("/home/ubuntu/ip.txt");
		
		// if file doesnt exists, then create it
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		writeToFile(ip);
	}
	
	public void writeToFile(String ip){
		try {
		
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(ip+"\n");
			bw.newLine();
			bw.flush();
			bw.close();
			
			System.out.println("Done");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
}