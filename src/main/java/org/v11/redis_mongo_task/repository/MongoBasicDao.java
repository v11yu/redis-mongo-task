package org.v11.redis_mongo_task.repository;

import com.mongodb.DBObject;

public interface MongoBasicDao {
	/**
	 * 更新数据库字段
	 * @param newObj 新的obj
	 * @param keyNames 需要更新的keys,逗号隔开
	 */
	public void update(DBObject newObj, String keyNames);
	/**
	 * 通过id获取Object
	 * @param id
	 * @return
	 */
	public DBObject findById(String id);
}
