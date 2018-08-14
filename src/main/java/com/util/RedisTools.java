package com.util;

import redis.clients.jedis.Jedis;

public class RedisTools {
	private static final String LOCK_SUCCESS = "OK";
	private static final String SET_IF_NOT_EXIST = "NX";
	private static final String SET_WITH_EXPIRE_TIME = "PX";

	public static boolean tryGetDistributedLock(Jedis jedis, String lockKey,
			String requestId, int expireTime) {
		String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST,
				SET_WITH_EXPIRE_TIME, expireTime);
		if (LOCK_SUCCESS.equals(result)) {
			
		}
		return false;
	}
}
