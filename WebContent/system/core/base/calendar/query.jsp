
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<title>日程安排</title>
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
function checkForm(){
  var sendTimeMin = document.getElementById("startTime");
  var sendTimeMax = document.getElementById("endTime");
  if(sendTimeMin.value!=""&&sendTimeMax.value!=""){
    if(compareDate(sendTimeMin , sendTimeMax)){
      alert("起始日期不能大于结束日期!");
      sendTimeMax.focus();
      sendTimeMax.select();
      return false;
    }  
  } 
  return true;
}
function compareDate(beginDate , endDate) {
	d1Arr=beginDate.value.split('-');
    d2Arr=endDate.value.split('-');
    v1=new Date(d1Arr[0],d1Arr[1],d1Arr[2]);
    v2=new Date(d2Arr[0],d2Arr[1],d2Arr[2]);
    return v1>v2;
}
function cal_export(){
  if(checkForm()){
    $("type").value="1";
    document.form1.target='_blank';
    document.form1.submit();
  }
  
}
function query(){
  if(checkForm()){
    $("type").value="";
    document.form1.target='_self';
    document.form1.submit();
  }
}
function doOnload(){

}
</script>
</head>
<body class="" topmargin="5" onload="doOnload();">
<!-- <table border="0" style="width:1680px;" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="Big"><span class="topbar clearfix" style="padding-left:20px;font-size:14px; font-weight:bold;"> 日程安排查询</span>
    </td>
  </tr>
</table> -->
<fieldset>
<legend style="font-size:14px; font-weight:bold; width:1680px;" class = "topbar clearfix">日程安排查询</legend>
<br>
<form action="<%=contextPath%>/calendarManage/queryCal.action"  method="post" name="form1" onsubmit="return checkForm();">
 <table  class="TableBlock" style="font-family:微软雅黑 width:100%;background-color:#fff;" align="center">
    <tr>
      <td nowrap class="TableData" style="width:100px;text-indent: 15px;"> 日期：</td>
      <td class="TableData" style="width:1680px;">
      	 <input type="text" id="startTime" name="startDate" size="15" maxlength="10" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="BigInput" value="">
       		至
       	 <input type="text" id="endTime" name="endDate" size="15" maxlength="10" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="BigInput" value="">
       </td>
    </tr>
    
    <tr>
      <td nowrap class="TableData" style="width:100px;text-indent: 15px;"> 状态：</td>
      <td class="TableData">
        <select id="overStatus" name="overStatus" class="BigSelect">
          <option value="0">所有</option>
          <option value="1">未开始</option>
          <option value="2">进行中</option>
          <option value="3">已超时</option>
          <option value="4">已完成</option>
        </select>
      </td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="width:100px;text-indent: 15px;"> 事务类型：</td>
      <td class="TableData">
        <select id="calType" name="calType" class="BigSelect">
          <option value="">所有</option>
          <option value="1">工作事务</option>
          <option value="2">个人事务</option>
        </select>
      </td>
    </tr>
    <tr>
      <td nowrap class="TableData" style="width:100px;text-indent: 15px;"> 事务内容：</td>
      <td class="TableData">
        <!--<input id="content" name="content" size="33" class="BigInput">-->
		<textarea id="content" name="content" cols="42" rows="3"
			class="BigInput"></textarea>
      </td>
    </tr>
    <tr align="center" class="TableControl">
      <td colspan="2" nowrap>
	    <div class="" align="left"
			style="text-indent: 305px; margin-top: 5px;">
        <input type="button" value="查询" class="btn-win-white" onclick="query();">&nbsp;&nbsp;
     <!--    <input type="button" value="导出/打印" class="btn btn-primary" onclick="cal_export();">&nbsp;&nbsp; -->
        <input type="button" class="btn-win-white" value="返回" onclick=" history.go(-1);"> 
        </div>
	  </td>
    </tr>
  </table>
</form>
</fieldset>
</body>
</html>