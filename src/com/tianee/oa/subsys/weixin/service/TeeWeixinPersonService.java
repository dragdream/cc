package com.tianee.oa.subsys.weixin.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qq.weixin.util.WXpiException;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.subsys.weixin.ParamesAPI.AccessToken;
import com.tianee.oa.subsys.weixin.ParamesAPI.WeixinUtil;
import com.tianee.oa.subsys.weixin.model.TeeWeiXinUserModel;
import com.tianee.oa.subsys.weixin.model.TeeWeixinDeptModel;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 通讯录成员管理类
 * 
 * @author syl
 * @date 2015.9.19
 */
@Service
public class TeeWeixinPersonService extends TeeBaseService{
	// 创建成员地址
	public static String CREATE_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=ACCESS_TOKEN";
	// 更新成员地址
	public static String UPDATA_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=ACCESS_TOKEN";
	// 删滁成员地址
	public static String DELETE_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=ACCESS_TOKEN&userid=ID";
	//批量删除成员地址
	public static String BATCH_DELETE_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete?access_token=ACCESS_TOKEN";
	// 获取成员地址
	public static String GET_PERSON_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=ID";
	// 获取部门成员地址
	public static String GET_GROUP_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD&status=STATUS";
	// 获取部门列表
	public static String GET_DEPT_LIST_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN&id=ID";

	@Autowired
	TeePersonDao teePersonDao;
	
	@Autowired
	TeePersonService personService;
	
	
	/**
	 * 根据用户Id获取用户信息
	 * @param UserID
	 * @return
	 */
	public TeePerson getPersonByUserId(String UserID){
		TeePerson person = personService.getPersonByWXUserId(UserID);
		return person;
	}
	
	/**
	 * 获取部门成员列表
	 * @param ACCESS_TOKEN  调用接口凭证
	 * @param DEPARTMENT_ID 部门Id
	 * @param FETCH_CHILD   否  1/0：是否递归获取子部门下面的成员  
	 * @param STATUS   人员状态  0获取全部成员，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加，未填写则默认 
	 * @return
	 */
	public JSONArray getPersonByDeptIdAndFetchFhildAndStatus(String ACCESS_TOKEN , String  DEPARTMENT_ID , String FETCH_CHILD , String STATUS ){
		JSONArray list = new JSONArray();
		String GET_GROUP_URL_TEMP = GET_GROUP_URL.replace("ACCESS_TOKEN" , ACCESS_TOKEN)
				.replace("DEPARTMENT_ID", DEPARTMENT_ID)
				.replace("FETCH_CHILD", FETCH_CHILD)
				.replace("STATUS", STATUS);
		JSONObject obj = WeixinUtil.httpRequest(GET_GROUP_URL_TEMP, "GET", null);
		if(obj != null){
			String errcode = TeeStringUtil.getString(obj.get("errcode"));
			if(errcode.equals("0")){//获取成功
				list = obj.getJSONArray("userlist"); 
			}
		}
		return list;
	}
	
	/**
	 * 同步人员
	 * @param userIds
	 * @param oper  0-同步所有 1-同步选中人员 1-删除选中人员
	 * @throws WXpiException
	 * @throws IOException 
	 */
	public void syncPerson(String userIds,String deptIds,String oper,HttpServletResponse response) throws WXpiException, IOException{
		PrintWriter pw = response.getWriter();
		
		pw.write("<style>p{font-size:12px;}</style>");
		pw.flush();
		
		pw.write("<p style='color:red'>===开始同步OA人员到微信通讯录===</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
		pw.flush();
		
		List<TeePerson> sysUserList = null;
		if("0".equals(oper)){//同步所有人员
			sysUserList = simpleDaoSupport.find("from TeePerson where deleteStatus!='1' and notLogin!='1'", null);
		}else if("1".equals(oper)){//同步指定人员
			sysUserList = simpleDaoSupport.find("from TeePerson where deleteStatus!='1' and notLogin!='1' and ("+TeeDbUtility.IN("uuid", userIds)+" or "+TeeDbUtility.IN("dept.uuid", deptIds)+")", null);
		}else if("2".equals(oper)){//删除指定人员
			sysUserList = simpleDaoSupport.find("from TeePerson where deleteStatus!='1' and notLogin!='1' and ("+TeeDbUtility.IN("uuid", userIds)+" or "+TeeDbUtility.IN("dept.uuid", deptIds)+")", null);
		}
		
		if(!"2".equals(oper)){
			boolean exists = false;
			
			int prcsed = 1;
			int total = sysUserList.size();
			
			//同步用户信息
			for(TeePerson sysUser:sysUserList){
				TeeDepartment dept = sysUser.getDept();//上级部门
				Set<TeeDepartment> set = new HashSet<TeeDepartment>();
				List<TeeDepartment> otherDept  = sysUser.getDeptIdOther();//其他部门
				set.addAll(otherDept);
				set.add(dept);
				if(set.size() > 0){//主部门或者辅助部门都不为空
					Iterator<TeeDepartment> iterator = set.iterator();
					StringBuffer weixinDeptIdStrBuf = new StringBuffer();
					String weixinDeptIdStr = "";//微信部门ID字符串
					while (iterator.hasNext()) {
						TeeDepartment deptTemp = iterator.next();
						long weixinDeptId = deptTemp.getWeixinDeptId();
						if(weixinDeptId > 0){
							weixinDeptIdStrBuf.append(weixinDeptId).append(",");
						}
					}
					if(weixinDeptIdStrBuf.length() > 0){
						weixinDeptIdStr = weixinDeptIdStrBuf.substring(0, weixinDeptIdStrBuf.length() - 1);
					}
					
 					//如果其存在的部门存在微信部门主键，则进行同步
					if(!TeeUtility.isNullorEmpty(sysUser.getMobilNo()) && !weixinDeptIdStr.equals("")){
						String position = "";
						if(sysUser.getUserRole() != null){
							position = sysUser.getUserRole().getRoleName();
						}
						
						TeeWeiXinUserModel model = new TeeWeiXinUserModel();
						model.setEmail(TeeStringUtil.getString(sysUser.getEmail()));
						model.setName(sysUser.getUserName());
						model.setUserid("WX_UID_"+sysUser.getUuid());
						model.setPosition(position);
						model.setDeparment(weixinDeptIdStr);
						model.setWeixinid(TeeStringUtil.getString(sysUser.getWeixinNo()));
						String gender = "0";
						String sex = TeeStringUtil.getString(sysUser.getSex());
						if(sex.equals("0")){
							gender = "1";
						}else if(sex.equals("1")){
							gender = "2";
						}
						model.setGender(gender);
						model.setMobile(TeeStringUtil.getString(sysUser.getMobilNo()));
						model = create(model);//先创建用户
						if(model.getErrcode() != null && model.getErrcode().equals("60102")){//用户已存在更新
							try{
								update(model);
								sysUser.setGsbPassword(model.getUserid());
							}catch(Exception ex){
								ex.printStackTrace();
							}
						}else{
							sysUser.setGsbPassword(model.getUserid());
						}
						
						pw.write("<p style=''>同步OA用户["+sysUser.getUserName()+"]已完成。("+(prcsed++)+"/"+total+")</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
						pw.flush();
						
					}else{
						pw.write("<p style='color:#cdcdcd'>同步OA用户["+sysUser.getUserName()+"]失败，请确认该用户至少绑定手机或微信号。("+(prcsed++)+"/"+total+")</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
						pw.flush();
					}
				}
			}
		}else{//删除用户
			//单个删除
			StringBuffer batchUserId = new StringBuffer();
			int prcsed = 1;
			int total = sysUserList.size();
			for(TeePerson sysUser:sysUserList){
				batchUserId.append("\"" + sysUser.getUserId() + "\",");
				try{
					delete(sysUser.getUserId());
					sysUser.setGsbPassword(null);
					
					pw.write("<p style=''>删除用户["+sysUser.getUserName()+"]已完成。("+(prcsed++)+"/"+total+")</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
					pw.flush();
				}catch(Exception ex){
					pw.write("<p style=''>删除用户["+sysUser.getUserName()+"]失败，原因："+ex.getMessage()+"。("+(prcsed++)+"/"+total+")</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
					pw.flush();
				}
			}
			//批量删除
			/*if(batchUserId.length() > 0){
				try{
					batchDelete(batchUserId.substring(0, batchUserId.length() -1));
				}catch(Exception ex){}
			}*/
			
		}
		
		pw.write("<p style='color:green'>===已完成===</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
		pw.flush();
		
	}
	/**
	 * 创建成员
	 * 
	 * @param userid
	 *            员工UserID。对应管理端的帐号，企业内必须唯一。长度为1~64个字符
	 * @param name
	 *            成员名称。长度为1~64个字符
	 * @param department
	 *            成员所属部门id列表 格式： "department": [x, y]
	 * @param position
	 *            职位信息
	 * @param mobile
	 *            手机号码。企业内必须唯一，mobile/weixinid/email三者不能同时为空
	 * @param gender
	 *            性别。gender=0表示男，=1表示女。默认gender=0
	 * @param tel
	 *            办公电话。长度为0~64个字符
	 * @param email
	 *            邮箱。长度为0~64个字符。企业内必须唯一
	 * @param weixinid
	 *            微信号。企业内必须唯一
	 * */
	public static TeeWeiXinUserModel create(TeeWeiXinUserModel model) {
		String postData = "{\"userid\": \"%s\","
					+ "\"name\": \"%s\","
					+"\"department\": ["+model.getDeparment()+"],"
					+ "\"position\": \"%s\","
					+"\"mobile\": \"%s\","
					+"\"gender\": \"%s\","
					+"\"email\": \"%s\","
					+"\"weixinid\": \"%s\"}";
		String data =  String.format(postData, model.getUserid(), model.getName(), model.getPosition()
				, model.getMobile(), model.getGender(), model.getEmail(),model.getWeixinid());
		String CREATE_URL_TEMP = CREATE_URL.replace("ACCESS_TOKEN" , AccessToken.getAccessTokenInstance().getToken());
		JSONObject obj = WeixinUtil.httpRequest(CREATE_URL_TEMP, "POST", data);
		String errcode = "";
		if(obj != null){
			 errcode = TeeStringUtil.getString(obj.get("errcode"));//60102 userid 已存在
			 if(errcode.equals("0")){
				 model.setErrcode("0");//创建成功
			 }else if (errcode.equals("60102")){
				 model.setErrcode(errcode);//用户已存在
			 }
		}
		return  model;
	}

	/**
	 * 更新成员
	 * 
	 * @param userid
	 *            员工UserID。对应管理端的帐号，企业内必须唯一。长度为1~64个字符
	 * @param name
	 *            成员名称。长度为1~64个字符
	 * @param department
	 *            成员所属部门id列表 格式： "department": [x]
	 * @param position
	 *            职位信息
	 * @param mobile
	 *            手机号码。企业内必须唯一，mobile/weixinid/email三者不能同时为空
	 * @param gender
	 *            性别。gender=0表示男，=1表示女。默认gender=0
	 * @param tel
	 *            办公电话。长度为0~64个字符
	 * @param email
	 *            邮箱。长度为0~64个字符。企业内必须唯一
	 * @param weixinid
	 *            微信号。企业内必须唯一
	 * @param enable
	 *            启用/禁用成员。1表示启用成员，0表示禁用成员
	 * */
	public static String update(TeeWeiXinUserModel model) {
		String postData = "{\"userid\": \"%s\","
				+ "\"name\": \"%s\","
				+"\"department\": ["+model.getDeparment()+"],"
				+ "\"position\": \"%s\","
				+"\"mobile\": \"%s\","
				+"\"gender\": \"%s\","
				+"\"email\": \"%s\","
				+"\"weixinid\": \"%s\"}";
		String data =  String.format(postData, model.getUserid(), model.getName(), model.getPosition()
				, model.getMobile(), model.getGender(), model.getEmail(),model.getWeixinid());
		String UPDATE_URL_TEMP = UPDATA_URL.replace("ACCESS_TOKEN" , AccessToken.getAccessTokenInstance().getToken());
		JSONObject obj = WeixinUtil.httpRequest(UPDATE_URL_TEMP, "POST", data);
		String errcode = "";
		if(obj != null){
			 errcode = TeeStringUtil.getString(obj.get("errcode"));
		}
		return  errcode;
	}
	
	
	/**
	 * 简单更新成员
	 * 
	 * @param userid
	 *            员工UserID。对应管理端的帐号，企业内必须唯一。长度为1~64个字符
	 * @param name
	 *            成员名称。长度为1~64个字符
	 * @param department
	 *            成员所属部门id列表 格式： "department": [x]
	 */
	public static String simpleUpdate(String userid, String name,  String postDept) {
		String postData = "{\"userid\":\"%s\",\"name\": \"%s\",\"department\": [1]}";
		String data =  String.format(postData, userid, name,postDept);
		
		String GET_GROUP_URL_TEMP = UPDATA_URL.replace("ACCESS_TOKEN" , AccessToken.getAccessTokenInstance().getToken());
		JSONObject obj = WeixinUtil.httpRequest(GET_GROUP_URL_TEMP, "POST", data);
		String errcode = "";
		if(obj != null){
			 errcode = TeeStringUtil.getString(obj.get("errcode"));
		}
		return  errcode;
	}

	/**
	 * 单个删除成员
	 * 
	 * @param userid
	 *            员工UserID。对应管理端的帐号
	 * */
	public static String delete(String userid) {
		String delete_url = DELETE_URL.replace("ID", userid).replace("ACCESS_TOKEN" , AccessToken.getAccessTokenInstance().getToken());
		JSONObject obj = WeixinUtil.httpRequest(delete_url, "GET", null);
		String errcode = "";
		if(obj != null){
			 errcode = TeeStringUtil.getString(obj.get("errcode"));
		}
		return errcode;
	}
	
	
	/**
	 * 批量删除成员
	 * 如果其中一个人员删除出问题，其他全部不能删除，不介意用
	 * @param userid
	 *            员工UserID。对应管理端的帐号
	 * */
	public static String batchDelete(String userids) {
		String delete_url = BATCH_DELETE_URL.replace("ACCESS_TOKEN" , AccessToken.getAccessTokenInstance().getToken());
		String data =  "{\"useridlist\":[" + userids + "]}";
		JSONObject obj = WeixinUtil.httpRequest(delete_url, "POST", data);
		String errcode = "";
		if(obj != null){
			 errcode = TeeStringUtil.getString(obj.get("errcode"));
		}
		return errcode;
	}
	
	public List<TeeWeixinDeptModel> getDepartmentList(String id){
		String delete_url = GET_DEPT_LIST_URL.replace("ACCESS_TOKEN" , AccessToken.getAccessTokenInstance().getToken()).replace("ID", id);
		JSONObject obj = WeixinUtil.httpRequest(delete_url, "GET", null);
		List<TeeWeixinDeptModel> list = new ArrayList();
		if(obj.has("department")){
			JSONArray arr = obj.getJSONArray("department");
			TeeWeixinDeptModel deptModel = null;
			for(int i=0;i<arr.size();i++){
				deptModel = new TeeWeixinDeptModel();
				deptModel.setId(arr.getJSONObject(i).getInt("id"));
				deptModel.setDeptName(arr.getJSONObject(i).getString("name"));
				deptModel.setParentid(arr.getJSONObject(i).getInt("parentid"));
				
				list.add(deptModel);
			}
		}
		
		
		return list;
	}

	/**
	 * 获取成员
	 * 
	 * @param userid
	 *            员工UserID。对应管理端的帐号
	 * */
	public static String getPerson(String userid) {
		String getperson_url = GET_PERSON_URL.replace("ID", userid);
		return getperson_url;
	}

	/**
	 * 获取部门成员
	 * 
	 * @param department_id
	 *            获取的部门id
	 * @param fetch_child
	 *            1/0：是否递归获取子部门下面的成员 （可选）
	 * @param status
	 *            0获取全部员工，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加 （可选）
	 * */
	public static String getGroup(String department_id) {
		String getgroup_url = GET_GROUP_URL.replace("ID", department_id);
		return getgroup_url;
	}

	public List<TeeWeiXinUserModel> getPersonsByDept(String id) throws NumberFormatException{
		String accessToken = AccessToken.getAccessTokenInstance().getToken();
		JSONArray arr = getPersonByDeptIdAndFetchFhildAndStatus(accessToken,id,"0","0");
		TeePerson person = null;
		List<TeeWeiXinUserModel> userList = new ArrayList();
		for(int i=0;i<arr.size();i++){
			TeeWeiXinUserModel model = new TeeWeiXinUserModel();
			userList.add(model);
			model.setUserid(arr.getJSONObject(i).getString("userid"));
			model.setName(arr.getJSONObject(i).getString("name"));
			person = personService.getPersonByWXUserId(model.getUserid());
			if(person==null){
				model.setMobile("false");
				continue;
			}
			model.setMobile("true");
			model.setEmail(person.getUserName());
			model.setErrcode(person.getUserId());
			
		}
		return userList;
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
	
	
	public void bindUser(String dingUser,String oaUser){
		TeePerson person = personService.selectByUuid(Integer.parseInt(oaUser));
		if(person!=null){
			person.setGsbPassword(dingUser);
		}
	}
	
	public void cancelBindUser(String dingUser,String oaUser){
		TeePerson person = personService.getPersonByUserId(oaUser);
		if(person!=null){
			person.setGsbPassword(null);
		}
	}
	
}
