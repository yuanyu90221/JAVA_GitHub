package com.yuanyu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileTranversal implements Runnable{
	static Properties prop = new Properties();
	public static void main(String[] args) {
		FileTranversal.doDelete();
	}
	
	public static void displayDirectoryContents(File dir){
		try {
		File[] files = dir.listFiles();
			for(File file : files){
				if(file.isDirectory()){
					System.out.println("directory:" +file.getCanonicalPath());
					displayDirectoryContents(file);
				}
				else{
					System.out.println("delete file:" + file.getCanonicalPath());
					file.delete();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static {
		loadProperties();
	}
	public static void loadProperties(){
	    InputStream input = null;
	    try {
			input = new FileInputStream("config.properties");
			prop.load(input);
			System.out.println("deletedir:"+ prop.getProperty("deletedir"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(input != null){
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void run() {
		File currentDir = new File(prop.getProperty("deletedir"));
		while(true){			
			displayDirectoryContents(currentDir);
		}		
	}
	
	public static void doDelete(){
		FileTranversal ft = new FileTranversal();
		Thread deleteThread = new Thread(ft);
		deleteThread.start();
	}

}
