package org.v11.redis_mongo_task.utils;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

/**
 * <code>MongoDB</code>单例模式
 * @author v11
 */
public class MongoDbUtil {
	/** mongoDb server ip */
	private static String IP = DBConfig.getValue("MongoIp");
	/** mongoDb server port */
	private static Integer PORT = DBConfig.getNum("MongoPort");
	/** 数据库名 */
	private static String DB_NAME = DBConfig.getValue("MongoDBName");
	private DB database;
	private static MongoDbUtil uniqueInstance;
	private static MongoClient mongoClient;
	private MongoDbUtil(){
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
    		uniqueInstance = new MongoDbUtil();
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
    		uniqueInstance = new MongoDbUtil();
    	}
    	return uniqueInstance.mongoClient;
    }

}
