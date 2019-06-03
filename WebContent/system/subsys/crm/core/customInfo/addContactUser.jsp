<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String customerSid = request.getParameter("customerSid")==null?"0":request.getParameter("customerSid");
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
function doInit(){
	getCustomerName();
	getCrmCodeByParentCodeNo("USER_IMPORTANT","important");
 	getCrmCodeByParentCodeNo("USER_POS","_pos");
}

function commit(){
	if($("#form1").form("validate")){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeCrmContactUserController/addOrUpdate.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			top.$.jBox.tip(json.rtMsg,"info");
			return true;
		}
		top.$.jBox.tip(json.rtMsg,"error");
		return false;
	}
}

function getCustomerName(){
	var url = "<%=contextPath%>/TeeCrmCustomerInfoController/getById.action?sid=<%=customerSid%>";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		$("#customerName").val(json.rtData.customerName);
		$("#customerId").val(json.rtData.sid);
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
				<input type="text" class="BigInput" disabled="disabled" name="customerName" id="customerName" />
				<input type="hidden" class="BigInput"  name="customerId" id="customerId" />
			</td>
			<td>
				姓名<span style="color:red;font-weight:bold;">*</span>：
				</td>
			<td>
				<input type='text' id="name" name="name"  class="BigInput easyui-validatebox" maxlength="50" required="true"/>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				性别：
				</td>
			<td>
				<select id="gender" name="gender" class="BigSelect">
					<option value='0'>男</option>
					<option value='1'>女</option>
				</select>
			</td>
			<td>
				所属部门：
				</td>
			<td>
				<input type='text' id="department" name="department" class="BigInput"/>
			</td>
		</tr >
		<tr class='TableData' align='left'>
			<td>
				重要程度：
				</td>
			<td>
				<select id="important" name="important" class="BigSelect">
				</select>
			</td>
			<td>
				出生日期：
				</td>
			<td>
				<input type="text" id='birthdayDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='birthdayDesc' class="Wdate BigInput" />
			</td>
		</tr >
		<tr class='TableData' align='left'>
			<td>
				状态：
				</td>
			<td colspan='3'>
				<select id="_pos" name="_pos" class="BigSelect">
				</select>
			</td>
		</tr >
		<tr class='TableData' align='left'>
			<td>
				简介：
				</td>
			<td colspan='3'>
				<textarea id="brief" name="brief" class="BigTextarea" cols='60' rows='6' validType="maxLength[300]">
				</textarea>
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
				<input type="text" class="BigInput easyui-validatebox" validType='maxLength[100]' name="telephone" id="telephone" />
			</td>
			<td>
				分机电话：
				</td>
			<td>
				<input type="text" name="telephone1" id="telephone1" class="BigInput easyui-validatebox" validType='maxLength[100]'> 
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td>
				移动电话：
				</td>
			<td>
				<input type="text" class="BigInput easyui-validatebox" name="mobilePhone" id="mobilePhone" validType="maxLength[100]"/>
			</td>
			<td>
				传真：
				</td>
			<td>
				<input type="text" class="BigInput" name="fax" id="fax" maxlength="50"/>
			</td>
		</tr >
		<tr class='TableData' align='left'>
			<td>
				邮箱：
			</td>
			<td>
				<input type="text" name="email" id="email"  class="BigInput easyui-validatebox" validType="maxLength[50]"> 
			</td>
			<td>
				QQ：
				</td>
			<td>
				<input type="text" name="qq" id="qq"  class="BigInput easyui-validatebox" validType="QQ"> 
			</td>
		</tr>
	</table>
</form>
</body>
</html>