<%@page import="java.net.URLEncoder"%>
<%@page import="com.tianee.oa.core.org.model.TeePersonModel"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.tianee.oa.core.org.service.TeePersonService"%>
<%@page import="com.tianee.oa.core.org.bean.TeePerson"%>
<%@page import="com.tianee.webframe.httpmodel.TeeJson"%>
<%@page import="com.tianee.webframe.util.global.TeeBeanFactory"%>
<%@page import="com.tianee.oa.core.system.service.TeeSystemService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String userName = request.getParameter("userName");
	String name = request.getParameter("name");
	TeeSystemService systemService = (TeeSystemService)TeeBeanFactory.getBean("teeSystemService");
	TeePersonService personService = (TeePersonService)TeeBeanFactory.getBean("teePersonService");
	TeeJson json = systemService.doLoginInByUserIdService(request, response);
	
	if(json.isRtState()){//如果成功
		TeePerson person = personService.getPersonByUserId(userName);
		response.sendRedirect("/system/frame/"+person.getTheme()+"/index.jsp");
	}else{//如果失败
		JSONObject jsonObject = JSONObject.fromObject(json.getRtData());
		int code = jsonObject.getInt("code");
		if(code==0){//用户不存在
			TeePersonModel personModule = new TeePersonModel();
			
			personModule.setUserName(userName);
			personModule.setUserId(userName);
			personModule.setTheme("4");//设置默认主题
			personModule.setDeptId("1");//默认是系统管理部下面
			personModule.setUserRoleStr("2");//默认员工角色
			personModule.setMenuGroupsStr("2");//默认员工菜单组
			personService.addOrUpdate(personModule,request);
			
			response.sendRedirect("singleLogin4Prcs.jsp?userName="+URLEncoder.encode(userName, "UTF-8")+"&name="+URLEncoder.encode(name, "UTF-8"));
		}else{
			out.println(json.getRtMsg());
		}
	}
	
%>