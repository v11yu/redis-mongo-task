package org.v11.redis_mongo_task.utils;

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
		jedis = new Jedis(host, port);
	}
	public static Jedis getUniqueJedis(){
		if(unique == null){
			unique = new RedisUtil();
		}
		return unique.jedis;
	}
}
