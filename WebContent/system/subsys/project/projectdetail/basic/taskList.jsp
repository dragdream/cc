<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//项目主键
String  projectId=TeeStringUtil.getString(request.getParameter("projectId"));
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src = '<%=contextPath %>/common/zt_webframe/js/jquery.tree.js'></script>
<script type="text/javascript" src = '<%=contextPath %>/common/zt_webframe/js/jquery.treegrid.js'></script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>任务列表</title>
</head>
<script>
var projectId="<%=projectId%>";
var status=0;//项目状态
function doInit(){
	$("#treeGrid").treegrid({
		url: contextPath + "/taskController/getTaskListByProjectId.action?projectId="+projectId,
		/* view:window.EASYUI_DATAGRID_NONE_DATA_TIP, */ 
		method: 'post',
        idField: 'sid',
        treeField: 'taskName',
        pagination:false,
        border:false,
        columns:[[
      			{field:'taskName',title:'任务名称'},
      			{field:'taskNo',title:'任务序号',width:150},
      			{field:'managerName',title:'负责人',width:150},
      			{field:'beginTimeStr',title:'计划开始日期',width:150},
      			{field:'endTimeStr',title:'计划结束日期',width:150},
      			{field:'days',title:'工期',width:120,formatter:function(value,rowData,rowIndex){
      				return rowData.days+"天";
      			}},
      			{field:'progress',title:'进度',width:120,formatter:function(value,rowData,rowIndex){
      				return rowData.progress+"%";
      			}},
      			{field:'opt_',title:'操作',width:200,formatter:function(value,rowData,rowIndex){
      			   var isCreater=rowData.isCreater;
      			   var opt="<a href=\"#\" onclick=\"detail("+rowData.sid+")\">详情</a>";
      			   getProjectStatus();
      			   if(isCreater==1&&status==3){
      				   opt+="&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"edit("+rowData.sid+")\">编辑</a>&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"del("+rowData.sid+")\">删除</a>";
      			   }
      			   return opt;
      			}}
      		]]
        
	});
}


//根据项目主键  获取项目状态
function getProjectStatus(){
	var url=contextPath+"/projectController/getInfoByUuid.action";
	var json=tools.requestJsonRs(url,{uuid:projectId});
	if(json.rtState){
		var data=json.rtData;
		status=data.status;
	}
}

//获取任务详情
function detail(sid){
	var url=contextPath+"/system/subsys/project/projectdetail/basic/taskDetail.jsp?sid="+sid+"&&projectId="+projectId;
	bsWindow(url ,"任务详情",{width:"600",height:"320",buttons:
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

//编辑任务
function  edit(sid){
	var url=contextPath+"/system/subsys/project/projectdetail/addOrUpdateTask.jsp?sid="+sid+"&projectId="+projectId;
	bsWindow(url ,"编辑任务",{width:"1000",height:"400",buttons:
		[
         {name:"确定",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){
		   var b=cw.commit();
		   if(b){
			 $.MsgBox.Alert_auto("任务编辑成功！"); 
			 $("#treeGrid").treegrid("reload");
			 return true;
		   }else{
			 //$.MsgBox.Alert_auto("任务创建失败！");  
			 return false;  
		   }
		   
		}else if(v=="关闭"){
			return true;
		}
	}});
}


//删除任务
function  del(sid){
	//判断当前任务是否有后置任务  或者  子任务
	var url1=contextPath+"/taskController/isHigherOrPre.action";
	var json1=tools.requestJsonRs(url1,{sid:sid});
	if(json1.rtState){
		var data1=json1.rtData;
		if(data1==1){
			$.MsgBox.Alert_auto("该任务存在子任务或后置任务，暂不支持删除！");
		}else{
			$.MsgBox.Confirm ("提示", "是否确认删除该任务？", function(){

				var url=contextPath+"/taskController/delBySid.action";
				var json=tools.requestJsonRs(url,{sid:sid});
				if(json.rtState){
					 $.MsgBox.Alert_auto("删除成功！"); 
					 $("#treeGrid").treegrid("reload");
				} 
			  });
		}
	}
	
	
	
	
	
	
}
</script>
<body onload="doInit()">
    <table id="treeGrid" class="easyui-treegrid" fit="true" ></table>
</body>
</html>