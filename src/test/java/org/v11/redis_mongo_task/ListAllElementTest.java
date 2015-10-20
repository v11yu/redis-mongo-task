package org.v11.redis_mongo_task;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.v11.redis_mongo_task.utils.RedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

public class ListAllElementTest {
	Jedis jedis = RedisUtil.getUniqueJedis();
	
	public void testListByScan(){
		//jedis.scan(cursor, params)
		Long startTime = System.currentTimeMillis();
        List<String> retList = new ArrayList<String>();
        Jedis jedis = RedisUtil.getUniqueJedis();
        String scanRet = "0";
        do {
            ScanResult ret = jedis.scan(scanRet);
            scanRet = ret.getStringCursor();
            retList.addAll(ret.getResult());
        } while (!scanRet.equals("0"));
        System.out.println(retList.size());
        Long endTime = System.currentTimeMillis();
        System.out.println("testListByScan using time is:"+(endTime - startTime));
	}
	public void testListByGet(){
		Long startTime = System.currentTimeMillis();
		Set<String> keys = jedis.keys("*");
		System.out.println(keys.size());
		Long endTime = System.currentTimeMillis();
        System.out.println("testListByGet using time is:"+(endTime - startTime));
	}
	public static void main(String[] args) {
		new ListAllElementTest().testListByGet();
		new ListAllElementTest().testListByScan();
		
	}
}
