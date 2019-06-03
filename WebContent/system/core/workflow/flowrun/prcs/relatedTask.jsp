<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
  int runId=TeeStringUtil.getInteger(request.getParameter("runId"),0);
%>
<head>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>相关任务</title>
<script type="text/javascript">
var runId=<%=runId %>;
var datagrid ;
//初始化
function doInit(){
	doSearch();
}



function doSearch(){
	var param=tools.formToJson($("#form1"));
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/TeeFlowRelatedResourceController/relatedTaskList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		queryParams:param,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:200},
			{field:'taskTitle',title:'任务名称',width:120},
			{field:'createUserName',title:'布置人',width:120},
			{field:'createTime',title:'创建时间',width:200},
		]]
	});
}



//点击确定
function doSave(){
	var params = {};
	var selections = $("#datagrid").datagrid("getSelections");
	if(selections.length==0){
        $.MsgBox.Alert_auto("请勾选相关任务！");		
	}else{
		var sids = [];
		for(var i=0;i<selections.length;i++){
			sids.push(selections[i].sid);
		}
		params["relatedIds"]=sids.join(",");
		params["type"]=2;
		params["runId"]=runId;
		var url=contextPath+"/TeeFlowRelatedResourceController/add.action";
		var json=tools.requestJsonRs(url,params);
		return json;
		
	}
	
}
</script>
</head>
<body onload="doInit();">
<div id="toolbar" class = "topbar clearfix">
  <form id="form1">
   <table style="width:100%">
      <tr>
      	 <td>任务名称：</td>
         <td>
         	&nbsp;&nbsp;&nbsp;<input type="text" style="height:23px;width: 222px" name="taskTitle" id="taskTitle" />
         </td>
         <td>布置人：</td>
         <td> 
            <input name="createUserId" id="createUserId" type="hidden"/>
			 <input name="createUserName" id="createUserName" type="text" style="height:23px;"/>
			<span class='addSpan'>
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectSingleUser(['createUserId','createUserName'],'14')" value="选择"/>
			   &nbsp;&nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('createUserId','createUserName')" value="清空"/>
			</span>
         </td>
      </tr>
      <tr>
         <td>创建时间：</td>
         <td colspan="2">
         	从<input type="text" class="Wdate" name="start1" id="start1" style="height:23px;width: 100px" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'start2\')}'})" readonly="readonly"/>
         	至
         	<input type="text" class="Wdate" name="start2" id="start2" style="height:23px;width: 100px"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'start1\')}'})" readonly="readonly"/>
         </td>
         <td>
         	<button type="button" class="btn-win-white" onclick="doSearch();">查询</button>
         </td>
      </tr>
   </table>
  </form>
</div>
<table id="datagrid" fit="true"></table>

</body>
</html>