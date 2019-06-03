<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	String sid = request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<script>
var sid='<%=sid%>';
function doInit(){
	getHrCodeByParentCodeNo("PM_POLITICS" , "policy");
	getHrCodeByParentCodeNo("PM_RELATIOIN" , "relation");
	var url = contextPath+"/TeeHumanSocialController/getModelById.action?sid="+sid;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
	}else{
		alert(json.rtMsg);
	}
}

function commit(){
	if($("#form1").form("validate")){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeHumanSocialController/updateHumanSocial.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			top.$.jBox.tip(json.rtMsg,"info");
			return true;
		}
		top.$.jBox.tip(json.rtMsg,"error");
		return false;
	}
}
</script>

</head>
<body onload="doInit()" >
<form id="form1" name="form1">
	<table  style="width:100%;font-size:12px" class="TableBlock">
		<tr class='TableData'>
			<td>
				<b>成员名称：</b>
			</td>
			<td>
				<input type="text"  id="memberName" name="memberName" required="true" class="easyui-validatebox BigInput"/>
			</td>
			<td>
				<b>与本人关系：</b>
			</td>
			<td>
				<select class="BigSelect" id="relation" name="relation" >
				</select>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>出生日期：</b>
			</td>
			<td>
				<input type="text" id='birthdayDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='birthdayDesc' class="Wdate BigInput" />
			</td>
			<td>
				<b>政治面貌：</b>
			</td>
			<td>
				<select class="BigSelect" id="policy" name="policy" >
				</select>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>职业：</b>
			</td>
			<td>
				<input type="text" class="BigInput" id="occupation" name="occupation" />
			</td>
			<td>
				<b>担任职务：</b>
			</td>
			<td>
				<input type="text" class="BigInput" id="post" name="post"/>
			</td>
		</tr>
			<tr class='TableData'>
			<td>
				<b>联系电话（个人）：</b>
			</td>
			<td>
				<input type="text" class="BigInput" id="telNoPersonal" name="telNoPersonal"/>
			</td>
			<td>
				<b>联系电话（单位）：</b>
			</td>
			<td>
				<input type="text" class="BigInput" id="telNoCompany" name="telNoCompany"/>
			</td>
		</tr>
			<tr class='TableData'>
			<td>
				<b>工作单位：</b>
			</td>
			<td colspan="3">
				<input type="text" class="BigInput" id="workAt" name="workAt"/>
			</td>
		</tr>
			<tr class='TableData'>
			<td>
				<b>单位地址：</b>
			</td>
			<td colspan="3">
				<input type="text" class="BigInput" id="workAddress" name ="workAddress" />
			</td>
		</tr>
			<tr class='TableData'>
			<td>
				<b>家庭住址：</b>
			</td>
			<td colspan="3">
				<input type="text" class="BigInput" id="homeAddress" name="homeAddress"/>
			</td>
		</tr>
		<tr class='TableData'>
			<td>
				<b>备注：</b>
			</td>
			<td colspan="3">
				<textarea style="width:425px;height:100px" class="BigTextarea" id="remark" name="remark"></textarea>
			</td>
		</tr>
	</table>
		<input type="hidden" class="BigInput" id="humanDocSid" name="humanDocSid" />
	<input type="hidden" class="BigInput" id="sid" name="sid" value="<%=sid%>"/>
	</form>
</body>
</html>