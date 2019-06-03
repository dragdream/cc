package com.tianee.webframe.util.cache;


public class Cache {
	private String cacheName = "default_cache_";
	public Cache(String cacheName){
		this.cacheName = cacheName;
	}
	
	public Element get(String key){
		Element element = null;
		element = (Element) RedisClient.getInstance().hgetObject(cacheName, key);
		return element;
	}
	
	public Element get(int key){
		Element element = null;
		element = (Element) RedisClient.getInstance().hgetObject(cacheName, String.valueOf(key));
		return element;
	}
	
	public void put(Element element){
		RedisClient.getInstance().hsetObject(cacheName,element.getKey(), element);
	}
	
	public void remove(String key){
		RedisClient.getInstance().hDel(cacheName, key);
	}
	
	public void remove(int key){
		RedisClient.getInstance().hDel(cacheName,String.valueOf(key));
	}
	
	public void removeAll(){
		RedisClient.getInstance().del(cacheName);
	}
}
