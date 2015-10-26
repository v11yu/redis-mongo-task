package org.v11.redis_mongo_task;

import java.util.Set;

import org.v11.redis_mongo_task.repository.RedisBasicDao;
import org.v11.redis_mongo_task.repository.impl.RedisBasicDaoImpl;
import org.v11.redis_mongo_task.task.JobTools;
import org.v11.redis_mongo_task.task.TaskJob;
import org.v11.redis_mongo_task.utils.DBConfig;
import org.v11.redis_mongo_task.utils.Log;
import org.v11.redis_mongo_task.utils.RedisUtil;
import org.v11.redis_mongo_task.utils.TaskConfig;

import redis.clients.jedis.Jedis;

public class NewUpdateApp {
	static int SplitNum = 5;
	public void jobDetail() throws InterruptedException{
		Log.info("初始化properties文件....");
		TaskConfig.init();
		DBConfig.init();
		Log.info("开始执行redis 更新mongodb操作....");
		RedisBasicDao redisDao = new RedisBasicDaoImpl();
		Set<String> allkeys = RedisUtil.keys("*");
		Log.info("已有redis keys数量...."+allkeys.size());
		//split data to number of <$SplitNum>, and thread to update mongodb
		Set<String>[] keys = JobTools.splitList(allkeys, SplitNum);
		TaskJob[] taskJobs = new TaskJob[SplitNum];
		Thread ths[] = new Thread[SplitNum];
		//init && start thread
		for(int i=0;i<SplitNum;i++){
			taskJobs[i] = new TaskJob();
			taskJobs[i].setData(keys[i], redisDao);
			ths[i] = new Thread(taskJobs[i]);
			ths[i].start();
		}
		//join thread and wait it end
		for(int i=0;i<SplitNum;i++) ths[i].join();
		int hasUpdated = 0;
		for(int i=0;i<SplitNum;i++) hasUpdated += taskJobs[i].getHasUpdated();
		Log.info("更新了redis keys数量..."+hasUpdated);
	}
	public static void main(String[] args) throws InterruptedException {
		NewUpdateApp up = new NewUpdateApp();
		while(true){
			up.jobDetail();
			int TIME = TaskConfig.getNum("time_interval");
			Thread.sleep(1000*60*TIME);
		}
	}
}
