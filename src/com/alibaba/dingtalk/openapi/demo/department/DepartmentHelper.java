package com.alibaba.dingtalk.openapi.demo.department;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.OApiResultException;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DepartmentHelper {

	public static long createDepartment(String accessToken, String name, 
			String parentId, String order) throws OApiException {
		String url = Env.OAPI_HOST + "/department/create?" +
				"access_token=" + accessToken;
		JSONObject args = new JSONObject();
		args.put("name", name);
		args.put("parentid", parentId);
		args.put("order", order);
		JSONObject response = HttpHelper.httpPost(url, args);
		if (response.containsKey("id")) {
			return response.getLong("id");
		}
		else {
			throw new OApiResultException("id");
		}
	}

	
	/**
	 * 取出指定部门下的直属子部门
	 * @param accessToken
	 * @param id
	 * @return
	 * @throws OApiException
	 */
	public static List<Department> listDepartments(String accessToken,String id) 
			throws OApiException {
		String url = Env.OAPI_HOST + "/department/list?" +
				"access_token=" + accessToken+"&id="+id;
		JSONObject response = HttpHelper.httpGet(url);
		if (response.containsKey("department")) {
			JSONArray arr = response.getJSONArray("department");
			List<Department> list  = new ArrayList<Department>();
			for (int i = 0; i < arr.size(); i++) {
				list.add(arr.getObject(i, Department.class));
			}
			return list;
		}
		else {
			throw new OApiResultException("department");
		}
	}
	
	/**
	 * 取出指定部门下的所有递归子部门
	 * @param accessToken
	 * @param id
	 * @return
	 * @throws OApiException
	 */
	public static List<Department> listDepartmentsAll(String accessToken,String id) 
			throws OApiException {
		String url = Env.OAPI_HOST + "/department/list?" +
				"access_token=" + accessToken+"&id="+id+"&fetch_child=true";
		JSONObject response = HttpHelper.httpGet(url);
		if (response.containsKey("department")) {
			JSONArray arr = response.getJSONArray("department");
			List<Department> list  = new ArrayList<Department>();
			for (int i = 0; i < arr.size(); i++) {
				list.add(arr.getObject(i, Department.class));
			}
			return list;
		}
		else {
			throw new OApiResultException("department");
		}
	}
	
	public static Department getDepartment(String accessToken,String id) 
			throws OApiException {
		String url = Env.OAPI_HOST + "/department/get?" +
				"access_token=" + accessToken+"&id="+id;
		JSONObject response = HttpHelper.httpGet(url);
		return JSON.parseObject(response.toString(), Department.class);
	}
	
	
	public static void deleteDepartment(String accessToken, Long id) throws OApiException{
		String url = Env.OAPI_HOST  + "/department/delete?" +
				"access_token=" + accessToken + "&id=" + id;
		HttpHelper.httpGet(url);
	}
	
	
	public static void updateDepartment(String accessToken, String name, 
			String parentId, String order, long id) throws OApiException{
		String url = Env.OAPI_HOST  + "/department/update?" +
				"access_token=" + accessToken;
		JSONObject args = new JSONObject();
		args.put("name", name);
		args.put("parentid", parentId);
		args.put("order", order);
		args.put("id",id);
		HttpHelper.httpPost(url, args);
	}
}
