package org.v11.redis_mongo_task;

import org.junit.Test;
import org.v11.redis_mongo_task.repository.MongoBasicDao;
import org.v11.redis_mongo_task.repository.impl.MongoBasicDaoImpl;

import com.mongodb.DBObject;

public class DataTest {
	@Test
	public void testMsg(){
		MongoBasicDao msgDao = new MongoBasicDaoImpl("msginfo");
    	DBObject obj = msgDao.findById("tsina_3881187430482731");
        System.out.println(obj);
	}
}
