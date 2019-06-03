package com.tianee.oa.subsys.bisengin.util;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.DbUtils;

import com.tianee.webframe.exps.TeeOperationException;

public class BisQuery {
	protected Connection conn = null;
	protected StringBuffer sql = null;
	
	public BisQuery(Connection conn){
		this.conn = conn;
	}
	
	public void executeUpdate(String sql,Object params[]){
		DbUtils dbUtils = new DbUtils(conn);
		try {
			dbUtils.executeUpdate(sql, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Object executeInsert(String sql,String dialect,Object params[]){
		DbUtils dbUtils = new DbUtils(conn);
		try {
			return dbUtils.executeInsert(sql, dialect,params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<?> queryToBeanList(Class type, String sql, Object[] params,int firstResult,int pageSize){
		DbUtils dbUtils = new DbUtils(conn);
		try {
			return dbUtils.queryToBeanList(type, sql, params, firstResult,pageSize);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Map<String, Object>> queryToMapList(String sql, Object[] params,int firstResult,int pageSize){
		DbUtils dbUtils = new DbUtils(conn);
		try {
			return dbUtils.queryToMapList(sql, params, firstResult,pageSize);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Object queryToBean(Class type,String sql, Object[] params){
		DbUtils dbUtils = new DbUtils(conn);
		try {
			return dbUtils.queryToBean(type, sql, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Map queryToMap(String sql, Object[] params){
		DbUtils dbUtils = new DbUtils(conn);
		try {
			return dbUtils.queryToMap(sql, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
