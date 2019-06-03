<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<title>待审批</title>
<script type="text/javascript">
var datagrid ;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/TeeDutyComplaintController/datagrid.action?status=1",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			//{field:'sid',checkbox:true,title:'ID',width:200},
			{field:'userName',title:'申诉人',width:200},
			{field:'remarkTimeStr',title:'记录时间',width:200},
			{field:'registerNum',title:'登记次数',width:200,formatter:function(value,rowData,rowIndex){
				   var num=rowData.registerNum;
				   var desc="";
				   if(num==1){
					   desc="第一次登记";
				   }else if(num==2){
					   desc="第二次登记";
				   }else if(num==3){
					   desc="第三次登记";
				   }else if(num==4){
					   desc="第四次登记";
				   }else if(num==5){
					   desc="第五次登记";
				   }else if(num==6){
					   desc="第六次登记";
				   }
				   return desc;
				}},
			{field:'createTimeStr',title:'创建时间',width:200},
			{field:'opt_',title:'操作',width:200,formatter:function(value,rowData,rowIndex){
			   var opt="";
			   opt+="<a href=\"#\" onclick=\"detail("+rowData.sid+")\">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;";
			   return opt;
			}},
			
		]]
	});
}


//查看详情
function  detail(sid){
    var  url=contextPath+"/system/core/base/attend/complain/detail.jsp?sid="+sid;
	bsWindow(url ,"申诉详情",{width:"600",height:"320",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="关闭"){
			return true;
		}
	}}); 
}
</script>
</head>
<body onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">
<table id="datagrid" fit="true"></table>
</body>
</html>