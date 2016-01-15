package org.v11.redis_mongo_task.repository;

import org.junit.Test;
import org.v11.redis_mongo_task.repository.impl.MongoBasicDaoImpl;
import org.v11.redis_mongo_task.utils.DBConfig;

import com.mongodb.BasicDBObject;

public class TestUpdate {
	public static void main(String[] args) {
		String id = "10";
		MongoBasicDaoImpl dao = new MongoBasicDaoImpl(DBConfig.getValue("MongoColName"));
		BasicDBObject obj = new BasicDBObject();
		obj.put("lala", "123");
		dao.update(obj, id);
		System.out.println(dao.findById(id));
		System.out.println(obj);
	}
}
