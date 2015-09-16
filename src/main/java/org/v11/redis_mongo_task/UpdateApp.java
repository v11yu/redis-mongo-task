package org.v11.redis_mongo_task;

import java.util.HashSet;
import java.util.Set;

import org.v11.redis_mongo_task.repository.RedisBasicDao;
import org.v11.redis_mongo_task.repository.impl.RedisBasicDaoImpl;
import org.v11.redis_mongo_task.utils.Log;
import org.v11.redis_mongo_task.utils.TaskConfig;

import redis.clients.jedis.Jedis;

public class UpdateApp {
	static final int TIME = TaskConfig.getNum("time_interval");
	public void jobDetail(){
		Log.info("开始执行redis 更新mongodb操作....");
		HashSet<String> mk = new HashSet<>();
		RedisBasicDao redisDao = new RedisBasicDaoImpl();
		Jedis jedis = redisDao.getJedis();
		Set<String> allkeys = jedis.keys("*");
		for(String key:allkeys){
			Log.info("do redis key: "+key);
			//if(!key.matches("tsina*")) continue;
			String id = key;
			if(mk.contains(id)) continue;
			mk.add(id);
			if(redisDao.update(id)){
				redisDao.delete(id);
			}	
		}
	}
	public static void main(String[] args) throws InterruptedException {
		UpdateApp up = new UpdateApp();
		while(true){
			up.jobDetail();
			Thread.sleep(1000*60*TIME);
		}
	}
}
