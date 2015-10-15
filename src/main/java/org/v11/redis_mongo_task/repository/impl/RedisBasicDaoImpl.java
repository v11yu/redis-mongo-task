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
		Log.debug("删除操作:redis delete pattern "+pattern);
		Set<String> keys = jedis.keys(pattern);
		if(keys.size() == 0) return ;
		String a[] = new String[keys.size()];
		jedis.del(keys.toArray(a));
	}
	@Override
	public boolean update(String id) {
		// TODO Auto-generated method stub
		Log.debug("检查 "+id+" 是否需要更新");
		Map<String,String> mp = null;
		try{
			mp = jedis.hgetAll(id);
		}catch(Exception e){
			Log.error("redis 取值type异常 "+id+" data type error "+e);
			delete(id);
			return false;
		}
		if(mp == null){
			Log.error(id + " mp is null");
			return false;
		}
		if(mp.size() < KEY_NUMS){
			Log.debug("keys数量异常 "+mp.size());
			return false;
		}
		DBObject obj = mongoDao.findById(id);
		if(obj == null){
			Log.error(id + " mongo obj is null");
			delete(id);
			return false;
		}
		String keys[] = KEYS.split(",");
		String types[] = TaskConfig.getValue("type").split(",");
		int i=0;
		try{
			for (String attr : keys) {
				String value = mp.get(attr);
				if (value != null) {
					Log.debug("value != null");
					if (types[i].equals("string")) obj.put(attr, value);
					else if (types[i].equals("int")) obj.put(attr, Integer.parseInt(value));
					else if (types[i].equals("double")) obj.put(attr, Double.parseDouble(value));
					else {
						Log.error("未知类型!!undefine type error");
						return false;
					}
				} else {
					Log.debug("value null");
					return false;
				}
				i++;
		}
		}catch(Exception e){
			Log.error("类型定义错误 "+id+" config define type error!! please check task.properties file "+e);
			delete(id);
			return false;
		}
		Log.debug("更新操作: "+id+" 写入更新属性值");
		mongoDao.update(obj, KEYS);
		return true;
	}
	public Jedis getJedis() {
		return jedis;
	}
	@Override
	public void delete(String key) {
		// TODO Auto-generated method stub
		Log.debug("删除操作:redis delete "+key);
		jedis.del(key);
	}
}
