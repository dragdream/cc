<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="background-color: #f2f2f2">
<head>
<%@ include file="/header/header2.0.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>申诉详情</title>
<script type="text/javascript">
var sid=<%=sid %>;
//初始化
function doInit(){
	var  url=contextPath+"/TeeDutyComplaintController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var data=json.rtData;
		bindJsonObj2Cntrl(data);
		
		if(data.registerNum==1){
			$("#registerNumDesc").html("第一次登记");
		}else if(data.registerNum==2){
			$("#registerNumDesc").html("第二次登记");
		}else if(data.registerNum==3){
			$("#registerNumDesc").html("第三次登记");
		}else if(data.registerNum==4){
			$("#registerNumDesc").html("第四次登记");
		}else if(data.registerNum==5){
			$("#registerNumDesc").html("第五次登记");
		}else if(data.registerNum==6){
			$("#registerNumDesc").html("第六次登记");
		}
		
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
          <td id="registerNumDesc"></td>
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