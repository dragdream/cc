<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//项目主键
  String  uuid=TeeStringUtil.getString(request.getParameter("uuid"));
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>项目批注</title>
</head>
<script>
var projectId="<%=uuid%>";
var datagrid;
var loginUserUuid=<%=loginUser.getUuid()%>;
var status=0;//项目状态
function doInit(){
	getProjectStatus();
	getNotationList();
	isViewer();
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



//获取项目批注列表
function getNotationList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/projectNotationController/getNotationListByProjectId.action?projectId="+projectId,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			/* {field:'sid',checkbox:true,title:'ID',width:100}, */
			{field:'createrName',title:'批注领导',width:100},
			{field:'content',title:'批注内容',width:60},
			{field:'createTimeStr',title:'批注时间',width:60},
			{field:'opt_',title:'操作',width:50,formatter:function(value,rowData,rowIndex){
				var createrId=rowData.createrId;
				var opt="";
				if(loginUserUuid==createrId&&status==3){
					return "<a href=\"#\" onclick=\"addOrUpdate("+rowData.sid+")\">编辑</a>&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"del("+rowData.sid+");\">删除</a>";
				}
			}}
            
		]]
	});
}

//判断当前登陆人 是不是该项目的观察者  只有观察者才能创建批注
function isViewer(){
	var url=contextPath+"/projectController/isViewer.action";
	var json=tools.requestJsonRs(url,{projectId:projectId});
	if(json.rtState){
		var data=json.rtData;
		if(data==1&&status==3){//是观察者   并且  项目的状态是进行中
			$("#createBtn").show();
		}else{//不是观察者
			$("#createBtn").hide();
		}
	}
	
}

//新建批注
function addOrUpdate(sid){
	var url=contextPath+"/system/subsys/project/projectdetail/basic/addOrUpdateNotation.jsp?projectId="+projectId+"&&sid="+sid;
	var title="";
	if(sid>0){
		title="编辑批注";
	}else{
		title="新建批注";
	}
	bsWindow(url ,title,{width:"600",height:"180",buttons:
		[
         {name:"确定",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){
		   var b=cw.commit();
		   if(b){
			 $.MsgBox.Alert_auto("保存成功！");
			 datagrid.datagrid("reload");
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

//删除项目批注
function  del(sid){
	 $.MsgBox.Confirm ("提示", "是否确认删除该批注？", function(){
		 var url=contextPath+"/projectNotationController/delBySid.action";
			var json=tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！");
				datagrid.datagrid("reload");
			}
	  });
	
	
}
</script>

<body onload="doInit();" style="padding-left: 10px;padding-right: 10px;padding-top: 5px">
   <div id="toolbar" class="toolbar clearfix">
     <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/project/img/icon_xiangmupizhu.png">
	 	<span class="title">项目批注 <span id="totalMail" class="attach"></span></span>
	 </div>
      <div class="rignt fr" style="margin-bottom: 10px">
         <input type="button" class="btn-win-white" value="新建批注" id="createBtn"  onclick="addOrUpdate(0)" style="display: none;"/> 
      </div>
   </div>
   <table id="datagrid" fit="true"></table>
</body>
</html>