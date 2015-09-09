package org.v11.redis_mongo_task.utils;

import org.apache.log4j.Logger;
import org.v11.redis_mongo_task.repository.impl.MongoBasicDaoImpl;

public class Log {
	private static Logger log = Logger.getLogger(Log.class);
	public static void info(Object msg){
		log.info(msg);
	}
	public static void error(Object msg){
		log.error(msg);
	}
	public static void debug(Object msg){
		log.debug(msg);
	}
	public static void warn(Object msg){
		log.warn(msg);
	}
}
