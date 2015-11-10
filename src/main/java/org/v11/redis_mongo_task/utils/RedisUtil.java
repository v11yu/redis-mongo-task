package org.v11.redis_mongo_task.utils;

import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * <code>Redis</code>单例模式
 * @author v11
 */
public class RedisUtil {
	private static RedisUtil unique;
	private Jedis jedis;
	private String host = DBConfig.getValue("RedisIp");
	private Integer port = DBConfig.getNum("RedisPort");
	private RedisUtil(){
		jedis = new Jedis(host, port,300*1000);
	}
	public static Jedis getUniqueJedis(){
		if(unique == null){
			unique = new RedisUtil();
		}
		return unique.jedis;
	}
	public static synchronized Set<String> keys (String pattern){
		
		return RedisUtil.getUniqueJedis().keys(pattern);
	}
	public static synchronized Long del(final String... keys){
		
		return RedisUtil.getUniqueJedis().del(keys);
	}
	public static synchronized Long del(final String key){
		
		return RedisUtil.getUniqueJedis().del(key);
	}
	public static synchronized Map<String, String> hgetAll(final String key){
		return RedisUtil.getUniqueJedis().hgetAll(key);
	}
}
