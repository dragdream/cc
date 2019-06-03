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
<title>相关流程</title>
<script type="text/javascript">
var runId=<%=runId %>;
var datagrid ;
//初始化
function doInit(){
	doSearch();
	
	$(document).click(function(){
		$("#flowDiv").hide();
	});
}

function showFlowTree(e){
	$("#flowDiv").show();
	e.stopPropagation();
}

function doSearch(){
	var param=tools.formToJson($("#form1"));
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/TeeFlowRelatedResourceController/relatedFlowRunList.action",
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
			{field:'runId',checkbox:true,title:'ID',width:200},
			{field:'run_ID',title:'流水号',width:50,formatter:function(value,rowData,rowIndex){
			    return rowData.runId;
			}},
			{field:'runName',title:'流程标题',width:120},
			{field:'beginUser',title:'发起人',width:120},
			{field:'beginTime',title:'创建时间',width:200},
		]]
	});
}



//点击确定
function doSave(){
	var params = {};
	var selections = $("#datagrid").datagrid("getSelections");
	if(selections.length==0){
        $.MsgBox.Alert_auto("请勾选相关流程！");		
	}else{
		var runIds = [];
		for(var i=0;i<selections.length;i++){
			runIds.push(selections[i].runId);
		}
		params["relatedIds"]=runIds.join(",");
		params["type"]=1;
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
      	 <td>所属流程：</td>
         <td>
         	<input type="hidden" name="flowId" id="flowId"  />
         	<input type="text" style="height:23px" name="flowName" id="flowName" readonly onclick="showFlowTree(event)"/>
         </td>
         <td>流水号：</td>
         <td>&nbsp;&nbsp;&nbsp;<input type="text" name="runId" id="runId" style="width:100px;height:23px" /></td>
         <td>流程标题：</td>
         <td><input type="text" name="runName" id="runName" style="height:23px" /></td>
      </tr>
      <tr>
         <td>创建人：</td>
         <td> 
            <input name="beginUserId" id="beginUserId" type="hidden"/>
			 <input name="beginUserName" id="beginUserName" type="text" style="height:23px;"/>
			<span class='addSpan'>
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectSingleUser(['beginUserId','beginUserName'],'14')" value="选择"/>
			   &nbsp;&nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('beginUserId','beginUserName')" value="清空"/>
			</span>
         </td>
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

<div id="flowDiv" style="border:1px solid gray;background:#f0f0f0;position:absolute;top:37px;height:250px;width:220px;left:68px;display: none">
<iframe style="width:100%;height:100%" src="flowTree.jsp"></iframe>
</div>
</body>
</html>