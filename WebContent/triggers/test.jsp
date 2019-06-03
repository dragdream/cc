<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="sun.misc.BASE64Decoder"%>
<%@page import="java.util.Map"%>
<%@page import="com.tianee.webframe.util.str.TeeJsonUtil"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%
	/**
	** 获取参数
	**/
	String runId = request.getParameter("runId");//流程id
	String runName = request.getParameter("runName");//流程名称
	String oper = request.getParameter("oper");//操作类型：SAVE、TURNNEXT
	String datas = new String(new BASE64Decoder().decodeBuffer(request.getParameter("datas")));//流程数据
	String requestData = "";//转交参数数据
	if(request.getParameter("requestData")!=null){
		requestData = new String(new BASE64Decoder().decodeBuffer(request.getParameter("requestData")));
	}
	String nextNodes = request.getParameter("nextNodes");//转交下一步的节点名称，多个用逗号分隔
	String curNode = request.getParameter("curNode");//当前节点的名称
	
	
	try{
		if("SAVE".equals(oper)){//流程保存后触发
			Map<String,String> dataMap = TeeJsonUtil.JsonStr2Map(datas);
			
			out.print("{\"status\":0,\"msg\":\"\"}");
		
		}else if("TURNNEXT".equals(oper)){//流程转交后触发
			out.print("{\"status\":0,\"msg\":\"\"}");
		}else{
			out.print("{\"status\":0,\"msg\":\"\"}");
		}
	}catch(Exception ex){
		out.print("{\"status\":1,\"msg\":\""+ex.getMessage()+"\"}");
	}
	
%>