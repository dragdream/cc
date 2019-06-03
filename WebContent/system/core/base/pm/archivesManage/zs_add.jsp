<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String humanDocSid=request.getParameter("humanDocSid");
	String personName = request.getParameter("personName");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script>
var humanDocSid='<%=humanDocSid%>';
var personName='<%=personName%>';
function doInit(){
	getHrCodeByParentCodeNo("PM_CERT_TYPE" , "certType");
	getHrCodeByParentCodeNo("PM_CERT_ATTR" , "certAttr");
}

function commit(callback){
	if(checkForm() &&$ ("#form1").valid()){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeHumanCertController/addHumanCert.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			callback(json.rtState);
		}else{
		   $.MsgBox.Alert_auto(json.rtMsg);
		}
	}
}

function checkForm(){
	if($("#certCode").val()=="" || $("#certCode").val()=="null" || $("#certCode").val()==null){
		$.MsgBox.Alert_auto("请输入证书编号！");
		return false;
	}
	  if($("#validTimeDesc").val() && $("#endTimeDesc").val()){
		    if(document.getElementById("validTimeDesc").value > document.getElementById("endTimeDesc").value){
		    	$.MsgBox.Alert_auto("生效时间不能大于结束时间！");
		      return false;
			 }
	  }
	return true;
}
</script>
<style>
html{
   background-color: #f2f2f2;
}
	td{
		line-height:28px;
		min-height:28px;
	}
</style>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px;background-color: #f2f2f2;">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/pm/img/icon_dabj.png">
		<span class="title">新增/编辑  <%= personName %> 的证书</span>
	</div>
</div>
<form id="form1" name="form1">
	<table class="TableBlock" width="100%" align="center">
	    <tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/rsdagl/dagl/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		       <B style="color: #0050aa">基本信息</B>
		   </td>
	     </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				证照编号<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input style="width: 350px;height: 23px;" type="text" id="certCode" name="certCode" required="true" class="easyui-validatebox BigInput"/>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				证照类型：
			</td>
			<td>
				<select style="width: 200px;height: 23px;" class="BigSelect" id="certType" name="certType">
				</select>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				证照名称：
			</td>
			<td>
				<input style="width: 350px;height: 23px;" type="text" class="BigInput" id="certName" name="certName"/>
			</td>
	    </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				取证日期：
			</td>
			<td>
				<input style="width: 200px;height: 23px;" type="text" id='getTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="getTimeDesc" class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				生效日期：
			</td>
			<td>
				<input style="width: 200px;height: 23px;" type="text" id='validTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name='validTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				结束日期：
			</td>
			<td>
				<input style="width: 200px;height: 23px;" type="text" id='endTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name='endTimeDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				证件属性：
			</td>
			<td>
				<select style="width: 200px;height: 23px;" class="BigSelect" id="certAttr" name="certAttr">
				</select>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				发证机构：
			</td>
			<td>
				<textarea style="width:425px;height:100px" class="BigTextarea" id="certOrg" name="certOrg"></textarea>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				备注：
			</td>
			<td>
				<textarea style="width:425px;height:100px" class="BigTextarea" id="remark" name="remark" ></textarea>
			</td>
		</tr>
	</table>
	<input type="hidden" class="BigInput" id="humanDocSid" name="humanDocSid" value="<%=humanDocSid%>"/>
	</form>
</body>
</html>