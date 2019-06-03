<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" charset="UTF-8">

var datagrid ;
function doInit(){
	query();
}

//回填数据OpenRelateData
function use(runId,runName){
	//查看流程详情
//     var url=contextPath+"/workflow/view.action?runId="+runId;
	var html="<a href=\"javascript:void(0)\" onclick=\"OpenRelateData("+runId+",1)\">"+runName+"</a>";
	xparent.CK_EDITOR_OBJ.execCommand('insertHtml', html);
	window.close();
}

//查看流程详情
function view(runId){
	 var url=contextPath+"/workflow/view.action?runId="+runId;
	 openFullWindow(url);
}



//查询
function query(){
	var params=  tools.formToJson($("#form")) ;
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/workflow/getMyBeginRunList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		queryParams:params,
		columns:[[
			{field:'runId',title:'流水号',width:50},
			{field:'runName',title:'工作名称',width:150,formatter:function(value,rowData,rowIndex){
				var runId=rowData.runId;
				var runName=rowData.runName;
				return "<a href=\"javascript:void(0)\" onclick=\"view("+runId+")\">"+runName+"</a>";
			}},
			{field:'status',title:'状态',width:150,formatter:function(value,rowData,rowIndex){
				var status=rowData.status;
				var html="";
				if(status==0){//进行中
					html="<span style='color:green;'>进行中</span>";
				}else{ //已结束
					html="<span style='color:red;'>已结束</span>";
				}
				return  html;
			}},
			{field:'beginTimeStr',title:'发起时间',width:150},
			{field:'oper_',title:'操作',width:100,formatter:function(value,rowData,rowIndex){
				return "<a href=\"#\" onclick=\"use("+rowData.runId+",'"+rowData.runName+"')\">回填</a>";
			}},
		]]
	});

	
}
</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
<div id="toolbar">
  <form id="form" style="padding:10px">
		<table class="none_table" style="width:100%">
			<tr>				
				<td class="TableData" nowrap>流水号：</td>
				<td class="TableData">
				    <input type="text" name="runId" class="BigInput" style="height:20px;width: 60px;"/>
				</td>
				<td class="TableData" nowrap>工作名称：</td>
				<td class="TableData">
					<input type="text" name="runName"  class="BigInput" style="height:20px;"/>
				</td>
				<td class="TableData" nowrap>流程状态：</td>
				<td class="TableData">
					<select name="status" style="height:20px" class="BigSelect">
					<option value="0">所有状态</option>
					<option value="1">执行中</option>
					<option value="2">已结束</option>
				</select>
				</td>
				<td class="TableData" nowrap>发起时间：</td>
				<td colspan="2">
					<input  type="text"  name="beginTime" id="beginTime" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate BigInput" style="width:100px;height: 20px"/>
					&nbsp;至&nbsp;
					<input type="text"   name="endTime" id="endTime" readonly onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\',{d:0});}',dateFmt:'yyyy-MM-dd'})" class="Wdate BigInput"  style="width:100px;height: 20px"/>
				</td>
				<td colspan="4">
					<button type="button"  onclick="query()" class="btn-win-white fr">查询</button>
				</td>
			</tr>
		</table>
</form>
</div>
<table id="datagrid" fit="true"></table>
</body>
</html>