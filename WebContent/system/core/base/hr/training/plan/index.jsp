<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/training/js/training.js"></script>

<title>培训计划管理</title>
<script type="text/javascript">

function doInit(){
	getInfoList();
}

var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/trainingPlanController/getRecruitList.action",
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:true,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'planNo',title:'培训计划编号',width:50},
			{field:'planName',title:'培训计划名称',width:100,
				formatter:function(value,rowData,rowIndex){
					return "<a href='javascript:void(0)' onclick='showInfoFunc("+rowData.sid+")'>"+ rowData.planName +"</a>";
			}},
			{field:'planChannelName',title:'培训渠道',width:40,formatter:function(value,rowData,rowIndex){
				return value;
			}},
			{field:'courseTypesName',title:'培训形式',width:50,formatter:function(value,rowData,rowIndex){
				return value;
			}},
			
			
			{field:'approvePersonName',title:'审批人',width:40},
			{field:'createTimeStr',title:'创建日期',width:50},
			{field:'planStatus',title:'计划状态',width:40,formatter:function(value, rowData, rowIndex){
				return planStatusFunc(value);
			}},
			{field:'2',title:'操作',width:80,formatter:function(value, rowData, rowIndex){
				var planStatus = rowData.planStatus
				var str = "";//"<a href='javascript:void(0)' onclick='showInfoFunc("+rowData.sid+")'>详细信息 </a>";
				if(planStatus !=1){
					str += "&nbsp;&nbsp;<a href='javascript:void(0)' onclick='toAddOrUpdate("+rowData.sid+")'>修改</a>";
				}
				str += "&nbsp;&nbsp;<a href='javascript:void(0)' onclick='deleteSingleFunc("+rowData.sid+")'>删除</a>";
				return str;
			}}
		]]
	});
}


/**
 * 新建编辑信息
 */
function toAddOrUpdate(sid){
	var  url = contextPath + "/system/core/base/hr/training/plan/editTrainingPlan.jsp?sid=" + sid;
	window.location.href = url;
}


/**
 * 添加维护信息
 */
function addInfo(){
  var title = "新建培训计划";
  var url = contextPath + "/system/core/base/hr/training/plan/editTrainingPlan.jsp";
  bsWindow(url ,title,{width:"900",height:"330",buttons:
		[
		 {name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			cw.doSaveOrUpdate(function(){
			  $.jBox.tip("保存成功！", "info", {timeout: 1800});
			  datagrid.datagrid('reload');
			  BSWINDOW.modal("hide");
			});
		}else if(v=="关闭"){
			return true;
		}
	}});
}


/**
 * 编辑信息
 */
function editFunc(sid){
  var title = "编辑培训计划";
  var url = contextPath + "/system/core/base/hr/training/plan/editTrainingPlan.jsp?sid=" + sid;
  bsWindow(url ,title,{width:"900",height:"360",buttons:
		[
		 {name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			cw.doSaveOrUpdate(function(){
				$.jBox.tip("保存成功！", "info", {timeout: 1800});
				datagrid.datagrid('reload');
				BSWINDOW.modal("hide");
			});
		}else if(v=="关闭"){
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
		alert("至少选择一项");
		return ;
	}
	deleteObjFunc(ids);
}
/**
 * 删除维护信息
 */
function deleteObjFunc(ids){
  $.jBox.confirm("确定要删除所选中记录？","确认",function(v){
		if(v=="ok"){
			var url = contextPath + "/trainingPlanController/deleteObjById.action";
			var para = {sids:ids};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$.jBox.tip("删除成功！", "info", {timeout: 1800});
				datagrid.datagrid('reload');
			}
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
	var queryParams=tools.formToJson($("#form1"));
	datagrid.datagrid('options').queryParams=queryParams; 
	datagrid.datagrid("reload");
	$('#searchDiv').modal('hide');
}



</script>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px">
<table id="datagrid" fit="true" ></table>
	<div id="toolbar">
		<div class="base_layout_top" style="position:static">
			<table width="100%">
				<tr>
					<td>
						<span class="easyui_h1">培训计划管理</span>
					</td>
					<td align=right>
						<input type="button" value="添加培训计划" onclick="toAddOrUpdate();" class="btn btn-info"/>&nbsp;
						<input type="button" value="批量删除" onclick="batchDeleteFunc();" class="btn btn-danger"/>&nbsp;
						<button type="button" onclick="" class="btn btn-success" data-toggle="modal" data-target="#searchDiv"><i class="glyphicon glyphicon-search"></i>&nbsp;高级检索</button>
					</td>
				</tr>
			</table>
		</div>
		<br>
		
		<form id="form1" name="form1" method="post">
			<div class="modal fade" id="searchDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title" id="myModalLabel">查询条件</h4>
						</div>
						<div class="modal-body">
							<table class="TableBlock"  style="text-align: left;" align="center" >
								<tr>
									<td class="TableData">计划编号：</td>
									<td class="TableData">
										<input class="BigInput" type="text" id = "planNo" name='planNo' size="20"/>
									</td>
									<td class="TableData">名称：</td>
									<td class="TableData">
										<input class="BigInput" type="text" id = "planName" name='planName' size="20"/>
									</td>
								</tr>
								<tr>
									<td class="TableData">计划状态：</td>
									<td class="TableData">
										<select id="planStatus" name="planStatus" class="BigSelect">
											<option value="">请选择</option>
											<option value="0">待审批</option>
											<option value="1">已批准</option>
											<option value="2">未批准</option>
										</select>
									</td>
									<td class="TableData">审批人：</td>
									<td class="TableData">
										<input type=hidden name="approvePersonId" id="approvePersonId" value="">
										<input  name="approvePersonName" id="approvePersonName" class="BigStatic BigInput" size="10"  readonly value=""></input>
										<span>
											<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['approvePersonId', 'approvePersonName']);">添加</a>
											<a href="javascript:void(0);" class="orgClear" onClick="$('#approvePersonId').val('');$('#approvePersonName').val('');">清空</a>
										</span>
									</td>
								</tr>
								<tr>
									<td class="TableData">培训说明：</td>
									<td class="TableData">
										<input class="BigInput" type="text" id = "description" name='description' size="20"/>
									</td>
									<td class="TableData">培训备注：</td>
									<td class="TableData">
										<input class="BigInput" type="text" id = "remark" name='remark' size="20"/>
									</td>
								</tr>
								<tr>
									<td class="TableData">培训开始日期：</td>
									<td colspan="3" class="TableData">
										<input class="BigInput" type="text" id = "startDateStrMin" name='startDateStrMin' size="20" class="BigInput  easyui-validatebox" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'startDateStrMax\',{d:0});}',dateFmt:'yyyy-MM-dd'})"  value="" />
										至
										<input class="BigInput" type="text" id = "startDateStrMax" name='startDateStrMax' size="20" class="BigInput  easyui-validatebox" onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDateStrMin\',{d:0});}',dateFmt:'yyyy-MM-dd'})"  value=""/>
									</td>
								</tr>
								<tr>
									<td class="TableData">培训结束日期：</td>
									<td colspan="3" class="TableData">
										<input class="BigInput" type="text" id = "endDateStrMin" name='endDateStrMin' size="20" class="BigInput  easyui-validatebox" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endDateStrMax\',{d:0});}',dateFmt:'yyyy-MM-dd'})"  value="" />
										至
										<input class="BigInput" type="text" id = "endDateStrMax" name='endDateStrMax' size="20" class="BigInput  easyui-validatebox" onClick="WdatePicker({minDate:'#F{$dp.$D(\'endDateStrMin\',{d:0});}',dateFmt:'yyyy-MM-dd'})"  value=""/>
									</td>
								</tr>
								<tr align="center">
									<td class="TableData" colspan="4">
										<input type="button" class="btn btn-primary" onclick="doSearch();" value="查询">
										&nbsp;&nbsp;<input type="reset" value="重置" class="btn btn-primary" >
									</td>
									
								</tr>
								
							</table>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>