package com.tianee.webframe.util.cache;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import com.tianee.webframe.util.global.TeeSysProps;

/**
 * 分布式缓存客户端
 * @author kakalion
 *
 */
public class RedisClient extends BaseRedisClient{
	private static BaseRedisClient obj = null;
//	private Jedis jedis;// 非切片额客户端连接
//	private ShardedJedis shardedJedis;// 切片额客户端连接
//	private ShardedJedisPool shardedJedisPool;// 切片连接池

	public RedisClient() {
		initialPool();
//		initialShardedPool();
//		shardedJedis = shardedJedisPool.getResource();
//		jedis = jedisPool.getResource();
		obj = this;
	}

	/**
	 * 初始化非切片池
	 */
	private void initialPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
//		config.setMaxTotal(1500);
//		config.setMaxIdle(10);
//		config.setMaxWaitMillis(1000*10);
//		config.setTestOnBorrow(true);
		
		
//		config.setMaxTotal(1500);
//		config.setMaxIdle(50);
//		config.setMinIdle(8);//设置最小空闲数
//		config.setMaxWaitMillis(10000);
//		config.setTestOnBorrow(true);
//		config.setTestOnReturn(true);
//		//Idle时进行连接扫描
//		config.setTestWhileIdle(true);
//		//表示idle object evitor两次扫描之间要sleep的毫秒数
//		config.setTimeBetweenEvictionRunsMillis(30000);
//		//表示idle object evitor每次扫描的最多的对象数
//		config.setNumTestsPerEvictionRun(10);
//		//表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
//		config.setMinEvictableIdleTimeMillis(60000);
		
		
		String ip = TeeSysProps.getString("REDIS_IP");
		String pwd = TeeSysProps.getString("REDIS_PWD");
		if(ip.contains(":") || ip.contains(",")){
			String ips[] = ip.split(",");
			Set<String> sentinels = new HashSet<String>();
	        for(String ipt:ips){
	        	sentinels.add(ipt);
	        }
	        jedisSentinelPool = new JedisSentinelPool("master",sentinels,config,pwd);
	        
		}else{
	        jedisPool = new JedisPool(config, TeeSysProps.getString("REDIS_IP"), Integer.parseInt( TeeSysProps.getString("REDIS_PORT")),10*1000,pwd);
		}
	
	}
	

	public static BaseRedisClient getInstance(){
		if(obj==null){
			obj = new RedisClient();
		}
		return obj;
	}
	
}
