package org.v11.redis_mongo_task.repository.impl;

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
	public void delete(String pattern) {
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
		Set<String> st = jedis.keys(id+"*");
		if(st.size() != KEY_NUMS){
			return false;
		}
		DBObject obj = mongoDao.findById(id);
		for(String attr:st){
			String dbkey = attr.split("#")[1];
			String value = jedis.get(attr);
			obj.put(dbkey, value);
		}
		Log.info("更新操作: "+id+" 写入更新属性值");
		mongoDao.update(obj, KEYS);
		return true;
	}
	public Jedis getJedis() {
		return jedis;
	}
}
