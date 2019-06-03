<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="com.tianee.oa.oaconst.TeeConst" %>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page import="com.tianee.webframe.util.date.TeeDateUtil"%>
<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@page import="org.springframework.core.io.ClassPathResource"%>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page import="com.tianee.oa.core.org.bean.TeeDepartment" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<%@ include file="/system/mobile/mui/header.jsp" %>
<%
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ contextPath + "/";
String mobilePath = contextPath + "/system/mobile";

TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
int deptId = 0;
if(loginUser.getDept()!=null){
	deptId = loginUser.getDept().getUuid();
}
%>
<%
String moduleId = request.getParameter("moduleId");
if (moduleId == null) {
  moduleId = "";
}
String privNoFlag = request.getParameter("privNoFlag");
if (privNoFlag == null || "".equals(privNoFlag)) {
  privNoFlag = "0";
}
String privOp = request.getParameter("privOp");
if (privOp == null) {
  privOp = "";
}

String objSelectType = request.getParameter("objSelectType");
if (objSelectType == null) {
	objSelectType = "";
}

//人员条件filter，目前工作流需要处理
String userFilter = request.getParameter("userFilter") == null ? "0" : request.getParameter("userFilter")  ;

String to_id_field = request.getParameter("to_id_field");
String to_name_field = request.getParameter("to_name_field");
String single_select = request.getParameter("single_select");
%>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/tools.js"></script> 
<title></title>
</head>
<body onload="doInit()">
	<header class="mui-bar mui-bar-nav">
		<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" onclick="doSelect()">
		    <span class="mui-icon mui-icon-checkmarkempty"></span>确定
		</button>
		<h1 class="mui-title" >选择角色</h1>
	</header>
	<div class="mui-content" id="content">
		<div class="mui-scroll">
			<!--数据列表-->
			<ul class="mui-table-view" id="dataList">
				
			</ul>
		</div>
	</div>
<style>
.selected{
	background:#428bca;
	color:white;
}
.item{
	font-size:14px;
}
</style>

<script>
var selectedColor = "rgb(0, 51, 255)";
var RoleId,RoleName;
var moduleId = "<%=moduleId%>";
objSelectType = '<%=objSelectType%>';
var privOp = '<%=privOp%>';
var privNoFlag = "<%=privNoFlag%>";
var userFilter = parent.RANDOM_USER_FILTER;
var deptId = <%=deptId%>;
var contextPath = "<%=contextPath%>";
var parentWindowObj = window.dialogArguments;
var to_id_field = parent.document.getElementById("<%=to_id_field%>");//父级文本框对象Id
var to_name_field = parent.document.getElementById("<%=to_name_field%>");//父级文本框对象Name
var single_select = "<%=single_select%>";//是否是单用户选择
var idsArray = [];//选中的ids
var namesArray = [];//选中的names
var userFilterArray = [];//用户过滤数组
var loadedUsers = [];//已加载的用户


function doSelect(){
	window.top.userSelectDiv.style.display = "none";
}

var deptMap = {};
var pidMap = {};
function doInit(){
	
	if(single_select=="1"){
		single_select = true;
	}else{
		single_select = false;
	}
	
	var json = tools.requestJsonRs(contextPath+"/userRoleController/getAllRole.action",{});
	var rows = json.rtData;
	for(var i=0;i<rows.length;i++){
		//渲染node
		var render = [];
		render.push("<li class=\"mui-table-view-cell mui-media\" id=\"node"+rows[i].uuid+"\" onclick=\"clickIt(this)\" uuid=\""+rows[i].uuid+"\" name=\""+rows[i].roleName+"\">");
		render.push("<div class=\"mui-media-body\">");
		render.push(rows[i].roleName);
		render.push("</div>");
		render.push("</li>");
		$("#dataList").append(render.join(""));
	}
	
	//初始化
	if(to_id_field.value!=""){
		
		var sp = to_id_field.value.split(",");
		for(var i=0;i<sp.length;i++){
			idsArray.push(sp[i]);
		}
		
		var sp = to_name_field.value.split(",");
		for(var i=0;i<sp.length;i++){
			namesArray.push(sp[i]);
		}
		
		for(var i=0;i<idsArray.length;i++){
			renderIt(document.getElementById("node"+idsArray[i]));
		}
	}
}

function renderDept(level,node){
	var children = node.children;
	
	//渲染node
	var render = [];
	render.push("<li class=\"mui-table-view-cell mui-media\" id=\"node"+node.id+"\" onclick=\"clickIt(this)\" uuid=\""+node.id+"\" name=\""+node.title+"\">");
	render.push("<div class=\"mui-media-body\">");
	var blank = "";
	var icon = contextPath+"/common/jquery/ztree/css/zTreeStyle/img/diy/node_dept.gif";
	if(node.iconSkin=="pIconHome"){
		icon = contextPath+"/common/jquery/ztree/css/zTreeStyle/img/diy/1_open.png";
	}
	icon = "<img src='"+icon+"' />&nbsp;&nbsp;";
	for(var i=0;i<level;i++){
		blank+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}
	if(children.length!=0){
		render.push(blank+icon+node.title);
	}else{
		render.push(blank+icon+node.title);
	}
	
	render.push("</div>");
	render.push("</li>");
	$("#dataList").append(render.join(""));
	
	//渲染子节点
	for(var i=0;i<children.length;i++){
		renderDept(level+1,children[i]);
	}
}


function clickIt(obj){
	var uuid = $(obj).attr("uuid");
	if($(obj).attr("class").indexOf("selected")!=-1){//如果是已被选中的，则取消选中
		for(var i=0;i<idsArray.length;i++){
			if(parseInt(idsArray[i])==parseInt(uuid)){
				idsArray.splice(i,1);
				namesArray.splice(i,1);
				break;
			}
		}
		$(obj).removeClass("selected");
	}else{//如果未被选中，则开启选中
		if(single_select){
			cancelAll();
		}
		$(obj).addClass("selected");
		idsArray.push(uuid);
		namesArray.push($(obj).attr("name"));
	}
	to_id_field.value = idsArray.join(",");
	to_name_field.value = namesArray.join(",");
}

function renderIt(obj){
	$(obj).addClass("selected");
}

/**
 * 取消选择所有项
 */
function cancelAll(){
	$(".selected").each(function(i,obj){
		$(obj).removeClass("selected");
	});
	idsArray = [];
	namesArray = [];
	to_id_field.value = "";
	to_name_field.value = "";
}

/***
 * 按人员ID和用户名模糊查询
 */
function doSearch(){
	$("#moreDiv").show();
	var name = $("#searchBox").val();
	$("#userList").html("");//清空面板
	loadedUsers = [];//清空已加载的记录
	
	//只获取前20个用户//
	var hitCount = 0;
	var json = tools.requestJsonRs(contextPath+"/orgSelectManager/getSelectUserByUserIdOrUserName.action",{user:name,userFilter:userFilter,privNoFlag:0});
	
	
	for(var i=0;i<json.rtData.length;i++){
		loadedUsers.push(json.rtData[i].uuid);//加载当前用户
		renderItem({id:json.rtData[i].uuid,name:json.rtData[i].userName});//渲染当前用户
		hitCount++;
	}
	if(hitCount==0){
		//$("#userList").html("<br/><center>没有找到相关人员</center>");
	}
}

function loadMore(){
	var exists = false;
	for(var i=0;i<userFilterArray.length;i++){
		exists = false;
		for(var j=0;j<loadedUsers.length;j++){
			if(userFilterArray[i].uuid==loadedUsers[j]){
				exists = true;
				break;
			}
		}
		if(!exists){
			renderItem({id:userFilterArray[i].uuid,name:userFilterArray[i].userName});//渲染当前用户
		}
	}
	$("#moreDiv").hide();
}
</script>
</body>
</html>