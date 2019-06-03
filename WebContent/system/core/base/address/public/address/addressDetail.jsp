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
<title>添加通讯组</title>
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/easyui2.0.jsp" %>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

var id = '<%=id%>';
function doInit(){
	loadData(id);
}


function loadData(id){
	var url = "<%=contextPath%>/teeAddressController/getAddressById.action";
	var jsonRs = tools.requestJsonRs(url,{"id":id});
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		if(data){
			bindJsonObj2Cntrl(data);
		}
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}
</script>
 
</head>
<body onload="doInit()">
<table class="TableBlock" width="100%" align="center">
    <tr>
      <td nowrap class="TableHeader" colspan="5"><b>&nbsp;联系人信息</b></td>
    </tr>
    <tr>
    	<!-- 名片单元格 -->
    	<td class="TableData" width="40%" rowspan="6" style="background-color:rgb(240, 240, 240);">
    		&nbsp;&nbsp;&nbsp;<u><font style="FONT-SIZE:9pt;" color="blue"><b><span id="psnName"></span></b></font></u>
    		<font size="2"><b>&nbsp;&nbsp;&nbsp;手机：</b></font><font color=blue><span id="mobilNo"></span></font><br>
    		<font size="2" ><b>&nbsp;&nbsp;&nbsp;电子邮件：</b></font><font color=blue><span id="email"></span></font><br>
    		<font size="2" ><b>&nbsp;&nbsp;&nbsp;QQ号码：</b></font><font color=blue><span id="oicqNo"></span></font><br>
    		<font  size="2"><b>&nbsp;&nbsp;&nbsp;MSN：</b></font><font color=blue><span id="icqNo"></span></font><br>
    		<font  size="2"><b>&nbsp;&nbsp;&nbsp;工作电话：</b></font><font color=blue><span id="telNoDept"></span></font><br>
    		<font  size="2"><b>&nbsp;&nbsp;&nbsp;工作传真：</b></font><font color=blue><span id="faxNoDept"></span></font><br>
    		<font  size="2"><b>&nbsp;&nbsp;&nbsp;家庭电话：</b></font><font color=blue><span id="telNoHomes"></span></font>&nbsp;&nbsp;
    		<font  size="2"><b>&nbsp;&nbsp;&nbsp;小灵通：</b></font><font color=blue><span id="bpNo"></span></font><br>
    	</td>
      <td nowrap class="TableData" width="100"> 性别：</td>
      <td class="TableData" id="sex"></td>
    </tr>
    <tr>
      <td nowrap class="TableData"> 生日：</td>
      <td class="TableData" id="birthday"></td>
    </tr>
    <tr>
      <td nowrap class="TableData"> 昵称：</td>
      <td class="TableData" id="nickName"></td>
    </tr>
    <tr>
      <td nowrap class="TableData"> 职务：</td>
      <td class="TableData" id="ministration"></td>
    </tr>
    <tr>
      <td nowrap class="TableData"> 配偶：</td>
      <td class="TableData" id="mate"></td>    
    </tr>
    <tr>
      <td nowrap class="TableData"> 子女：</td>
      <td class="TableData" id="child"></td>    
    </tr>
 </table>
 <table class="TableBlock" width="100%" align="center">
    <tr>
      <td nowrap class="TableHeader" colspan="5"><b>&nbsp;工作单位信息</b></td>
    </tr>
    <tr>
      <td nowrap class="TableData" width="100"> 单位名称：</td>
      <td class="TableData" colspan=4 id="deptName"></td>
    </tr>
    <tr>
      <td nowrap class="TableData"> 单位地址：</td>
      <td class="TableData" colspan=4 id="addDept"></td>
    </tr>
    <tr>
      <td nowrap class="TableData"> 单位邮编：</td>
      <td class="TableData" colspan=4 id="postNoDept"></td>
    </tr>
  </table>
 <table class="TableBlock" width="100%" align="center">
    <tr>
      <td nowrap class="TableHeader" colspan="5"><b>&nbsp;家庭信息</b></td>
    </tr>
    <tr>
      <td nowrap class="TableData" width="100"> 家庭住址：</td>
      <td class="TableData" colspan=4 id="addHome"></td>
    </tr>
    <tr>
      <td nowrap class="TableData"> 家庭邮编：</td>
      <td class="TableData" colspan=4 id="postNoHome"></td>
    </tr>
    <tr>
      <td nowrap class="TableData"> 家庭电话：</td>
      <td class="TableData" colspan=4 id="telNoHome"></td>
    </tr>
    <tr>
      <td nowrap class="TableData"> 备注：</td>
      <td class="TableData" colspan=4 id="notes"></td>
    </tr>
    <tr>
      <td nowrap class="TableData" colspan="5">
        
      </td>
    </tr>
  </table>

</body>
</html>
 