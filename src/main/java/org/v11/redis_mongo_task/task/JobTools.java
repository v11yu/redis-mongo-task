package org.v11.redis_mongo_task.task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JobTools {
	/**
	 * 切割数组，分为k份，再进行multithreading
	 * @param all 数据
	 * @param k 份数
	 * @return
	 */
	 public static <T> Set<T>[] splitList(Set<T> all,int k){
		 if(all == null) return null;
		 Set<T> nums[] = new Set[k];
		 for(int i=0;i<k;i++) nums[i] = new HashSet<T>();
		 int i = 0;
		 for(T key:all){
			 nums[(i++)%k].add(key);
		 }
		 return nums;
	}
}
