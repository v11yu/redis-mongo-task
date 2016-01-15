package org.v11.redis_mongo_task.utils;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

/**
 * <code>MongoDB</code>单例模式
 * <br /> 用于存缓存数据
 * @author v11
 */
public class MemMongoDbUtil {
	/** mongoDb server ip */
	private static String IP = DBConfig.getValue("MemMongoIp");
	/** mongoDb server port */
	private static Integer PORT = DBConfig.getNum("MemMongoPort");
	/** 数据库名 */
	private static String DB_NAME = DBConfig.getValue("MemMongoDBName");
	private DB database;
	private static MemMongoDbUtil uniqueInstance;
	private static MongoClient mongoClient;
	private MemMongoDbUtil(){
		try {
			mongoClient = new MongoClient( IP , PORT );
			database = mongoClient.getDB(DB_NAME);
		}  catch (MongoException ex) {
			throw new IllegalArgumentException(ex);
		}
	}
	/**
	 * Gets a collection with a given name.
	 * If the collection does not exist, a new collection is created.
	 * @param collectionName the name of the collection to return
	 * @return the collection
	 */
    public static synchronized DBCollection getCollection(String collectionName){
    	if(uniqueInstance == null){
    		uniqueInstance = new MemMongoDbUtil();
    	}
        return uniqueInstance.database.getCollection(collectionName);
    }
    /**
     * the mongoclient is singleton<p />
     * A MongoDB client with internal connection pooling. For most applications
     * @return
     */
    public static synchronized MongoClient getMongo(){
    	if(uniqueInstance == null){
    		uniqueInstance = new MemMongoDbUtil();
    	}
    	return uniqueInstance.mongoClient;
    }

}
