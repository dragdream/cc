<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
  //获取分类主键
  int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分类详情</title>
</head>
<script>
var sid=<%=sid%>;
function doInit(){
	var url=contextPath+"/supTypeController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
	}
}




</script>
<body onload="doInit()" style="background-color: #f2f2f2">
  <table class="TableBlock" style="width: 100%">
    <tr>
       <td style="text-indent: 10px;width: 20%">分类名称：</td>
       <td name="typeName" id="typeName"></td>
    </tr>
    <tr>
       <td style="text-indent: 10px">排序号：</td>
       <td name="orderNum" id="orderNum"></td>
    </tr>
    <tr>
       <td style="text-indent: 10px">所属人员：</td>
       <td name="userNames" id="userNames"></td>
    </tr>
    <tr>
       <td style="text-indent: 10px">所属角色：</td>
       <td name="roleNames" id="roleNames"></td>
    </tr>
    <tr>
       <td style="text-indent: 10px">所属部门：</td>
       <td name="deptNames" id="deptNames"></td>
    </tr>
  </table>
</body>
</html>