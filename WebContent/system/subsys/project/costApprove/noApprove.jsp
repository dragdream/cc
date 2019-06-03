<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>待审批</title>
</head>

<script>
//初始化
var datagrid;
function  doInit(){
	var url=contextPath+"/projectCostController/getApproveListByStatus.action?status=0";
	datagrid = $('#datagrid').datagrid({
		url:url,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'projectName',title:'项目名称',width:120,formatter:function(value,rowData,rowIndex){
                return "<a href=\"#\" onclick=\"projectDetail('"+rowData.projectId+"')\">"+rowData.projectName+"</a>";
			}},
			{field:'costTypeName',title:'费用类型',width:80},
			{field:'createrName',title:'申请人',width:80},
			{field:'createTimeStr',title:'申请时间',width:100},
			{field:'amount',title:'申请金额',width:80},
			{field:'description',title:'费用说明',width:200},
			{field:'opt_',title:'操作',width:120,formatter:function(value,rowData,rowIndex){
                 return "<a href=\"#\" onclick=\"approve(1,"+rowData.sid+")\">同意</a>&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"noApprove(2,"+rowData.sid+")\">拒绝</a>";
			}},
		]]
	});
}

//项目详情
function projectDetail(uuid){
    var url=contextPath+"/system/subsys/project/projectdetail/index.jsp?uuid="+uuid;	
    openFullWindow(url);
}


//审批  同意/拒绝
function approve(status,sid){
	var mess="";
	var tips="";
	if(status==1){
		mess="是否确认同意该费用申请？";
		tips="已同意";
	}else{
		mess="是否确认拒绝该费用申请？";
		tips="已拒绝";
	}
	$.MsgBox.Confirm ("提示", mess, function(){
	   var url=contextPath+"/projectCostController/approve.action";
	   var json=tools.requestJsonRs(url,{status:status,sid:sid});
	   if(json.rtState){
		   $.MsgBox.Alert_auto(tips);
		   datagrid.datagrid('reload');
	   }
	   
    });
}



//拒绝
function noApprove(status,sid){ 

	var url=contextPath+"/system/subsys/project/costApprove/refusedReason.jsp?sid="+sid+"&&status="+status;
	bsWindow(url ,"拒绝原因",{width:"500",height:"133",buttons:
		[
         {name:"确定",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"},
		 ]
		,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v=="确定"){
			$.MsgBox.Confirm ("提示", "是否确认拒绝该费用申请？", function(){
		       var state=cw.commit();
		       if(state){
		    	    $.MsgBox.Alert_auto("已拒绝！");
					datagrid.datagrid("reload");
					d.hide();
		       }else{
		    	   $.MsgBox.Alert_auto("操作失败！");
		       }
		      
	        });
	 }else if(v=="关闭"){
			return true;
		}
	}});
	
}
</script>
<body onload="doInit()">
   <table id="datagrid" fit="true"></table>
</body>
</html>