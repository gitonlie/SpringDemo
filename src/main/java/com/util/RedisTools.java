package com.util;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis工具类
 * 
 * @author Administrator
 *
 */
public class RedisTools {
	private static Logger logger = LoggerFactory.getLogger(RedisTools.class);
	public static JedisPool jedisPool = null;

	static {
		ResourceBundle resource = ResourceBundle.getBundle("redis");
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(Integer.parseInt(resource
				.getString("redis.maxActive")));
		poolConfig.setMaxIdle(Integer.parseInt(resource
				.getString("redis.maxIdle")));
		poolConfig.setMaxWaitMillis(Long.parseLong(resource
				.getString("redis.maxWait")));
		poolConfig.setTestOnBorrow(Boolean.valueOf(resource
				.getString("redis.testOnBorrow")));

		jedisPool = new JedisPool(poolConfig,
				resource.getString("redis.host"), Integer.parseInt(resource
						.getString("redis.port")));
	}
	
	  /**
     * Object转换byte[]类型
     *
     * @param key
     * @return
	 * @throws Exception 
     */
    public static byte[] toBytes(Object object) throws Exception {
        return ObjectUtils.serialize(object);
    }
 
    /**
     * byte[]型转换Object
     *
     * @param key
     * @return
     * @throws Exception 
     */
    public static Object toObject(byte[] bytes) throws Exception {
        return ObjectUtils.unserialize(bytes);
    }
	
	/**
	 * 获取缓存
     * <p>通过key获取储存在redis中的value</p>
     * <p>并释放连接</p>
     * @param key
     * @return 成功返回value 失败返回null
     */
    public static String get(String key){
        Jedis jedis = null;
        String value = null;
        try {
        	  jedis = jedisPool.getResource();
              if (jedis.exists(key)) {
                  value = jedis.get(key);
                  value = StringUtils.isNotBlank(value)
                          && !"nil".equalsIgnoreCase(value) ? value : null;
                  logger.debug("get {} = {}", key, value);
              }       
        } catch (Exception e) {           
        	logger.error(e.getMessage());
        } finally {
        	jedis.close();
        }
        return value;
    }
    /**
     * 获取缓存
     * @param key
     * @return
     */
    public static Object getObject(String key){
    	Object value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(toBytes(key))) {
                value = toObject(jedis.get(toBytes(key)));
                logger.debug("getObject {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.warn("getObject {} = {}", key, value, e);
        } finally {
        	jedis.close();
        }
        return value;
    }
    
    /**
     * 设置缓存
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param cacheSeconds
     *            超时时间，0为不超时
     * @return
     */
    public static String set(String key, String value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(key, value);
            if (cacheSeconds != 0) {
                jedis.expire(key,cacheSeconds);
            }
            logger.debug("set {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("set {} = {}", key, value, e);
        } finally {
        	jedis.close();
        }
        return result;
    }
    
    
    /**
     * 设置缓存
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param cacheSeconds
     *            超时时间，0为不超时
     * @return
     */
    public static String setObject(String key, Object value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(toBytes(key), toBytes(value));
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.debug("setObject {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setObject {} = {}", key, value, e);
        } finally {
        	jedis.close();
        }
        return result;
    }
    
    
    public static Long setnx(String key, String value){
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.setnx(key, value);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
        	jedis.close();
        }
        return result;
    }
    
    public static String getSet(String key, String value){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.getSet(key, value);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
        	jedis.close();
        }
        return result;
    }
 
    public static Long expire(String key, int seconds){
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.expire(key, seconds);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
        	jedis.close();
        }
        return result;
    }
 
    public static Long del(String key){
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.del(key);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
        	jedis.close();
        }
        return result;
    }
}
