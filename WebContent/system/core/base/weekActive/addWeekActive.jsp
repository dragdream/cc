<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Date date = new Date();
	SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	String curDateStr = dateFormat1.format(date);
	String dateStr = request.getParameter("date");
	String dateType = request.getParameter("dateType");//1-上午；2-下午
	if(TeeUtility.isNullorEmpty(dateStr)){
		dateStr = curDateStr;
	}else{
		if("1".equals(dateType)){
			dateStr += " 08:30";
		}else{
			dateStr += " 13:30";
		}
	}
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0) ;


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>新建周活动安排</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
var sid = "<%=sid%>";
function doInit(){
	if(sid>0){
		getInfoById(sid);
	}
}


/* 查看详情 */
function getInfoById(sid){
	var url =   "<%=contextPath%>/weekActiveController/getInfoById.action";
	var para = {sid : sid};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc) {
			bindJsonObj2Cntrl(prc);
		}
	} else {
		$.MsgBox.Alert(jsonObj.rtMsg);
	}
}


function checkForm(){
    var check= $("#form1").valid(); 
    if(!check){
    	return false;
    }
    return true;
}

/**
 * 保存数据
 */
function doSaveOrUpdate(callback){
  if(checkForm()){
    var url = "<%=contextPath %>/weekActiveController/addOrUpdate.action";
    var para =  tools.formToJson($("#form1"));
    var jsonRs = tools.requestJsonRs(url,para);
    if(jsonRs.rtState){
      callback(jsonRs.rtState);
    }else{
    	$.MsgBox.Alert(jsonRs.rtMsg);
    }
  }
}

</script>
<style>
.TableBlock tr>td>textarea{
	margin:0;
}

</style>
</head>
<body onload="doInit();" style="overflow: hidden;padding: 10px;background-color: #f2f2f2;">
<form action=""  method="post" name="form1" id="form1">
<input type="hidden" name="sid" id="sid" value="<%=sid%>">
<table align="center" width="100%" class="TableBlock" >
	<tr>
		<td style="text-indent:10px;" nowrap class="TableData"  width="10%;" >时间：<font style='color:red'>*</font></td>
		<td class="TableData" width="60%;" colspan="3">
			<input style="width:300px;font-family: MicroSoft YaHei;height: 25px;" type="text" name="activeStart" id="activeStart" size="20" newMaxLength="20" value="<%=dateStr %>" class="BigInput Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" required value="" readonly>
		</td>
	</tr>
	<tr>
		<td style="text-indent:10px;" nowrap class="TableData"  width="15%;" >出席：<font style='color:red'>*</font></td>
		<td class="TableData" style="padding-top: 10px">
			<input type="hidden" name="activeUserId" id="activeUserId" value="">
			<textarea style="width:300px;overflow-y: auto;font-family: MicroSoft YaHei;height: 100px;font-size: 12px;"  name="activeUserName" id="activeUserName" class="SmallStatic" required   rows="4" cols="50" readonly ></textarea>
			  <span class='addSpan'>
			   <img style="cursor: pointer;" src="/common/zt_webframe/imgs/grbg/zhdapgl/add.png" onClick="selectUser(['activeUserId', 'activeUserName']);" value="添加"/>
			 &nbsp;&nbsp;
			  <img style="cursor: pointer;" src="/common/zt_webframe/imgs/grbg/zhdapgl/clear.png" onClick="$('#activeUserId').val('');$('#activeUserName').val('');" value="清空"/>
		     </span>
		</td>
	</tr>
	<tr>
		<td style="text-indent:10px;" nowrap class="TableData" >内容：<font style='color:red'>*</font></td>
		<td class="TableData" style="padding-top: 10px;padding-bottom: 10px"  colspan="3">
			<textarea  style="width:300px;resize:none;height: 100px;font-family: MicroSoft YaHei;font-size: 12px;" id="activeContent" name="activeContent" cols="50" rows="5" newMaxLength="500"  required></textarea>
		</td>
	</tr>
</table>
</form>
</body>
<script>
	$("#form1").validate();
</script>
</html>