package org.v11.redis_mongo_task.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public interface MongoBasicDao {
	/**
	 * 更新数据库字段
	 * @param updateObj 新的obj
	 * @param id 需要更新的id
	 */
	public boolean update(BasicDBObject updateObj, String id);
	/**
	 * 通过id获取Object
	 * @param id
	 * @return
	 */
	public DBObject findById(String id);
}
