package org.v11.redis_mongo_task.repository.impl;

import java.util.Map;
import java.util.Set;

import org.v11.redis_mongo_task.repository.MongoBasicDao;
import org.v11.redis_mongo_task.repository.RedisBasicDao;
import org.v11.redis_mongo_task.utils.DBConfig;
import org.v11.redis_mongo_task.utils.Log;
import org.v11.redis_mongo_task.utils.RedisUtil;
import org.v11.redis_mongo_task.utils.TaskConfig;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import redis.clients.jedis.Jedis;

public class RedisBasicDaoImpl implements RedisBasicDao{
	String KEYS;
	Integer KEY_NUMS;
	MongoBasicDao mongoDao;
	private final String colName = DBConfig.getValue("MongoColName");
	public RedisBasicDaoImpl() {
		// TODO Auto-generated constructor stub
		mongoDao = new MongoBasicDaoImpl(colName);
		KEYS = TaskConfig.getValue("keys");
		KEY_NUMS = KEYS.split(",").length;
	}
	@Override
	public void deleteByPattern(String pattern) {
		// TODO Auto-generated method stub
		Log.debug("删除操作:redis delete pattern "+pattern);
		Set<String> keys = RedisUtil.keys(pattern);
		if(keys.size() == 0) return ;
		String a[] = new String[keys.size()];
		RedisUtil.del(keys.toArray(a));
	}	
	@Override
	public void delete(String key) {
		// TODO Auto-generated method stub
		Log.debug("删除操作:redis delete "+key);
		RedisUtil.del(key);
	}
	@Override
	public boolean updateNoCheck(String id) {
		// TODO Auto-generated method stub
		Log.debug("直接进行 "+id+" 更新");
		Map<String,String> mp = null;
		try{
			mp = RedisUtil.hgetAll(id);
		}catch(Exception e){
			Log.error("redis取值异常，可能是redis 取值type异常（非mp类型） "+id+" data type error "+e);
			delete(id);
			return false;
		}
		if(mp == null){
			Log.error(id + " mp is null");
			return false;
		}
		BasicDBObject updateObj = new BasicDBObject();
		String keys[] = KEYS.split(",");
		String types[] = TaskConfig.getValue("type").split(",");
		int i=0,hit = 0;
		try{
			for (String attr : keys) {
				String value = mp.get(attr);
				if (value != null) {
					hit ++;
					Log.debug("value != null");
					if (types[i].equals("string")) updateObj.append(attr, value);
					else if (types[i].equals("int")) updateObj.append(attr, Integer.parseInt(value));
					else if (types[i].equals("double")) updateObj.append(attr, Double.parseDouble(value));
					else {
						Log.error("未知类型!!undefine type error");
						delete(id);
						return false;
					}
				}
				i++;
		}
		}catch(Exception e){
			Log.error("类型定义错误 "+id+" config define type error!! please check task.properties file "+e);
			delete(id);
			return false;
		}
		Log.debug("更新操作: "+id+" 写入更新属性值,命中属性个数："+ hit);
		if(!mongoDao.update(updateObj, id)){
			Log.debug(id + " mongo update fail");
			delete(id);
			return false;
		}
		return true;
	}
}
