<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//任务主键
  int  supId=TeeStringUtil.getInteger(request.getParameter("supId"),0);
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>办理情况</title>
</head>
<script>
var datagrid ;
var supId=<%=supId%>;
var loginUserUuid=<%=loginUser.getUuid()%>;
function doInit(){
	getList();
}

//获取分类列表数据
function getList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/supFeedBackController/getFeedBackListBySupId.action?supId="+supId,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'title',title:'反馈情况',width:120},
			{field:'createrName',title:'办理人',width:200},
			{field:'createTimeStr',title:'办理时间',width:80,sortable:true},
			{field:'opt_',title:'操作',width:200,formatter:function(value,rowData,rowIndex){
				var opt="<a href=\"#\" onclick=\"detail("+rowData.sid+")\">详情</a>";
				if(loginUserUuid==rowData.createrId){
					opt+="&nbsp;&nbsp;<a href=\"#\" onclick=\"edit("+rowData.sid+")\">编辑</a>";
					opt+="&nbsp;&nbsp;<a href=\"#\" onclick=\"del("+rowData.sid+")\">删除</a>";
				}
				opt+="&nbsp;&nbsp;<a href=\"#\" onclick=\"reply("+rowData.sid+")\">回复</a>";
				return opt;
			}}
		]]
	});
}

//编辑
function edit(sid){
	var url=contextPath+"/system/subsys/supervise/handle/doFeedBack.jsp?sid="+sid;
	bsWindow(url ,"编辑反馈",{width:"550",height:"180",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var a=cw.save();
		    if(a){
		    	$.MsgBox.Alert_auto("编辑成功！",function(){
		    		datagrid.datagrid("reload");
		    	});
		    	return true;
		    }
		}else if(v=="关闭"){
			return true;
		}
	}});
}


//回复
function reply(feedBackId){
	var url=contextPath+"/system/subsys/supervise/handle/reply.jsp?feedBackId="+feedBackId;
	bsWindow(url ,"反馈回复",{width:"550",height:"140",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var a=cw.save();
		    if(a){
		    	$.MsgBox.Alert_auto("回复成功！");
		    	return true;
		    }
		}else if(v=="关闭"){
			return true;
		}
	}});
}

//删除
function del(sid){
	$.MsgBox.Confirm ("提示", "是否确认删除该反馈信息？", function(){
		  var url = contextPath + "/supFeedBackController/delBySid.action";
			var para = {sid:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){					
				$.MsgBox.Alert_auto("删除成功！");
				datagrid.datagrid('reload');	
			}   
	  });
}


//详情
function detail(fbId){
	
	var url=contextPath+"/system/subsys/supervise/handle/detail.jsp?fbId="+fbId;
	bsWindow(url ,"反馈详情",{width:"600",height:"400",buttons:
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


<body onload="doInit()">
   <table id="datagrid" fit="true"></table>
</body>
</html>