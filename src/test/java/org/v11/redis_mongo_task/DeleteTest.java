package org.v11.redis_mongo_task;

import org.junit.Test;
import org.v11.redis_mongo_task.repository.RedisBasicDao;
import org.v11.redis_mongo_task.repository.impl.RedisBasicDaoImpl;

public class DeleteTest {
	RedisBasicDao redisDao = new RedisBasicDaoImpl();
	@Test
	public void redisDeleteAll(){
		redisDao.deleteByPattern("*");
	}
	public static void main(String[] args) {
		new DeleteTest().redisDeleteAll();
	}
}
