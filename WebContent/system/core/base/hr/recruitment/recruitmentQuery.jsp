<%@page import="com.tianee.oa.core.base.hr.TeeHrCodeManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/js/hr.js"></script>
<title>招聘录用查询管理</title>
<script type="text/javascript">

function doInit(){
	getDeptParent();
	getHrCode();
	//getInfoList();
}

var datagrid;
function getInfoList(){
	var queryParams=tools.formToJson($("#form1"));
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/recruitmentController/getRecruitList.action",
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
			{field:'sid',checkbox:true,title:'ID',width:10},
			{field:'planName',title:'计划编号',width:50},
			{field:'position',title:'招聘岗位',width:80},
			{field:'hrPoolName',title:'应聘人姓名',width:50},
			{field:'recordPersonName',title:'录用负责人',width:50},
			{field:'recordTimeStr',title:'录入日期',width:50,formatter:function(value, rowData, rowIndex){
				if(value){
					value = value.substring(0,10);
				}
				return value;
			}},
			{field:'2',title:'操作',width:100,formatter:function(value, rowData, rowIndex){
				var str = "<a href='javascript:void(0)' onclick='showInfoFunc("+rowData.sid+")'>详细信息 </a>&nbsp;&nbsp;";
				//str += "&nbsp;&nbsp;<a href='javascript:void();' onclick='editFunc("+rowData.sid+")'>建人事档案</a>";
				str += "&nbsp;&nbsp;<a href='javascript:void();' onclick='toAddOrUpdate("+rowData.sid+")'>编辑</a>";
				str += "&nbsp;&nbsp;<a href='javascript:void();' onclick='deleteSingleFunc("+rowData.sid+")'>删除</a>";
				return str;
			}}
		]]
	});
}






/**
 * 详情信息
 */
function showInfoFunc(sid){
  var title = "招聘录用详细信息";
  var url = contextPath + "/system/core/base/hr/recruitment/recruitmentDetail.jsp?sid=" + sid;
  bsWindow(url ,title,{width:"850",height:"320",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		if(v=="关闭"){
			return true;
		}
	}});
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
		$.MsgBox.Alert_auto("请至少选择一项！");
		return ;
	}
	deleteObjFunc(ids);
}
/**
 * 删除维护信息
 */
function deleteObjFunc(ids){
	
	 $.MsgBox.Confirm ("提示", "确定要删除所选中记录？", function(){
		 var url = contextPath + "/recruitmentController/deleteObjById.action";
			var para = {sids:ids};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！",function(){
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

/**
 * 新建编辑信息
 */
function toAddOrUpdate(sid){
	var  url = contextPath + "/system/core/base/hr/recruitment/addOrUpdate.jsp?sid=" + sid;
	window.location.href = url;
}



/**
 * 获取部门
 */
function getDeptParent(){
	var url =  "<%=contextPath %>/deptManager/getDeptTreeAll.action";
		var config = {
				zTreeId:"deptIdZTree",
				requestURL:url,
	           	onClickFunc:onclickDept,
				async:false,
				onAsyncSuccess:setDeptParentSelct
			};
		zTreeObj = ZTreeTool.config(config);
		//setTimeout('setDeptParentSelct()',500);
} 
/**
 * 初始化后选中节点,上级部门
 */
function setDeptParentSelct(){
	ZTreeObj = $.fn.zTree.getZTreeObj(ZTreeTool.zTreeId);
    if(ZTreeObj == null){
    	setTimeout('setDeptParentSelct()',500);
    }else{
    	ZTreeObj.expandAll(true);
    	 var node = ZTreeObj.getNodeByParam("id",$("#deptId").val(),null);
    	    if(node){
    	    	ZTreeObj.selectNode(node);
    	  }
    }  
    ZTreeTool.inputBindZtree(ZTreeTool.zTreeId,'deptId','');
}

//点击树执行事件
function onclickDept (event, treeId, treeNode) {
	$("#deptIdName").val(treeNode.name);
	$("#deptId").val(treeNode.id);
	ZTreeTool.hideZtreeMenu();
}


/**
 * 获取所有代码
 */
function getHrCode(){
	//员工类型 
	getHrCodeByParentCodeNo("STAFF_OCCUPATION" , "employeeType");
	//职称
	getHrCodeByParentCodeNo("PRESENT_POSITION" , "presentPosition");
}

function toReturn(){
	window.location.reload();
}



//批量导出
function  batchExport(){
 	var para =  tools.formToJson($("#form1")) ;
 	var param=tools.jsonObj2String(para);
 	$("#exportIframe").attr("src",contextPath+"/recruitmentController/exportExcel.action?param="+param); 
	
}
</script>
</head>
<body onload="doInit()" style="overflow:;font-size:12px">
	<table id="datagrid" fit="true" ></table>
	<div id="toolbar">
		<form id="form1" name="form1" method="post">
		<div style="margin-top:10px;" id="searchDiv">
			<table align="center" width="100%" class="TableBlock_page" >
				<tr>
					<td nowrap class="TableData"  width="15%;" style="text-indent: 15px">计划名称：</td>
					<td class="TableData" width="35%;" >
						<input type="text" name="planName" id="planName" size="" class="BigInput  easyui-validatebox"  size="15" maxlength="100">
					</td>
					<td nowrap class="TableData"  width="15%;" >应聘者姓名：</td>
					<td class="TableData" width="35%;" >
						<input type="text" name="hrPoolName" id="hrPoolName" class="BigInput  easyui-validatebox"  size="15"  maxlength="100">
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData"  width="15%;"  style="text-indent: 15px">招聘岗位：</td>
					<td class="TableData" width="35%;" >
						<input type="text" name="position" id="position" size="" class="BigInput  easyui-validatebox" maxlength="100">
					</td>
					<td nowrap class="TableData"  width="15%;" >OA中用户名 ：</td>
					<td class="TableData" width="60%;" >
						<input type="text" name="oaName" id="oaName" size="" class="BigInput  easyui-validatebox" maxlength="100">
					</td>
				</tr>
				<tr>
				
				    <td nowrap class="TableData" width="" style="text-indent: 15px">招聘部门：</td>
					<td class="TableData" >
						<input name="deptId" id="deptId" type="hidden"/>
						<input type="text" name="deptName" id="deptName" readonly="readonly"/>
						<span class="addSpan">
						   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectSingleDept(['deptId','deptName'],'14')" value="选择"/>
						   &nbsp;&nbsp;
						   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('deptId','deptName')" value="清空"/>
						</span>
					</td>
					<td nowrap class="TableData"  width="">录用负责人：</td>
					<td class="TableData" width="">
						<input type=hidden name="recordPersonId" id="recordPersonId" value="">
						<input type="text" name="recordPersonName" id="recordPersonName" class="BigInput" size="10"  readonly value=""></input>
						<span class="addSpan">
						   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onClick="selectSingleUser(['recordPersonId', 'recordPersonName']);" value="选择"/>
						   &nbsp;&nbsp;
						   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onClick="$('#recordPersonId').val('');$('#recordPersonName').val('');" value="清空"/>
						</span>
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData" width="" style="text-indent: 15px">录入日期：</td>
					<td class="TableData"  colspan="3">
						<input type="text" style="width: 143px" name="recordTimeStrMin" id="recordTimeStrMin" size="" class="Wdate BigInput" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'recordTimeStrMax\',{d:0});}',dateFmt:'yyyy-MM-dd'})"  value="">
						至
						<input type="text" style="width: 143px" name="recordTimeStrMax" id="recordTimeStrMax" size="" class="Wdate BigInput" onClick="WdatePicker({minDate:'#F{$dp.$D(\'recordTimeStrMin\',{d:0});}',dateFmt:'yyyy-MM-dd'})"  value="">
					</td>
				</tr>

				<tr>
					<td nowrap class="TableData"  width="15%;" style="text-indent: 15px">员工类型：</td>
					<td class="TableData" width="35%;" >
					<select name="employeeType" id="employeeType"   class="BigSelect"  title="<%=TeeHrCodeManager.hrCodeSettingPath%>">
						<option value="">请选择</option>
					</select
					</td>
					<td nowrap class="TableData"  width="15%;" >行政等级 ：</td>
					<td class="TableData" width="60%;" >
						<input type="text" name="administrationLevel" id="administrationLevel" size="" class="BigInput  easyui-validatebox"  value="">
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData"  width="15%;" style="text-indent: 15px">职务：</td>
					<td class="TableData" width="35%;" >
						<input type="text" name="jobPosition" id="jobPosition" size="" class="BigInput  easyui-validatebox"  value="">
					</td>
					<td nowrap class="TableData"  width="15%;" >职称：</td>
					<td class="TableData" width="60%;" >
					<select name="presentPosition" id="presentPosition"   class="BigSelect easyui-validatebox" title="<%=TeeHrCodeManager.hrCodeSettingPath%>">
						<option value="">请选择</option>
					</select
					</td>
				</tr>
				<tr>
					<td nowrap class="TableData"  width="" style="text-indent: 15px">备　　注：</td>
					<td class="TableData" width="" colspan="3">
						<textarea rows="3" cols="60" id="remark" name="remark" maxlength="200" class="BigTextarea"></textarea>
					</td>
				</tr>
				<tr>
				<tr align="center">
					<td nowrap class="TableData" colspan=4>
						<div align="right">
						   <input type="button"  value="查询" class="btn-win-white" onclick="doSearch();"/>&nbsp;&nbsp;
						   <input type="reset" value="重置" class="btn-del-red" >
						</div>	
					</td>
				</tr>
			</table>
		</div>
		</form>
		<div style="text-align:left;display:none;margin:10px;" id="optItem">
		    <button class="btn-win-white" onclick="batchExport()">批量导出</button>&nbsp;&nbsp;
			<button class="btn-del-red " onclick="batchDeleteFunc()">批量删除</button>&nbsp;&nbsp;
			<input type="button"  value="返回" class="btn-win-white" onclick="toReturn();"/>&nbsp;&nbsp;
		</div>

	</div>
	
	<iframe id="exportIframe" style="display:none"></iframe>
</body>
</html>