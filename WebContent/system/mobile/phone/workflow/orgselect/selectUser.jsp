<%@page import="com.tianee.oa.oaconst.TeeConst"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.org.bean.TeePerson" %>
<%@ page import="com.tianee.oa.core.org.bean.TeeDepartment" %>
<%
String contextPath = request.getContextPath();
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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>单项人员选择</title>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/tools.js"></script> 

<script type="text/javascript">
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

function doInit(){
	if(single_select=="1"){
		single_select = true;
	}else{
		single_select = false;
	}
	
	//userFilterArray = eval("("+window.external.findPersonDatas(userFilter)+")");//获取用户过滤信息的数组，带排序
	
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
			renderItem({id:idsArray[i],name:namesArray[i]});
			loadedUsers.push(idsArray[i]);//加入已加载的用户
		}
		
		
	}else{
		for(var i=0;i<userFilterArray.length;i++){
			if(i==20){
				break;
			}
			loadedUsers.push(userFilterArray[i].uuid);//加载当前用户
			renderItem({id:userFilterArray[i].uuid,name:userFilterArray[i].userName});//渲染当前用户
		}
		if(userFilterArray.length==0){
			doSearch();
		}
	}
}

/**
 * 渲染项
 */
function renderItem(item){
	var exists = false;
	for(var i=0;i<idsArray.length;i++){
		if(parseInt(idsArray[i])==parseInt(item.id)){
			exists = true;
			break;
		}
	}
	var render = [];
	render.push("<div uuid="+item.id+" class='item "+(exists?"selected":"")+"' onclick='clickIt(this)'>");
	render.push(item.name);
	render.push("</div>");
	$("#itemDiv").append(render.join(""));
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
		namesArray.push($(obj).text());
	}
	to_id_field.value = idsArray.join(",");
	to_name_field.value = namesArray.join(",");
}

/**
 * 取消选择所有项
 */
function cancelAll(){
	$("#itemDiv .selected").each(function(i,obj){
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
	$("#itemDiv").html("");//清空面板
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
		$("#itemDiv").html("<br/><center>没有找到相关人员</center>");
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
<style>
.item{
font-size:14px;
border:1px solid #e2e2e2;
color:black;
padding:10px;
margin-top:5px;
text-align:center;
}
.selected{
background:#428bca;
color:white;
}
</style>
</head>
<body onload="doInit()" style="overflow:hidden">
<div style="position:absolute;top:0px;left:0px;right:0px;height:30px;padding-top:2px;padding-left:5px;">
	<button class='btn btn-primary' onclick="parent.$('#dataFetchFrm').hide();">确定</button>
	<input type="text" id="searchBox" style="border:2px solid orange" oninput="doSearch()"/>
</div>
<div style="position:absolute;bottom:0px;left:0px;right:0px;top:30px;overflow:auto" id="itemDiv">
	
</div>
</body>
</html>