<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//问题主键
  int  sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<html xmlns="http://www.w3.org/1999/xhtml" >
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
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
   <div id="toolbar" class="topbar clearfix" >
       <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/project/img/icon_wentixiangqing.png">
		<span class="title">问题详情 <span id="totalMail" class="attach"></span></span>
	   </div>
   </div>
   <div>
      <table class="TableBlock_page" width="100%">
        <tr>
           <td width="15%" style="text-indent: 10px" class="TableData">问题名称：</td>
           <td  width="85%" id="questionName" name="questionName" class="TableData"></td>
        </tr>
        <tr>
           <td style="text-indent: 10px" class="TableData">优先级：</td>
           <td id="questionLevel" name="questionLevel" class="TableData"></td>
        </tr>
        <tr>
           <td style="text-indent: 10px" class="TableData">问题描述：</td>
           <td id="questionDesc" name="questionDesc" class="TableData"></td>
        </tr>
        <tr>
           <td style="text-indent: 10px" class="TableData">处理人：</td>
           <td id="operatorName" name="operatorName" class="TableData"></td>
        </tr>
        <tr>
           <td style="text-indent: 10px" class="TableData">创建时间：</td>
           <td id="createTimeStr" name="createTimeStr" class="TableData"></td>
        </tr>
        <tr>
           <td style="text-indent: 10px" class="TableData">问题状态：</td>
           <td id="status" name="status" class="TableData"></td>
        </tr>
        <tr>
           <td style="text-indent: 10px" class="TableData">处理结果：</td>
           <td id="result" name="result" class="TableData"></td>
        </tr>
        <tr>
           <td style="text-indent: 10px" class="TableData">处理时间：</td>
           <td id="handleTimeStr" name="handleTimeStr" class="TableData"></td>
        </tr>
      </table>
   
   </div>
   
</body>
</html>