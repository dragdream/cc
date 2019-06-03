<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//任务主键
  int  sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>项目问题</title>
</head>
<script>
var datagrid;
var sid=<%=sid%>;
var loginUserId=<%=loginUser.getUuid()%>;

//初始化方法
function  doInit(){
	isCreaterAndOnProgress();
	getQuestionList();
	
}
//获取与当前任务相关的问题列表
function getQuestionList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/projectQuestionController/getQuestionListByTaskId.action?taskId="+sid,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
		/* 	{field:'sid',checkbox:true,title:'ID',width:100}, */
			{field:'questionName',title:'问题名称',width:120,formatter:function(value,rowData,rowIndex){
			    return "<a href=\"#\" onclick=\"detail("+rowData.sid+")\">"+rowData.questionName+"</a>";
			}},
			{field:'createrName',title:'提交人',width:60},
			{field:'operatorName',title:'处理人',width:60},
			{field:'questionLevel',title:'优先级',width:60},
			{field:'status',title:'状态',width:60,formatter:function(value,rowData,rowIndex){
			    var status=rowData.status;
			    if(status==0){
			    	return "待处理";
			    }else{
			    	return "已处理";
			    }
			}},
			{field:'opt_',title:'操作',width:100,formatter:function(value,rowData,rowIndex){
			    return "<a href=\"#\" onclick=\"detail("+rowData.sid+")\">详情</a>";
			}},
		]]
	});
	
	
	
	
}

//创建问题
function create(){
	var url=contextPath+"/system/subsys/project/taskdetail/addQuestion.jsp?taskId="+sid;
	bsWindow(url ,"新建问题",{width:"600",height:"250",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var a=cw.commit();
		    if(a){
		    	$.MsgBox.Alert_auto("保存成功！");
		    	datagrid.datagrid("reload");
		    	return true;
		    }else{
		    	$.MsgBox.Alert_auto("保存失败！");
		    	return false;
		    }
		}else if(v=="关闭"){
			return true;
		}
	}});
}


//查看问题详情
function detail(sid){
	var url=contextPath+"/system/subsys/project/taskdetail/questionDetail.jsp?sid="+sid;
	bsWindow(url ,"问题详情",{width:"600",height:"290",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		 if(v=="关闭"){
			return true;
		}
	}});
	
	
}



//判断当前登陆人是不是任务负责人  并且  当前任务的状态是不是进行中 （status=0）
function isCreaterAndOnProgress(){
	var url=contextPath+"/taskController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var data=json.rtData;
		var status=data.status;
		var managerId=data.managerId;
		if(status==0&&managerId==loginUserId){
			$("#createBtn").show();
		}else{
			$("#createBtn").hide();
		}
	}

}
</script>

<body onload="doInit()" style="padding-right: 10px;overflow: hidden;">
   <div id="toolbar" class="topbar clearfix">
      <div class="right fr">
         <input type="button" value="创建问题" class="btn-win-white" onclick="create()" style="display: none;" id="createBtn"/>
      </div>
   </div>
   <table id="datagrid" fit="true"></table>
</body>
</html>