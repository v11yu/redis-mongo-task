package org.v11.redis_mongo_task.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Task配置文件
 * @author v11
 */
public class TaskConfig {
	private TaskConfig(){}
	private static Properties props = new Properties(); 
	public static void init(){
		props = new Properties();
		try {
			InputStream in = 
					TaskConfig.class.getResourceAsStream("/task.properties");
			//props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mongodb.properties"));
			props.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	static{
		init();
	}
	public static String getValue(String key){
		return props.getProperty(key);
	}
	public static int getNum(String key){
		return Integer.parseInt(props.getProperty(key).trim());
	}
    public static void updateProperties(String key,String value) {    
            props.setProperty(key, value); 
    } 
}
