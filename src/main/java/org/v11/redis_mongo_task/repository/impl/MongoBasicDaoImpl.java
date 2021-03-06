package org.v11.redis_mongo_task.repository.impl;

import org.apache.log4j.Logger;
import org.v11.redis_mongo_task.repository.MongoBasicDao;
import org.v11.redis_mongo_task.utils.Log;
import org.v11.redis_mongo_task.utils.MongoDbUtil;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class MongoBasicDaoImpl implements MongoBasicDao{
	DBCollection col;
	String colName;
	/*
	 *  add menMongoDao
	 */
	MongoBasicDao menMongoDao; 
	public MongoBasicDaoImpl(String colName) {
		// TODO Auto-generated constructor stub
		this.colName = colName;
		col = MongoDbUtil.getCollection(colName);
		menMongoDao = new MemMongoBasicDaoImpl(colName);
	}
	@Override
	public boolean update(BasicDBObject updateObj, String id) {
		// TODO Auto-generated method stub
		/*
		 * add update to two mongo instance
		 */
		menMongoDao.update(updateObj, id);
		try {
			Log.debug("更新操作: " + colName + " options" + updateObj + ", _id:"
					+ id);
			DBObject query = new BasicDBObject("_id", id);
			col.update(query, new BasicDBObject("$set", updateObj));
			return true;
		} catch (Exception e) {
			Log.error(id+" "+updateObj);
			Log.error("mongo更新数据库异常" + e);
			return false;
		}
	}

	@Override
	public DBObject findById(String id) {
		// TODO Auto-generated method stub
		try {
			DBObject obj = col.findOne(id);
			Log.debug("查询操作: " + colName + " findById" + id + ", result:" + obj);
			return obj;
		} catch (Exception e) {
			Log.error("mongo查询数据库异常"+e.getMessage());
			return null;
		}
	}

}
