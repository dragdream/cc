<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建/编辑业务系统信息</title>
<%@ include file="/header/header2.0.jsp" %>
<script type="text/javascript">
var sid=<%=sid %>;
//初始化
function doInit(){
	if(sid>0){
		getInfoBySid();
	}	
}



//根据主键获取详情
function getInfoBySid(){
	var url=contextPath+"/ApplicationSystemMaintainController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var data=json.rtData;
		bindJsonObj2Cntrl(data);
	}
}



//保存操作
function doSave(){
	if(check()){
		var url=contextPath+"/ApplicationSystemMaintainController/addOrUpdate.action";
		var param=tools.formToJson($("#form1"));
		var json=tools.requestJsonRs(url,param);
		return json;
	}
}

//验证   
function check(){
	var systemName=$("#systemName").val();
	if(systemName==null||systemName==""){
		$.MsgBox.Alert_auto("请填写系统名称！");
		return false;
	}
	return true;
}
</script>
</head>
<body onload="doInit()" style="background-color: #f2f2f2">
 <form id="form1">
   <input type="hidden" name="sid" id="sid" value="<%=sid %>" />
   <table  class="TableBlock" width="100%" align="center" >
        <tr>
			<td nowrap class="TableData" width="100" style="text-indent: 10px">系统名称：</td>
			<td class="TableData">
				<input type="text" class="BigInput" name="systemName" id="systemName" style="height: 23px;width: 400px"/>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100" style="text-indent: 10px">访问地址：</td>
			<td class="TableData">
				<input type="text" class="BigInput" name="url" id="url" style="height: 23px;width: 400px"/>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100" style="text-indent: 10px">人员权限：</td>
			<td class="TableData">
			    <input type="hidden" name="userIds" id="userIds"/>
				<textarea rows="5" cols="40" name="userNames" id="userNames"></textarea>
			    <span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['userIds','userNames'],'14')" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('userIds','userNames')" value="清空"/>
					</span>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100" style="text-indent: 10px">部门权限：</td>
			<td class="TableData">
			    <input type="hidden" name="deptIds" id="deptIds"/>
				<textarea rows="5" cols="40" name="deptNames" id="deptNames"></textarea>
			    <span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectDept(['deptIds','deptNames'],'14')" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('deptIds','deptNames')" value="清空"/>
					</span>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100" style="text-indent: 10px">角色权限：</td>
			<td class="TableData">
			    <input type="hidden" name="roleIds" id="roleIds"/>
				<textarea rows="5" cols="40" name="roleNames" id="roleNames"></textarea>
			     <span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectRole(['roleIds','roleNames'],'14')" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('roleIds','roleNames')" value="清空"/>
					</span>
			</td>
		</tr>
		
   
   </table>
 </form>
</body>
</html>