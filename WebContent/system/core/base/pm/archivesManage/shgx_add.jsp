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
	getHrCodeByParentCodeNo("PM_POLITICS" , "policy");
	getHrCodeByParentCodeNo("PM_RELATIOIN" , "relation");
}
function commit(callback){
	if(checkForm()&& $("#form1").valid()){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeHumanSocialController/addHumanSocial.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			callback(json.rtState);
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
}
function checkForm(){
	if($("#memberName").val()=="" || $("#memberName").val()=="null" || $("#memberName").val()==null){
		$.MsgBox.Alert_auto("请输入成员姓名！");
		return false;
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
		<span class="title">新增/编辑  <%= personName %> 的社会关系</span>
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
				成员名称<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" type="text"  id="memberName" name="memberName" required class="BigInput"/>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				与本人关系：
			</td>
			<td>
				<select  style="height: 23px;width: 200px;" class="BigSelect" id="relation" name="relation" >
				</select>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				出生日期：
			</td>
			<td>
				<input  style="height: 23px;width: 200px;" type="text" id='birthdayDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='birthdayDesc' class="Wdate BigInput" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				政治面貌：
			</td>
			<td>
				<select  style="height: 23px;width: 200px;" class="BigSelect" id="policy" name="policy" >
				</select>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				职业：
			</td>
			<td>
				<input  style="height: 23px;width: 350px;" type="text" class="BigInput" id="occupation" name="occupation" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				担任职务：
			</td>
			<td>
				<input  style="height: 23px;width: 350px;" type="text" class="BigInput" id="post" name="post"/>
			</td>
	    </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				联系电话（个人）：
			</td>
			<td>
				<input  style="height: 23px;width: 350px;" type="text" class="BigInput" id="telNoPersonal" name="telNoPersonal"/>
			</td>
	    </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				联系电话（单位）：
			</td>
			<td>
				<input  style="height: 23px;width: 350px;" type="text" class="BigInput" id="telNoCompany" name="telNoCompany"/>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				工作单位：
			</td>
			<td>
				<input  style="height: 23px;width: 350px;" type="text" class="BigInput" id="workAt" name="workAt"/>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				单位地址：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" type="text" class="BigInput" id="workAddress" name ="workAddress" />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				家庭住址：
			</td>
			<td>
				<input  style="height: 23px;width: 350px;" type="text" class="BigInput" id="homeAddress" name="homeAddress"/>
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				备注：
			</td>
			<td>
				<textarea style="width:425px;height:100px" class="BigTextarea" id="remark" name="remark"></textarea>
			</td>
		</tr>
	</table>
	<input type="hidden" class="BigInput" id="humanDocSid" name="humanDocSid" value="<%=humanDocSid%>"/>
	</form>
</body>
</html>