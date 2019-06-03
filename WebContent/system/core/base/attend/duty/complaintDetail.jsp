<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String remarkTimeStr=TeeStringUtil.getString(request.getParameter("remarkTimeStr"));
   int registerNum=TeeStringUtil.getInteger(request.getParameter("registerNum"), 0);
   int userId=TeeStringUtil.getInteger(request.getParameter("userId"),0);
   String desc="";
   if(registerNum==1){
	   desc="第一次登记";
   }else if(registerNum==2){
	   desc="第二次登记";
   }else if(registerNum==3){
	   desc="第三次登记";
   }else if(registerNum==4){
	   desc="第四次登记";
   }else if(registerNum==5){
	   desc="第五次登记";
   }else if(registerNum==6){
	   desc="第六次登记";
   }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="background-color: #f2f2f2">
<head>
<%@ include file="/header/header2.0.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>申诉详情</title>
<script type="text/javascript">
var remarkTimeStr="<%=remarkTimeStr %>";
var registerNum=<%=registerNum%>;
var userId=<%=userId %>;
//初始化
function doInit(){
	var  url=contextPath+"/TeeDutyComplaintController/view.action";
	var json=tools.requestJsonRs(url,{remarkTimeStr:remarkTimeStr,registerNum:registerNum,userId:userId});
	if(json.rtState){
		var data=json.rtData;
		bindJsonObj2Cntrl(data);
		
		if(data.attachList.length>0){
			var attaches = data.attachList;
			for(var i=0;i<attaches.length;i++){
				var fileItem = tools.getAttachElement(attaches[i]);
				$("#attachments").append(fileItem);
			}
		}
	}
}
</script>
</head>
<body onload="doInit();" style="background-color: #f2f2f2">
   <table class="TableBlock" width="100%">
       <tr>
          <td style="text-indent: 15px;width: 20%">记录时间：</td>
          <td id="remarkTimeStr" name="remarkTimeStr"></td>
       </tr>
       <tr>
          <td style="text-indent: 15px;width: 20%">登记次数：</td>
          <td><%=desc %></td>
       </tr>
       <tr>
          <td style="text-indent: 15px;width: 20%">审批人员：</td>
          <td id="approverName" name="approverName"></td>
       </tr>
       <tr>
          <td style="text-indent: 15px;width: 20%">审批状态：</td>
          <td id="statusDesc" name="statusDesc"></td>
       </tr>
       <tr>
          <td style="text-indent: 15px;width: 20%">申诉时间：</td>
          <td id="createTimeStr" name="createTimeStr"></td>
       </tr>
       <tr>
          <td style="text-indent: 15px;width: 20%">申诉原因：</td>
          <td id="reason" name="reason"></td>
       </tr>
       <tr>
          <td style="text-indent: 15px;width: 20%">附件：</td>
          <td><span id="attachments"></span></td>
       </tr>
   </table>
</body>
</html>