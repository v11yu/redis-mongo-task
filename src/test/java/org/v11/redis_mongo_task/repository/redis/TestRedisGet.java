package org.v11.redis_mongo_task.repository.redis;

import java.util.Map;

import org.v11.redis_mongo_task.utils.RedisUtil;

public class TestRedisGet {
	public static void main(String[] args) {
		Map<String,String> mp = RedisUtil.hgetAll("54");
		RedisUtil.del("54");
		System.out.println(mp);
	}
}
