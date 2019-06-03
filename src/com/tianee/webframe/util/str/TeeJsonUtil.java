package com.tianee.webframe.util.str;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import com.tianee.webframe.util.date.TeeDateUtil;

/**
 * 
 * @author syl
 *
 */
public class TeeJsonUtil {
	
	public final static String quote = "\"";
	
	private  static  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static Map filter = new HashMap();
	
	private static Map<Class,String[]> filter4Class = new HashMap<Class,String[]>();
	
	private static Map<String,TeeJsonParsing> parsing = new HashMap<String,TeeJsonParsing>();
	
	public static void setDateFormat(SimpleDateFormat sdf){
		sdf = sdf;
	}
	
	/**
	 * 字段名称过滤器，例如将所有seqId过滤成为id，从而拼出json
	 * @param o
	 * @param t
	 */
	public static void addFieldFilter(String o,String t){
		filter.put(o, t);
	}
	
	/**
	 * 字段名称过滤器，例如将指定类的seqId过滤成为id，从而拼出json
	 * addFieldFilter(Student.class,new String[]{"seqId","id"})
	 * @param clazz
	 * @param mapping
	 */
	public static void addFieldFilter(Class clazz,String[] mapping){
		filter4Class.put(clazz, mapping);
	}
	
	public static String mapToJson(Map map){
		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject.toString();
	}
	
	/**
	 * 从JSON字符串转换成json数组
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static Object toArray(String json,Class clazz){
		JSONArray jsonArray = JSONArray.fromObject(json);
		return JSONArray.toArray(jsonArray, clazz);
	}
	
	/**
	 * 从JSON字符串转换成json数组
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static Object toArray(String json){
		JSONArray jsonArray = JSONArray.fromObject(json);
		return JSONArray.toArray(jsonArray);
	}
	
	/**
	 * 从JSON字符串转换成json数组
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static List toList(String json){
		JSONArray jsonArray = JSONArray.fromObject(json);
		return JSONArray.toList(jsonArray);
	}
	
	/**
	 * 从JSON字符串转换成json对象
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static Object toObject(String json,Class clazz){
		JSONObject jsonObject = JSONObject.fromObject(json);
		return JSONObject.toBean(jsonObject, clazz);
	}

	/**
	 * 将List对象转换为Json数组
	 * @param list
	 * @return
	 */
//	public static String toJson(List<?> list){
//		StringBuffer tmp = new StringBuffer();
//		tmp.append("[");
//		boolean hasExist = false;
//		for(Object o:list){
//			hasExist = true;
//			if(o instanceof Integer
//					|| o instanceof Long
//					|| o instanceof Short
//					|| o instanceof Double
//					|| o instanceof Float
//					|| o instanceof Byte){
//				
//				tmp.append(o+",");
//				
//			}else if(o instanceof String || o instanceof Character){
//				tmp.append(quote+o+quote+",");
//			}else{
//				tmp.append(toJson(o)+",");
//			}
//		}
//		if(hasExist){
//			tmp.deleteCharAt(tmp.length()-1);
//		}
//		tmp.append("]");
//		return tmp.toString();
//	}
	
	/**
	 * 将Map、实体类对象     转换为Json数组
	 * @param list
	 * @return
	 */
	public static String toJson(Object obj){
		if(obj instanceof List){
			JSONArray jsonArray = JSONArray.fromObject(obj);
			return jsonArray.toString();
		}else{
			JSONObject jsonObject = JSONObject.fromObject(obj);
			return jsonObject.toString();
		}
		
	}
	
	
	public static String toKeyValues(Field fields[],Method methods[],Object o,Class clazz){
		StringBuffer tmp = new StringBuffer();
		boolean hasExist = false;
		for(Field field:fields){
			for(Method method:methods){
				if(method.getName().toLowerCase().indexOf(field.getName().toLowerCase())!=-1
						&& method.getReturnType()!=void.class){
					tmp.append(toKeyValue(field,method,o,clazz)+",");
					hasExist = true;
					break;
				}
			}
		}
		if(hasExist){
			tmp.deleteCharAt(tmp.length()-1);
		}
		return tmp.toString();
	}
	
	public static String toKeyValues(Map params){
		Set<Entry> entry = params.entrySet();
		Iterator it = entry.iterator();
		StringBuffer tmp = new StringBuffer();
		String key = null;
		Object value = null;
		Entry en = null;
		boolean hasExist = false;
		while(it.hasNext()){
			hasExist = true;
			en = (Entry) it.next();
			key = (String) en.getKey();
			value = (Object) en.getValue();
			tmp.append(toKeyValue(key,value,null)+",");
		}
		if(hasExist){
			tmp.deleteCharAt(tmp.length()-1);
		}
		return tmp.toString();
	}
	
	public static String toKeyValue(String key,Object value,Class clazz){
		String tmp = "";
		TeeJsonParsing jsonParsing = parsing.get(key);
		value = (jsonParsing==null?value:jsonParsing.parse(value));
		
		String [] mapping = filter4Class.get(clazz);
		if(mapping!=null && key.equals(mapping[0])){
			key = mapping[1];
		}else{
			key = filter.get(key)==null?key:(String)filter.get(key);
		}
		
		key = quote+key+quote;
		if(value instanceof ArrayList){//if ArrayList
			tmp = key+":"+toJson((List<?>)value);
		}else if(value instanceof Integer
				|| value instanceof Long
				|| value instanceof Short
				|| value instanceof Double
				|| value instanceof Float
				|| value instanceof Byte){
			
			tmp = key+":"+value;
			
		}else if(value instanceof String
				|| value instanceof Character){
			value = ((String)value).replace("\\", "\\\\").replace("\"", "\\\"");
			
			if(value instanceof String){
				tmp = key+":"+quote+value+quote;
			}else{
				tmp = key+":"+quote+value+quote;
			}
			
		}else if(value instanceof java.util.Date){
			
			tmp = key+":"+quote+(sdf!=null?sdf.format(value):((java.util.Date)value).getTime())+quote;
			
		}else if(value instanceof java.sql.Date){
			
			tmp = key+":"+quote+value+quote;
			
		}else if(value==null){
			
			tmp = key+":"+null;
			
		}else{
			tmp = key+":"+toJson(value);
		}
		return tmp;
	}
	
	public static String toKeyValue(Field field,Method method,Object o,Class clazz){
		String key = field.getName();
		Object value = null;
		String tmp = "";
		try {
			value = method.invoke(o, null);
			tmp = toKeyValue(key, value,clazz);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tmp;
	}
	

	
	/**
	 * 对象转Json(包括Map、List 、String、数组等)
	 * @param obj : 实体对象
	 * @return
	 */
	public static JSONObject  obj2Json(Object obj){
		if(obj == null){
			new Object();
		}
		JSONObject jsonObject = JSONObject.fromObject( obj );       
		return jsonObject;
	}
	
	/**
	 * 字符窜 转Json
	 * @param jsonStr : json字符串 
	 * @return
	 */
	public static JSONObject  jsonString2Json(String jsonStr){
		if(TeeUtility.isNullorEmpty(jsonStr)){
			jsonStr = "";
		}
		jsonStr = TeeUtility.encodeSpecialJson(jsonStr);
	    JSONObject jsonObject = JSONObject.fromObject( jsonStr );       
		return jsonObject;
	}
	
	/**
	 * json对象转XMl字符串
	 * @param jsonObj : json对象
	 * @return
	 */
	public static String json2Xml(JSONObject jsonObj){
		XMLSerializer xmlS = new XMLSerializer();
		String xmlStr = xmlS.write(jsonObj);
		return xmlStr ;
	}
	
	
	/**
	 * json字符串转XMl字符串
	 * @param jsonStr : json字符串
	 * @return
	 */
	public static String jsonString2Xml(String jsonStr){
		JSONObject jsonObj = jsonString2Json( jsonStr);
		XMLSerializer xmlS = new XMLSerializer();
		String xmlStr = xmlS.write(jsonObj);
		return xmlStr ;
	}
	
	/**
	 * xml转json数组
	 * @param xml
	 * @return
	 */
	public static JSONArray xml2JsonArray(String xml){
		XMLSerializer xmlS = new XMLSerializer();
		JSONArray jsonArray = (JSONArray) xmlS.read( xml ); 
        return jsonArray;
	}
	
	 /** 
     * 将json字符串对象转换成Map <String>
     * 
     * @param jsonObject json对象 
     * @return Map对象 
     */ 
    @SuppressWarnings("unchecked") 
    public  static Map<String, String> JsonStr2Map( String jsonStr) 
    { 	
    	JSONObject jsonObject = null;
    	try{
    		jsonObject = jsonString2Json(jsonStr);
    	}catch(Exception e){return new HashMap();}
    	
        Map<String, String> result = new HashMap<String, String>(); 
        Iterator<String> iterator = jsonObject.keys(); 
        String key = null; 
        String value = null; 
        while (iterator.hasNext()) 
        { 
            key = iterator.next(); 
            value = jsonObject.getString(key); 
            result.put(key, value); 
        } 
        return result; 
    } 
    
    /**
     * json字符串转换为map数组
     * @param jsonStr
     * @return
     */
    public static  List<Map<String, String>> JsonStr2MapList( String jsonStr) 
    { 
    	
    	List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
    	if(jsonStr==null || "".equals(jsonStr)){
    		return mapList;
    	}
    	JSONArray jsonArray = JSONArray.fromObject(jsonStr);
    	Iterator iterator = jsonArray.iterator();
    	JSONObject obj =null;
    	while(iterator.hasNext()){
    		obj = (JSONObject) iterator.next();
    		mapList.add(JsonStr2Map(obj.toString()));
    	}
    	obj = null;
        return mapList; 
    } 
    
    /**
     * json字符串转换为对象 数组
     * @param jsonStr
     * @return
     */
    public static  List JsonStr2ObjectList( String jsonStr ,Class clazz) 
    { 
    	List mapList = new ArrayList();
    	if(jsonStr==null || "".equals(jsonStr)){
    		return mapList;
    	}
    	JSONArray jsonArray = JSONArray.fromObject(jsonStr);
    	Iterator iterator = jsonArray.iterator();
    	JSONObject obj =null;
    	while(iterator.hasNext()){
    		obj = (JSONObject) iterator.next(); 
    		mapList.add(JSONObject.toBean(obj, clazz));
    	}
    	obj = null;
        return mapList; 
    } 
    
    /**
     * json字符串转换为对象 数组 -- 采用原生自己写的
     * @param jsonStr
     * @return
     */
    public static  List JsonStr2ObjectList4Protogen( String jsonStr ,Class clazz) 
    { 
    	List mapList = new ArrayList();
    	if(jsonStr==null || "".equals(jsonStr)){
    		return mapList;
    	}
    	JSONArray jsonArray = JSONArray.fromObject(jsonStr);
    	Iterator iterator = jsonArray.iterator();
    	JSONObject obj =null;
    	while(iterator.hasNext()){
    		obj = (JSONObject) iterator.next(); 
    		try {
				mapList.add(convertJsonObj2Bean(obj, clazz));
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	obj = null;
        return mapList; 
    } 
 
    /** 
     * 将javaBean转换成Map 
     * 
     * @param javaBean javaBean 
     * @return Map对象 
     */ 
    public static Map<String, String> bean2Map(Object javaBean) 
    { 
        Map<String, String> result = new HashMap<String, String>(); 
        Method[] methods = javaBean.getClass().getDeclaredMethods(); 

        for (Method method : methods) 
        { 
            try 
            { 
                if (method.getName().startsWith("get") ) 
                { 
                    String field = method.getName(); 
                    field = field.substring(field.indexOf("get") + 3); 
                    field = field.toLowerCase().charAt(0) + field.substring(1); 

                    Object value = method.invoke(javaBean, (Object[])null); 
                    result.put(field, null == value ? "" : value.toString()); 
                } else if(  method.getName().startsWith("is")) { 
                    String field = method.getName(); 
                    field = field.substring(field.indexOf("is") + 2); 
                    field = field.toLowerCase().charAt(0) + field.substring(1); 

                    Object value = method.invoke(javaBean, (Object[])null); 
                    result.put(field, null == value ? "" : value.toString()); 
                }
            } 
            catch (Exception e) 
            { 
            } 
        } 

        return result; 
    } 
    

    /** 
     * 将javaBean转换成Map<object> 原生
     * 
     * @param javaBean javaBean 
     * @return Map对象 
     */ 
    public static Map<String, Object> bean2ProtogenesisMap(Object javaBean) 
    { 
        Map<String, Object> result = new HashMap<String, Object>(); 
        Method[] methods = javaBean.getClass().getDeclaredMethods(); 

        for (Method method : methods) 
        { 
            try 
            { 
                if (method.getName().startsWith("get") ) 
                { 
                    String field = method.getName(); 
                    field = field.substring(field.indexOf("get") + 3); 
                    field = field.toLowerCase().charAt(0) + field.substring(1); 

                    Object value = method.invoke(javaBean, (Object[])null); 
                    result.put(field,   value); 
                } else if(  method.getName().startsWith("is")) { 
                    String field = method.getName(); 
                    field = field.substring(field.indexOf("is") + 2); 
                    field = field.toLowerCase().charAt(0) + field.substring(1); 

                    Object value = method.invoke(javaBean, (Object[])null); 
                    result.put(field,  value); 
                }
            } 
            catch (Exception e) 
            { 
            } 
        } 

        return result; 
    } 


    /** 
     * 将一个 Map 对象转化为一个 JavaBean 
     * @param type 要转化的类型 
     * @param map 包含属性值的 map 
     * @return 转化出来的 JavaBean 对象 
     * @throws IntrospectionException 
     *             如果分析类属性失败 
     * @throws IllegalAccessException 
     *             如果实例化 JavaBean 失败 
     * @throws InstantiationException 
     *             如果实例化 JavaBean 失败 
     * @throws InvocationTargetException 
     *             如果调用属性的 setter 方法失败 
     */ 
    public static Object convertMap2Bean(Object obj , Class clazz, Map map) 
            throws IntrospectionException, IllegalAccessException, 
            InstantiationException, InvocationTargetException { 
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz); // 获取类属性 
       // obj = type.newInstance(); // 创建 JavaBean 对象 

        // 给 JavaBean 对象的属性赋值 
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
        for (int i = 0; i< propertyDescriptors.length; i++) { 
            PropertyDescriptor descriptor = propertyDescriptors[i]; 
            String propertyName = descriptor.getName(); 
            if (map.containsKey(propertyName)) { 
	                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。 
	              Object value = map.get(propertyName); 
	              //判断属性类型
	              if(  descriptor.getPropertyType() == int.class || descriptor.getPropertyType() == Integer.class){
	            	  value = TeeStringUtil.getInteger(value, 0);
	              } else  if(  descriptor.getPropertyType() == double.class){
	            	  value = TeeStringUtil.getDouble(value, 0);
	              }else  if(  descriptor.getPropertyType() == Float.class){
	            	  value = TeeStringUtil.getFloat( value ,0f );
	              } else  if(  descriptor.getPropertyType() == Long.class){
	            	  value = TeeStringUtil.getLong(value, 0);
	              } else  if(  descriptor.getPropertyType() == Byte.class){
	            	  value = TeeStringUtil.getByte( value , (byte) 0);
	              } else if(descriptor.getPropertyType() ==  java.util.Date.class){
	            	  if(!TeeUtility.isNullorEmpty(value)){
	            		  if(value instanceof java.util.Date){
		            		  
		            	  }else{
		            		  value = TeeDateUtil.parseDateByPattern((String)value);
		            	  } 
	            	  } 
	      		  }else if(descriptor.getPropertyType() == java.sql.Date.class){
	      			  if(!TeeUtility.isNullorEmpty(value)){
	      				if( value instanceof java.sql.Date){  
		            	  }else{
		            		  Date Date = TeeDateUtil.parseDateByPattern((String)value);
		            		  java.sql.Date tempDate = new java.sql.Date(Date.getTime());
		            		  value = tempDate;
		            	  }
	      			  }
	      			
	      		  }
	             Object[] args = new Object[1]; 
	             args[0] = value; 
                 descriptor.getWriteMethod().invoke(obj, args); 
            } 
        } 
        return obj; 
    } 



    /** 
     * 将一个 Map 对象转化为一个 JavaBean 
     * @param type 要转化的类型 
     * @param map 包含属性值的 map 
     * @return 转化出来的 JavaBean 对象 
     * @throws IntrospectionException 
     *             如果分析类属性失败 
     * @throws IllegalAccessException 
     *             如果实例化 JavaBean 失败 
     * @throws InstantiationException 
     *             如果实例化 JavaBean 失败 
     * @throws InvocationTargetException 
     *             如果调用属性的 setter 方法失败 
     */ 
    public static Object convertJsonObj2Bean(JSONObject obj , Class clazz) 
            throws IntrospectionException, IllegalAccessException, 
            InstantiationException, InvocationTargetException { 
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz); // 获取类属性 
       // obj = type.newInstance(); // 创建 JavaBean 对象 
        Object objTemp = clazz.newInstance();
        // 给 JavaBean 对象的属性赋值 
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
        for (int i = 0; i< propertyDescriptors.length; i++) { 
            PropertyDescriptor descriptor = propertyDescriptors[i]; 
            String propertyName = descriptor.getName(); 
            if (obj.containsKey(propertyName)) { 
	                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。 
	              Object value = obj.get(propertyName); 
	              //判断属性类型
	              if(  descriptor.getPropertyType() == int.class || descriptor.getPropertyType() == Integer.class){
	            	  value = TeeStringUtil.getInteger(value, 0);
	              } else  if(  descriptor.getPropertyType() == double.class){
	            	  value = TeeStringUtil.getDouble(value, 0);
	              }else  if(  descriptor.getPropertyType() == Float.class){
	            	  value = TeeStringUtil.getFloat( value ,0f );
	              } else  if(  descriptor.getPropertyType() == Long.class){
	            	  value = TeeStringUtil.getLong(value, 0);
	              } else  if(  descriptor.getPropertyType() == Byte.class){
	            	  value = TeeStringUtil.getByte( value , (byte) 0);
	              } else if(descriptor.getPropertyType() ==  java.util.Date.class){
	            	  if(!TeeUtility.isNullorEmpty(value)){
	            		  if(value instanceof java.util.Date){
		            		  
		            	  }else{
		            		  value = TeeDateUtil.parseDateByPattern((String)value);
		            	  } 
	            	  } 
	      		  }else if(descriptor.getPropertyType() == java.sql.Date.class){
	      			  if(!TeeUtility.isNullorEmpty(value)){
	      				if( value instanceof java.sql.Date){  
		            	  }else{
		            		  Date Date = TeeDateUtil.parseDateByPattern((String)value);
		            		  java.sql.Date tempDate = new java.sql.Date(Date.getTime());
		            		  value = tempDate;
		            	  }
	      			  }
	      		  }
	             Object[] args = new Object[1]; 
	             args[0] = value; 
                 descriptor.getWriteMethod().invoke(objTemp, args); 
            } 
        } 
        return objTemp; 
    } 
	
	public static void main(String[] args) throws Exception{ 
		TeeJsonUtil tju = new TeeJsonUtil();
		/*String jsonStr = "{\"vore\": \"\n222\"}";
		JSONObject obj = TeeJsonUtil.jsonString2Json(jsonStr);
		System.out.println(obj);*/
		/*
		TeePersonModel model = new TeePersonModel();
		//model.setUuid(1);
		System.out.println();
		Map map = new HashMap();
		map.put("userName", "admin");
		map.put("uuid", "1");
		map.put("birthday", "2014-09-12");
		model = (TeePersonModel)convertMap2Bean(model, model.getClass(), map );
		System.out.println(model.getUserName() +":"+ model.getUuid()+":"+ model.getBirthday());
*/		/*TeeUserRoleModel m = new TeeUserRoleModel();
		m.setRoleNo(1);
		Map map = new HashMap();
		map.put("roleName", "admin");
		map.put("uuid","1");
		
		//m.setLastPassTime(lastPassTime);
	
		m = (TeeUserRoleModel)convertMap2Bean(m, m.getClass(), map);
		System.out.println(m.getRoleNo() +":"+ m.getRoleName() +":"+ m.getUuid());
	*/
		/*TeePersonModel model = new TeePersonModel();
		model.setUserName("aaa");
		model.setUserId("bbbb");;
		model.setUserNo(2);
		String ss = getValueByKey(model.getClass().getDeclaredField("userName"), model.getClass().getMethod("getUserName"), model, TeePersonModel.class);
		String ss2 = getValueByKey(model.getClass().getDeclaredField("userNo"), model.getClass().getMethod("getUserNo"), model, TeePersonModel.class);
		
		System.out.println(ss);
		System.out.println(ss2);*/
		//tju.JsonStr2ObjectList(jsonStr, clazz)
//		TeeJsonUtil tju = new TeeJsonUtil();
//		Map a = new HashMap();
//		a.put("wocao", 1);
//		System.out.println(toJson(a));
     } 
	
	
	
	
	
	/**
	 * 根据对象方法 + 属性 获取对象的值
	 * @param field
	 * @param method
	 * @param o
	 * @param clazz
	 * @return
	 */
	public static String getValueByKey(Field field,Method method,Object o,Class clazz){
		String key = field.getName();
		Object value = null;
		String tmp = "";
		try {
			value = method.invoke(o, null);
			tmp = getValueByKey(key, value,clazz);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tmp;
	}
	private static String getValueByKey(String key,Object value,Class clazz){
		String tmp = "";
		TeeJsonParsing jsonParsing = parsing.get(key);
		value = (jsonParsing==null?value:jsonParsing.parse(value));
		
		String [] mapping = filter4Class.get(clazz);
		if(mapping!=null && key.equals(mapping[0])){
			key = mapping[1];
		}else{
			key = filter.get(key)==null?key:(String)filter.get(key);
		}
		
		key = quote+key+quote;
		if(value instanceof ArrayList){//if ArrayList
			tmp = toJson((List<?>)value);
		}else if(value instanceof Integer
				|| value instanceof Long
				|| value instanceof Short
				|| value instanceof Double
				|| value instanceof Float
				|| value instanceof Byte){
			
			tmp = value + "";
			
		}else if(value instanceof String
				|| value instanceof Character){
			value = ((String)value).replace("\\", "\\\\").replace("\"", "\\\"");
			
			if(value instanceof String){
				tmp = value + "";
			}else{
				tmp = value + "";
			}
			
		}else if(value instanceof java.util.Date){
			
			tmp = (sdf!=null?sdf.format(value):((java.util.Date)value).getTime())+ "";
			
		}else if(value instanceof java.sql.Date){
			
			tmp = value + "";
			
		}else if(value==null){
			
			tmp = null;
			
		}else{
			tmp = toJson(value);
		}
		return tmp;
	}
	
}

