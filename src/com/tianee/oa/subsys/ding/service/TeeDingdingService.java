package com.tianee.oa.subsys.ding.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.department.Department;
import com.alibaba.dingtalk.openapi.demo.department.DepartmentHelper;
import com.alibaba.dingtalk.openapi.demo.user.User;
import com.alibaba.dingtalk.openapi.demo.user.UserHelper;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.fastjson.JSONObject;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeModuleConst;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author kakalion
 *
 */
@Service
public class TeeDingdingService extends TeeBaseService{
	
	@Autowired
	private TeePersonService personService;
	
	
	/**
	 * 获取基础信息
	 */
	public Map getBasicParam(){
		Map data = new HashMap();
		data.put("DD_CORPID", TeeSysProps.getString("DD_CORPID"));
		data.put("DD_CORPSECRET", TeeSysProps.getString("DD_CORPSECRET"));
		data.put("DD_URL", TeeSysProps.getString("DD_URL"));
		
		data.put("DING_EMAIL_APPID", TeeSysProps.getString("DING_EMAIL_APPID"));
		data.put("DING_NOTIFY_APPID", TeeSysProps.getString("DING_NOTIFY_APPID"));
		data.put("DING_NEWS_APPID", TeeSysProps.getString("DING_NEWS_APPID"));
		data.put("DING_WORKFLOW_APPID", TeeSysProps.getString("DING_WORKFLOW_APPID"));
		data.put("DING_CALENDAR_APPID", TeeSysProps.getString("DING_CALENDAR_APPID"));
		data.put("DING_DIARY_APPID", TeeSysProps.getString("DING_DIARY_APPID"));
		data.put("DING_SCHEDULE_APPID", TeeSysProps.getString("DING_SCHEDULE_APPID"));
		data.put("DING_TASK_APPID", TeeSysProps.getString("DING_TASK_APPID"));
		data.put("DING_CUSTOMER_APPID", TeeSysProps.getString("DING_CUSTOMER_APPID"));
		data.put("DING_TOPIC_APPID", TeeSysProps.getString("DING_TOPIC_APPID"));
		data.put("DING_PERSONDISK_APPID", TeeSysProps.getString("DING_PERSONDISK_APPID"));
		data.put("DING_PUBDISK_APPID", TeeSysProps.getString("DING_PUBDISK_APPID"));
		return data;
	}
	
	/**
	 * 保存基础信息
	 */
	public void saveBasicParam(Map<String,String> requestData){
		String DD_URL = requestData.get("DD_URL");
		String DD_CORPSECRET = requestData.get("DD_CORPSECRET");
		String DD_CORPID = requestData.get("DD_CORPID");
		
		TeeSysProps.getProps().setProperty("DD_URL", DD_URL);
		TeeSysProps.getProps().setProperty("DD_CORPSECRET", DD_CORPSECRET);
		TeeSysProps.getProps().setProperty("DD_CORPID", DD_CORPID);
		
		simpleDaoSupport.executeUpdate("update TeeSysPara set paraValue=? where paraName=?", new Object[]{DD_URL,"DD_URL"});
		simpleDaoSupport.executeUpdate("update TeeSysPara set paraValue=? where paraName=?", new Object[]{DD_CORPSECRET,"DD_CORPSECRET"});
		simpleDaoSupport.executeUpdate("update TeeSysPara set paraValue=? where paraName=?", new Object[]{DD_CORPID,"DD_CORPID"});
	
	}
	
	/**
	 * 保存应用ID
	 */
	public void saveAppParam(Map<String,String> requestData){
		String modelType = TeeStringUtil.getString(requestData.get("modelType"));//模块类型
		String WEIXIN_APPID = requestData.get("WEIXIN_APPID");
//		String WEIXIN_TOKEN =  requestData.get("WEIXIN_TOKEN");
//		String WEIXIN_ENCODING_AESKEY = requestData.get("WEIXIN_ENCODING_AESKEY");
//		String WEIXIN_APPID_NAME = "";
//		String WEIXIN_TOKEN_NAME = "";
//		String WEIXIN_ENCODING_AESKEY_NAME = "";
//		String temp[] = getWeixinAppInfo(modelType, WEIXIN_APPID_NAME, WEIXIN_TOKEN_NAME, WEIXIN_ENCODING_AESKEY_NAME);
//		TeeSysProps.getProps().setProperty(temp[0] , WEIXIN_APPID);
//		TeeSysProps.getProps().setProperty(temp[1] , WEIXIN_TOKEN);
//		TeeSysProps.getProps().setProperty(temp[2], WEIXIN_ENCODING_AESKEY);
		simpleDaoSupport.executeUpdate("update TeeModuleSort set DD_APP_ID=? where KEY_=?", new Object[]{WEIXIN_APPID,modelType});
		TeeModuleConst.MODULE_SORT_DD_APP_ID.put(modelType, WEIXIN_APPID);
	}
	
	/**
	 * 同步组织机构
	 * @throws OApiException 
	 */
	public void syncOrg(HttpServletResponse response) throws Exception{
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter pw = response.getWriter();
		pw.write("<style>p{font-size:12px;}</style>");
		pw.flush();
		
		pw.write("<p style='color:red'>===获取钉钉端组织机构信息===</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
		pw.flush();
		
//		String DD_CORPID = TeeSysProps.getString("DD_CORPID");
//		String DD_CORPSECRET = TeeSysProps.getString("DD_CORPSECRET");
		String accessToken = TeeSysProps.getString("DING_ACCESS_TOKEN");
		//先获取所有的钉钉组织机构
		List<Department> deptList = DepartmentHelper.listDepartments(accessToken,"");
		List<User> userList = null;
		List<Long> defaultDept = new ArrayList<Long>();
		defaultDept.add(Long.parseLong("1"));
		int proces = 1;
		int total = deptList.size();
		
		for(Department dept:deptList){
			pw.write("<p style='color:red'>===获取钉钉端["+dept.name+"]部门下人员信息===</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			pw.flush();
			
			//将所有的部门的人员都归属到顶级部门中
			userList = UserHelper.getDepartmentUser(accessToken, Long.parseLong(dept.id));

			int proces0 = 1;
			int total0 = userList.size();
			
			for(User user:userList){
				user.department = defaultDept;
				UserHelper.updateUser(accessToken, user);
				
				pw.write("<p style=''>钉钉人员["+user.name+"]已移置顶层("+(proces0++)+"/"+total0+")</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
				pw.flush();
			}
			
			//将部门归属到顶级部门中
			if(!"1".equals(dept.id)){
				DepartmentHelper.updateDepartment(accessToken, dept.name, "1", "1", Long.parseLong(dept.id));
				
				pw.write("<p style=''>钉钉部门["+dept.name+"]已移置顶层("+(proces++)+"/"+total+")</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
				pw.flush();
			}
		}
		
		proces = 1;
		total = deptList.size();
		
		pw.write("<p style='color:red'>===开始删除钉钉通讯录中的所有部门，以便与OA部门保持一致===</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
		pw.flush();
		
		//删除除了顶级部门的所有部门
		for(Department dept:deptList){
			if(!"1".equals(dept.id)){
				
				DepartmentHelper.deleteDepartment(accessToken, Long.parseLong(dept.id));
				
				pw.write("<p style=''>钉钉部门["+dept.name+"]已删除("+(proces++)+"/"+total+")</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
				pw.flush();
			}
		}
		
		//从顶层到根节点排序方式获取系统部门信息
		List<TeeDepartment> sysDeptList = simpleDaoSupport.find("from TeeDepartment order by length(deptParentLevel) asc, deptNo asc", null);
		long deptId = 0;
		
		proces = 1;
		total = sysDeptList.size();
		
		pw.write("<p style='color:red'>===开始将系统内部部门同步至钉钉通讯录===</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
		pw.flush();
		
		for(TeeDepartment sysDept:sysDeptList){
			if(sysDept.getDeptParent()!=null){
				deptId = DepartmentHelper.createDepartment(accessToken, 
						sysDept.getDeptName(), 
						String.valueOf(sysDept.getDeptParent().getDingdingDeptId()), 
						String.valueOf(sysDept.getDeptNo()));
			}else{
				deptId = DepartmentHelper.createDepartment(accessToken, 
						sysDept.getDeptName(), 
						String.valueOf("1"), 
						String.valueOf(sysDept.getDeptNo()));
			}
			sysDept.setDingdingDeptId(deptId);
			
			pw.write("<p style=''>OA部门["+sysDept.getDeptName()+"]已同步 ("+(proces++)+"/"+total+")</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			pw.flush();
		}
		
		pw.write("<p style='color:green'>===任务已完成===</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
		pw.flush();
	}
	
	/**
	 * 同步人员
	 * @throws OApiException 
	 */
	public void syncPerson(String userIds,String deptIds,String oper,HttpServletResponse response) throws Exception{
//		String DD_CORPID = TeeSysProps.getString("DD_CORPID");
//		String DD_CORPSECRET = TeeSysProps.getString("DD_CORPSECRET");
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter pw = response.getWriter();
		pw.write("<style>p{font-size:12px;}</style>");
		pw.flush();
		
		pw.write("<p style='color:red'>===开始同步OA人员到钉钉通讯录===</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
		pw.flush();
		
		
		String accessToken = TeeSysProps.getString("DING_ACCESS_TOKEN");
		List<TeePerson> sysUserList = new ArrayList<TeePerson>();
		List<TeePerson> sysDeptUserList = new ArrayList<TeePerson>();
		deptIds = getDeptUserIds(deptIds);
		if("0".equals(oper)){//同步所有人员
			sysUserList = simpleDaoSupport.find("from TeePerson where deleteStatus!='1' and notLogin!='1'", null);
		}else if("1".equals(oper)){//同步指定人员
			sysUserList = simpleDaoSupport.find("from TeePerson where deleteStatus!='1' and notLogin!='1' and ("+TeeDbUtility.IN("uuid", userIds)+" or "+TeeDbUtility.IN("dept.uuid", deptIds)+")", null);
		}else if("2".equals(oper)){//删除指定人员
			sysUserList = simpleDaoSupport.find("from TeePerson where deleteStatus!='1' and notLogin!='1' and ("+TeeDbUtility.IN("uuid", userIds)+" or "+TeeDbUtility.IN("dept.uuid", deptIds)+")", null);
		}
		
		int prcsed = 1;
		int total = sysUserList.size();
		
		if(!"2".equals(oper)){
			User user = null;
			boolean exists = false;
			//同步用户信息
			for(TeePerson sysUser:sysUserList){
				if(sysUser.getDept()!=null){
					//如果其存在的部门存在钉钉部门主键，则进行同步
					if(!TeeUtility.isNullorEmpty(sysUser.getMobilNo()) && sysUser.getDept().getDingdingDeptId()!=0){
						//获取对应的钉钉账户，查看是否存在
						try{
							user = UserHelper.getUser(accessToken, sysUser.getGsbUserId());
							exists = true;
						}catch(Exception ex){}
						
						if(user==null){//更新用户
							user = new User();
						}
						user.department = new ArrayList();
						user.department.add(sysUser.getDept().getDingdingDeptId());
						user.email = sysUser.getEmail();
						user.mobile = sysUser.getMobilNo();
						user.name = sysUser.getUserName();
						user.position = sysUser.getUserRole().getRoleName();
						user.userid = "DING_UID_"+sysUser.getUuid();
						
						
						if(exists){//更新用户
							try{
								UserHelper.updateUser(accessToken, user);
								sysUser.setGsbUserId(user.userid);
							}catch(Exception ex){
								ex.printStackTrace();
							}
						}else{//创建
							try{
								UserHelper.createUser(accessToken, user);
								sysUser.setGsbUserId(user.userid);
							}catch(Exception ex){
								ex.printStackTrace();
							}
						}
						
						pw.write("<p style=''>同步OA用户["+sysUser.getUserName()+"]已完成。("+(prcsed++)+"/"+total+")</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
						pw.flush();
					}else{
						pw.write("<p style='color:#cdcdcd'>同步OA用户["+sysUser.getUserName()+"]失败，请确认该用户是否绑定手机号。("+(prcsed++)+"/"+total+")</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
						pw.flush();
					}
				}
			}
		}else{//删除用户
			for(TeePerson sysUser:sysUserList){
				try{
					UserHelper.deleteUser(accessToken, sysUser.getGsbUserId());
					sysUser.setGsbUserId(null);
					pw.write("<p style=''>删除用户["+sysUser.getUserName()+"]已完成。("+(prcsed++)+"/"+total+")</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
					pw.flush();
				}catch(Exception ex){
					pw.write("<p style=''>删除用户["+sysUser.getUserName()+"]失败，原因："+ex.getMessage()+"。("+(prcsed++)+"/"+total+")</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
					pw.flush();
				}
			}
		}
		
	}
	
	
	
	public void bindUser(String dingUser,String oaUser){
		TeePerson person = personService.selectByUuid(Integer.parseInt(oaUser));
		if(person!=null){
			person.setGsbUserId(dingUser);
		}
	}
	
	public void cancelBindUser(String dingUser,String oaUser){
		TeePerson person = personService.getPersonByUserId(oaUser);
		if(person!=null){
			person.setGsbUserId(null);
		}
	}
	
	public List<User> getPersonsByDept(String id) throws NumberFormatException, OApiException{
		String accessToken = TeeSysProps.getString("DING_ACCESS_TOKEN");
		List<User> deptList = UserHelper.getDepartmentUser(accessToken, Long.parseLong(id));
		TeePerson person = null;
		for(User user:deptList){
			person = personService.getPersonByDingUserId(user.userid);
			if(person==null){
				user.active = false;
				continue;
			}
			user.active = true;
			user.position = person.getUserName();
			user.avatar = person.getUserId();
		}
		return deptList;
	}
	
	/**
	 * 获取部门id串
	 * @param deptUserIds
	 * @return
	 */
	private String getDeptUserIds(String deptUserIds){
		List<TeeDepartment> deptList = simpleDaoSupport.find("from TeeDepartment dept where "+TeeDbUtility.IN("dept.uuid", deptUserIds), null);
		Set<TeeDepartment> levelDeptList = new HashSet<TeeDepartment>();
		for(TeeDepartment dept:deptList){
			levelDeptList.add(dept);
			
			levelDeptList.addAll(simpleDaoSupport.find("from TeeDepartment dept where dept.deptParentLevel like '"+dept.getDeptParentLevel()+dept.getGuid()+"%' ", null));
		}
		
		StringBuffer sb = new StringBuffer();
		int i=0;
		for(TeeDepartment dept:levelDeptList){
			sb.append(dept.getUuid());
			if(i!=levelDeptList.size()-1){
				sb.append(",");
			}
			i++;
		}
		
		return sb.toString();
		
	}

	
	/**
	 * 同步指定部门
	 * @param response
	 * @param request
	 * @throws IOException 
	 */
	public void syncDept(HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		
		//认证码
		String accessToken = TeeSysProps.getString("DING_ACCESS_TOKEN");
		//获取部门id
	    int deptId=TeeStringUtil.getInteger(request.getParameter("deptId"),0);
	    //获取部门
	    TeeDepartment dept=(TeeDepartment) simpleDaoSupport.get(TeeDepartment.class,deptId);
	    
	    
	    response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter pw = response.getWriter();
		pw.write("<style>p{font-size:12px;}</style>");
		pw.flush();
		
		pw.write("<p style='color:red'>===获取钉钉端组织机构信息===</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
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
	    	
	    	long dingDeptId=0;
	    	for (TeeDepartment teeDepartment : deptList) {
				if(teeDepartment.getUuid()==deptId){//当前部门
					if(teeDepartment.getDingdingDeptId()==0){//不存在  则新增
						dingDeptId=DepartmentHelper.createDepartment(accessToken, 
                    			teeDepartment.getDeptName(), 
                    			teeDepartment.getDeptParent()==null?String.valueOf("1"):String.valueOf(teeDepartment.getDeptParent().getDingdingDeptId()), 
        						String.valueOf(teeDepartment.getDeptNo()));
						teeDepartment.setDingdingDeptId(dingDeptId);
						pw.write("<p style=''>OA部门["+teeDepartment.getDeptName()+"]已同步</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
            			pw.flush();
					}else{//存在  则更新
						DepartmentHelper.updateDepartment(accessToken, teeDepartment.getDeptName(), teeDepartment.getDeptParent()==null?String.valueOf("1"):String.valueOf(teeDepartment.getDeptParent().getDingdingDeptId()), String.valueOf(teeDepartment.getDeptNo()),teeDepartment.getDingdingDeptId() );
						pw.write("<p style=''>OA部门["+teeDepartment.getDeptName()+"]已更新</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
            			pw.flush();
					}
					
				}else{//父级部门
                    if(teeDepartment.getDingdingDeptId()==0){//不存在  则新增
                    	
                    	dingDeptId=DepartmentHelper.createDepartment(accessToken, 
                    			teeDepartment.getDeptName(), 
                    			teeDepartment.getDeptParent()==null?String.valueOf("1"):String.valueOf(teeDepartment.getDeptParent().getDingdingDeptId()), 
        						String.valueOf(teeDepartment.getDeptNo()));
                    	teeDepartment.setDingdingDeptId(dingDeptId);
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

	
	
	/**
	 * 从钉钉上删除某个部门
	 * @param response
	 * @param request
	 * @throws Exception 
	 */
	public void delDingDept(HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		//认证码
		String accessToken = TeeSysProps.getString("DING_ACCESS_TOKEN");
		//获取部门id
		int deptId=TeeStringUtil.getInteger(request.getParameter("deptId"),0);
		//获取部门
		TeeDepartment dept=(TeeDepartment) simpleDaoSupport.get(TeeDepartment.class,deptId);
		//钉钉顶层部门
		List<Long> defaultDept = new ArrayList<Long>();
		defaultDept.add(Long.parseLong("1"));	    
			    
	    response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter pw = response.getWriter();
		pw.write("<style>p{font-size:12px;}</style>");
		pw.flush();
		
		pw.write("<p style='color:red'>===获取钉钉端组织机构信息===</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
		pw.flush();
	    
	    
	    if(dept!=null){//选择的部门不为空   存在
	        if(dept.getDingdingDeptId()==0){
	        	pw.write("<p style=''>所选部门未同步到钉钉中，无需删除！</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
				pw.flush();
	        }else{
	        	//取出部门下 所有递归子部门
	        	List<Department> list  = DepartmentHelper.listDepartmentsAll(accessToken, String.valueOf(dept.getDingdingDeptId()));
	        	//取出当前部门
	        	Department dingDept=DepartmentHelper.getDepartment(accessToken, String.valueOf(dept.getDingdingDeptId()));
	        	list.add(dingDept);
	        	List<User> userList=null;
	        	TeeDepartment d=null;
	        	if(list!=null&&list.size()>0){
	        		for (Department department : list) {
	        			userList = UserHelper.getDepartmentUser(accessToken,TeeStringUtil.getLong(department.id, 0));
	        			if(userList!=null&&userList.size()>0){
	        				for (User user : userList) {
	        					user.department = defaultDept;
	        					UserHelper.updateUser(accessToken, user);
	        					
	        					pw.write("<p style=''>钉钉人员["+user.name+"]已移置顶层</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
	        					pw.flush();
							}
	        			}
	        			
	        			//取出对应的OA中的部门  并且设置dingDeptId为0
	        			d=(TeeDepartment) simpleDaoSupport.unique(" from TeeDepartment where dingdingDeptId = ?", new Object[]{TeeStringUtil.getLong(department.id, 0)});
	        			if(d!=null){
	        				d.setDingdingDeptId(0);
	        			}
	        			
	        			
	        			DepartmentHelper.deleteDepartment(accessToken,TeeStringUtil.getLong(department.id, 0));
	        			pw.write("<p style=''>钉钉部门["+department.name+"]已刪除</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
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
