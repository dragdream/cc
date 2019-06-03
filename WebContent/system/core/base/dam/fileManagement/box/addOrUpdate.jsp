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
<title>新建/编辑卷盒</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
</style>
<script type="text/javascript">
var sid = <%=sid%>;

function doInit(){
	//保管期限
	getSysCodeByParentCodeNo("DAM_RT" , "retentionPeriod");
	
	if(sid>0){
		getInfoBySid();
	}
}


/**
 * 新建或者更新外出
 */
function doSaveOrUpdate(){
	if(checkFrom()){
		var url = contextPath + "/damBoxController/addOrUpdate.action";
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
		 //判断盒号是否存在
		 if(isExitsBoxNo()){
			 return true; 
		 }else{
			 $.MsgBox.Alert_auto("盒号已存在，请重新填写！");
			 return false;
		 } 
	 } 
}

//根据主键获取详情
function getInfoBySid(){
	var url =   "<%=contextPath%>/damBoxController/getById.action";
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
	

//判断盒号是否存在
function  isExitsBoxNo(){
	var boxNo=$("#boxNo").val();
	var url=contextPath+"/damBoxController/checkBoxNo.action";
	var json=tools.requestJsonRs(url,{boxNo:boxNo,sid:sid});
	return json.rtState;
}
</script>

</head>
<body onload="doInit();" style="background-color: #f2f2f2">
<form id="form1" name="form1" method="post">
	<table class="TableBlock" width="100%" align="center" >
		<tr>
			<td nowrap class="TableData"  style="width: 100px;text-indent: 10px;">盒号：</td>
			<td class="TableData">
				<input type="text" id="boxNo" name="boxNo" class="BigInput" required="true" style="width: 300px;height: 23px" />
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" style="width: 100px;text-indent: 10px;">年份：</td>
			<td class="TableData">
				<input type="text" id="year" name="year" class="BigInput" required="true" style="width: 300px;height: 23px"/>
			</td>
		</tr>	
		<tr>
			<td nowrap class="TableData" style="width: 100px;text-indent: 10px;">密级：</td>
			<td class="TableData">
				<select id="mj" name="mj" style="width: 300px;height: 23px">
				    <option value="">空</option>
				    <option value="内部">内部</option>
				    <option value="秘密">秘密</option>
				    <option value="机密">机密</option>
				    <option value="绝密">绝密</option>
				</select>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" style="width: 100px;text-indent: 10px;">保管期限：</td>
			<td class="TableData">
			   <select id="retentionPeriod" name="retentionPeriod" style="width: 300px;height: 23px">
				</select>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData" style="width: 100px;text-indent: 10px;">备注：</td>
			<td class="TableData">
				<textarea rows="6" cols="50" name="remark" ia="remark"></textarea>
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
