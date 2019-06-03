package com.tianee.webframe.util.cache;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;

import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.openfire.TeeOpenfireUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStrZipUtil;


/**
 * 缓存管理器
 * @author kakalion
 *
 */
public class TeeCacheManager {
	
	private static Map<String,Cache> cacheMap = new HashMap();
	
	/**
	 * 从缓存管理器中获取缓存
	 * @param cacheName
	 * @return
	 */
	public static Cache getCache(String cacheName){
		Cache cache = cacheMap.get(cacheName);
		if(cache==null){
			synchronized (cacheMap) {
				cache = cacheMap.get(cacheName);
				if(cache==null){
					cache = new Cache(cacheName);
					cacheMap.put(cacheName, cache);
				}
			}
		}
		return cache;
	}
	
	/**
	 * 从缓存中获取元素
	 * @param cache
	 * @return
	 */
	public static Element getElement(Cache cache,String elementName){
		return cache.get(elementName);
	}
	
	/**
	 * 从缓存中获取元素
	 * @param cacheName
	 * @param elementName
	 * @return
	 */
	public static Element getElement(String cacheName,String elementName){
		return getElement(getCache(cacheName),elementName);
	}
	
	/**
	 * 向缓存中存入元素
	 * @param cache
	 * @param element
	 */
	public static void put(Cache cache,Element element){
		cache.put(element);
	}
	
	/**
	 * 向缓存中存入元素
	 * @param cacheName
	 * @param element
	 */
	public static void put(String cacheName,Element element){
		getCache(cacheName).put(element);
	}
	
	/**
   * 刷新界面缓存
   */
	  public static void refreshThemeCache(){
		  try {
				TeeSysProps.getProps().setProperty("THEME_LOGO_TEXT_3", 
						TeeFileUtility.loadLine2Buff(TeeSysProps.getRootPath()+"/system/frame/3/logo.txt").toString());
				TeeSysProps.getProps().setProperty("THEME_LOGO_TEXT_4", 
						TeeFileUtility.loadLine2Buff(TeeSysProps.getRootPath()+"/system/frame/4/logo.txt").toString());
				TeeSysProps.getProps().setProperty("THEME_LOGO_TEXT_5", 
						TeeFileUtility.loadLine2Buff(TeeSysProps.getRootPath()+"/system/frame/5/logo.txt").toString());
				TeeSysProps.getProps().setProperty("THEME_LOGO_TEXT_6", 
						TeeFileUtility.loadLine2Buff(TeeSysProps.getRootPath()+"/system/frame/6/logo.txt").toString());
				TeeSysProps.getProps().setProperty("THEME_LOGO_TEXT_DEFAULT", 
						TeeFileUtility.loadLine2Buff(TeeSysProps.getRootPath()+"/system/frame/default/logo.txt").toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }
	  
	  /**
	   * 刷新模块缓存
	   */
	  public static void refreshModuleConstCache(){
		  
	  }
	  
	  /**
	   * 刷新Redis组织机构缓存数据
	   */
	  public static void refreshRedisOrg(){
		  refreshRedisDept();
		  refreshRedisRole();
		  refreshRedisPerson();
//		  	Connection dbConn = null;
//
//			List<Map> datas;
//			try {
//				dbConn = TeeDbUtility.getConnection();
//				// 生成部门
//				DbUtils dbUtils = new DbUtils(dbConn);
//				datas = dbUtils.queryToMapList("select "
//						+ "dept.DEPT_NAME as DEPTNAME," + "dept.UUID as UUID,"
//						+ "dept.DEPT_FULL_ID as DEPTFULLID,"
//						+ "dept.DEPT_FULL_NAME as DEPTFULLNAME,"
//						+ "dept.DEPT_PARENT as PARENTID " + "from department dept",
//						null);
//
//				for (Map data : datas) {
//					data.put("deptName", data.get("DEPTNAME"));
//					data.put("uuid", data.get("UUID"));
//					data.put("deptFullId", data.get("DEPTFULLID"));
//					data.put("deptFullName", data.get("DEPTFULLNAME"));
//					data.put("parentId", data.get("PARENTID"));
//				}
//
//				String jsonStr = TeeJsonUtil.toJson(datas);
//				File file = new File(TeeSysProps.getRootPath()
//						+ "/../gen/dept.json");
//				if (file.exists()) {
//					file.delete();
//				}
//
//				FileOutputStream outputStream = null;
//				try {
//					outputStream = new FileOutputStream(file);
//					TeeStrZipUtil.compress(jsonStr.getBytes("UTF-8"), outputStream);
//					
//					ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
//					TeeStrZipUtil.compress(jsonStr.getBytes("UTF-8"), arrayOutputStream);
//					byte b [] = new byte[arrayOutputStream.size()];
//					ByteArrayInputStream x = new ByteArrayInputStream(b);
//					x.read(b);
//					
//					BASE64Encoder base64Encoder = new BASE64Encoder();
//					dbUtils.executeUpdate("update org_cache set value_=?,md5_=? where key_=?",new Object[]{});
//					
////					outputStream.write(jsonStr.getBytes("UTF-8"));
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} finally {
//					try {
//						outputStream.close();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//
//				// 生成用户
//				datas = dbUtils
//						.queryToMapList(
//								"select "
//										+ "person.uuid as UUID,"
//										+ "person.user_Id as USERID,"
//										+ "person.user_Name as USERNAME,"
//										+ "person.dept_id as DEPTID,"
//										+ "person.avatar as PHOTOID,"
//										+ "person.sex as SEX,"
//										+ "person.user_Role as ROLEID "
//										+ "from Person person,Department dept,user_role role where person.dept_id=dept.uuid and person.user_role=role.uuid and person.DELETE_STATUS='0'",
//								null);
//
//				// 创建redis缓存连接池
//				Jedis jedis = null;
//				try {
//					jedis = RedisClient.getInstance().getJedis();
//				} catch (Exception ex) {
//				}
//
//				for (Map data : datas) {
//					data.put("uuid", data.get("UUID"));
//					data.put("userId", data.get("USERID"));
//					data.put("userName", data.get("USERNAME"));
//					data.put("deptId", data.get("DEPTID"));
//					data.put("photoId", data.get("PHOTOID"));
//					data.put("roleId", data.get("ROLEID"));
//					data.put("sex", data.get("SEX"));
//
//					if (jedis != null) {
//						try {
//							jedis.set("USER_INFO_DATA_" + data.get("UUID"),
//									data.get("USERID") + "");
//							jedis.set("USER_INFO_REVERSE_DATA_"
//									+ data.get("USERID").hashCode(),
//									data.get("UUID") + "");
//						} catch (Exception ex) {
//						}
//					}
//				}
//
//				if (jedis != null) {
//					try {
//						RedisClient.getInstance().free(jedis);
//					} catch (Exception ex) {
//					}
//				}
//
//				jsonStr = TeeJsonUtil.toJson(datas);
//				file = new File(TeeSysProps.getRootPath() + "/../gen/person.json");
//				if (file.exists()) {
//					file.delete();
//				}
//
//				outputStream = null;
//				try {
//					outputStream = new FileOutputStream(file);
//					outputStream.write(jsonStr.getBytes("UTF-8"));
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} finally {
//					try {
//						outputStream.close();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//
//				// 生成角色
//				datas = dbUtils.queryToMapList("select " + "role.uuid as UUID,"
//						+ "role.role_Name as ROLENAME " + "from User_Role role",
//						null);
//
//				jsonStr = TeeJsonUtil.toJson(datas);
//				file = new File(TeeSysProps.getRootPath() + "/../gen/role.json");
//				if (file.exists()) {
//					file.delete();
//				}
//
//				for (Map data : datas) {
//					data.put("uuid", data.get("UUID"));
//					data.put("roleName", data.get("ROLENAME"));
//				}
//
//				outputStream = null;
//				jsonStr = TeeJsonUtil.toJson(datas);
//				try {
//					outputStream = new FileOutputStream(file);
//					outputStream.write(jsonStr.getBytes("UTF-8"));
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} finally {
//					try {
//						outputStream.close();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} finally {
//				TeeDbUtility.closeConn(dbConn);
//			}
	  }
	  
		public static void refreshRedisPerson(){
			Connection dbConn = null;

			List<Map> datas;
			try {
				dbConn = TeeDbUtility.getConnection();
				DbUtils dbUtils = new DbUtils(dbConn);

				String jsonStr = "";
				// 生成用户
				datas = dbUtils
						.queryToMapList(
								"select "
										+ "person.uuid as UUID,"
										+ "person.user_Id as USERID,"
										+ "person.user_Name as USERNAME,"
										+ "person.dept_id as DEPTID,"
										+ "person.avatar as PHOTOID,"
										+ "person.sex as SEX,"
										+ "person.user_Role as ROLEID "
										+ "from Person person,Department dept,user_role role where person.dept_id=dept.uuid and person.user_role=role.uuid and person.DELETE_STATUS='0'",
								null);

				boolean success = false;
				int faildCount = 0;
				for (Map data : datas) {
					data.put("uuid", data.get("UUID"));
					data.put("userId", data.get("USERID"));
					data.put("userName", data.get("USERNAME"));
					data.put("deptId", data.get("DEPTID"));
					data.put("photoId", data.get("PHOTOID"));
					data.put("roleId", data.get("ROLEID"));
					data.put("sex", data.get("SEX"));

					if(faildCount<5){
						//openfire同步
						success = TeeOpenfireUtil.createUser(data.get("USERID")+"", data.get("USERNAME")+"");
						if(!success){
							faildCount++;
						}
					}
				}

				jsonStr = TeeJsonUtil.toJson(datas);
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				TeeStrZipUtil.compress(jsonStr.getBytes("UTF-8"), arrayOutputStream);
				dbUtils.executeUpdate("update org_cache set value_=?,version_=version_+1 where key_=?",new Object[]{arrayOutputStream.toByteArray(),"person"});
				dbConn.commit();

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				TeeDbUtility.closeConn(dbConn);
			}
		}
	  
		public static void refreshRedisDept(){
			Connection dbConn = null;

			List<Map> datas;
			try {
				dbConn = TeeDbUtility.getConnection();
				// 生成部门
				DbUtils dbUtils = new DbUtils(dbConn);
				datas = dbUtils.queryToMapList("select "
						+ "dept.DEPT_NAME as DEPTNAME," + "dept.UUID as UUID,"
						+ "dept.DEPT_FULL_ID as DEPTFULLID,"
						+ "dept.DEPT_FULL_NAME as DEPTFULLNAME,"
						+ "dept.DEPT_PARENT as PARENTID " + "from department dept",
						null);

				for (Map data : datas) {
					data.put("deptName", data.get("DEPTNAME"));
					data.put("uuid", data.get("UUID"));
					data.put("deptFullId", data.get("DEPTFULLID"));
					data.put("deptFullName", data.get("DEPTFULLNAME"));
					data.put("parentId", data.get("PARENTID"));
				}

				String jsonStr = TeeJsonUtil.toJson(datas);
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				TeeStrZipUtil.compress(jsonStr.getBytes("UTF-8"), arrayOutputStream);
				dbUtils.executeUpdate("update org_cache set value_=?,version_=version_+1 where key_=?",new Object[]{arrayOutputStream.toByteArray(),"dept"});
				dbConn.commit();

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				TeeDbUtility.closeConn(dbConn);
			}
		}

		public static void refreshRedisRole(){
			Connection dbConn = null;

			List<Map> datas;
			try {
				dbConn = TeeDbUtility.getConnection();
				DbUtils dbUtils = new DbUtils(dbConn);

				// 生成角色
				datas = dbUtils.queryToMapList("select " + "role.uuid as UUID,"
						+ "role.role_Name as ROLENAME " + "from User_Role role",
						null);
				
				for (Map data : datas) {
					data.put("uuid", data.get("UUID"));
					data.put("roleName", data.get("ROLENAME"));
				}

				String jsonStr = TeeJsonUtil.toJson(datas);

				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				TeeStrZipUtil.compress(jsonStr.getBytes("UTF-8"), arrayOutputStream);
				dbUtils.executeUpdate("update org_cache set value_=?,version_=version_+1 where key_=?",new Object[]{arrayOutputStream.toByteArray(),"role"});
				dbConn.commit();

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				TeeDbUtility.closeConn(dbConn);
			}
		}
}
