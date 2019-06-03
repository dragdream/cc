<%@page import="com.tianee.webframe.util.auth.TeeFunctionControl"%>
<%TeeFunctionControl.distinguishTheVersion(request,response);%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src = '<%=contextPath %>/common/zt_webframe/js/jquery.tree.js'></script>
<script type="text/javascript" src = '<%=contextPath %>/common/zt_webframe/js/jquery.treegrid.js'></script>
<title>督办任务分类</title>
</head>
<script>
//初始化
function doInit(){

	getList();
	
	
}

//获取分类列表数据
function getList(){
	$("#treeGrid").treegrid({
		url: contextPath + "/supTypeController/getSupTypeList.action",
		method: 'post',
        idField: 'sid',
        toolbar:"#toolbar",
        treeField: 'typeName',
        pagination:false,
        border:false,
        columns:[[
      			{field:'typeName',title:'分类名称',width:250},
      		//	{field:'taskNo',title:'排序号',width:150},
      			{field:'userNames',title:'所属人员',width:250},
      			{field:'roleNames',title:'所属角色',width:250},
      			{field:'deptNames',title:'所属部门',width:250},
      			{field:'opt_',title:'操作',width:300,formatter:function(value,rowData,rowIndex){
      			   var opt="<a href=\"#\" onclick=\"detail("+rowData.sid+")\">详情</a>";
      			   opt+="&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"addOrUpdate("+rowData.sid+")\">编辑</a>&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"del("+rowData.sid+")\">删除</a>"; 
      			   return opt;
      			}}
      		]]
        
	});
}

//查看详情
function detail(sid){
	var url=contextPath+"/system/subsys/supervise/supervisionType/detail.jsp?sid="+sid;
	bsWindow(url ,"督办任务分类详情",{width:"600",height:"210",buttons:
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

//新建/编辑
function addOrUpdate(sid){
	var url=contextPath+"/system/subsys/supervise/supervisionType/addOrUpdate.jsp?sid="+sid;
	var title="";
	var mess="";
	if(sid>0){
		title="编辑分类";
		mess="编辑成功！";
	}else{
		title="新建分类";
		mess="新建成功！";
	}
	
	bsWindow(url ,title,{width:"600",height:"320",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		   var a=cw.commit();
		   if(a){
			   $.MsgBox.Alert_auto(mess);
			   $("#treeGrid").treegrid("reload");
			   return true;
		   }
		}else if(v=="关闭"){
			return true;
		}
	}});
	
}


//删除
function del(sid){
	$.MsgBox.Confirm ("提示", "是否确认删除该督办任务分类？", function(){
		  var url = contextPath + "/supTypeController/delBySid.action";
			var para = {sid:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){					
				$.MsgBox.Alert_auto("删除成功！");
				$("#treeGrid").treegrid('reload');	
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}  
	  });
	
	
}
</script>

<body onload="doInit()" style="overflow:hidden;padding-right: 10px;padding-left: 10px">
<div  id="toolbar" class="topbar clearfix">
   <div class="fl left">
      <img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/supervise/imgs/icon_duchafenlei.png">
	  <span class="title">督办任务分类</span>
   </div>
   <div class="fr right">
      <input type="button" value="新建分类" class="btn-win-white" onclick="addOrUpdate(0)"/>
   </div>  
</div>
<table id="treeGrid" class="easyui-treegrid" fit="true" ></table>
</body>
</html>