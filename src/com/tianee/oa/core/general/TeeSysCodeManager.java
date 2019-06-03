package com.tianee.oa.core.general;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tianee.oa.core.general.bean.TeeSysCode;
import com.tianee.webframe.util.cache.Cache;
import com.tianee.webframe.util.cache.Element;
import com.tianee.webframe.util.cache.TeeCacheManager;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

public class TeeSysCodeManager {
	public static Cache cache;
	public TeeSysCodeManager() {
		// TODO Auto-generated constructor stub
		
	}
	static {
		cache = TeeCacheManager.getCache("sysCode");
	}
	
	
	
	/**
	 * 把所有系统代码  存入缓存中
	 * @author syl
	 * @date 2014-2-26
	 * @param conn
	 */
	public static  void createSysCodeCache(Connection conn ){
		  //获取缓存对象
		
		Map mainMap = new HashMap();//主各类代码
		if(cache != null && conn != null ){
			//先删除所有缓存数据
			cache.removeAll();
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "select * FROM  SYS_CODE  order by PARENT_ID , CODE_ORDER";
			try{
				ps = conn.prepareStatement(sql);
			    rs = ps.executeQuery();
			    while(rs.next()){
			        int  sid = rs.getInt("SID");
			        String codeNo = rs.getString("CODE_NO");
			        String codeName = rs.getString("CODE_NAME");
			        int parentId = rs.getInt("PARENT_ID");
			        int codeOrder = rs.getInt("CODE_ORDER");
			        Map map = new HashMap();
			        if(parentId > 0){
			        	String parentCodeNo = (String)mainMap.get(parentId);//获取父分类编码
			        	if(!TeeUtility.isNullorEmpty(parentCodeNo)){
			        		Element element = cache.get(parentCodeNo);
			        		if(element != null){
			        			Map childMap = (Map)element.getValue();
			        			if(childMap != null){
			        				childMap.put(codeNo, codeName);
			        				cache.put(element);
			        			}
			        		}
			        	}
			        }else{
			        	mainMap.put(sid, codeNo);
			        	 //存放个人缓存菜单权限元素
						Element element = new Element(codeNo , map);
						//存放到缓存里
						cache.put(element);
			        }
			     
			    }  
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  finally {
				try {
					ps.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		/*List list =  cache.getKeys();
		for (int i = 0; i < list.size(); i++) {
			Element element  = cache.get(list.get(i));
			
			Map<String,Object> childMap = (Map)element.getValue();
			for(Map.Entry<String,Object> entry: childMap.entrySet()) {

				 System.out.print(entry.getKey() + ":" + entry.getValue() + "\t");

			}
		}*/
	}
	
	/**
	 * 获取子代码记录（Map）   by 主类编号
	 * @author syl
	 * @date 2014-3-2
	 * @param parentCodeNo
	 * @return
	 */
	public static Map getChildSysCodeMapByParentCodeNo(String parentCodeNo){
		Map mainMap = null;//
		if(cache != null){
			mainMap = new HashMap();
			Element element  = cache.get(parentCodeNo);
			if(element != null){
				mainMap = (Map)element.getValue();
			}
			
		}
		return mainMap;
	}
	
	
	
	/**
	 * 获取子代码列表 （list） by 主类编号
	 * @author syl
	 * @date 2014-3-2
	 * @param parentCodeNo
	 * @return
	 */
	public static List<Map<String, Object>> getChildSysCodeListByParentCodeNo(String parentCodeNo){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(cache != null){
			Element element  = cache.get(parentCodeNo);
			if(element != null){
				Map<String,Object> childMap = (Map<String,Object>)element.getValue();
				for(Map.Entry<String,Object> entry: childMap.entrySet()) {
					Map<String,Object> code = new HashMap<String,Object>(); 
					code.put("codeNo", entry.getKey());
					code.put("codeName", entry.getValue());
					list.add(code);				
				}
			}
			
		}
		
		Collections.sort(list, new Comparator<Map>() {
			public int compare(Map arg0, Map arg1) {
				String hits0 = (String)arg0.get("codeNo");
				String hits1 = (String)arg1.get("codeNo");
				if (hits1.compareTo(hits0) > 0) {
					return -1;
				} else if (hits1.equals(hits0)) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		return list;
	}
	
	/**
	 * 根据主类编码  和子代码编码 获取 子代码名称
	 * @author syl
	 * @date 2014-3-2
	 * @param parentCodeNo
	 * @param codeNo
	 * @return
	 */
	public static String getChildSysCodeNameCodeNo(String parentCodeNo , String codeNo){
		String codeName = "";
		if(cache != null){
			Element element  = cache.get(parentCodeNo);
			if(element != null){
				Map<String,Object> childMap = (Map<String,Object>)element.getValue();
				if(childMap != null){
					codeName = (String)childMap.get(codeNo);
				}
			}
		}
		return TeeStringUtil.getString(codeName);
	}
	
	/**
	 * 根据编号删除元素
	 * @author syl
	 * @date 2014-3-2
	 * @param codeNo
	 */
	public void removeSysCodeElement(String codeNo){
		if(cache != null){
			cache.remove(codeNo);
		}
	}
	
	
	/**
	 * 根据编号修改元素 (新增或修改)
	 * @author syl
	 * @date 2014-3-2
	 * @param codeNo
	 */
	public  void updateSysCodeElement( String oldCodeNo , String codeNo ){
		if(cache != null && !oldCodeNo.equals(codeNo)){
			//获取以前子代码Map
			Map<String,Object> childMap  = new HashMap<String,Object>();
			if(!TeeUtility.isNullorEmpty(oldCodeNo)){
				//获取以前元素
				Element element  = cache.get(oldCodeNo);
				if(element != null){
					//获取以前子代码Map
					childMap = (Map<String,Object>)element.getValue();
					//删除之前元素
					cache.remove(oldCodeNo);
				}
				
			}
			
			
			//创建新元素
			Element elementNew  = new Element(codeNo , childMap);
			cache.put(elementNew);
		}
	}
	
	
	
	/**
	 * 根据主代码编号修改元素 中 子代码信息(包括新增 和修改   oldCodeNo为空，则是新增)
	 * @author syl
	 * @date 2014-3-2
	 * 
	 * @param  oldParentCodeNo 之前主分类编号
	 * @param parentCodeNo  主分类编号
	 * @param oldCodeNo   老子代码编号
	 * @param TeeSysCode : 系统代码对象
	 */
	public  void updateChildSysCodeElement(String oldParentCodeNo ,String parentCodeNo , String oldCodeNo , TeeSysCode code){
		if(cache != null ){
			if(!TeeUtility.isNullorEmpty(oldParentCodeNo) && !oldParentCodeNo.equals(parentCodeNo)){//更改主编号
				//获取以前元素
				Element element  = cache.get(oldParentCodeNo);
				//获取以前子代码Map
				Map<String,Object> childMap = (Map<String,Object>)element.getValue();
				//先删除之前
				childMap.remove(oldCodeNo);
				cache.put(element);
				
				//获取新元素
				Element elementNew  = cache.get(parentCodeNo);
				//获取以前子代码Map
				Map<String,Object> childMap2 = (Map<String,Object>)elementNew.getValue();
				//新增新子代码
				childMap2.put(code.getCodeNo(), code.getCodeName());
				cache.put(elementNew);
			}else{//没更改主分类
				//获取以前元素
				Element element  = cache.get(parentCodeNo);
				//获取以前子代码Map
				Map<String,Object> childMap = (Map<String,Object>)element.getValue();
				//先删除之前
				if(!TeeUtility.isNullorEmpty(oldCodeNo)){
					childMap.remove(oldCodeNo);
				}
				//新增
				childMap.put(code.getCodeNo(), code.getCodeName());
				cache.put(element);
			}
			
		}
	}
	
	/**
	 * 根据主代码编号修改元素 中 子代码信息(删除)
	 * @author syl
	 * @date 2014-3-2
	 * @param parentCodeNo  主分类编号
	 * @param codeNo   子代码编号
	 */
	public  void deleteChildSysCodeElement(String parentCodeNo , String codeNo){
		if(cache != null ){
			//获取以前元素
			Element element  = cache.get(parentCodeNo);
			//获取以前子代码Map
			Map<String,Object> childMap = (Map<String,Object>)element.getValue();
			//删除之前
			childMap.remove(codeNo);
			cache.put(element);
		}
	}
}
