package org.v11.redis_mongo_task;

import java.util.HashSet;
import java.util.Set;

import org.v11.redis_mongo_task.repository.RedisBasicDao;
import org.v11.redis_mongo_task.repository.impl.RedisBasicDaoImpl;
import org.v11.redis_mongo_task.utils.DBConfig;
import org.v11.redis_mongo_task.utils.Log;
import org.v11.redis_mongo_task.utils.TaskConfig;

import redis.clients.jedis.Jedis;

public class UpdateApp {
	
	public void jobDetail(){
		Log.info("初始化properties文件....");
		TaskConfig.init();
		DBConfig.init();
		Log.info("开始执行redis 更新mongodb操作....");
		RedisBasicDao redisDao = new RedisBasicDaoImpl();
		Jedis jedis = redisDao.getJedis();
		Set<String> allkeys = jedis.keys("*");
		Log.info("已有redis keys数量...."+allkeys.size());
		int hasUpdated = 0;
		for(String key:allkeys){
			//if(!key.matches("tsina*")) continue;
			String id = key;
			if(redisDao.updateNoCheck(id)){
				redisDao.delete(id);
				hasUpdated++;
			}
			if(hasUpdated>0 &&hasUpdated % 2000 == 0){
				Log.info("hasUpdated "+hasUpdated);
			}
		}
		Log.info("更新了redis keys数量..."+hasUpdated);
	}
	public static void main(String[] args) throws InterruptedException {
		UpdateApp up = new UpdateApp();
		while(true){
			up.jobDetail();
			int TIME = TaskConfig.getNum("time_interval");
			Thread.sleep(1000*60*TIME);
		}
	}
}
