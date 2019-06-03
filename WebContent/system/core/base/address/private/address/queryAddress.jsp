<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	String id = request.getParameter("id") == null ? "0" : request.getParameter("id") ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>通讯薄查找</title>
	<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

function doInit(){
	getAddressGroup();
}
/**
 * 保存
 */
function doSave(){
		var url = "<%=contextPath%>/teeAddressController/addAddress.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			alert("保存成功！");
			window.parent.location.href ="<%=contextPath%>/system/core/base/address/private/address/index.jsp";
		}else{
			alert(jsonRs.rtMsg);
		}
}

function checkForm(){
    return $("#form1").form('validate'); 
}

function getAddressGroup(){
	var url = "<%=contextPath%>/teeAddressGroupController/getAddressGroups.action";
	var para = {};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		createSelect(jsonRs.rtData);
	}else{
		alert(jsonRs.rtMsg);
	}
}

function createSelect(rtData){
	var selectObj = document.createElement("select");
	//$("<select name='groupId' ></select>");
	selectObj.setAttribute("name", "groupId");
		selectObj.setAttribute("class", "BigSelect");
	  var vOption=document.createElement("option");
	     vOption.setAttribute("value","-1"); 
	     vOption.appendChild(document.createTextNode("全部"));
	     selectObj.appendChild(vOption);
	$.each(rtData,function(i,v){
	     var vOption=document.createElement("option");
	     vOption.setAttribute("value",v.seqId);
	     vOption.appendChild(document.createTextNode(v.groupName)); 
	     selectObj.appendChild(vOption);
	});
	$("#groupSelect").append(selectObj);
}


</script>
 
</head>
<body onload="doInit()">
 <table border="0" width="100%" cellspacing="0" cellpadding="3"  align="">
    <tr>
      <td class="Big">
        
        <span class="Big3">
        	  查询联系人
         </span>
       </td>
    </tr>
  </table><br>

	<form  method="post" name="form1" id="form1" >
<table class="TableBlock" width="60%" align="center">
  <tr>
    <td nowrap class="TableData" width="120" >分组：</td>
    <td nowrap class="TableData" align="left" id="groupSelect">
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="120">姓名：</td>
    <td nowrap class="TableData" align="left">
        <input type='text' name="psnName" class="easyui-validatebox BigInput" maxlength="50"  size="20" />
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="120">性别：</td>
    <td nowrap class="TableData" align="left">
       <select id="sex" name="sex" class="BigSelect">
       			<option value="0">男</option>
       		    <option value="1">女</option>
       </select>
    </td>
   </tr>
   <tr>
		<td nowrap class="TableData" width="120">生日：</td>
		<td nowrap class="TableData">
		<input type="text" name="birthday" id="birthday" size="16" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate BigInput">
		</td>
	</tr>
   <tr>
		<td nowrap class="TableData" width="120">昵称：</td>
		<td nowrap class="TableData">
		<input type='text' name="nickName" class="easyui-validatebox BigInput" maxlength="50"  size="20"  />
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="120">单位名称：</td>
		<td nowrap class="TableData">
		<input type='text' name="deptName" class="easyui-validatebox BigInput" maxlength="50"  size="20"  />
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="120">工作电话：</td>
		<td nowrap class="TableData">
		<input type='text' name="telNoDept" class="easyui-validatebox BigInput" maxlength="50"  size="20" />
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="120">单位地址：</td>
		<td nowrap class="TableData">
		<input type='text' name="addDept" class="easyui-validatebox BigInput" maxlength="100"  size="45" />
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="120">家庭电话：</td>
		<td nowrap class="TableData">
		<input type='text' name="telNoHome" class="easyui-validatebox BigInput" maxlength="50"  size="20" />
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="120">家庭住址：</td>
		<td nowrap class="TableData">
		<input type='text' name="addHome" class="easyui-validatebox BigInput" maxlength="50"  size="50" />
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="120">手机：</td>
		<td nowrap class="TableData">
		<input type='text' name="mobilNo" class="easyui-validatebox BigInput" maxlength="50"  size="20"/>
		</td>
	</tr>
	<tr>
		<td nowrap class="TableData" width="120">备注：</td>
		<td nowrap class="TableData">
		<input type='text' name="notes" class="easyui-validatebox BigInput" maxlength="50"  size="20" />
		</td>
	</tr>
	
	 <tr>
	    <td nowrap  class="TableControl" colspan="2" align="center">
	        <input type="button" value="保存" class="btn btn-primary" title="保存" onclick="doSave()" >&nbsp;&nbsp;
	          <input type="reset" value="重置" class="btn btn-primary" title="重置" />
	        <input type='hidden' value='<%=id %>' name='sid'/>
	    </td>
   </tr>
</table>
  </form>

</body>
</html>
 