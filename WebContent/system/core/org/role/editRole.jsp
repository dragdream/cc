<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
		String uuid = request.getParameter("uuid");
		String deptId = request.getParameter("deptId")==null?"":request.getParameter("deptId");
		String deptName = request.getParameter("deptName")==null?"":request.getParameter("deptName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background:#f0f0f0">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>编辑角色</title>
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/validator2.0.jsp" %>



<script type="text/javascript">

function doInit(){
	if(<%=uuid%> != ''){
		var url = "<%=contextPath %>/userRoleController.action?load";
		var json = tools.requestJsonRs(url,{uuid:'<%=uuid%>'});
		if(json.rtState){
			var data = json.rtData;
			$("#uuid").attr("value",data.uuid);
			$("#roleNo").attr("value",data.roleNo);
			$("#roleName").attr("value",data.roleName);
			$("#deptName").attr("value",data.deptName);
			$("#deptId").attr("value",data.deptId);
		}
	}
}

function doSaveOrUpdate(callback){
	if(!checkFrom()){
		return ;
	}
	if($("#deptId").val()==""){
		$("#deptId").val("0");
	}
	
	var url = "<%=contextPath %>/userRoleController.action?edit";
	if(<%=uuid%> == '' && <%=uuid%> == 0){
		url = "<%=contextPath%>/userRoleController.action?add";
	}

	var para =  tools.formToJson($("#form1")) ;
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		callback(jsonRs);
	}else{
		if(jsonRs.rtData == 1){
			$("#roleNo").select();
			$("#roleNo").focus();
		}
		alert(jsonRs.rtMsg);
	}
}

/**
 * 校验
 */
function checkFrom(){
	 var check = $("#form1").valid();
	 if(!check ){
		 return false; 
	 }
	 return true; 
}


function backIndex(){

	window.location.href = "<%=contextPath %>/system/core/org/role/manageRole.jsp";
}
</script>

</head>
<body onload="doInit()" style="background:#f0f0f0">
<center style="width:100%;">
	<form  method="post" name="form1" id="form1" >
		<table class="TableBlock" width="100%" align="center">
		   <tr>
		    <td nowrap class="TableData">角色排序号：</td>
		    <td nowrap class="TableData">
		    <input type="text" name="roleNo" id="roleNo" required ></td>
		   </tr>
		   <tr>
		    <td nowrap class="TableData" width="120">角色名称：<span style=""></span></td>
		    <td nowrap class="TableData">
		        <input type="text" name="roleName" required id="roleName" >&nbsp;
		        <input type="hidden" name="uuid" id="uuid" value="0" />
		    </td>
		   </tr>
		    <td nowrap class="TableData" width="120">所属部门：<span style=""></span></td>
		    <td nowrap class="TableData">
		        <input type="text" name="deptName" readonly  id="deptName" value="<%=deptName%>">&nbsp;
		        <input type="hidden" name="deptId" id="deptId" value="<%=deptId %>" />
		        <img style="cursor:pointer" src="/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectSingleDept(['deptId','deptName'],'0')" value="选择">
		    </td>
		   </tr>
		</table>
 	 </form>
</center>
</body>
<script>
/**
 * 选择部门
 * @return
 */
function selectDept(retArray , moduleId,privNoFlag , noAllDept,callBackFunc) {
  deptRetNameArray = retArray;
  objSelectType  = retArray[2] || "";
  var url = contextPath + "/system/core/orgselect/selectMultiDeptPost.jsp?objSelectType=" + objSelectType;
  var has = false;
  if (moduleId) {
    url += "&moduleId=" + moduleId ;
  }
  if (privNoFlag) {
    url += "&privNoFlag=" + privNoFlag ;
  }
  if (noAllDept) {
    url += "&noAllDept=" + noAllDept ;
  }
  if(callBackFunc){
	  url += "&callBackPara=" + callBackFunc ;
  }
  var IM_OA;
try{
    IM_OA = window.external.IM_OA;
}catch(e){}

if(window.showModelDialog || IM_OA){
	  dialogChangesize(url, 560, 400);
  }else{
	  openWindow(url,"选择人员", 560, 400);
  }
  
}


/**
 * 选择单个部门
 * @return
 */
function selectSingleDept(retArray , moduleId,privNoFlag , noAllDept) {
  deptRetNameArray = retArray;
  objSelectType  = retArray[2] || "";
  var url = contextPath + "/system/core/orgselect/selectMultiDeptPost.jsp?objSelectType=" + objSelectType + "&isSingle=1";
  var has = false;
  if (moduleId) {
    url += "&moduleId=" + moduleId ;
  }
  if (privNoFlag) {
    url += "&privNoFlag=" + privNoFlag ;
  }
  if (noAllDept) {
    url += "&noAllDept=" + noAllDept ;
  }
  var IM_OA;
try{
    IM_OA = window.external.IM_OA;
}catch(e){}

if(window.showModelDialog || IM_OA){
	  dialogChangesize(url, 560, 400);
  }else{
	  openWindow(url,"选择人员", 560, 400);
  }
  
}

</script>
</html>
 