<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//问题主键
  int  sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>问题详情</title>
</head>
<script>
var sid=<%=sid%>;
//获取问题详情
function doInit(){
   var url=contextPath+"/projectQuestionController/getInfoBySid.action";
   var json=tools.requestJsonRs(url,{sid:sid});
   if(json.rtState){
	   var data=json.rtData;
	   bindJsonObj2Cntrl(data);
	   if(data.status==0){
		   $("#status").html("待处理");
	   }else{
		   $("#status").html("已处理"); 
	   }
   }
}

</script>
<body onload="doInit()" style="background-color: #f2f2f2">
   <div>
      <table class="TableBlock" width="100%">
        <tr>
           <td width="15%" style="text-indent: 10px">问题名称：</td>
           <td  width="85%" id="questionName" name="questionName"></td>
        </tr>
        <tr>
           <td style="text-indent: 10px">优先级：</td>
           <td id="questionLevel" name="questionLevel"></td>
        </tr>
        <tr>
           <td style="text-indent: 10px">问题描述：</td>
           <td id="questionDesc" name="questionDesc"></td>
        </tr>
        <tr>
           <td style="text-indent: 10px">处理人：</td>
           <td id="operatorName" name="operatorName"></td>
        </tr>
        <tr>
           <td style="text-indent: 10px">创建时间：</td>
           <td id="createTimeStr" name="createTimeStr"></td>
        </tr>
        <tr>
           <td style="text-indent: 10px">问题状态：</td>
           <td id="status" name="status"></td>
        </tr>
        <tr>
           <td style="text-indent: 10px">处理结果：</td>
           <td id="result" name="result"></td>
        </tr>
        <tr>
           <td style="text-indent: 10px">处理时间：</td>
           <td id="handleTimeStr" name="handleTimeStr"></td>
        </tr>
      </table>
   
   </div>
   
</body>
</html>