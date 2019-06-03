package com.tianee.thirdparty.sphy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jacob.req.JacobRequest;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.thirdparty.sphy.bean.TeeSphy;
import com.tianee.thirdparty.sphy.model.TeeSphyModel;
import com.tianee.thirdparty.sphy.service.TeeSphyService;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("teeSphyController")
public class TeeSphyController {
	@Autowired
	private TeeSphyService teeSphyService;
	
	/**
	 * 获取所有当前人创建的会议
	 * */
	@ResponseBody
	@RequestMapping("/myCreateSphyList")
	public TeeEasyuiDataGridJson myCreateSphyList(TeeDataGridModel m,HttpServletRequest request){
		return teeSphyService.myCreateSphyList(m,request);
	}
	
	/**
	 * 获取当前登录人所要参加的会议
	 * */
	@ResponseBody
	@RequestMapping("/mySphyList")
	public TeeEasyuiDataGridJson mySphyList(TeeDataGridModel m,HttpServletRequest request){
		return teeSphyService.mySphyList(m,request);
	}
	/**
	 * 添加会议
	 * */
	@ResponseBody
	@RequestMapping("/addSphy")
	public TeeJson addSphy(TeeSphyModel model,HttpServletRequest request){
		TeeJson json=new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		model.setCreateUser(person.getUuid());
		if(model.getSid()>0){//修改
			int meeting=updateMeeting(model);
			if(meeting>0){
				//删除本次会议的所有参会记录
				model.setSphyId(meeting);
				teeSphyService.deleteSphyBody(model.getSid());
				json=teeSphyService.addSphy(model,person);
				model.setHyId(model.getSid());
				addQXMeetingRole(model);//授权
			}
		}else{//添加
			int meeting = addMeeting(model);//添加会议
			if(meeting>0){
				model.setSphyId(meeting);
				json=teeSphyService.addSphy(model,person);
				model.setSid(TeeStringUtil.getInteger(json.getRtData(), 0));
				addQXMeetingRole(model);//授权
			}
		}
		return json;
	}
	/**
	 * 修改会议
	 * */
	@ResponseBody
	@RequestMapping("/updateSphy")
	public TeeJson updateSphy(){
		TeeJson json=new TeeJson();
		return json;
	}
	/**
	 * 删除会议
	 * */
	@ResponseBody
	@RequestMapping("/deleteSphy")
	public TeeJson deleteSphy(HttpServletRequest request){
		TeeJson json=new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);//OA系统会议id
		//int sphyId = TeeStringUtil.getInteger(request.getParameter("sphyId"),0);//视频会议系统会议id
		TeeSphy sp=teeSphyService.getSphId(sid);
		int sphyId=sp.getSphyId();
		if(sphyId>0){
			int flag=deleteMeeting(sphyId);//删除视频会议（视频系统）
			if(flag>0){
				json=teeSphyService.deleteSphy(sid);//删除视频会议（OA系统）
				//删除本次会议的所有参会记录
				teeSphyService.deleteSphyBody(sid);
			}
		}
		return json;
	}
	/**
	 * 关于人员操作的接口
	 * */
	@ResponseBody
	@RequestMapping("/sync")
	public Object syncUser(HttpServletRequest request){
		//{"userName":"ceshi99","userId":"ceshi99","uuid":"247","deptId":36,"roleId":1,"email":"","phone":"","operation":"0"}
		Map ret = new HashMap();
		String json = request.getParameter("json");
		JSONObject fromObject = JSONObject.fromObject(json);
		Map map=(Map)fromObject;
		String operation=TeeStringUtil.getString(map.get("operation"));
		if("0".equals(operation)){//添加用户
			boolean addPerson = addPerson(map);
			ret.put("status", addPerson);
		}else if("1".equals(operation)){//修改用户
			boolean updatePerson = updatePerson(map);
			ret.put("status", updatePerson);
		}else if("2".equals(operation)){//删除用户
			boolean deletePerson = deletePerson(map);
			ret.put("status", deletePerson);
		}
	    return ret;
	}
	/**
	 * 添加用户
	 * */
	public boolean addPerson(Map map){
		boolean flag=false;
		try {
			String sphyAddress = TeeSysProps.getProps().getProperty("SPHY_ADDRESS");
		    String endpoint = "http://"+sphyAddress+"/acenter/services/iactiveService?wsdl";  
		    // 直接引用远程的wsdl文件  
		    // 以下都是套路  
		    Service service = new Service();  
		    Call call = (Call) service.createCall();  
		    call.setTargetEndpointAddress(endpoint);  
		    QName qn = new QName("http://service.iactive.com.cn", "userOperate");
		    call.setOperationName(qn);// WSDL里面描述的接口名称  
		   
		    call.addParameter("deptXmlInfo",  
		            org.apache.axis.encoding.XMLType.XSD_STRING,  
		            javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_UID",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_PASS",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    

		    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_INT);// 设置返回类型  
		    //{"userName":"ceshi99","userId":"ceshi99","uuid":"247","deptId":36,"roleId":1,
		    //"email":"","phone":"","operation":"0"}
		    String userName=TeeStringUtil.getString(map.get("userName"));//用户姓名
		    String userId=TeeStringUtil.getString(map.get("userId"));//用户登录名
		    String email=TeeStringUtil.getString(map.get("email"));//用户电子邮件
		    String phone=TeeStringUtil.getString(map.get("phone"));//用户电话
		    String deptXmlInfo="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		    deptXmlInfo+="<USER>";
		    deptXmlInfo+="<OPT_METHOD>CREATE</OPT_METHOD>";
		    deptXmlInfo+="<IA_USERNAME>"+userId+"</IA_USERNAME>";//用户名
		    deptXmlInfo+="<IA_USER_SERIAL>"+userId+"</IA_USER_SERIAL>";//用户序列，唯一标识
		    deptXmlInfo+="<IA_U_TRUENAME>"+userName+"</IA_U_TRUENAME>";//真实姓名
		    deptXmlInfo+="<IA_U_PASSWORD>oaop2014</IA_U_PASSWORD>";//密码
		    deptXmlInfo+="<IA_U_MAIL_ADDR>"+email+"</IA_U_MAIL_ADDR>";//邮件
		    deptXmlInfo+="<IA_U_TELEPHONE>"+phone+"</IA_U_TELEPHONE>";//电话
		    deptXmlInfo+="<IA_DEPT_SERIAL>1</IA_DEPT_SERIAL>";//部门
		    deptXmlInfo+="</USER>";
		    String AUTH_UID = "admin"; 
		    String AUTH_PASS="iactive";
		    //String deptXmlInfo,String AUTH_UID,String AUTH_PASS
		    int result = (int) call.invoke(new Object[] {deptXmlInfo,AUTH_UID,AUTH_PASS});  
		    if(result==1){
		    	flag=true;
		    }
		    // 给方法传递参数，并且调用方法  
		     //System.out.println("result is " + result);  
		 } catch (Exception e) {  
		      System.err.println(e.toString());  
		 }
		return flag;
	}
	/**
	 * 修改人员
	 * */
	public boolean updatePerson(Map map){
		boolean flag=false;
		try {  
			String sphyAddress = TeeSysProps.getProps().getProperty("SPHY_ADDRESS");
		    String endpoint = "http://"+sphyAddress+"/acenter/services/iactiveService?wsdl";  
		    // 直接引用远程的wsdl文件  
		    // 以下都是套路  
		    Service service = new Service();  
		    Call call = (Call) service.createCall();  
		    call.setTargetEndpointAddress(endpoint);  
		    QName qn = new QName("http://service.iactive.com.cn", "userOperate");
		    call.setOperationName(qn);// WSDL里面描述的接口名称  
		   
		    call.addParameter("deptXmlInfo",  
		            org.apache.axis.encoding.XMLType.XSD_STRING,  
		            javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_UID",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_PASS",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    

		    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_INT);// 设置返回类型  
		    String userName=TeeStringUtil.getString(map.get("userName"));//用户姓名
		    String userId=TeeStringUtil.getString(map.get("userId"));//用户登录名
		    String email=TeeStringUtil.getString(map.get("email"));//用户电子邮件
		    String phone=TeeStringUtil.getString(map.get("phone"));//用户电话
		    String deptXmlInfo="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		    deptXmlInfo+="<USER>";
		    deptXmlInfo+="<OPT_METHOD>UPDATE</OPT_METHOD>";
		    deptXmlInfo+="<IA_USER_SERIAL>"+userId+"</IA_USER_SERIAL>";//用户序列，唯一标识
		    deptXmlInfo+="<IA_U_TRUENAME>"+userName+"</IA_U_TRUENAME>";//真实姓名
		    deptXmlInfo+="<IA_U_NICKNAME>"+userName+"</IA_U_NICKNAME>";
		    deptXmlInfo+="<IA_U_PASSWORD>oaop2014</IA_U_PASSWORD>";//密码
		    deptXmlInfo+="<IA_U_MAIL_ADDR>"+email+"</IA_U_MAIL_ADDR>";//电子邮件
		    deptXmlInfo+="<IA_U_TELEPHONE>"+phone+"</IA_U_TELEPHONE>";//用户的电话
		    deptXmlInfo+="<IA_DEPT_SERIAL>1</IA_DEPT_SERIAL>";//部门
		    deptXmlInfo+="</USER>";
		    String AUTH_UID = "admin"; 
		    String AUTH_PASS="iactive";
		    //String deptXmlInfo,String AUTH_UID,String AUTH_PASS
		    int result = (int) call.invoke(new Object[] {deptXmlInfo,AUTH_UID,AUTH_PASS});  
		    if(result==1){
		    	flag=true;
		    }
		    // 给方法传递参数，并且调用方法  
		    // System.out.println("result is " + result);  
		 } catch (Exception e) {  
		      System.err.println(e.toString());  
		 }
		return flag;
	}
	/**
	 * 删除人员
	 * */
	public boolean deletePerson(Map map){
		boolean flag=false;
		try {  
			String sphyAddress = TeeSysProps.getProps().getProperty("SPHY_ADDRESS");
		    String endpoint = "http://"+sphyAddress+"/acenter/services/iactiveService?wsdl";  
		    // 直接引用远程的wsdl文件  
		    // 以下都是套路  
		    Service service = new Service();  
		    Call call = (Call) service.createCall();  
		    call.setTargetEndpointAddress(endpoint);  
		    QName qn = new QName("http://service.iactive.com.cn", "userOperate");
		    call.setOperationName(qn);// WSDL里面描述的接口名称  
		   
		    call.addParameter("deptXmlInfo",  
		            org.apache.axis.encoding.XMLType.XSD_STRING,  
		            javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_UID",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_PASS",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    

		    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_INT);// 设置返回类型  
		    String userId=TeeStringUtil.getString(map.get("userId"));
		    userId=userId.substring(0, userId.length()-1);
		    String deptXmlInfo="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		    deptXmlInfo+="<USER>";
		    deptXmlInfo+="<OPT_METHOD>BATCH_DELETE</OPT_METHOD>";
		    deptXmlInfo+="<IA_USER_SERIAL>"+userId+"</IA_USER_SERIAL>";//唯一标识
		    deptXmlInfo+="</USER>";
		    String AUTH_UID = "admin"; 
		    String AUTH_PASS="iactive";
		    //String deptXmlInfo,String AUTH_UID,String AUTH_PASS
		    int result = (int) call.invoke(new Object[] {deptXmlInfo,AUTH_UID,AUTH_PASS});  
		    if(result==1){
		    	flag=true;
		    }
		    // 给方法传递参数，并且调用方法  
		    // System.out.println("result is " + result);  
		 } catch (Exception e) {  
		      System.err.println(e.toString());  
		 }
		return flag;
	}
	
	/**
	 * 添加会议
	 * */
	public int addMeeting(TeeSphyModel model){
		int result=0;
		try {  
			String sphyAddress = TeeSysProps.getProps().getProperty("SPHY_ADDRESS");
		    String endpoint = "http://"+sphyAddress+"/acenter/services/iactiveMeetingService?wsdl";  
		    // 直接引用远程的wsdl文件  
		    // 以下都是套路  
		    Service service = new Service();  
		    Call call = (Call) service.createCall();  
		    call.setTargetEndpointAddress(endpoint);  
		    QName qn = new QName("http://service.meeting.acenter.iactive.com.cn", "roomOperate");
		    call.setOperationName(qn);// WSDL里面描述的接口名称  
		   
		    call.addParameter("deptXmlInfo",  
		            org.apache.axis.encoding.XMLType.XSD_STRING,  
		            javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_UID",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_PASS",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    

		    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_INT);// 设置返回类型  
		    
		    String deptXmlInfo="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		    deptXmlInfo+="<ROOM>";
		    deptXmlInfo+="<OPT_METHOD>CREATE</OPT_METHOD>";
		    deptXmlInfo+="<IA_ROOMNAME>"+model.getRoomName()+"</IA_ROOMNAME>";//会议名称
		    deptXmlInfo+="<IA_R_APP>"+model.getAppType()+"</IA_R_APP>";//会议类型
		    deptXmlInfo+="<IA_R_REMARK>"+model.getRemark()+"</IA_R_REMARK>";//主题
		    deptXmlInfo+="<IA_R_PWD_NORMAL>"+model.getPwdNormal()+"</IA_R_PWD_NORMAL>";//会议密码
		    deptXmlInfo+="<IA_R_IS_PUBLIC>"+model.getIsPublic()+"</IA_R_IS_PUBLIC>";//是否公开
		    deptXmlInfo+="<IA_R_BANDWIDTH>"+model.getBandwidth()+"</IA_R_BANDWIDTH>";//带宽
		    deptXmlInfo+="<IA_R_MAX_USER>"+model.getMaxUser()+"</IA_R_MAX_USER>";//最大用户数
		    deptXmlInfo+="<IA_R_ANONYMOUS>"+model.getAnonymous()+"</IA_R_ANONYMOUS>";//是否允许匿名
		    deptXmlInfo+="<IA_ROOM_TYPE>"+model.getRoomType()+"</IA_ROOM_TYPE>";//是否MCU合屏
		    deptXmlInfo+="<IA_START_VALID_TIME>"+model.getStartTime()+"</IA_START_VALID_TIME>";//开始时间
		    deptXmlInfo+="<IA_END_VALID_TIME>"+model.getEndTime()+"</IA_END_VALID_TIME>";//结束时间
		    deptXmlInfo+="<IA_M_AGENDA_CONTENT>"+model.getAgendaContent()+"</IA_M_AGENDA_CONTENT>";//会议议程
		    deptXmlInfo+="<IA_CREATID>"+model.getCreateUser()+"</IA_CREATID>";//创建人id
//		    deptXmlInfo+="<IA_DEPT_SERIAL>1</IA_DEPT_SERIAL>";//部门
		    deptXmlInfo+="</ROOM>";
		    String AUTH_UID = "admin"; 
		    String AUTH_PASS="iactive";
		    //String deptXmlInfo,String AUTH_UID,String AUTH_PASS
		    result= (int) call.invoke(new Object[] {deptXmlInfo,AUTH_UID,AUTH_PASS});  
		    // 给方法传递参数，并且调用方法  
		     //System.out.println("result is " + result2);  
		 } catch (Exception e) {  
		      System.err.println(e.toString());  
		 }
		return result;
	}
	
	/**
	 * 授权会议角色
	 * */
	public void addQXMeetingRole(TeeSphyModel model){
		int result=0;
		if(model!=null){
			try {  
				String sphyAddress = TeeSysProps.getProps().getProperty("SPHY_ADDRESS");
			    String endpoint = "http://"+sphyAddress+"/acenter/services/iactiveMeetingService?wsdl";  
			    // 直接引用远程的wsdl文件  
			    // 以下都是套路  
			    Service service = new Service();  
			    Call call = (Call) service.createCall();  
			    call.setTargetEndpointAddress(endpoint);  
			    QName qn = new QName("http://service.meeting.acenter.iactive.com.cn", "setMeetingRole");
			    call.setOperationName(qn);// WSDL里面描述的接口名称  
			   
			    call.addParameter("roomId",  
			            org.apache.axis.encoding.XMLType.XSD_INT,  
			            javax.xml.rpc.ParameterMode.IN);// 接口的参数  
			    
			    call.addParameter("userId",  
					    org.apache.axis.encoding.XMLType.XSD_LONG,  
					    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
			    
			    call.addParameter("userSerial",  
					    org.apache.axis.encoding.XMLType.XSD_STRING,  
					    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
			    
			    call.addParameter("userType",  
					    org.apache.axis.encoding.XMLType.XSD_INT,  
					    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
			    
			    call.addParameter("AUTH_UID",  
					    org.apache.axis.encoding.XMLType.XSD_STRING,  
					    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
			    
			    call.addParameter("AUTH_PASS",  
					    org.apache.axis.encoding.XMLType.XSD_STRING,  
					    javax.xml.rpc.ParameterMode.IN);// 接口的参数  

			    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_INT);// 设置返回类型  
			    
			    String AUTH_UID = "admin"; 
			    String AUTH_PASS="iactive";
			    //String deptXmlInfo,String AUTH_UID,String AUTH_PASS
			    //主持人 
				String housts=model.getHosts();
				if(housts!=null && !"".equals(housts)){
					List<TeePerson> list=teeSphyService.findUserIdByUuids(housts);
					if(list!=null && list.size()>0){
						for(TeePerson p:list){
							result = (int) call.invoke(new Object[] {model.getSphyId(),0,p.getUserId(),1,AUTH_UID,AUTH_PASS});
						    if(result>0){
						    	teeSphyService.addSphyBody(model.getSid(),p,1);
						    }
						}
					}
				}
				//助理
				String assistants=model.getAssistant();
				if(assistants!=null && !"".equals(assistants)){
					List<TeePerson> list=teeSphyService.findUserIdByUuids(assistants);
					if(list!=null && list.size()>0){
						for(TeePerson p:list){
							result = (int) call.invoke(new Object[] {model.getSphyId(),0,p.getUserId(),3,AUTH_UID,AUTH_PASS});
							if(result>0){
						    	teeSphyService.addSphyBody(model.getSid(),p,3);
						    }
						}
					}
				}
				//普通用户
				String puUsers=model.getPuUser();
				if(puUsers!=null && !"".equals(puUsers)){
					List<TeePerson> list=teeSphyService.findUserIdByUuids(puUsers);
					if(list!=null && list.size()>0){
						for(TeePerson p:list){
							result = (int) call.invoke(new Object[] {model.getSphyId(),0,p.getUserId(),2,AUTH_UID,AUTH_PASS});
							if(result>0){
						    	teeSphyService.addSphyBody(model.getSid(),p,2);
						    }
						}
					}
				}
			    // 给方法传递参数，并且调用方法  
			    // System.out.println("result is " + result);  
			 } catch (Exception e) {  
			      System.err.println(e.toString());  
			 }
			
		}
	}
	
	/**
	 * 修改会议
	 * */
	public int updateMeeting(TeeSphyModel model){
		int result=0;
		try {  
			String sphyAddress = TeeSysProps.getProps().getProperty("SPHY_ADDRESS");
		    String endpoint = "http://"+sphyAddress+"/acenter/services/iactiveMeetingService?wsdl";  
		    // 直接引用远程的wsdl文件  
		    // 以下都是套路  
		    Service service = new Service();  
		    Call call = (Call) service.createCall();  
		    call.setTargetEndpointAddress(endpoint);  
		    QName qn = new QName("http://service.meeting.acenter.iactive.com.cn", "roomOperate");
		    call.setOperationName(qn);// WSDL里面描述的接口名称  
		   
		    call.addParameter("deptXmlInfo",  
		            org.apache.axis.encoding.XMLType.XSD_STRING,  
		            javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_UID",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_PASS",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    

		    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_INT);// 设置返回类型  
		    
		    String deptXmlInfo="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		    deptXmlInfo+="<ROOM>";
		    deptXmlInfo+="<OPT_METHOD>UPDATE</OPT_METHOD>";
		    deptXmlInfo+="<IA_ROOMID>"+model.getSphyId()+"</IA_ROOMID>";//会议id
		    deptXmlInfo+="<IA_ROOMNAME>"+model.getRoomName()+"</IA_ROOMNAME>";//会议名
		    deptXmlInfo+="<IA_R_APP>"+model.getAppType()+"</IA_R_APP>";//应用类型
		    deptXmlInfo+="<IA_R_REMARK>"+model.getRemark()+"</IA_R_REMARK>";//主题
		    deptXmlInfo+="<IA_R_IS_PUBLIC>"+model.getIsPublic()+"</IA_R_IS_PUBLIC>";//是否公开
		    deptXmlInfo+="<IA_R_BANDWIDTH>"+model.getBandwidth()+"</IA_R_BANDWIDTH>";//带宽
		    deptXmlInfo+="<IA_R_MAX_USER>"+model.getMaxUser()+"</IA_R_MAX_USER>";//最大用户数
		    deptXmlInfo+="<IA_R_ANONYMOUS>"+model.getAnonymous()+"</IA_R_ANONYMOUS>";//是否允许匿名
		    deptXmlInfo+="<IA_ROOM_TYPE>"+model.getRoomType()+"</IA_ROOM_TYPE>";//是否MCU合屏
		    deptXmlInfo+="<IA_START_VALID_TIME>"+model.getStartTime()+"</IA_START_VALID_TIME>";//开始时间
		    deptXmlInfo+="<IA_END_VALID_TIME>"+model.getEndTime()+"</IA_END_VALID_TIME>";//结束时间
		    deptXmlInfo+="<IA_M_AGENDA_CONTENT>"+model.getAgendaContent()+"</IA_M_AGENDA_CONTENT>";//会议议程
		    deptXmlInfo+="</ROOM>";
		    String AUTH_UID = "admin"; 
		    String AUTH_PASS="iactive";
		    //String deptXmlInfo,String AUTH_UID,String AUTH_PASS
		    result = (int) call.invoke(new Object[] {deptXmlInfo,AUTH_UID,AUTH_PASS});  
		    // 给方法传递参数，并且调用方法  
		     //System.out.println("result is " + result);  
		 } catch (Exception e) {  
		      System.err.println(e.toString());  
		 }
		return result;
	}
	/**
	 * 删除会议
	 * */
	public int deleteMeeting(int sphyId){
		int result=0;
		try {  
			String sphyAddress = TeeSysProps.getProps().getProperty("SPHY_ADDRESS");
		    String endpoint = "http://"+sphyAddress+"/acenter/services/iactiveMeetingService?wsdl";  
		    // 直接引用远程的wsdl文件  
		    // 以下都是套路  
		    Service service = new Service();  
		    Call call = (Call) service.createCall();  
		    call.setTargetEndpointAddress(endpoint);  
		    QName qn = new QName("http://service.meeting.acenter.iactive.com.cn", "roomOperate");
		    call.setOperationName(qn);// WSDL里面描述的接口名称  
		   
		    call.addParameter("deptXmlInfo",  
		            org.apache.axis.encoding.XMLType.XSD_STRING,  
		            javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_UID",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    
		    call.addParameter("AUTH_PASS",  
				    org.apache.axis.encoding.XMLType.XSD_STRING,  
				    javax.xml.rpc.ParameterMode.IN);// 接口的参数  
		    

		    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_INT);// 设置返回类型  
		    
		    String deptXmlInfo="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		    deptXmlInfo+="<ROOM>";
		    deptXmlInfo+="<OPT_METHOD>DELETE</OPT_METHOD>";
		    deptXmlInfo+="<IA_ROOMID>"+sphyId+"</IA_ROOMID>";
		    deptXmlInfo+="</ROOM>";
		    String AUTH_UID = "admin"; 
		    String AUTH_PASS="iactive";
		    //String deptXmlInfo,String AUTH_UID,String AUTH_PASS
		    result= (int) call.invoke(new Object[] {deptXmlInfo,AUTH_UID,AUTH_PASS});  
		    // 给方法传递参数，并且调用方法  
		     System.out.println("result is " + result);  
		 } catch (Exception e) {  
		      System.err.println(e.toString());  
		 }
		return result;
	}
	/**
	 * 获取会议详情
	 * */
    @ResponseBody
    @RequestMapping("findSphyById")
	public TeeJson findSphyById(HttpServletRequest request){
		return teeSphyService.findSphyById(request);
	}
    /**
     * 获取参会记录
     * */
    @ResponseBody
    @RequestMapping("/cjSphyList")
    public TeeJson cjSphyList(HttpServletRequest request){
    	return teeSphyService.cjSphyList(request);
    }
    
    /**
     * 修改参会状态
     * */
    @ResponseBody
    @RequestMapping("/updateCjHyStatus")
    public TeeJson updateCjHyStatus(HttpServletRequest request){
    	return teeSphyService.updateCjHyStatus(request);
    }
    
    /**
     * 同步数据
     * @throws IOException 
     * */
    @ResponseBody
    @RequestMapping("/tongBuNumber")
    public void tongBuNumber(HttpServletRequest request,HttpServletResponse response) throws IOException{
    	List<Map> listMap = teeSphyService.tongBuNumber();
		PrintWriter pw = response.getWriter();
    	getRegisterRecordInfo(listMap,pw);
    }
    
	public void getRegisterRecordInfo(List<Map> listMap,PrintWriter pw){
		
		if(pw!=null){
//			pw.write("<p>开始刷新用户数据</p>");
//			pw.flush();
			pw.println("<script>");
			pw.println("document.write('<div>开始同步用户数据</div>');");
			pw.println("</script>");
			pw.flush();
		}
		int count=0;
		for (int i=0;i<listMap.size();i++) {
			Map map = listMap.get(i);
			String userName=TeeStringUtil.getString(map.get("userName"));//用户姓名
			boolean b = addPerson(map);
			if(!b){
				b=updatePerson(map);
			}
			count++;
			if(pw!=null && b){
//				pw.write("<p>已同步“"+userName+"”的用户数据，"+count+"/"+listMap.size()+"</p>");
//				pw.flush();
				pw.println("<script>");
				pw.println("document.write('<div>已同步“"+userName+"”的用户数据，"+count+"/"+listMap.size()+"</div>');");
				pw.println("</script>");
				pw.flush();
			}
		} 
		if(pw!=null){
//			pw.write("<p>刷新完毕！</p><br/>");
//			pw.flush();
			pw.println("<script>");
			pw.println("document.write('<div>同步完毕！</div>');");
			pw.println("</script>");
			pw.flush();
		}
		
	}
}

