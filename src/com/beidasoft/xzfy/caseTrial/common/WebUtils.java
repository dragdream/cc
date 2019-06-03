package com.beidasoft.xzfy.caseTrial.common;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.alibaba.fastjson.JSONObject;
import com.tianee.oa.core.org.bean.TeePerson;

public class WebUtils {
	/**
	 * Description:获取当前时间 yyyy/MM/dd HH:mm:ss
	 * @author ZCK
	 * @version 0.1 2019年4月23日
	 * @return
	 * String
	 */
	public static String getCurrentTime() {
		//获取当前时间
		Date currentTime = new Date();
		//格式化
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		//返回当前日期
		return formatter.format(currentTime).toString();
	}
	
	/**
	 * request转JSONObject
	 * @param request
	 * @return JSONObject
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject requestToJson(HttpServletRequest request) {
		Enumeration<String> enu = request.getParameterNames();
		JSONObject json = new JSONObject();
		while (enu.hasMoreElements()) {
			String key = (String) enu.nextElement();
			String value = request.getParameter(key);
			json.put(key, value);
		}
		return json;
	}

	/**
	 * JSONObject转为hql语句
	 * 
	 * @param json
	 * @param tableName
	 * @return
	 */
	public static String jsonToHql(JSONObject json, String tableName) {
		StringBuffer hql = new StringBuffer("from " + tableName + " where 1 = 1 ");
		for (String key : json.keySet()) {
			if (json.getString(key) != null && json.getString(key) != "") {
				hql.append("and " + key + " like  ");
				hql.append(" '%" + json.getString(key) + "%' ");
			}
		}
		return hql.toString();
	}

	/**
	 * request转为hql语句
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String requestToHql(HttpServletRequest request, String tableName) {
		Enumeration<String> enu = request.getParameterNames();
		StringBuffer hql = new StringBuffer("from " + tableName + " where 1 = 1 ");
		while (enu.hasMoreElements()) {
			String key = (String) enu.nextElement();
			String value = request.getParameter(key);
			if (value != null && value != "" && !"page".equals(key) && !"rows".equals(key)) {
				hql.append("and " + key + " like ? ");
				hql.append(" '%" + value + "%' ");
			}
		}
		return hql.toString();
	}

	/**
	 * 
	 * @param json
	 * @return
	 */
	public static JSONObject clearDatagridJson(JSONObject json) {
		json.remove("page");
		json.remove("rows");
		return json;
	}

	/**
	 * request转JSON
	 * 
	 * @param request
	 * @param beanClass
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	public static <T> T requestToBean(HttpServletRequest request, // 返回值为随意的类型 传入参数为request 和该随意类型
			Class<T> beanClass) {
		// Date类型转换器
		ConvertUtils.register(new Converter() {

			public Object convert(Class type, Object value) {

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				try {
					if(value != "")
					return simpleDateFormat.parse(value.toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return null;
			}
		}, Date.class);
		try {
			// 实例化随意类型
			T bean = beanClass.newInstance();
			// 获得参数的一个列举
			Enumeration enu = request.getParameterNames();
			// 遍历列举来获取所有的参数
			while (enu.hasMoreElements()) {
				String name = (String) enu.nextElement();
				String value = request.getParameter(name);
				// 注意这里导入的是 import org.apache.commons.beanutils.BeanUtils;
				BeanUtils.setProperty(bean, name, value);
			}
			return bean;
		} catch (Exception e) {
			// 如果错误 则向上抛运行时异常
			throw new RuntimeException(e);
		}
	}

	/**
	 * request参数转换成bean
	 * @param request
	 * @param bean
	 */
	@SuppressWarnings("rawtypes")
	public static <T> void requestToBean(HttpServletRequest request, // 返回值为随意的类型 传入参数为request 和该随意类型
			T bean) {
		// Date类型转换器
		ConvertUtils.register(new Converter() {

			public Object convert(Class type, Object value) {

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					if(value != null && !"".equals(value)) 
					return simpleDateFormat.parse(value.toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return null;
			}
		}, Date.class);
		try {
			// 实例化随意类型
			// 获得参数的一个列举
			Enumeration enu = request.getParameterNames();
			// 遍历列举来获取所有的参数
			while (enu.hasMoreElements()) {
				String name = (String) enu.nextElement();
				String value = request.getParameter(name);
				// 注意这里导入的是 import org.apache.commons.beanutils.BeanUtils;
				BeanUtils.setProperty(bean, name, value);
			}
		} catch (Exception e) {
			// 如果错误 则向上抛运行时异常
			throw new RuntimeException(e);
		}
	}

	/**
	 *  Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> transBean2Map(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					if (value != "" && value != null) {
						map.put(key, value);
					}

				}

			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}
		return map;
	}
	
	//设置新增人员、时间等共通信息
	public static <T> void setAddInfo(T bean,TeePerson loginUser) {
		try {
			BeanUtils.setProperty(bean, "createdUser", loginUser.getUserName());
			BeanUtils.setProperty(bean, "createdUserId", String.valueOf(loginUser.getUuid()));
			BeanUtils.setProperty(bean, "createdTime", getCurrentTime());
		} catch (Exception e) {
			// 如果错误 则向上抛运行时异常
			throw new RuntimeException(e);
		}
	}
	
	//设置修改人员、时间等共通信息
	public static <T> void setModifyInfo(T bean,TeePerson loginUser) {
		try {
			BeanUtils.setProperty(bean, "modifiedUser", loginUser.getUserName());
			BeanUtils.setProperty(bean, "modifiedUserId", String.valueOf(loginUser.getUuid()));
			BeanUtils.setProperty(bean, "modifiedTime", getCurrentTime());
		} catch (Exception e) {
			// 如果错误 则向上抛运行时异常
			throw new RuntimeException(e);
		}
	}
	public static boolean isEmptyOrBlank(Object str){
		if(str==null || "".equals(str)){
			return true;
		}
		return false;
	}
}
