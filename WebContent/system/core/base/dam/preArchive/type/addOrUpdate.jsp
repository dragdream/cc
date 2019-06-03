<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	int  sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<title>新建/编辑预归档分类</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
</style>
<script type="text/javascript">
var sid = <%=sid%>;

function doInit(){
	if(sid>0){
		getInfoBySid();
	}
}


/**
 * 新建或者更新外出
 */
function doSaveOrUpdate(){
	if(checkFrom()){
		var url = contextPath + "/preArchiveTypeController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		var json = tools.requestJsonRs(url,para);
		return json;
	}
}
/**
 * 校验
 */
function checkFrom(){
	 var check = $("#form1").valid(); 
	 if(!check ){
		 return false; 
	 }else{
		 return true; 
	 } 
}

//根据主键获取详情
function getInfoBySid(){
	var url =   "<%=contextPath%>/preArchiveTypeController/getById.action";
	var para = {sid : sid};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
		}
	} else {
		$.MsgBox.Alert_auto("数据获取失败！");
	}
}
	


</script>

</head>
<body onload="doInit();" style="background-color: #f2f2f2">
<form id="form1" name="form1" method="post">
	<table class="TableBlock" width="100%" align="center" >
		<tr>
			<td nowrap class="TableData"  style="width: 100px;text-indent: 10px;">排序号：</td>
			<td class="TableData">
				<input type="text" id="sortNo" name="sortNo" class="BigInput" required="true" style="width: 300px;height: 23px" no_negative_number="true"/>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" style="width: 100px;text-indent: 10px;">分类名称：</td>
			<td class="TableData">
				<input type="text" id="typeName" name="typeName" class="BigInput" required="true" style="width: 300px;height: 23px"/>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" style="width: 100px;text-indent: 10px;">档案管理员：</td>
			<td class="TableData">
			    <input type="hidden" name="managerId" id="managerId"    />
			    <input type="text" name="managerName" id="managerName"  style="width: 250px;height: 23px"  required  readonly="readonly" />
				<span class='addSpan'>
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectSingleUser(['managerId','managerName'],'14')" value="选择"/>
					   &nbsp;&nbsp;
					   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('managerId','managerName')" value="清空"/>
					</span>
			</td>
		</tr>
	</table>
	<input id="sid" name="sid" type="hidden" value="<%=sid %>"> 
</form>
</body>
<script>
$("#form1").validate();
</script>
</html>
