package org.v11.redis_mongo_task.repository;

import redis.clients.jedis.Jedis;

public interface RedisBasicDao {
	public void delete(String key);
	public void deleteByPattern(String pattern);

	/**
	 * 直接将redis内容更新mongodb，不进行检查是否全部到达<br />
	 * update keys with check all attribute
	 * @param id
	 * @return
	 */
	/* 好处：有一部分key中，会有较多的keys，这样会减少mongodb压力 */
	public boolean updateNoCheck(String id);
}
