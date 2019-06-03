<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	int parentId=TeeStringUtil.getInteger(request.getParameter("parentId"), 0);
    int currentFolderSid=TeeStringUtil.getInteger(request.getParameter("currentFolderSid"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<%@ include file="/header/userheader.jsp" %>
<title>新建/编辑文件夹</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
</style>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript">
var parentId="<%=parentId%>"; //父文件夹主键
var currentFolderSid="<%=currentFolderSid%>";//当前文件夹的主键
//初始化方法
function  doInit(){
	//获取文件夹详情
	if(currentFolderSid>0){
		getFolderInfoBySid(currentFolderSid);
	}
	
}

//获取文件夹详情
function getFolderInfoBySid(currentFolderSid){
	var url =  contextPath+"/quieeReportController/getBySid.action";
	var para = {sid:currentFolderSid};
	var jsonObj = tools.requestJsonRs(url,para);
	bindJsonObj2Cntrl(jsonObj.rtData);
}
//保存
function doSaveOrUpdate(){
	if(check()){
		if(currentFolderSid>0){//编辑文件夹
			var url =  contextPath+"/quieeReportController/editFolder.action";
			var para =  tools.formToJson($("#form1")) ;
			para["sid"]=currentFolderSid;
			para["reportType"]=1;
			var jsonObj = tools.requestJsonRs(url,para);
			return jsonObj.rtState;	
		}else{
			//新建文件夹
			var url =  contextPath+"/quieeReportController/addFolder.action";
			var para =  tools.formToJson($("#form1")) ;
			para["parentId"]=parentId;
			var jsonObj = tools.requestJsonRs(url,para);
			return jsonObj.rtState;		
		}
		
	}	
}


//检验文件夹名称不能为空
function check(){
	var reportName=$("#reportName").val();
	var sortNo=$("#sortNo").val();
	var reg = /^(0|[1-9]\d*)$/;//非负整数
	if(reportName==null||reportName==""){
		alert("请填写文件夹名称！");
		return false;
	}
	if(!reg.test(sortNo)){
		alert("排序号格式错误，应为非负整数！");
		return false;
	}
	    return true;
}
</script>

</head>
<body onload="doInit();">
<form id="form1" name="form1" method="post">
	<table class="TableBlock" width="100%" align="center" style="margin-top:10px;">
		<tr>
			<td nowrap class="TableData" width="100">文件夹名称：</td>
			<td class="TableData">
			   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			   <input type="text" name="reportName" id="reportName" class="BigInput" style="width: 300px"/>		
			</td>	
		</tr>
		
		<tr>
			<td nowrap class="TableData" width="100">排序号：</td>
			<td class="TableData">
			 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			   <input type="text" name="sortNo" id="sortNo" class="BigInput" style="width: 300px"/>		
			</td>	
		</tr>
		
		<tr>
			<td nowrap class="TableData" width="100">管理权限：</td>
			<td class="TableData">
			     人员：
              <input id="userManageIds" name="userManageIds" type="hidden" value=''> 
			  <textarea name="userManageNames" id="userManageNames" class="SmallStatic BigTextarea" rows="2" cols="50"readonly="readonly"></textarea> &nbsp;&nbsp;
			  <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['userManageIds','userManageNames'])">选择</a>&nbsp;&nbsp; 
			  <a href="javascript:void(0);" class="orgClear"onClick="clearData('userManageIds','userManageNames')">清空</a>
			  <br>
			  <br>
			     部门：
			  <input id="deptManageIds" name="deptManageIds" type="hidden" value=''> 
			  <textarea name="deptManageNames" id="deptManageNames" class="SmallStatic BigTextarea" rows="2" cols="50" readonly="readonly"></textarea> &nbsp;&nbsp;
			  <a href="javascript:void(0);" class="orgAdd" onClick="selectDept(['deptManageIds','deptManageNames'])">选择</a>&nbsp;&nbsp; 
			  <a href="javascript:void(0);" class="orgClear"onClick="clearData('deptManageIds','deptManageNames')">清空</a>
			  <br>
			  <br>
			     角色：
			  <input id="roleManageIds" name="roleManageIds" type="hidden" value=''> 
			  <textarea name="roleManageNames" id="roleManageNames" class="SmallStatic BigTextarea" rows="2" cols="50" readonly="readonly"></textarea> &nbsp;&nbsp;
			  <a href="javascript:void(0);" class="orgAdd" onClick="selectRole(['roleManageIds','roleManageNames'])">选择</a>&nbsp;&nbsp; 
			  <a href="javascript:void(0);" class="orgClear"onClick="clearData('roleManageIds','roleManageNames')">清空</a>
			  <br>		
			</td>	
		</tr>
		
		<tr>
			<td nowrap class="TableData" width="100">查看权限：</td>
			<td class="TableData">
			     人员：
			  <input id="userViewIds" name="userViewIds" type="hidden" value=''> 
			  <textarea name="userViewNames" id="userViewNames" class="SmallStatic BigTextarea" rows="2" cols="50" readonly="readonly"></textarea> &nbsp;&nbsp;
			  <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['userViewIds','userViewNames'])">选择</a>&nbsp;&nbsp; 
			  <a href="javascript:void(0);" class="orgClear"onClick="clearData('userViewIds','userViewNames')">清空</a>
			  <br> 
			  <br> 
			     部门：
			   <input id="deptViewIds" name="deptViewIds" type="hidden" value=''> 
			  <textarea name="deptViewNames" id="deptViewNames" class="SmallStatic BigTextarea" rows="2" cols="50" readonly="readonly"></textarea> &nbsp;&nbsp;
			  <a href="javascript:void(0);" class="orgAdd" onClick="selectDept(['deptViewIds','deptViewNames'])">选择</a>&nbsp;&nbsp; 
			  <a href="javascript:void(0);" class="orgClear"onClick="clearData('deptViewIds','deptViewNames')">清空</a>
			  <br>
			  <br>
			     角色：
			  <input id="roleViewIds" name="roleViewIds" type="hidden" value=''> 
			  <textarea name="roleViewNames" id="roleViewNames" class="SmallStatic BigTextarea" rows="2" cols="50" readonly="readonly"></textarea> &nbsp;&nbsp;
			  <a href="javascript:void(0);" class="orgAdd" onClick="selectRole(['roleViewIds','roleViewNames'])">选择</a>&nbsp;&nbsp; 
			  <a href="javascript:void(0);" class="orgClear"onClick="clearData('roleViewIds','roleViewNames')">清空</a>
			  <br>	
			</td>	
		</tr>
		
	</table>
	
</form>
</body>

</html>