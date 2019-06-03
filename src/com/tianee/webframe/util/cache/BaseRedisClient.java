package com.tianee.webframe.util.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

public class BaseRedisClient {
	protected JedisPool jedisPool = null;// 非切片连接池
	protected JedisSentinelPool jedisSentinelPool = null;// 非切片连接池
	
	private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;
	
	private Jedis getResource(){
		String ip = TeeSysProps.getString("REDIS_IP");
		if(ip.contains(":") || ip.contains(",")){
			return this.jedisSentinelPool.getResource();
		}else{
			return this.jedisPool.getResource();
		}
	}
	
	/**
	 * 存入对象
	 * @param key
	 * @param object
	 */
	public void setObject(String key,Object object){
		ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        Jedis jedis = null;
        try {
        	jedis = getResource();
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            jedis.set(key.getBytes(), bytes);
        } catch (Exception e) {
        	
        }finally{
        	if(jedis!=null){
        		jedis.close();
        	}
        }
	}
	
	public void set(String key,String value){
        Jedis jedis = null;
        try {
        	jedis = getResource();
            jedis.set(key, value);
        } catch (Exception e) {
        	
        }finally{
        	if(jedis!=null){
        		jedis.close();
        	}
        }
	}
	
	/**
	 * 存入对象
	 * @param shardedJedis
	 * @param key
	 * @param field
	 * @param object
	 */
	public void hsetObject(String key,String field,Object object){
		ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        Jedis shardedJedis = null;
        try {
        	shardedJedis = getResource();
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            shardedJedis.hset(key.getBytes(), field.getBytes(),bytes);
        } catch (Exception e) {
        	e.printStackTrace();
        }finally{
        	if(shardedJedis!=null){
        		shardedJedis.close();
        	}
        }
	}
	
	public void del(String key){
		Jedis shardedJedis = null;
        try {
        	shardedJedis = getResource();
            // 序列化
            shardedJedis.del(key);
        } catch (Exception e) {
        	e.printStackTrace();
        }finally{
        	if(shardedJedis!=null){
        		shardedJedis.close();
        	}
        }
	}
	
	public void delObject(String key){
		Jedis shardedJedis = null;
        try {
        	shardedJedis = getResource();
            // 序列化
            shardedJedis.del(key.getBytes());
        } catch (Exception e) {
        	e.printStackTrace();
        }finally{
        	if(shardedJedis!=null){
        		shardedJedis.close();
        	}
        }
	}
	
	/**
	 * 存入对象
	 * @param shardedJedis
	 * @param key
	 * @param field
	 * @param object
	 */
	public void setExObject(String key,Object object,int seconds){
		ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        Jedis shardedJedis = null;
        try {
        	shardedJedis = getResource();
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            shardedJedis.setex(key.getBytes(), seconds, bytes);
        } catch (Exception e) {
        	e.printStackTrace();
        }finally{
        	if(shardedJedis!=null){
        		shardedJedis.close();
        	}
        }
	}
	
	/**
	 * 获取对象
	 * @param shardedJedis
	 * @param key
	 * @return
	 */
	public Object getObject(String key){
		Jedis shardedJedis = null;
		
		ByteArrayInputStream bais = null;
        try {
        	shardedJedis = getResource();
        	byte[] bytes = shardedJedis.get(key.getBytes());
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
        	return null;
        } finally{
        	if(shardedJedis!=null){
        		shardedJedis.close();
        	}
        }
	}
	
	
	/**
	 * 获取对象
	 * @param shardedJedis
	 * @param key
	 * @return
	 */
	public String get(String key){
		Jedis shardedJedis = null;
        try {
        	shardedJedis = getResource();
            return shardedJedis.get(key);
        } catch (Exception e) {
        	return null;
        } finally{
        	if(shardedJedis!=null){
        		shardedJedis.close();
        	}
        }
	}
	
	/**
	 * @param shardedJedis
	 * @param key
	 * @return
	 */
	public void expire(String key,int seconds){
		Jedis shardedJedis = null;
        try {
        	shardedJedis = getResource();
        	shardedJedis.expire(key, seconds);
        } catch (Exception e) {
        } finally{
        	if(shardedJedis!=null){
        		shardedJedis.close();
        	}
        }
	}
	
	/**
	 * @param shardedJedis
	 * @param key
	 * @return
	 */
	public boolean exists(String key){
		Jedis shardedJedis = null;
        try {
        	shardedJedis = getResource();
        	return shardedJedis.exists(key);
        } catch (Exception e) {
        	return false;
        } finally{
        	if(shardedJedis!=null){
        		shardedJedis.close();
        	}
        }
	}
	
	
	/**
	 * @param shardedJedis
	 * @param key
	 * @return
	 */
	public boolean existsObject(String key){
		Jedis shardedJedis = null;
        try {
        	shardedJedis = getResource();
        	return shardedJedis.exists(key.getBytes());
        } catch (Exception e) {
        	return false;
        } finally{
        	if(shardedJedis!=null){
        		shardedJedis.close();
        	}
        }
	}
	
	/**
	 * @param shardedJedis
	 * @param key
	 * @return
	 */
	public void expireObject(String key,int seconds){
		Jedis shardedJedis = null;
        try {
        	shardedJedis = getResource();
        	shardedJedis.expire(key.getBytes(), seconds);
        } catch (Exception e) {
        } finally{
        	if(shardedJedis!=null){
        		shardedJedis.close();
        	}
        }
	}
	
	/**
	 * 获取对象
	 * @param key
	 * @param field
	 * @return
	 */
	public Object hgetObject(String key,String field){
		Jedis shardedJedis = null;
		
		ByteArrayInputStream bais = null;
        try {
        	shardedJedis = getResource();
            // 反序列化
        	byte[] bytes = shardedJedis.hget(key.getBytes(),field.getBytes());
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
        	return null;
        } finally{
        	if(shardedJedis!=null){
        		shardedJedis.close();
        	}
        }
	}
	
	
	public String hget(String key,String field){
		Jedis shardedJedis = null;
        try {
        	shardedJedis = getResource();
        	return shardedJedis.hget(key, field);
        } catch (Exception e) {
        	return null;
        } finally{
        	if(shardedJedis!=null){
        		shardedJedis.close();
        	}
        }
	}
	
	public void hset(String table,String key,String field){
		Jedis shardedJedis = null;
        try {
        	shardedJedis = getResource();
        	shardedJedis.hset(table,key, field);
        } catch (Exception e) {
        	
        } finally{
        	if(shardedJedis!=null){
        		shardedJedis.close();
        	}
        }
	}
	
	public void setEx(String key,String value,int seconds){
		Jedis shardedJedis = null;
        try {
        	shardedJedis = getResource();
        	shardedJedis.setex(key, seconds, value);
        } catch (Exception e) {
        	
        } finally{
        	if(shardedJedis!=null){
        		shardedJedis.close();
        	}
        }
	}
	
	public void hDelObject(String key,String field){
		Jedis shardedJedis = null;
		try{
			shardedJedis = getResource();
			shardedJedis.hdel(key.getBytes(), field.getBytes());
		}catch(Exception ex){
			
		}finally{
			if(shardedJedis!=null){
				shardedJedis.close();
			}
		}
	}
	
	public void hDel(String key,String field){
		Jedis shardedJedis = null;
		try{
			shardedJedis = getResource();
			shardedJedis.hdel(key, field);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(shardedJedis!=null){
				shardedJedis.close();
			}
		}
	}
	
	public Map<String,Object> hgetAllObject(String key){
		Jedis shardedJedis = null;
		Map<byte[],byte[]> bytes = null;
		try{
			shardedJedis = getResource();
			bytes = shardedJedis.hgetAll(key.getBytes());
		}catch(Exception ex){
			return null;
		}finally{
			if(shardedJedis!=null){
				shardedJedis.close();
			}
		}
		
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		Map<String,Object> targets = new HashMap<String,Object>();
		Set<byte[]> keys = bytes.keySet();
		for(byte[] key0:keys){
			try {
	            // 反序列化
	            bais = new ByteArrayInputStream(bytes.get(key0));
	            ois = new ObjectInputStream(bais);
	            targets.put(new String(key0), ois.readObject());
	        } catch (Exception e) {
	        	e.printStackTrace();
	        } finally{
	        	try {
					bais.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		}
		return targets;
	}
	
	
	/**
	 * 增加计数器
	 * @param key
	 * @return
	 */
	public long incr(String key){
        Jedis jedis = null;
        try {
        	jedis = getResource();
            return jedis.incr(key);
        } catch (Exception e) {
        	return 0;
        }finally{
        	if(jedis!=null){
        		jedis.close();
        	}
        }
	}
	
	/**
	 * 减少计数器
	 * @param key
	 * @return
	 */
	public long decr(String key){
        Jedis jedis = null;
        try {
        	jedis = getResource();
            return jedis.decr(key);
        } catch (Exception e) {
        	return 0;
        }finally{
        	if(jedis!=null){
        		jedis.close();
        	}
        }
	}
	
	/**
	 * 获取计数器
	 * @param key
	 * @return
	 */
	public long getCount(String key){
        Jedis jedis = null;
        try {
        	jedis = getResource();
            return TeeStringUtil.getLong(jedis.get(key), 0);
        } catch (Exception e) {
        	return 0;
        }finally{
        	if(jedis!=null){
        		jedis.close();
        	}
        }
	}
	
	/**
	 * 加入集合
	 * @param key
	 * @return
	 */
	public void sadd(String key,String value){
        Jedis jedis = null;
        try {
        	jedis = getResource();
        	jedis.sadd(key, value);
        } catch (Exception e) {
        	
        }finally{
        	if(jedis!=null){
        		jedis.close();
        	}
        }
	}
	
	/**
	 * 删除集合中的某个元素
	 * @param key
	 * @return
	 */
	public void srem(String key,String value){
        Jedis jedis = null;
        try {
        	jedis = getResource();
        	jedis.srem(key, value);
        } catch (Exception e) {
        	
        }finally{
        	if(jedis!=null){
        		jedis.close();
        	}
        }
	}
	
	/**
	 * 获取集合中的所有元素
	 * @param key
	 * @return
	 */
	public Set<String> smembers(String key){
        Jedis jedis = null;
        try {
        	jedis = getResource();
        	return jedis.smembers(key);
        } catch (Exception e) {
        	return null;
        }finally{
        	if(jedis!=null){
        		jedis.close();
        	}
        }
	}
	
	/**
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public boolean getLock(String lockKey, String requestId, int expireTime) {
    	Jedis jedis = null;
        try {
        	jedis = getResource();
        	String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

            if (LOCK_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        } catch (Exception e) {
        	return false;
        }finally{
        	if(jedis!=null){
        		jedis.close();
        	}
        }

    }
    
    
    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseLock(String lockKey, String requestId) {

    	Jedis jedis = null;
        try {
        	jedis = getResource();
        	String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

            if (RELEASE_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        } catch (Exception e) {
        	return false;
        }finally{
        	if(jedis!=null){
        		jedis.close();
        	}
        }
    }
}
