<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    int runId=TeeStringUtil.getInteger(request.getParameter("runId"),0);
%>
<html>
<head>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>恢复执行</title>
<script type="text/javascript">
var  runId=<%=runId %>;
//初始化
var datagrid ;
function doInit(){
	//根据流程获取flowRunPrcs
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/workQuery/getRecoverFlowRunPrcsList.action?runId="+runId,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:false,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			//{field:'sid',checkbox:true,title:'ID',width:200},
			{field:'prcsId',title:'步骤序号',width:80},
			{field:'prcsName',title:'步骤名称',width:200},
			{field:'prcsUserName',title:'办理人',width:80},
			{field:'flag',title:'办理状态',width:80,formatter:function(value,rowData,rowIndex){
			   var flag=rowData.flag;// 1-未接收  2-办理中  3-转交下一步  4-已办结  5-预设步骤
			   if(flag==1){
				   return "未接收";
			   }else if(flag==2){
				   return "办理中";
			   }else if(flag==3){
				   return "转交下一步";
			   }else if(flag==4){
				   return "已办结";
			   }else if(flag==5){
				   return "预设步骤";
			   }
			}},
			{field:'createTimeDesc',title:'创建时间',width:120},
			{field:'opt_',title:'操作',width:80,formatter:function(value,rowData,rowIndex){
			   return "<a href=\"#\" onclick=\"recover("+rowData.sid+")\">恢复</a>";
			}}
		]]
	});
}

//恢复
function recover(frpSid){
	  top.$.MsgBox.Confirm ("提示", "是否确认将该流程恢复到所选步骤？", function(){
		  var url=contextPath+"/workQuery/recover.action";
			var json=tools.requestJsonRs(url,{frpSid:frpSid,runId:runId});
			if(json.rtState){
				$.MsgBox.Alert_auto("恢复成功！",function(){
					//关闭bsWindow
					parent.$("#win_ico").click();
				});
			}else{
				$.MsgBox.Alert_auto("恢复失败！");
			}
	  });
	
}
</script>
</head>
<body onload="doInit();">
  <table id="datagrid" fit="true"></table>
</body>
</html>