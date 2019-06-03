package com.tianee.oa.subsys.weixin.service;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dingtalk.openapi.demo.department.Department;
import com.alibaba.dingtalk.openapi.demo.department.DepartmentHelper;
import com.alibaba.dingtalk.openapi.demo.user.User;
import com.alibaba.dingtalk.openapi.demo.user.UserHelper;
import com.qq.weixin.util.WXpiException;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.subsys.weixin.ParamesAPI.AccessToken;
import com.tianee.oa.subsys.weixin.ParamesAPI.WeixinUtil;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeWeixinDeptService extends TeeBaseService{
	

	
	// 创建部门地址
	public final static String CREATE_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=ACCESS_TOKEN";
	// 更新部门地址
	public final static String UPDATE_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=ACCESS_TOKEN";
	// 删除部门地址
	public final static String DELETE_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=ACCESS_TOKEN&id=ID";
	// 获取部门列表地址
	public final static String GETLIST_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN&id=PARENT_ID";

	
	@Autowired
	private TeeWeixinPersonService weixinPersonService;
	
	
	
	@Autowired
	private TeeDeptDao deptDao;
	
	/**
	 * 同步组织机构
	 * 先删除微信中所有部门，在重新新建部门
	 * @throws WXpiException 
	 * @throws IOException 
	 */
	public void syncOrg(HttpServletResponse response) throws  WXpiException, IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter pw = response.getWriter();
		pw.write("<style>p{font-size:12px;}</style>");
		pw.flush();
		
		pw.write("<p style='color:red'>===获取所有微信端组织机构===</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
		pw.flush();
		
		//先获取所有的微信组织机构
		JSONArray deptList = getWeixinDeptByParentId(AccessToken.getAccessTokenInstance().getToken(), 1);
		List<Long> defaultDept = new ArrayList<Long>();
		defaultDept.add(Long.parseLong("1"));
		
		pw.write("<p style='color:red'>===获取所有微信端人员信息===</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
		pw.flush();
		
		//获取所有部门人员的
		JSONArray userList = weixinPersonService.getPersonByDeptIdAndFetchFhildAndStatus(AccessToken.getAccessTokenInstance().getToken(), "1", "1", "0");
		
		pw.write("<p style='color:red'>===循环微信端中所有人员，将账号暂时放入顶级部门===</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
		pw.flush();
		
		//循环所有人员的，将所有部门的人全部放到顶级部门，
		Iterator userIterator = userList.iterator();
		long totalUser = userList.size();
		int processUser = 1;
		while(userIterator.hasNext()){
			JSONObject obj = (JSONObject)userIterator.next();
			int tempDeptId = TeeStringUtil.getInteger(obj.get("id"),0);//获取微信部门Id
			
			//更新人员
			String code = weixinPersonService.simpleUpdate(obj.getString("userid"), obj.getString("name"), "[1]");
			
			pw.write("<p style=''>微信人员["+obj.getString("name")+"]已移置顶层("+(processUser++)+"/"+totalUser+")</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			pw.flush();
		}
		
		pw.write("<p style='color:red'>===循环所有部门，将部门下的人员暂时放入顶级部门===</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
		pw.flush();
		
		//循环所有部门的，将所有部门的人全部放到顶级部门，
		Iterator iterator = deptList.iterator();
		totalUser = deptList.size();
		processUser = 1;
		while(iterator.hasNext()){
			JSONObject obj = (JSONObject)iterator.next();
			int tempDeptId = TeeStringUtil.getInteger(obj.get("id"),0);//获取微信部门Id
			if(tempDeptId > 1){
				String code = update(obj.getString("name"), tempDeptId + "", "1", "0");
			}
			
			pw.write("<p style=''>微信部门["+obj.getString("name")+"]已移置顶层("+(processUser++)+"/"+totalUser+")</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			pw.flush();
		}
		
		pw.write("<p style='color:red'>===循环所有部门，将部门删除===</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
		pw.flush();
		totalUser = deptList.size();
		processUser = 1;
		
		//循环所有部门，删除
		Iterator iterator2 = deptList.iterator();
		while(iterator2.hasNext()){
			JSONObject obj = (JSONObject)iterator2.next();
			int tempDeptId = TeeStringUtil.getInteger(obj.get("id"),0);//获取微信部门Id
			if(tempDeptId > 1){
				delete(tempDeptId + "");
			}
			
			pw.write("<p style=''>微信部门["+obj.getString("name")+"]已删除("+(processUser++)+"/"+totalUser+")</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			pw.flush();
		}
		
		pw.write("<p style='color:red'>===开始将系统内部部门同步至微信企业号===</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
		pw.flush();
		
		//创建部门
		//从顶层到根节点排序方式获取系统部门信息
		List<TeeDepartment> sysDeptList = deptDao.getAllDept();
		long deptId = 0;
		totalUser = sysDeptList.size();
		processUser = 1;
		
		for(TeeDepartment sysDept:sysDeptList){
			if(sysDept.getDeptParent()!=null){
				deptId = create(sysDept.getDeptName(), String.valueOf(sysDept.getDeptParent().getWeixinDeptId()), String.valueOf(sysDept.getDeptNo()));
			}else{
				deptId = create(sysDept.getDeptName(), 
						String.valueOf("1"), 
						String.valueOf(sysDept.getDeptNo()));
			}
			sysDept.setWeixinDeptId(deptId);
			
			pw.write("<p style=''>OA部门["+sysDept.getDeptName()+"]已同步("+(processUser++)+"/"+totalUser+")</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			pw.flush();
		}
		
		pw.write("<p style='color:green'>===已完成===</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
		pw.flush();
	}
	
	/**
	 * 创建部门
	 * @param name  部门名称。长度限制为1~64个字符
	 * @param parentid  父亲部门id。根部门id为1
	 * */
	public static long create(String name, String parentid , String order) {
		String postData = "{\"name\":\"%s\",\"parentid\": %s ,\"order\":%s}";
		String data =  String.format(postData, name ,parentid,order);
		// 更新部门地址
		String CREATE_URL_TEMP = CREATE_URL.replace("ACCESS_TOKEN", AccessToken.getAccessTokenInstance().getToken());
		JSONObject obj = WeixinUtil.httpRequest(CREATE_URL_TEMP, "POST", data);
		int  errcode = obj.getInt("errcode");
		long id = 0;
		if(errcode == 0){
			id = TeeStringUtil.getLong(obj.getInt("id"), 0);
		}
		return id;
	}
	

	/**
	 * 更新部门
	 * 
	 * @param name
	 *            更新的部门名称。长度限制为0~64个字符。修改部门名称时指定该参数
	 * @param id
	 *            部门id
	 * */
	public static String update(String id ,String name,  String parentid , String order) {
		String postData = "{\"id\":%s,\"name\":\"%s\" ,\"parentid\":%s ,\"order\":\"%s\" } ";
		String data =  String.format(postData,   name ,id,parentid,order);
		// 更新部门地址
		String UPDATE_URL_TEMP = UPDATE_URL.replace("ACCESS_TOKEN", AccessToken.getAccessTokenInstance().getToken());
		JSONObject obj = WeixinUtil.httpRequest(UPDATE_URL_TEMP, "POST", data);
		int  errcode = obj.getInt("errcode");
		return errcode + "";
	}
	

	/**
	 * 删除部门
	 * 
	 * @param id
	 *            部门id
	 * */
	public static String delete(String id) {
		String delete_url = DELETE_URL.replace("ACCESS_TOKEN", AccessToken.getAccessTokenInstance().getToken()).replace("ID", id);
		JSONObject obj =  WeixinUtil.httpRequest(delete_url, "GET", null);
		int  errcode = obj.getInt("errcode");
		return errcode + "";
	}

	/**
	 * 获取微信部门      ps.获取所有，将parentId设置为1
	 * @param request
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public JSONArray getWeixinDeptByParentId(String accessToken , int parentDeptId) throws WXpiException{
		String getListUrl = GETLIST_URL.replace("ACCESS_TOKEN", accessToken).replace("PARENT_ID", ""+ parentDeptId);
		JSONObject jsonObject = WeixinUtil.httpRequest(getListUrl, "GET", null);
		JSONArray list = new JSONArray();
		if(jsonObject != null){
			int errcode = jsonObject.getInt("errcode");
			if(errcode == 0){//获取成功
				list = jsonObject.getJSONArray("department");
			}
		}
		return list;
	}
	/**
	 * 查看微信组织机构
	 * @param parentDeptId
	 * @return
	 * @throws WXpiException
	 */
	public TeeJson getWeixinDeptAsync(String parentDeptId) throws WXpiException{
		TeeJson json = new TeeJson();
		String getListUrl = GETLIST_URL.replace("ACCESS_TOKEN", AccessToken.getAccessTokenInstance().getToken()).replace("PARENT_ID", ""+ parentDeptId);
		JSONObject jsonObject = WeixinUtil.httpRequest(getListUrl, "GET", null);
		List<TeeZTreeModel> orgDeptList = new ArrayList<TeeZTreeModel>(); 
		JSONArray list = new JSONArray();
		if(jsonObject != null){
			int errcode = jsonObject.getInt("errcode");
			if(errcode == 0){//获取成功
				list = jsonObject.getJSONArray("department");
			}
		}
		for (int i = 0; i < list.size(); i++) {
			JSONObject org = (JSONObject) list.get(i);
			String id = org.getString("id");
			String parentid = org.getString("parentid");
			TeeZTreeModel model = null;
			if(id.equals(parentDeptId)){
				model = new TeeZTreeModel(id, org.getString("name"), true, parentid, true, "pIconHome" , "");
			}else{
				model = new TeeZTreeModel(id, org.getString("name"), true, parentid, true, "deptNode" , "");
			}
			orgDeptList.add(model);
		}
		JSONArray userList = weixinPersonService.getPersonByDeptIdAndFetchFhildAndStatus(AccessToken.getAccessTokenInstance().getToken(), parentDeptId, "1", "0");
		for (int i = 0; i < userList.size(); i++) {
			JSONObject user = (JSONObject) userList.get(i);
			String id = user.getString("userid");
			String name = user.getString("name");
			JSONArray parentArray = user.getJSONArray("department");//[1, 2]
			for (int j = 0; j < parentArray.size();j++) {
				String parentId = parentArray.get(j).toString();
				TeeZTreeModel model  = new TeeZTreeModel(id, name, false,parentId, true, "person_online_node" , "");
				orgDeptList.add(model);
			}
		}
		json.setRtData(orgDeptList);
        json.setRtState(true);
		return json;
	}

	
	
	
	/**
	 * 同步指定部门
	 * @param response
	 * @throws Exception 
	 */
	public void syncDept(HttpServletResponse response,HttpServletRequest request) throws Exception {
		
		//获取部门id
	    int deptId=TeeStringUtil.getInteger(request.getParameter("deptId"),0);
	    //获取部门
	    TeeDepartment dept=(TeeDepartment) simpleDaoSupport.get(TeeDepartment.class,deptId);
	    
	    
	    response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter pw = response.getWriter();
		pw.write("<style>p{font-size:12px;}</style>");
		pw.flush();
		
		pw.write("<p style='color:red'>===获取微信端组织机构信息===</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
		pw.flush();
	    
	    
	    if(dept!=null){//选择的部门不为空   存在
	    
			String deptFullId=dept.getDeptFullId();
			if(deptFullId.startsWith("/")){
				deptFullId=deptFullId.substring(1,deptFullId.length());
			}
	    	String dIds[]=deptFullId.split("/");
	    	
	    	List<TeeDepartment> deptList=new ArrayList<TeeDepartment>();//存放当前部门   以及当前部门的父级部门
	    	int dId=0;
	    	TeeDepartment d=null;
	    	for (String id : dIds) {
				dId=TeeStringUtil.getInteger(id, 0);
				d=(TeeDepartment) simpleDaoSupport.get(TeeDepartment.class,dId);
				deptList.add(d);
			}	    	
	    	
	    	long weiXinDeptId=0;
	    	for (TeeDepartment teeDepartment : deptList) {
				if(teeDepartment.getUuid()==deptId){//当前部门
					if(teeDepartment.getWeixinDeptId()==0){//不存在  则新增
						
						weiXinDeptId=create(teeDepartment.getDeptName(), teeDepartment.getDeptParent()==null?String.valueOf("1"):String.valueOf(teeDepartment.getDeptParent().getWeixinDeptId()), String.valueOf(teeDepartment.getDeptNo()));
						
						teeDepartment.setWeixinDeptId(weiXinDeptId);
						pw.write("<p style=''>OA部门["+teeDepartment.getDeptName()+"]已同步</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
            			pw.flush();
					}else{//存在  则更新
						update(String.valueOf(teeDepartment.getDingdingDeptId()), teeDepartment.getDeptName() , teeDepartment.getDeptParent()==null?String.valueOf("1"):String.valueOf(teeDepartment.getDeptParent().getWeixinDeptId()), String.valueOf(teeDepartment.getDeptNo()));
						pw.write("<p style=''>OA部门["+teeDepartment.getDeptName()+"]已更新</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
            			pw.flush();
					}
					
				}else{//父级部门
                    if(teeDepartment.getWeixinDeptId()==0){//不存在  则新增
                    	weiXinDeptId=create(teeDepartment.getDeptName(), teeDepartment.getDeptParent()==null?String.valueOf("1"):String.valueOf(teeDepartment.getDeptParent().getWeixinDeptId()), String.valueOf(teeDepartment.getDeptNo()));
                    	teeDepartment.setWeixinDeptId(weiXinDeptId);
                    	pw.write("<p style=''>OA部门["+teeDepartment.getDeptName()+"]已同步</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
            			pw.flush();
					}  
				}
			}
	
	    }else{//部门为空  不存在
	    	pw.write("<p style=''>所选部门不存在</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			pw.flush();
	    }
	
		
	}

	public void delWeiXinDept(HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		
		//获取部门id
		int deptId=TeeStringUtil.getInteger(request.getParameter("deptId"),0);
		//获取部门
		TeeDepartment dept=(TeeDepartment) simpleDaoSupport.get(TeeDepartment.class,deptId);
		//weixin顶层部门
		List<Long> defaultDept = new ArrayList<Long>();
		defaultDept.add(Long.parseLong("1"));	    
			    
	    response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter pw = response.getWriter();
		pw.write("<style>p{font-size:12px;}</style>");
		pw.flush();
		
		pw.write("<p style='color:red'>===获取微信端组织机构信息===</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
		pw.flush();
	    
	    
	    if(dept!=null){//选择的部门不为空   存在
	        if(dept.getWeixinDeptId()==0){
	        	pw.write("<p style=''>所选部门未同步到微信中，无需删除！</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
				pw.flush();
	        }else{
	        	//取出部门下 所有递归子部门
	        	JSONArray list = getWeixinDeptByParentId(AccessToken.getAccessTokenInstance().getToken(), TeeStringUtil.getInteger(String.valueOf(dept.getWeixinDeptId()),0));
	        	
	        	
	        	JSONArray userList=null;
	        	TeeDepartment d=null;
	        	if(list!=null&&list.size()>0){
	        		for (int i=list.size()-1;i>=0;i--) {
	        			userList = weixinPersonService.getPersonByDeptIdAndFetchFhildAndStatus(AccessToken.getAccessTokenInstance().getToken(), ((JSONObject)list.get(i)).getString("id"), "0","0");
	        			
        				//循环所有人员的，将所有部门的人全部放到顶级部门，
        				Iterator userIterator = userList.iterator();
        				while(userIterator.hasNext()){
        					JSONObject obj = (JSONObject)userIterator.next();
        					int tempDeptId = TeeStringUtil.getInteger(obj.get("id"),0);//获取微信部门Id
        					
        					//更新人员
        					String code = weixinPersonService.simpleUpdate(obj.getString("userid"), obj.getString("name"), "[1]");
        					
        					pw.write("<p style=''>微信人员["+obj.getString("name")+"]已移置顶层</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
        					pw.flush();
        				}
	        			
	        			
	        			//取出对应的OA中的部门  并且设置dingDeptId为0
	        			d=(TeeDepartment) simpleDaoSupport.unique(" from TeeDepartment where weixinDeptId = ?", new Object[]{TeeStringUtil.getLong(((JSONObject)list.get(i)).getString("id"), 0)});
	        			if(d!=null){
	        				d.setWeixinDeptId(0);
	        			}
	        			
	        			
	        		    delete(((JSONObject)list.get(i)).getString("id"));
	        			pw.write("<p style=''>微信部门["+((JSONObject)list.get(i)).getString("name")+"]已刪除</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
	        			pw.flush();
	        			
	        			
	        			
					}
	        		
	        	}
	        }
	    }else{//部门为空  不存在
	    	pw.write("<p style=''>所选部门在OA中不存在</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			pw.flush();
	    }
		
	}
	
}
