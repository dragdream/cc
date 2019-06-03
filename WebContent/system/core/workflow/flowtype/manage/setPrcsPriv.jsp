<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
	int flowTypeId = TeeStringUtil.getInteger(request.getParameter("flowTypeId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>设置经办权限</title>
		<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css">
	<link rel="stylesheet" href="<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/js/src/teeValidagteBox.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/js/sys.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/services/TeeFlowTypeService.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/services/TeeFlowSortService.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/services/TeeFormSortService.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/js/ZTreeSync.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/js/src/orgselect.js"></script>
	<script>
		var contextPath = "<%=contextPath%>";
		var flowTypeId = <%=flowTypeId%>;
		function doInit(){
			var url = contextPath+"/flowType/getPrcsPriv.action";
			var json = tools.requestJsonRs(url,{flowId:flowTypeId});
			if(json.rtState){
				var prcsUser = json.rtData.prcsUser;
				var ids = "";
				var desc = "";
				for(var i=0;i<prcsUser.length;i++){
					ids+=prcsUser[i].value+",";
					desc+=prcsUser[i].name+",";
				}
				$("#prcsUser").attr("value",ids);
				$("#prcsUserDesc").attr("value",desc);


				var prcsDept = json.rtData.prcsDept;
				var ids = "";
				var desc = "";
				for(var i=0;i<prcsDept.length;i++){
					ids+=prcsDept[i].value+",";
					desc+=prcsDept[i].name+",";
				}
				$("#prcsDept").attr("value",ids);
				$("#prcsDeptDesc").attr("value",desc);

				var prcsRole = json.rtData.prcsRole;
				var ids = "";
				var desc = "";
				for(var i=0;i<prcsRole.length;i++){
					ids+=prcsRole[i].value+",";
					desc+=prcsRole[i].name+",";
				}
				$("#prcsRole").attr("value",ids);
				$("#prcsRoleDesc").attr("value",desc);
			}
		}

		function commit(callback){
			var url = contextPath+"/flowType/savePrcsPriv.action";
			var para = tools.formToJson($("#form"));
			para["flowId"] = flowTypeId;
			var json = tools.requestJsonRs(url,para);
			callback(json);
		}
	</script>
</head>
<body  onload="doInit()">
<form id="form">
<table width="100%"  align="center"  class="TableBlock">
	<TBODY>
		<TR>
			<td nowrap class="TableData TableBG" style="width:80px">办理人员：</td>
	      	<td class="TableData">
				<textarea class="BigTextarea" readonly rows="5" cols="30" id="prcsUserDesc"></textarea>
				<input type="hidden" id="prcsUser"  name="prcsUser"/>
				<a href="javascript:void()" onclick="selectUser(['prcsUser', 'prcsUserDesc'])">添加</a>
				&nbsp;<a href="javascript:void()" onclick="clearData('prcsUser','prcsUserDesc')">清空</a>
	      	</td>
		</TR>
		<TR>
			<td nowrap class="TableData TableBG">办理部门：</td>
	      	<td class="TableData">
				<textarea class="BigTextarea" readonly  rows="5" cols="30" id="prcsDeptDesc"></textarea>
				<input type="hidden" id="prcsDept"  name="prcsDept"/>
				<a href="javascript:void()" onclick="selectDept(['prcsDept', 'prcsDeptDesc'])">添加</a>
				&nbsp;<a href="javascript:void()" onclick="clearData('prcsDept','prcsDeptDesc')">清空</a>
	      	</td>
		</TR>
		<TR>
			<td nowrap class="TableData TableBG" >办理角色：</td>
	      	<td class="TableData">
				<textarea class="BigTextarea" readonly  rows="5" cols="30" id="prcsRoleDesc"></textarea>
				<input type="hidden" id="prcsRole" name="prcsRole" />
				<a href="javascript:void()" onclick="selectRole(['prcsRole', 'prcsRoleDesc'])">添加</a>
				&nbsp;<a href="javascript:void()" onclick="clearData('prcsRole','prcsRoleDesc')">清空</a>
	      	</td>
		</TR>
	</TBODY>
</table>
<input type="hidden" name="flowId" value="<%=flowTypeId %>"/>
</form>
</body>

</html>