<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<title>筛选查询</title>
<script type="text/javascript">

function doInit(){

}

var datagrid;
function getInfoList(){
	  var queryParams=tools.formToJson($("#form1"));

	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/hrFilterController/queryHrFilterList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		queryParams:queryParams,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'hrPoolName',title:'应聘者姓名',width:40},
			{field:'position',title:'应聘岗位',width:40},
			{field:'employeeMajor',title:'	所学专业',width:40},
			{field:'employeePhone',title:'联系电话',width:40},
			{field:'sendPersonName',title:'发起人'},
			{field:'2',title:'操作',width:80,formatter:function(value, rowData, rowIndex){
				var str = "";//"<a href='javascript:void(0)' onclick='showInfoFunc("+rowData.sid+")'>详细信息 </a>";
			
				str += "&nbsp;&nbsp;<a href='#' onclick='toAddOrUpdate("+rowData.sid+")'>修改</a>";
			
				str += "&nbsp;&nbsp;<a href='javascript:void();' onclick='deleteObjFunc("+rowData.sid+")'>删除</a>";
				return str;
			}}
		]]
	});
}




/**
 * 单个删除维护信息
 */
function deleteSingleFunc(sid){
	deleteObjFunc(sid);
}
/**
 * 批量删除
 */
function batchDeleteFunc(){
	var ids = getSelectItem();
	if(ids.length==0){
		$.MsgBox.Alert_auto("至少选择一项");
		return ;
	}
	deleteObjFunc(ids);
}
/**
 * 删除维护信息
 */
function deleteObjFunc(ids){
	$.MsgBox.Confirm ("提示", "确定要删除所选中记录？", function(){
		var url = contextPath + "/hrFilterController/deleteObjById.action";
		var para = {sids:ids};
		var json = tools.requestJsonRs(url,para);
		if(json.rtState){
			$.MsgBox.Alert_auto("刪除成功！",function(){
				datagrid.datagrid('reload');
			});
			
		}
	});
}
/**
 * 获取选中值
 */
function getSelectItem(){
	var selections = $('#datagrid').datagrid('getSelections');
	var ids = "";
	for(var i=0;i<selections.length;i++){
		ids+=selections[i].sid;
		if(i!=selections.length-1){
			ids+=",";
		}
	}
	return ids;
}
//根据条件查询
function doSearch(){
	 $('#searchDiv').toggle();
	 $("#optItem").show();
	 $(".datagrid-view").show();
	
	 getInfoList();
	 
}
//返回
function toReturn(){
	window.location.reload();

}
function doSearchFunc(){
	  datagrid.datagrid("reload"); 
}

/**
 * 编辑信息
 */
function toAddOrUpdate(sid){
	var  url = contextPath + "/system/core/base/hr/filter/addOrUpdate.jsp?sid=" + sid;
	window.location.href = url;
}

</script>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
	<table id="datagrid" fit="true" ></table>
	<div id="toolbar">
		
		<form id="form1" name="form1" method="post">
			<div style="margin-top:10px;" id="searchDiv">
				<table class="TableBlock_page"  style="text-align: left;" align="center" >
					 <tr>
					    <td nowrap class="TableData" width="15%" style="text-indent: 10px">应聘者姓名：</td>
					    <td class="TableData" >
					      <INPUT type="text" name="hrPoolName" id="hrPoolName" class="BigInput" size="15" value="">
					    	   </td>
					  </tr>
					  <tr>
					   <td nowrap class="TableData" style="text-indent: 10px">计划名称：</td>
					    <td class="TableData">
					          <INPUT type="text"name="planName" id="planName" class="BigInput easyui-validatebox BigStatic" required="true" size="15" readonly="readonly">
						      <INPUT type="hidden" name="planId" id="planId" value="">
						      <a href="javascript:void(0);" class="orgAdd" onClick="getRecruitPlan()">选择</a>
					    </td>  
					  </tr>
					  <tr>
					    <td nowrap class="TableData" style="text-indent: 10px">应聘岗位：</td>
					    <td class="TableData" >
					      <INPUT type="text" name="position" id="position" class=BigInput size="15" value="" maxlength="100">
					    </td> 
					  </tr>
					  <tr>
					    <td nowrap class="TableData"  style="text-indent: 10px">所学专业：</td>
					    <td class="TableData" >
					      <INPUT type="text"name="employeeMajor" id="employeeMajor" maxlength="100" class=BigInput size="15" value="">
					    </td>    
					  </tr>
					  <tr>
					    <td nowrap class="TableData" style="text-indent: 10px">联系电话：</td>
					    <td class="TableData">
					      <INPUT type="text"name="employeePhone" id="employeePhone" class=BigInput maxlength="100" size="15" value="">
					    </td>
					   </tr>
					   <tr>
					    <td nowrap class="TableData" style="text-indent: 10px">发起人：</td>
					    <td class="TableData" >
					     	<INPUT type="hidden" name="sendPersonId" id="sendPersonId" size="15" class="BigStatic BigInput "   readonly value="">
						    <INPUT type="text" name="sendPersonName" id="sendPersonName" class="BigStatic BigInput easyui-validatebox" value=""   required="true">
						    <a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['sendPersonId', 'sendPersonName']);">添加</a>
							<a href="javascript:void(0);" class="orgClear" onClick="$('#sendPersonId').val('');$('#sendPersonName').val('');">清空</a>
					    </td>     
					  </tr>
					  
					    <tr>
					    <td nowrap class="TableData" style="text-indent: 10px">筛选状态：</td>
					    <td class="TableData">
					    	<select class="BigSelect" name="filterState">
					    		<option value="">全部</option>
					    		<option value="0">待筛选</option>
					    		<option value="1">通过</option>
					    		<option value="2">未通过</option>
					    	</select>
					    </td>
					  </tr>
					<tr align="center">
						<td class="TableData" colspan="2">
						   <div align="right">
						      <input type="button" class="btn-win-white" onclick="doSearch();" value="查询">
							&nbsp;&nbsp;<input type="reset" value="重置" class="btn-del-red" >
						   </div>
							
						</td>
						
					</tr>
					
				</table>
			</div>
		
		
		</form>
		
		<div style="text-align:left;display:none;margin: 10px;" id="optItem">
			<!-- <button class="btn btn-primary btn-xs" onclick="toAddOrUpdate(0)">添加人才库</button>&nbsp;&nbsp; -->
			<button class="btn-del-red" onclick="batchDeleteFunc()">批量删除</button>&nbsp;&nbsp;
			<button class="btn-win-white" onclick="toReturn()">返回</button>&nbsp;&nbsp;
		</div>
	</div>
</body>
</html>