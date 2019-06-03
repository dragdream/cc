<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	String sid = request.getParameter("sid"); 
	String bookNo = request.getParameter("bookNo"); 
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date();
	String dateStr = sdf.format(date);
	long time=0;
	int day=30;
	time=(date.getTime()/1000)+60*60*24*day;
	Date newDate=new Date();
	newDate.setTime(time*1000); 
	String newDateStr = sdf.format(newDate);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<title>借书登记</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script Language="JavaScript">
function checkForm(){
	if($("#borrowDate").val()==""){
		alert("借书时间不能为空！");
		return false;
	}
	if($("#returnDate").val()==""){
		alert("还书时间不能为空！");
		return false;
	}
	//alert($("#borrowDate").val()+","+$("#returnDate").val());
	if($("#borrowDate").val() > $("#returnDate").val()){
        alert("借书时间不能大于还书时间！");
        return false;
    }
	return true;
}

function LoadWindow2()
{
	temp=document.form1.TO_ID.value;
  URL="bookno_select/?USER_ID=" + temp +"&LEND_FLAG=" +1;
  loc_x=document.body.scrollLeft+event.clientX-event.offsetX-100;
  loc_y=document.body.scrollTop+event.clientY-event.offsetY+170;
  window.showModalDialog(URL,self,"edge:raised;scroll:0;status:0;help:0;resizable:1;dialogWidth:320px;dialogHeight:245px;dialogTop:"+loc_y+"px;dialogLeft:"+loc_x+"px");
}

function doInit(){
	
}

function doSave(){
	if (checkForm()){
		var url = "<%=contextPath%>/bookManage/saveBorrowInfo.action";	
		var para = {bookNo:$("#bookNo").val(),borrowDate:$("#borrowDate").val(),returnDate:$("#returnDate").val(),borrowRemark:$("#borrowRemark").val()};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			alert(jsonRs.rtMsg);
			CloseWindow();
		}
	}
}
</script>
</head>
<body class="bodycolor" topmargin="5" onload="doInit();" style="background-color: #f4f4f4">
<!-- <table border="0" width="100%" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="Big"><h4>借书登记</h>
    </td>
  </tr>
</table> -->
  <form action=""  method="post" name="form1" id="form1">
<table class="TableBlock"  width="400" align="center" >

   <tr style="display:none;">
    <td nowrap class="TableData" width="120">借书人：</td>
    <td nowrap class="TableData">
      <input type="hidden" name="buserId" value=""> 	
      <input type="text" name="buserName" size="13" class="SmallStatic BigInput" value="" readonly>&nbsp;
     </td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="120">图书编号：</td>
    <td class="TableData">
      <input type="text" name="bookNo" id="bookNo" class="SmallStatic BigInput" size="13" maxlength="100" readonly value="<%=bookNo%>">&nbsp;
    </td> 
   </tr>
   <tr>
    <td nowrap class="TableData">借书日期：</td>
    <td nowrap class="TableData">
      <input type="text" name="borrowDate" id="borrowDate" size="11" maxlength="19" class="BigInput" value="<%=dateStr %>" onClick="WdatePicker()">
      
    </td> 
   </tr>
   <tr>
    <td nowrap class="TableData" width="120">归还日期：</td>
    <td nowrap class="TableData">
      <input type="text" name="returnDate" id="returnDate" size="11" maxlength="19" class="BigInput" value="<%=newDateStr %>" onClick="WdatePicker()">
      
    </td> 
   </tr>
   <tr>
    <td nowrap class="TableData" width="120">备注：</td>
    <td nowrap class="TableData">
      <textarea name="borrowRemark" id="borrowRemark" class= "BigTextarea" cols="35" rows="3"></textarea>
    </td>
   </tr>
  <!--  <tr>
    <td nowrap  class="TableHeader" colspan="2" align="center">
        <input type="button" onclick="doSave();" value="确定" class="btn btn-success" title="保存借书信息" name="button">&nbsp;&nbsp;
        <input type="button" value="关闭" class="btn btn-primary" onclick="javascript:CloseWindow();">
    </td>
   </tr> -->

</table>
  </form>
</body>
</html>