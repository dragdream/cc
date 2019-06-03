
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>

<%@ include file="/header/ztree.jsp"%>
<title>领导日程安排查询</title>
<link rel="stylesheet" href="<%=contextPath%>/common/jquery/ztree/css/demo.css" type="text/css"/>
<style>
</style>
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
	/* 获取部门树 */
	getLeaderPostDept();
}


/**
 * 获取管理范围的部门 ---- 月视图
 * @param deptId
 */
function getLeaderPostDept(deptId){
	var url =  contextPath + "/calendarManage/getLeaderMonthPostDept.action";
	var config = {
				zTreeId:"deptIdZTree",
				requestURL:url,
	           	onClickFunc:onclickUser,
				async:false,
				onAsyncSuccess:setPersonSelect
	};
	zTreeObj = ZTreeTool.config(config);
}

/**
 * 点击人员
 * @param event
 * @param treeId
 * @param treeNode
 */
function onclickUser (event, treeId, treeNode) {
	$("#deptIdName").val(treeNode.name);
	$("#deptId").val(treeNode.id);
	ZTreeTool.hideZtreeMenu();
	
}

/**
 * 选择人员事件
 * @param dateList
 * @param rtMsg
 */
function setPersonSelect(dateList,rtMsg){
	ZTreeObj = $.fn.zTree.getZTreeObj(ZTreeTool.zTreeId);
	var nodeName = ""; 
    ZTreeTool.inputBindZtree(ZTreeTool.zTreeId,'deptId',nodeName);
}


/**
 * 导出
 */
function extportCalendar(){
 	var para =  $("#form1").serialize();
 	//var para =  tools.formToJson($("#form1")) ;
 	window.location.href = "<%=contextPath %>/calendarManage/exportLeaderCalendarToCsv.action?"+para;
}
</script>
</head>
<body class="" topmargin="5" onload="doOnload();">
<table border="0" width="100%" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="Big"><span class="Big3" style="padding-left:5px;"> 领导日程安排查询</span>
    </td>
  </tr>
</table>
<br>
<form action="<%=contextPath%>/calendarManage/leaderQuery.action"  method="post" id="form1" name="form1" onsubmit="return checkForm();">
 <table  class="TableBlock" width="550" align="center">
    <tr>
        <td nowrap class="TableData" width="100"> 日期：</td>
        <td class="TableData">
      	    <input type="text" id="startTime" name="startDate" size="15" maxlength="10" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="BigInput" value="">
       		 至
       	    <input type="text" id="endTime" name="endDate" size="15" maxlength="10" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="BigInput" value="">
        </td>
    </tr>
    
     <tr>
        <td nowrap class="TableData" width="100"> 查询范围：</td>
        <td class="TableData">
     	 	<input id="deptId" name="deptId" type="text" style="display: none;" value='' />
			<ul id="deptIdZTree" class="ztree"style="margin-top: 0; width: 200px; display: none;"></ul>
      	 </td>
    </tr>
    <tr>
      <td nowrap class="TableData"> 状态：</td>
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
  <!--   <tr>
      <td nowrap class="TableData"> 事务类型：</td>
      <td class="TableData">
        <select id="calType" name="calType" class="BigSelect">
          <option value="">所有</option>
          <option value="1">工作事务</option>
          <option value="2">个人事务</option>
        </select>
      </td>
    </tr> -->
    <tr>
      <td nowrap class="TableData"> 事务内容：</td>
      <td class="TableData">
        <input type="text" id="content" name="content" size="33" class="BigInput"/>
      </td>
    </tr>
    <tr align="center" class="TableControl">
      <td colspan="2" nowrap>
        <input type='hidden' id="calType" name="calType" value="1" />
        <input type="button" value="查询" class="btn btn-primary" onclick="query();">&nbsp;&nbsp;
        <input type="button" value="导出/打印" class="btn btn-primary" onclick="extportCalendar();">&nbsp;&nbsp;
        <input type="button" class="btn btn-primary" value="返回" onclick=" history.go(-1);"> 
      </td>
    </tr>
  </table>
</form>
</body>
</html>