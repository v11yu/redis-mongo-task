package org.v11.redis_mongo_task.task;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.v11.redis_mongo_task.repository.RedisBasicDao;
import org.v11.redis_mongo_task.utils.Log;



public class TaskJob implements Runnable{
	Integer hasUpdated = 0;
	Set<String> keys;
	RedisBasicDao redisDao;
	public void setData(Set<String> keys,RedisBasicDao redisDao){
		this.keys = keys;
		this.redisDao =redisDao;
	}
	@Override
	public void run() {
		for(String key:keys){
			String id = key;
			if(redisDao.updateNoCheck(id)){
				redisDao.delete(id);
				hasUpdated++;
			}
			if(hasUpdated>0 &&hasUpdated % 2000 == 0){
				Log.info("hasUpdated "+hasUpdated);
			}
		}
	}
	public Integer getHasUpdated(){return hasUpdated;}
	
}
