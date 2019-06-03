<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);//电子围栏主键
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<%@ include file="/header/userheader.jsp" %>
<title>新建/编辑电子围栏信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
</style>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>


<script type="text/javascript">

var sid = <%=sid%>;

function doInit()
{
	if(sid > 0){
		getInfoBySid(sid);
	}
}


/**
 * 新建或者更新外出
 */
function doSaveOrUpdate(){
	if(checkFrom()){
		var url = contextPath + "/TeeFootPrintRangeController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonObj = tools.requestJsonRs(url,para);
		return jsonObj;
	}
}
/**
 * 校验
 */
function checkFrom(){
	
	 var check = $("#form1").valid();
	 if(!check ){
		 return false; 
	 }
	 return true; 
}



/**
 * 根据主键获取详情
 */
function getInfoBySid(sid){
	
	var url=contextPath+"/TeeFootPrintRangeController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var data=json.rtData;
		bindJsonObj2Cntrl(data);	
	}else{
		$.MsgBox.Alert_auto("电子围栏信息获取失败！");
	}
}
	




</script>

</head>
<body onload="doInit();" style="background-color:#f2f2f2">
<form id="form1" name="form1" method="post">
	<table class="TableBlock" width="100%" align="center" >
	    <tr>
			<td nowrap class="TableData" width="100" style="text-indent:10px">围栏名称：</td>
			<td class="TableData">
				<input type="text" id="rangeName" name="rangeName" class="BigInput"  style="width: 300px;height: 23px" required /></td>
		</tr>
		<tr>
			<td nowrap class="TableData" width="100" style="text-indent:10px">用户权限：</td>
			<td class="TableData">
			    <input name="userIds" id="userIds" type="hidden"/>
				<textarea class="BigTextarea readonly" id="userNames" name="userNames" style="height:80px;width:400px"  readonly></textarea>
				<span class='addSpan'>
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectUser(['userIds','userNames'],'14')" value="选择"/>
				   &nbsp;&nbsp;
				   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('userIds','userNames')" value="清空"/>
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
