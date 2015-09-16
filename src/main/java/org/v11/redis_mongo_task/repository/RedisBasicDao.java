package org.v11.redis_mongo_task.repository;

import redis.clients.jedis.Jedis;

public interface RedisBasicDao {
	public void delete(String key);
	public void deleteByPattern(String pattern);
	/**
	 * 检查判断,weibo的所有分析是否已经完成<br />
	 * 完成则自动执行更新操作
	 * @param id
	 * @return
	 */
	public boolean update(String id);
	public Jedis getJedis();
}
