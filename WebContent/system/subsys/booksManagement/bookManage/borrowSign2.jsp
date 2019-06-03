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
    if($("#bookNo").val()=="" || $("#bookNo").val()==0){
        alert("请选择图书编号！");
        return false;
    }
	if($("#postUserIds").val()==""){
        alert("借书人不能为空！");
        return false;
    }
	if($("#borrowDate").val()==""){
		alert("借书时间不能为空！");
		return false;
	}
	if($("#returnDate").val()==""){
		alert("还书时间不能为空！");
		return false;
	}

    var url1 = "<%=contextPath %>/bookManage/checkNumManage.action";
    var para1 =  {bookNo:$("#bookNo").val()};
    var jsonRs1 = tools.requestJsonRs(url1,para1);
    if(jsonRs1.rtState){
        if(jsonRs1.rtData <=0){
            alert("该图书已全部借出！");
            return false;
        }
    }
	return true;
}



function doInit(){
	booList();
}

function doSave(){
	if (checkForm()){
		var url = "<%=contextPath%>/bookManage/saveBorrowSign.action";
		var para = {postUserIds:$("#postUserIds").val(),bookNo:$("#bookNo").val(),borrowDate:$("#borrowDate").val(),returnDate:$("#returnDate").val(),borrowRemark:$("#borrowRemark").val()};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			alert(jsonRs.rtMsg);
			window.close();
			//location.reload();
		}
		return true;
	}else{
		return false;
	}
}
function booList(){
	var url = "<%=contextPath%>/bookManage/booList.action";
	var jsonRs = tools.requestJsonRs(url);
	var html="<option value='0'>请选择</option>";
	if(jsonRs.rtState){
		var data=jsonRs.rtData;
		console.info(data);
		for(var i=0;i<data.length;i++){
			html+="<option value='"+data[i].bookNo+"'>"+data[i].bookNo+"("+data[i].bookName+")</option>";
		}
		$("#bookNo").html(html);
	}
}
</script>
</head>
<body onload="doInit();" style="background-color: #f4f4f4;">
 <form name="form1" id="form1">
    <table class="TableBlock">
        <tr>
          <td nowrap class="TableData">借书人：</td>
          <td class="TableData" colspan="3">
            <input type="hidden" name="postUserIds" id="postUserIds" value="">
              <input name="postUserNames" id="postUserNames" class="SmallStatic BigInput" wrap="yes" readonly></textarea>
              <a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['postUserIds', 'postUserNames']);">添加</a>
              <a href="javascript:void(0);" class="orgClear" onClick="$('postUserIds').value='';$('postUserNames').value='';">清空</a>
          </td>
        </tr> 
   <tr>
    <td nowrap class="TableData" width="120">图书编号：</td>
    <td class="TableData">
         <select id="bookNo" name="bookNo" class="BigSelect">
           
         </select>
    </td> 
   </tr>
   <tr>
    <td nowrap class="TableData">借书日期：</td>
    <td nowrap class="TableData">
      <input type="text" name="borrowDate" id="borrowDate" size="11" maxlength="19" class="BigInput" value="<%=dateStr %>" onClick="WdatePicker()">
      为空为当前日期
    </td> 
   </tr>
   <tr>
    <td nowrap class="TableData" width="120">归还日期：</td>
    <td nowrap class="TableData">
      <input type="text" name="returnDate" id="returnDate" size="11" maxlength="19" class="BigInput" value="<%=newDateStr %>" onClick="WdatePicker()">
      为空为从借书之日起30天的日期
    </td> 
   </tr>
   <tr>
    <td nowrap class="TableData" width="120">备注：</td>
    <td nowrap class="TableData">
      <textarea name="borrowRemark" id="borrowRemark" class= "BigTextarea" cols="35" rows="3"></textarea>
    </td>
   </tr>
</table>
  </form>
</body>
</html>