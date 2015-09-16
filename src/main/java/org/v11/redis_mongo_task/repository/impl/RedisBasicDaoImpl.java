package org.v11.redis_mongo_task.repository.impl;

import java.util.Map;
import java.util.Set;

import org.v11.redis_mongo_task.repository.MongoBasicDao;
import org.v11.redis_mongo_task.repository.RedisBasicDao;
import org.v11.redis_mongo_task.utils.DBConfig;
import org.v11.redis_mongo_task.utils.Log;
import org.v11.redis_mongo_task.utils.RedisUtil;
import org.v11.redis_mongo_task.utils.TaskConfig;

import com.mongodb.DBObject;

import redis.clients.jedis.Jedis;

public class RedisBasicDaoImpl implements RedisBasicDao{
	private Jedis jedis;
	String KEYS;
	Integer KEY_NUMS;
	MongoBasicDao mongoDao;
	private final String colName = DBConfig.getValue("MongoColName");
	public RedisBasicDaoImpl() {
		// TODO Auto-generated constructor stub
		mongoDao = new MongoBasicDaoImpl(colName);
		jedis = RedisUtil.getUniqueJedis();
		KEYS = TaskConfig.getValue("keys");
		KEY_NUMS = KEYS.split(",").length;
	}
	@Override
	public void deleteByPattern(String pattern) {
		// TODO Auto-generated method stub
		Log.info("删除操作:redis delete pattern "+pattern);
		Set<String> keys = jedis.keys(pattern);
		if(keys.size() == 0) return ;
		String a[] = new String[keys.size()];
		jedis.del(keys.toArray(a));
	}
	@Override
	public boolean update(String id) {
		// TODO Auto-generated method stub
		Log.info("检查 "+id+" 是否需要更新");
		Map<String,String> mp = jedis.hgetAll(id);
		if(mp.size() != KEY_NUMS){
			return false;
		}
		DBObject obj = mongoDao.findById(id);
		String keys[] = KEYS.split(",");
		for(String attr:keys){
			String value = mp.get(attr);
			if(value !=null) obj.put(attr, mp.get(attr));
		}
		Log.info("更新操作: "+id+" 写入更新属性值");
		mongoDao.update(obj, KEYS);
		return true;
	}
	public Jedis getJedis() {
		return jedis;
	}
	@Override
	public void delete(String key) {
		// TODO Auto-generated method stub
		jedis.del(key);
	}
}
