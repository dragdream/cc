<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<style>
	td{
		line-height:28px;
		min-height:28px;
	}
.ztree{
		background:white;
		border:1px solid #f0f0f0;
	}
</style>

<script>
var sid = "<%=sid%>";
function doInit(){
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeCrmContactUserController/getById.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
		}
	}
}

</script>
</head>
<body onload="doInit();" style="margin:0 auto;padding:0 auto;text-align:center;font-size:12px;margin-top:10px;">
<form id="form1" name="form1">
	<table style="width:90%;font-size:12px;" class='TableBlock' align="center">
		<tr class="TableHeaer" align='left'>
			<td colspan="4" style="width:100%;height:36px;background:#E0EBF9;">
				<div>
					基本信息：
				</div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				所属客户<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<span id='customerName' name='customerName' class="BigInput" ></span>
			</td>
			<td>
				姓名：
				</td>
			<td>
				<span id="name" name="name" class="BigInput"></span>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				性别：
				</td>
			<td>
				<span id="genderDesc" name="genderDesc" class="BigSelect">
				</span>
			</td>
			<td>
				所属部门：
				</td>
			<td>
				<span type="text"  id="department" name="department"></span>
			</td>
		</tr >
		<tr class='TableData' align='left'>
			<td>
				重要程度：
				</td>
			<td>
				<span id="importantDesc" name="importantDesc" class="BigSelect">
				</span>
			</td>
			<td>
				出生日期：
				</td>
			<td>
				<span  id='birthdayDesc'  name='birthdayDesc'></span>
			</td>
		</tr >
		<tr class='TableData' align='left'>
			<td>
				状态：
				</td>
			<td colspan='3'>
				<span id="posDesc" name="posDesc" class="BigSelect">
				</span>
			</td>
		</tr >
		<tr class='TableData' align='left'>
			<td>
				简介：
				</td>
			<td colspan='3' id="brief">
			</td>
		</tr >
	</table>
	<table style="width:90%;font-size:12px" class='TableBlock' align="center">
		<tr class="TableHeaer" align='left'>
			<td colspan="4" style="width:100%;height:36px;background:#E0EBF9;">
				<div>
					联系方式：
				</div>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				公司电话：
			</td>
			<td>
				<span type="text" class="BigInput" name="telephone" id="telephone" ></span>
			</td>
			<td>
				分机电话：
				</td>
			<td>
				<span type="text" name="telephone1" id="telephone1" ></span>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				移动电话：
				</td>
			<td>
				<span type="text" class="BigInput" name="mobilePhone" id="mobilePhone" ></span>
			</td>
			<td>
				传真：
				</td>
			<td>
				<span type="text" class="BigInput" name="fax" id="fax"></span>
			</td>
		</tr >
		<tr class='TableData' align='left'>
			<td>
				邮箱：
			</td>
			<td>
				<span type="text" name="email" id="email"  class="BigInput"></span>
			</td>
			<td>
				QQ：
				</td>
			<td>
				<span type="text" name="qq" id="qq"  class="BigInput"></span>
			</td>
		</tr>
	</table>
	<br/><br/>
</form>
</body>
</html>