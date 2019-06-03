<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<%
   //项目主键
   String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
   //批注主键
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" style="background-color: #f2f2f2">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/address_cascade.js"></script>
<title>新建/编辑批注</title>
<script>
var projectId="<%=projectId%>";
var sid=<%=sid%>;
//初始化
function  doInit(){
	
	if(sid>0){
		getInfoBySid();
	}
	
}
//验证
function check(){
	//获取批注内容
	var content=$("#content").val();
	if(content==null||content==""){
		$.MsgBox.Alert_auto("请填写批注内容");
		return false;
	}
	return true;
}
//保存
function commit(){
	if(check()){
		var content=$("#content").val();
		var url=contextPath+"/projectNotationController/addOrUpdate.action";
		var json=tools.requestJsonRs(url,{projectId:projectId,sid:sid,content:content});
		return json.rtState;
	}
	
}
//根据主键和获取批注详情
function getInfoBySid(){
	var url=contextPath+"/projectNotationController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
	}
}
</script>
<body onload="doInit()" style="background-color: #f2f2f2;overflow: hidden;">
   <table class="TableBlock" style="margin-top: 10px" width="100%">
      <tr>
         <td class="TableData" style="text-indent: 10px">批注内容：</td>
         <td class="TableData">
            <textarea rows="8" cols="60" name="content" id="content"></textarea>
         </td>
      </tr>
   </table>

</body>
</html>
